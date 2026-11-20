from pathlib import Path
import re
for filename in ['page.tsx','gallery/page.tsx','quote/page.tsx','contact/page.tsx']:
 p=Path('app')/filename;s=p.read_text(encoding='utf-8');match=re.search(r'<a [^>]*className="skip"[^>]*>.*?</a>',s)
 if match:
  skip=match.group();s=s.replace(skip,'');s=s.replace('return <>','return <>'+skip,1)
 s=re.sub(r'<div className="bottom">(.*?)</div>',r'<nav className="bottom" aria-label="Quick contact">\1</nav>',s)
 s=s.replace('<div className="notice">Grand opening','<div className="notice" role="region" aria-label="Opening announcement">Grand opening')
 p.write_text(s,encoding='utf-8')
