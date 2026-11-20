from pathlib import Path
import json
import re
from pypdf import PdfReader
from PIL import Image, ImageDraw

root = Path(__file__).resolve().parents[2]
out = root / 'research/catalogue'
assets = out / 'images'
assets.mkdir(exist_ok=True)
records = []
for path in (root / 'catalogue').glob('*.pdf'):
    reader = PdfReader(path)
    if 'Catalogue' not in path.name:
        continue
    for index, page in enumerate(reader.pages):
        lines = (page.extract_text() or '').splitlines()
        code_at = next((i for i, line in enumerate(lines) if re.fullmatch(r'AMF-[A-Z0-9]+-\d+', line.strip())), None)
        if index < 3 or code_at is None:
            continue
        code = lines[code_at].strip()
        record = {'code': code, 'name': lines[code_at + 1].strip(), 'category': lines[code_at - 1].strip(), 'source': path.name, 'pdf_page': index + 1, 'printed_page': index, 'illustrative': True, 'images': []}
        # Keep original embedded image bytes; avoid enlarging catalogue imagery.
        for n, img in enumerate(page.images):
            dest = assets / (code + '-' + str(n + 1) + Path(img.name).suffix)
            dest.write_bytes(img.data)
            with Image.open(dest) as im:
                record['images'].append({'path': str(dest.relative_to(root)).replace('\\', '/'), 'width': im.width, 'height': im.height})
        records.append(record)
(out / 'designs.json').write_text(json.dumps(records, indent=2), encoding='utf-8')
rows = ['# Catalogue design inventory', '', 'Images are illustrative references, not completed client installations. PDF page is one-based; printed folio is one lower.', '', '| Code | Design | Category | PDF page |', '|---|---|---|---|']
for r in records:
    rows.append(f"| {r['code']} | {r['name']} | {r['category']} | {r['pdf_page']} |")
(out / 'DESIGNS.md').write_text('\n'.join(rows)+'\n', encoding='utf-8')
for prefix in ['relief', 'decorative', 'card', 'poster']:
    paths = sorted((root / 'tmp/pdfs').glob(prefix+'-*.png'))
    if not paths:
        continue
    sheet = Image.new('RGB', (1100, ((len(paths)+4)//5)*340), '#ddd8ce')
    draw = ImageDraw.Draw(sheet)
    for n, path in enumerate(paths):
        with Image.open(path) as im:
            im.thumbnail((212, 310))
            x, y = (n % 5)*220, (n//5)*340
            sheet.paste(im, (x, y))
            draw.text((x+4, y+313), path.stem, fill='black')
    sheet.save(out / (prefix+'-contact-sheet.jpg'))
print(json.dumps({'designs': len(records), 'images': sum(len(r['images']) for r in records), 'dimensions': sorted(set((i['width'], i['height']) for r in records for i in r['images']))}))
