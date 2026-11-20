from pathlib import Path
import json
root=Path('public/research/catalogue/images').resolve()
needed={Path(r['image']).name for r in json.loads(Path('app/gallery/designs.json').read_text(encoding='utf-8'))}
assert root==Path.cwd().resolve()/'public/research/catalogue/images'
removed=0
for p in root.iterdir():
 if p.is_file() and p.name not in needed:
  assert p.resolve().parent==root
  p.unlink();removed+=1
print('Removed unused generated public image copies:',removed,'; original research assets retained.')
