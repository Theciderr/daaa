from pathlib import Path
import json,re
root=Path(__file__).resolve().parents[1]
records=json.loads((root/'research/catalogue/designs.json').read_text())
out=[]
for r in records:
 text=(root/'research/catalogue'/r['source'].replace('.pdf','.txt')).read_text(encoding='utf-8')
 page=text.split('PAGE '+str(r['pdf_page'])+'\n',1)[1].split('\nPAGE ',1)[0]
 apps=page.split('COMMON APPLICATIONS\n',1)[1].split('FINISH OPTIONS',1)[0].strip()
 lines=apps.splitlines();uses=lines[:4]
 description=' '.join(lines[4:])
 offer='Jali & partitions' if not r['code'].startswith('AMF-3DR') else 'Sculpted wall art' if r['category']=='SCULPTED WALL ART' else 'Carved doors' if 'door' in apps.lower() else 'Relief wall panels'
 image=next(i for i in r['images'] if i['width']>1300 and i['height']>1400)
 materials=page.split('AVAILABLE MATERIALS\n',1)[1].split('COMMON APPLICATIONS',1)[0].strip()
 out.append(dict(code=r['code'],name=r['name'].title(),family=r['category'].title().replace(' Series',''),offer=offer,image='/'+image['path'],width=image['width'],height=image['height'],description=description,applications=uses,materials=materials,source=r['source'],pdfPage=r['pdf_page'],printedPage=r['printed_page']))
(root/'app/gallery').mkdir(exist_ok=True)
(root/'app/gallery/designs.json').write_text(json.dumps(out,indent=2),encoding='utf-8')
# Share the existing approved footer without changing its structure.
p=root/'app/page.tsx';s=p.read_text(encoding='utf-8');start=s.index('<footer ');end=s.index('</footer>',start)+len('</footer>');footer=s[start:end]
(root/'app/footer.tsx').write_text('import {Icon} from "./ui-icon";\nexport function Footer(){return '+footer+'}\n',encoding='utf-8')
s=s[:start]+'<Footer />'+s[end:];s='import {Footer} from "./footer";\n'+s;p.write_text(s,encoding='utf-8')
for name in ['page.tsx','home-details.tsx','footer.tsx','components.tsx']:
 p=root/'app'/name;s=p.read_text(encoding='utf-8').replace('/preview/catalogue.html','/gallery').replace('/preview/services.html','/gallery?view=custom-work');p.write_text(s,encoding='utf-8')
