from pathlib import Path
import re
for name in ['page.tsx','home-details.tsx','footer.tsx','components.tsx','gallery/page.tsx']:
 p=Path('app')/name;s=p.read_text(encoding='utf-8');s=re.sub(r'https://wa\.me/918950917008\?text=[^"\s\x27]+','/quote',s);p.write_text(s,encoding='utf-8')
p=Path('app/home-details.tsx');s=p.read_text(encoding='utf-8');s=re.sub(r"const quote='[^']+';","const quote='/quote';",s);p.write_text(s,encoding='utf-8')
p=Path('app/gallery/gallery.tsx');s=p.read_text(encoding='utf-8');s=re.sub(r"const quote=\(code=''\)=>.*?;\n", "const quote=(code='')=>'/quote'+(code?'?design='+encodeURIComponent(code):'');\n",s,count=1);p.write_text(s,encoding='utf-8')
p=Path('app/components.tsx');s=p.read_text(encoding='utf-8').replace('href="/quote"><Icon','href={current==="quote"?"#main":"/quote"} aria-current={current==="quote"?"page":undefined}><Icon');p.write_text(s,encoding='utf-8')
Path('public/preview/quote.html').write_text('<!doctype html><html lang="en"><meta charset="utf-8"><title>Opening Quote</title><a href="/quote">Open Quote</a><script>const u=new URL("/quote",location.origin);const d=new URLSearchParams(location.search).get("design");if(d)u.searchParams.set("design",d);location.replace(u.href)</script></html>',encoding='utf-8')
