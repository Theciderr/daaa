from pathlib import Path
p=Path('app/page.tsx');s=p.read_text(encoding='utf-8');s=s.replace('<section className="compact-prompt">','<HomeDetails /><section className="compact-prompt">');p.write_text(s,encoding='utf-8')
