from pathlib import Path
import re
p=Path('app/components.tsx');s=p.read_text(encoding='utf-8');s=s.replace("import {Icon} from './ui-icon';","import {Icon} from './ui-icon';\nimport {Brand} from './brand';")
s=re.sub(r'<a className="brand" href="/">.*?</a>','<Brand />',s,count=1);p.write_text(s,encoding='utf-8')
p=Path('app/footer.tsx');s=p.read_text(encoding='utf-8');s='import {Brand} from "./brand";\n'+s;s=re.sub(r'<a className="brand" href="/">.*?</a>','<Brand footer />',s,count=1);p.write_text(s,encoding='utf-8')
