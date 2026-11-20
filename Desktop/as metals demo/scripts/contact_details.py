from pathlib import Path
p=Path('app/contact/page.tsx');s=p.read_text(encoding='utf-8')
s=s.replace("const localityMap='https://www.google.com/maps/search/?api=1&query='+encodeURIComponent('Lakhnaur Sahib, Ambala, Haryana, India');\n",'')
start=s.index('<section className="contact-visit">');end=s.index('<section className="compact-prompt">',start)
s=s[:start]+'''<section className="contact-visit"><div><h2>Find us in Lakhnaur Sahib.</h2><address>Lakhnaur Sahib, Ambala</address><p className="muted">Call for workshop directions before visiting.</p><a className="button" href={directions} target="_blank" rel="noopener noreferrer">Ask for directions <Icon name="arrow"/></a></div><div className="visit-details"><div><h3>Working days</h3><p className="working-days">Every day</p></div><div><h3>Opening &amp; closing times</h3><span className="pending-detail">To be confirmed</span><p>Please call to confirm visiting hours.</p><a className="detail-link" href="tel:+918950917008"><Icon name="phone"/>+91 89509 17008</a></div></div></section>
'''+s[end:]
p.write_text(s,encoding='utf-8')
