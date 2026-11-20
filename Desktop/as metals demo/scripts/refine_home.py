from pathlib import Path
p=Path('app/page.tsx');s=p.read_text(encoding='utf-8');s='import {Icon} from "./icon";\n'+s
s=s.replace('Get a Quote on WhatsApp</a>','<Icon name="chat" />Get a Quote on WhatsApp</a>')
s=s.replace('WhatsApp Quote</a>','<Icon name="chat" />WhatsApp Quote</a>').replace('>Call</a>','><Icon name="phone" />Call</a>')
s=s.replace('View Designs</a>','View Designs<Icon name="arrow" /></a>').replace('View collection →','View collection <Icon name="arrow" />')
s=s.replace('<p className="eyebrow">BROWSE BY OFFERING</p>','')
s=s.replace('View all 54 designs</a>','View all 54 designs<Icon name="arrow" /></a>')
s=s.replace('width={1507} height={1627} sizes="(min-width: 1000px) 25vw, 50vw" /><h3>Jali','width={1413} height={1525} sizes="(min-width: 1000px) 25vw, 50vw" /><h3>Jali')
p.write_text(s,encoding='utf-8')
p=Path('app/components.tsx');s=p.read_text(encoding='utf-8');s=s.replace("import {useEffect,useState}","import {Icon} from './icon';\nimport {useEffect,useState}")
s=s.replace("{open?'Close':'Menu'}</button>","<Icon name={open?'close':'menu'} />{open?'Close':'Menu'}</button>")
s=s.replace('>WhatsApp Quote</a>','><Icon name="chat" />WhatsApp Quote</a>')
p.write_text(s,encoding='utf-8')
