from pathlib import Path
import re,shutil
root=Path(__file__).resolve().parents[1]
public=root/'public';public.mkdir(exist_ok=True)
shutil.copytree(root/'wireframes',public/'preview',dirs_exist_ok=True)
shutil.copytree(root/'research/catalogue/images',public/'research/catalogue/images',dirs_exist_ok=True)
shutil.copytree(root/'catalogue',public/'catalogue',dirs_exist_ok=True)
# Preview pages remain Phase 2 artifacts until their individual build checkpoints.
for file in (public/'preview').glob('*.html'):
 s=file.read_text(encoding='utf-8').replace('../research/','/research/').replace('../catalogue/','/catalogue/')
 s=s.replace('<body>','<body><div class="notice">Phase 2 preview · This page will be built at its next checkpoint. <a href="/">Back to Home</a></div>')
 file.write_text(s,encoding='utf-8')
css=(root/'wireframes/style.css').read_text(encoding='utf-8')
css='@import "tailwindcss";\n'+css+'''\nhtml{scroll-behavior:smooth}body{margin:0}button,a{ -webkit-tap-highlight-color:transparent}.skip{position:absolute;left:16px;top:-100px;z-index:50;background:var(--gold);color:var(--bg);padding:12px}.skip:focus{top:12px}.site-header{min-height:90px}.site-header .wrap{transition:padding .2s}.site-header.scrolled .wrap{padding-top:8px;padding-bottom:8px}.site-header.scrolled{box-shadow:0 1px 0 #51483a}.hero h1{letter-spacing:-.035em}.hero img{border-radius:2px}.collection-link{display:block}.collection-link img{transition:transform .2s}.collection-link:hover img{transform:scale(1.02)}.collection-link:focus-visible{outline-offset:6px}.section-title a{text-underline-offset:5px}.compact-prompt h2{font-size:32px}.footer-bottom{margin-bottom:16px}.hero{min-height:0}.notice{letter-spacing:.03em}.brand{font-size:21px}.collection-grid{margin-bottom:24px}.site-footer .brand{font-size:23px}.site-footer p{color:var(--muted)}@media(prefers-reduced-motion:reduce){html{scroll-behavior:auto}*,*::before,*::after{transition:none!important;animation:none!important}}\n'''
(root/'app/globals.css').write_text(css,encoding='utf-8')
s=(root/'wireframes/home.html').read_text(encoding='utf-8')
s=s[s.index('<body>')+6:s.index('<script')]
s=re.sub(r'<header\b.*?</header>','<Header />',s,flags=re.S)
s=s.replace('<div class="notice">Grand opening · 24 November 2026</div>',"{Date.now() < Date.parse('2026-11-24T00:00:00+05:30') && <div className=\"notice\">Grand opening · 24 November 2026</div>}")
s=s.replace('class=','className=').replace('loading="lazy"','loading="lazy"')
s=s.replace('<main className="wrap">','<a className="skip" href="#main">Skip to content</a><main id="main" className="wrap">')
s=s.replace('href="home.html"','href="/"').replace('href="quote.html"','href="https://wa.me/918950917008?text=Hello%20A.S%20Metal%20Fabricators%2C%20I%20would%20like%20a%20quote."')
for name in ['catalogue','services','contact']:
 s=s.replace('href="'+name+'.html','href="/preview/'+name+'.html')
s=s.replace('src="../research/','src="/research/').replace('href="../catalogue/','href="/catalogue/')
def img(m):
 tag=m.group();tag=tag.replace('<img','<Image');tag=tag.replace('width="1507"','width={1507}').replace('height="1627"','height={1627}')
 if 'width=' not in tag:tag=tag[:-1]+' width={1507} height={1627}>'
 tag=tag[:-1]+' sizes="(min-width: 1000px) 25vw, 50vw" />'
 if 'Solstice radial' in tag:tag=tag.replace('sizes="(min-width: 1000px) 25vw, 50vw"','sizes="(min-width: 800px) 50vw, 100vw" priority')
 return tag
s=re.sub(r'<img[^>]+>',img,s)
s=s.replace('<br>','<br />')
s=s.replace('<section id="choose">','<section id="choose"><Reveal>').replace('</div><p className="muted">Catalogue illustrations.','</div><p className="muted">Catalogue illustrations.')
s=s.replace('</a></section><section className="compact-prompt">','</a></Reveal></section><section className="compact-prompt">')
(root/'app/page.tsx').write_text('import Image from "next/image";\nimport {Header,Reveal} from "./components";\nexport const dynamic="force-dynamic";\nexport default function Home(){return <>\n'+s+'\n</>}\n',encoding='utf-8')
