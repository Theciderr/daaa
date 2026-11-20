from pathlib import Path
import re,json
for filename,path in [('gallery/page.tsx','/gallery'),('quote/page.tsx','/quote'),('contact/page.tsx','/contact')]:
 p=Path('app')/filename;s=p.read_text(encoding='utf-8');title,description=re.search(r"title:'([^']+)',description:'([^']+)'",s).groups();import_path='../site'
 s="import {pageMetadata} from '"+import_path+"';\n"+s
 s=re.sub(r'export const metadata:Metadata=\{.*?\};',f'export const metadata:Metadata=pageMetadata({json.dumps(title)},{json.dumps(description)},{json.dumps(path)});',s,count=1)
 p.write_text(s,encoding='utf-8')
p=Path('app/gallery/gallery.tsx');s=p.read_text(encoding='utf-8');s="import {Comparison} from './comparison';\n"+s if False else s.replace("import designs from './designs.json';","import designs from './designs.json';\nimport {Comparison} from './comparison';")
start=s.index('<div className="process-comparison">');end=s.index('</section>}',start)
s=s[:start]+'<Comparison/>'+s[end:]
# Escape events in dialogs must not move focus to the hidden header menu.
p.write_text(s,encoding='utf-8')
p=Path('app/components.tsx');s=p.read_text(encoding='utf-8').replace("if(e.key==='Escape'){setOpen(false);document.getElementById('menu-button')?.focus()}","if(e.key==='Escape'&&document.getElementById('menu-button')?.getAttribute('aria-expanded')==='true'){setOpen(false);document.getElementById('menu-button')?.focus()}")
p.write_text(s,encoding='utf-8')
p=Path('app/page.tsx');s=p.read_text(encoding='utf-8').replace(' 100vw" priority',' 100vw" preload');p.write_text(s,encoding='utf-8')
