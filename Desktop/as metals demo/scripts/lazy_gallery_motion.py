from pathlib import Path
p=Path('app/gallery/gallery.tsx');s=p.read_text(encoding='utf-8').replace('import { motion, useReducedMotion } from "framer-motion";\n','').replace('  const reduced = useReducedMotion();\n','')
s=s.replace('  const dialog = useRef<HTMLDialogElement>(null);','''  const grid=useRef<HTMLDivElement>(null);
  const initialRender=useRef(true);
  useEffect(()=>{
    if(initialRender.current){initialRender.current=false;return}
    if(window.matchMedia('(prefers-reduced-motion: reduce)').matches)return;
    let cancelled=false;
    import('framer-motion/mini').then(({animate})=>{if(!cancelled&&grid.current)animate(grid.current,{opacity:[.65,1]},{duration:.18})}).catch(()=>{});
    return()=>{cancelled=true};
  },[state.offer,state.family,state.q,state.page]);
  const dialog = useRef<HTMLDialogElement>(null);''')
s=s.replace('<motion.div','<div ref={grid}').replace('</motion.div>','</div>')
s=s.replace('              initial={reduced ? false : { opacity: 0.65 }}\n','').replace('              animate={{ opacity: 1 }}\n','').replace('              transition={{ duration: reduced ? 0 : 0.18 }}\n','')
p.write_text(s,encoding='utf-8')
