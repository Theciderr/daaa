# Project progress

Updated 21 November 2026 (Asia/Calcutta). Read with AGENTS.md and DESIGN.md at every new session.

## Current checkpoint
Phase 4 INCOMPLETE. User authorised final pass, but two performance optimization attempts did not reach the required mobile >=90 scores. STOPPED under the brief's two-failure rule; await go before further fixes. Recommend gpt-6.2-astra/high for the performance fix. Do not call this Checkpoint 7/final handover complete or deploy-ready.

Latest measured mobile Lighthouse (12.6.1, isolated headless Edge, default mobile simulation): Home performance 71, Gallery 83, Quote 81, Contact 85. Accessibility, best practices and SEO are 100 on all four pages. Reports at output/lighthouse/{home,gallery,quote,contact}.{html,json}, summary.json. Baseline and intermediate reports preserved; intermediate performance was 88/85/89/89. Latest changes regressed the measured run; do not present intermediate results as final or claim a causal explanation without trace review.

Final-pass changes implemented: canonical/OG/Twitter metadata, LocalBusiness schema using verified facts, sitemap/robots, generated social image, AS utility favicon, friendly 404, native keyboard 2D/3D comparison and text alternative, correct skip/quick-contact/notice landmarks, source formatting, unused public image-copy pruning (original research assets retained), header Escape/dialog conflict fix. Shared header no longer eagerly imports Framer Motion. Gallery mini animation loads only on filter changes; current build/typecheck and latest dynamic-import interaction script pass.

Current experiment: next.config.ts experimental.inlineCss=true, hero eager/high fetch priority, header logo eager rather than preloaded, Gallery import framer-motion/dom/mini on interaction. Do not assume inlineCss is an improvement: latest performance was lower. The first mini import used framer-motion/mini (hook-only); corrected to dom/mini and typechecked/built successfully. Revisit experimental inline CSS and traces first rather than guessing further.

Latest browser audit: all four pages at 375px/1280px, axe WCAG/best-practice scan zero violations, no page errors/overflow/missing alt, one H1, canonical/OG present. Native dialog focus/Escape, range ArrowRight (50→51), reduced motion/text alternative, sitemap/robots/OG/favicon endpoints and 404 all passed. Evidence output/final/browser-checks.json. Re-run after performance fixes before final handover. Quote logic checks still pass; no messages sent.

Audit tooling: Lighthouse/axe-core/Prettier installed as dev dependencies. npm audit --omit=dev reported zero production vulnerabilities. Lighthouse's temporary dev-tool dependency tree still has 10 high advisories after the recommended 12.6.1 version change; remove the temporary Lighthouse dependency after measuring (retain reports) or otherwise resolve it before calling final cleanup complete. Do not force-upgrade blindly. Audit runner uses isolated Playwright Edge because chrome-launcher failed to detect its dynamic debugging port. Script scripts/lighthouse-final.cjs requires local Lighthouse installed and the bundled Playwright path; scripts/check-filter-motion.cjs is ready but not yet run.

Domain: user gave asmetalfabiractors.com 'for example'. Treat as unconfirmed spelling/ownership, not a verified domain. SITE_URL config plus VERCEL_PROJECT_PRODUCTION_URL fallback implemented; local canonical remains localhost. .env.example and HANDOVER.md document configuration. HANDOVER.md is provisional while final checks remain incomplete. No deployment occurred.

## Approved decisions
- Source catalogue is authoritative; illustrative images must not be presented as completed installations.
- Existing dark/gold visual identity accepted. Refined stone monochromes, serif hierarchy, line icons, fine borders and subtle static gradients accepted for current direction.
- Navigation-based architecture, professional footer, all four offerings/full catalogue. Home expanded to six sections after feedback that it became too short.
- Local file selection followed by manual WhatsApp attachment explicitly approved. No hosted upload or automatic attachment.
- Contact working days confirmed by user: Every day. User asked to show just Lakhnaur Sahib, Ambala as location for now; no map pin. Exact opening/closing times remain unconfirmed and clearly marked.
- Latest gold logo supplied by user (81a0d918-58c8-46ec-b0d5-2c1dcfa711eb.png) supersedes earlier red/silver version. Use original PNG unchanged in app/brand.tsx, shared Header/Footer. No light backing; CSS trims empty display margins. Main site palette remains stone/gold.

## Completed
- Phase 1: four PDFs read, 63 pages rendered/reviewed, 54 unique designs indexed with source/image provenance. Research report covers eight comparable businesses.
- Phase 2: strategy and mobile/desktop wireframes revised; production implementation now supersedes Home/Gallery/Quote wireframes.
- Production Home: hero, four collections, catalogue feature/PDF links, custom CNC capabilities, material/finish guidance, closing quote prompt, professional shared footer.
- Production /gallery: 54 records, offering/family filters, code/name search, 12-design pagination, empty-state recovery, shareable native dialogs, source PDF page references, selected-design quote links. /gallery?view=custom-work contains capacity/material/finish guidance and static 2D/3D explanation.
- Production /quote: selected design/name/offer, change/remove, unknown-code recovery, required name/phone/requirement; optional size/material/message/reference. Inline validation/focus, direct WhatsApp alternative, prepared message/copy/reopen fallback. Opens a new WhatsApp tab; visitor still presses Send. Local filename reminder, no server upload or localStorage persistence.
- Production /contact: verified business/owner overview, all-offering links, phone/Instagram/quote contacts, locality Lakhnaur Sahib, Ambala, working days Every day, visiting-time placeholder, WhatsApp directions request. No invented map pin, history, reviews or service promises. Header/footer now target /contact with current-navigation state.
- Header/Home/Gallery/footer CTAs route to Quote; Gallery passes AMF code. Old Home/catalogue/custom/quote/contact preview URLs redirect to production equivalents.
- User-supplied gold logo installed globally; source at public/brand/as-metal-fabricators-gold.png. Header/footer no longer text-only wordmarks.

## Verification
- Production build and TypeScript pass. Webpack scripts avoid environment-specific Turbopack CSS subprocess failure. Next.js 16.4.6 and package-lock installed.
- Home/Gallery checked at 375px and 1280px; no overflow, images and source links valid, dialog Escape/focus return/Back, query links and filters checked.
- Quote checked at 375x812 and 1280x900; no horizontal/button overflow. Selected-code handoff, invalid required fields/first focus, optional defaults, retained values, unknown/remove-design and Gallery-to-Quote verified. Unicode/script-like input stays safe text in prepared message.
- Reference tests used synthetic browser File/DataTransfer fixtures after two disk-path upload_file attempts were denied by tool workspace-root configuration. Unsupported type rejected; supported PDF filename shown, manual attachment reminder included in message, remove clears input/summary/stale message. Simulated clipboard denial selects prepared message and shows copy instructions. Real OS picker was not exercised.
- All WhatsApp submission tests intercepted window.open. No business chat, upload or enquiry sent.
- Console clear. Full Lighthouse and final SEO/accessibility audit still Phase 4. Evidence: output/quote-verification.md.
- Contact: final production build/typecheck passed; checked at 375x812 and 1280x900 with no horizontal/button overflow, quote CTA above fold, responsive columns, correct menu/current nav, Escape close, all local links/PDFs HTTP 200. Phone/Instagram/directions URLs inspected without contacting business; no Google map shown. Confirmed old preview redirect. Console clear. Evidence: output/contact-verification.md.
- Logo: final build/typecheck pass, isolated headless Edge verified image load/no overflow at 375px/1280px, page errors empty. Screenshots output/playwright/logo-mobile.png and logo-footer.png visually inspected. Connected Chrome tool stalled while awaiting lazy footer decode; test switched to isolated browser without altering app behaviour. scripts/check-brand.cjs is environment-specific (bundled runtime path).

## Preview and commands
- Running production server: http://127.0.0.1:3005 (session 3727).
- About & contact: http://127.0.0.1:3005/contact
- Quote: http://127.0.0.1:3005/quote?design=AMF-3DR-01
- Restart: npm run build then node node_modules/next/dist/bin/next start --hostname 127.0.0.1 --port 3005
- Validate: npm run typecheck
- Message checks: node --experimental-strip-types scripts/check-quote.cjs (Node 22 warnings expected).
- Earlier preview servers on 3003 may be stale. Do not use those for review. An old dev process may occupy 3000; stop only a verified task process or select a free port.

## Needed from client
- Exact map pin/street directions and opening/closing times. Working days Every day confirmed; show only Lakhnaur Sahib, Ambala location for now as requested. Keep exact times placeholder and omit unverified map.
- Actual ordering stages before How ordering works can be published. No assumed deposits/approval/production/delivery sequence.
- Genuine workshop or completed-work photos; current catalogue scenes are illustrative. Gold PNG logo is now supplied/implemented. True vector is optional for future larger placements, not a blocker for this site.
- Installation/delivery/service area, warranty, turnaround and prices only if those claims should appear. Omit unsupported promises.
- Confirm business WhatsApp is active and public email may be published (catalogue: akay0171@gmail.com).
- Confirm public brand punctuation if needed: brief A.S Metal Fabricators, collateral A.S. METAL FABRICATORS.
- Higher-resolution originals if final crops need them. Do not synthesize missing craft detail.

## Exact next step after go
Resume Phase 4 with gpt-6.2-astra/high. Inspect output/lighthouse latest versus intermediate metrics/traces, especially TBT and inline-CSS duplication; resolve mobile performance >=90 without weakening audit settings or hiding content. Consider reverting the experimental CSS change first. Run scripts/check-filter-motion.cjs and final-browser-check.cjs on final build; preserve actual reports. Clean temporary audit dependencies and verify production audit, build/typecheck. Complete copy/animation/SEO review and update provisional HANDOVER.md/README. Then Checkpoint 7 with actual measured scores, remaining client details and deploy steps. Do not deploy unless authorised.

## Remaining final pass
Performance target still unmet; audit-tool dependency cleanup and final copy/handover review remain. Comparison/SEO endpoints and latest interaction/reduced-motion checks are implemented and checked. Automatic opening notice expires at 24 November 2026 00:00 Asia/Kolkata; do not claim actual opening without confirmation.

## Migration caution
Scripts/build_home.py and gallery_data.py are one-time migration helpers and must not be rerun over refactored React files. Source data now lives in app/gallery/designs.json; provenance in research/catalogue. See README.
