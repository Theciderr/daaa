# A.S Metal Fabricators website

Read AGENTS.md, DESIGN.md and PROGRESS.md first in every session. Use research/catalogue for extracted facts; re-open original PDFs only when necessary.

## Workflow
- Follow the user's phased brief. STOP at every checkpoint; proceed only after the user types `go`.
- Phase 1 research; Phase 2 strategy, tokens and HTML wireframe; Phase 3 one page at a time (Home, Gallery, Quote, About/contact); Phase 4 final pass.
- At each checkpoint update memory, summarize in <=10 lines, give preview command, list approvals, print the user's MODEL CHECKPOINT block, then wait.
- Recommended next models: Phase 2 gpt-6.2-astra/high; Home, Gallery, Quote, About gpt-6.2-sol/medium; final pass gpt-6.2-astra/medium. Home polish astra/medium.
- After two failures on the same problem stop and recommend astra for the fix; await go.
- Ask before important assumptions; subjective design choices need 2-3 options and a recommendation.

## Client and truth
- A.S Metal Fabricators; Lakhnaur Sahib, Ambala, Haryana, India; +91 89509 17008; Instagram @asmetalfab; grand opening 24 November 2026 (user supplied).
- Built by ExpandR (https://expandr.in); small Demo footer tag. Never imply this demo is completed client work.
- Catalogue is the source for services, design names/codes, materials and images. Preserve illustrative-image labels and project-specific material qualifications.
- Never invent services, prices, sizes, lead times, certifications, testimonials, clients, experience or statistics. Clearly mark missing content and maintain Needed from client in PROGRESS.md.
- Publish How ordering works only using steps the client confirms.

## Stack and quality
- Next.js App Router, TypeScript, Tailwind CSS, Framer Motion or GSAP; Vercel ready.
- Test each page locally at 375px and desktop; fix before its checkpoint.
- Target mobile Lighthouse >=90 in all four categories; measure, never claim unrun scores.
- next/image, WebP/AVIF, lazy loading, no layout shift, console errors or broken links; keyboard access, readable contrast, image alt text.
- Purposeful hero entrance, reveals, hover/zoom, 2D/3D comparison, smooth filters and shrinking sticky header. Respect reduced motion and aim for 60fps.
- SEO: real local searches, page titles/meta, LocalBusiness schema using verified facts, OG tags and sitemap. Short README.

## Design and conversion
- Mobile-first for homeowners, designers, architects and contractors arriving from local search/Instagram.
- Catalogue-led dark warm gold/amber brand; premium, calm, confident. Work imagery leads, strong hierarchy, generous spacing, consistent grid, <=2 typefaces.
- No stock gradient hero, purple/blue SaaS styling, emoji icons, generic welcome copy, repeated identical three-card sections, lorem ipsum or fake reviews.
- Primary CTA: Get a Quote on WhatsApp. Secondary: View Designs. CTA above fold, after galleries and in footer; mobile bottom bar: WhatsApp Quote and Call.
- Short quote form: name, phone, real-service dropdown, approximate size, optional material, photo/design upload, message. Submit opens prefilled WhatsApp; also provide plain WhatsApp button.
- File attachment handling must be honest: a WhatsApp text link cannot silently transfer a local file. Resolve approach in Phase 2 before implementation.
- Copy must be specific, plain and short for Ambala/Haryana customers.

<!-- BEGIN:nextjs-agent-rules -->

## This is NOT the Next.js you know

This version has breaking changes — APIs, conventions, and file structure may all differ from your training data. Read the relevant guide in `node_modules/next/dist/docs/` (resolved from this file's directory; in monorepos the `next` package may not be visible from the repo root) before writing any code. Heed deprecation notices.

This block is written and re-added by `next dev` — verify at `node_modules/next/dist/server/lib/generate-agent-files.js`. Removing it from a diff only re-creates the uncommitted change; committing it with your work keeps the tree clean.

<!-- END:nextjs-agent-rules -->
