from pathlib import Path
for name in ['components.tsx','footer.tsx']:
 p=Path('app')/name;s=p.read_text(encoding='utf-8').replace('/preview/contact.html','/contact')
 if name=='components.tsx':s=s.replace('href="/contact">About &amp; contact','href="/contact" aria-current={current==="contact"?"page":undefined}>About &amp; contact')
 p.write_text(s,encoding='utf-8')
Path('public/preview/contact.html').write_text('<!doctype html><html lang="en"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/contact"><title>Opening About and contact</title><p><a href="/contact">Open About and contact</a></p></html>',encoding='utf-8')
