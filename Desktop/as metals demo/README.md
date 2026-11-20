# A.S Metal Fabricators demo

Status: all four pages implemented; final pass incomplete because mobile Lighthouse performance remains below 90. See PROGRESS.md and the provisional HANDOVER.md. Latest reports are in output/lighthouse; no deployment has occurred.

Next.js App Router, TypeScript, Tailwind CSS and Framer Motion. Home, `/gallery`, `/quote` and `/contact` are production pages. Gallery includes the custom-work view at `/gallery?view=custom-work`, shareable `design` query, collection/family filters, search and pagination. Quote actions route through `/quote?design=AMF-...`; its direct WhatsApp alternative skips the form. Old preview links redirect to corresponding production pages.

Contact uses the user-confirmed location “Lakhnaur Sahib, Ambala” and working days “Every day”. Exact visiting times remain a clearly labelled placeholder. No map pin is shown until provided. Owner, phone and services are source-backed; no invented history/reviews.

```powershell
npm install
npm run dev
```

Open http://127.0.0.1:3000. Validate with `npm run build` and `npm run typecheck`. Deploy to Vercel as Next.js from this root, after the remaining page checkpoints and final checks.

Scripts explicitly use Webpack because this Windows environment failed during Turbopack CSS compiler startup. Verified production preview runs at http://127.0.0.1:3003 using `node node_modules/next/dist/bin/next start --hostname 127.0.0.1 --port 3003` after building. Use a free port if an earlier server is still running.

Catalogue images are illustrations, not completed projects. Sources remain in catalogue/; selected assets are served from public/. Do not run scripts/build_home.py after manually editing the React page: it is a one-time migration script.

Quote submit prepares a message; the visitor presses Send in WhatsApp. No server submission or automatic attachment. Local file selection/manual attachment is approved. Reference-handling logic was browser-tested with synthetic File fixtures because the tool denied disk paths; native OS chooser and actual WhatsApp sending were not exercised. See output/quote-verification.md. No business enquiry was sent during testing.

Quote attachments: user approved local file selection followed by manual attachment in WhatsApp. No file upload/server storage or client-side persistence. Optional filename is included as a reminder. Form validation and message encoding checks: `node --experimental-strip-types scripts/check-quote.cjs` (Node 22; type-stripping warnings are expected). Browser tests intercept window.open to inspect the handoff without opening a business chat.

Read AGENTS.md, DESIGN.md and PROGRESS.md before continuing work. Each page requires its own review checkpoint.

Gallery data: app/gallery/designs.json, traced to source filename/PDF page/printed folio. Source images remain labelled catalogue illustrations. Scripts under scripts/ are migration helpers; gallery_data.py extracts the initial shared footer and must not be rerun over the refactored React files. Update structured data separately when sources change. Quote links preserve design code/name in prepared WhatsApp text; no enquiry is sent by opening a link.
