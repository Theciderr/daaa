# A.S Metal Fabricators — demo handover

**Provisional: Phase 4 is not complete.** Latest mobile performance scores are Home 71, Gallery 83, Quote 81 and Contact 85, below the requested 90 target. Other Lighthouse categories are 100. Performance fixes, latest filter-animation verification and temporary audit-tool dependency cleanup remain. Do not treat this document as final deployment approval. See PROGRESS.md for the required resume step and preserved reports.

The site has four main routes: Home `/`, Catalogue `/gallery`, Quote `/quote` and About & contact `/contact`. Custom work is a catalogue view at `/gallery?view=custom-work`. The supplied gold logo is used throughout. ExpandR credit and Demo tag remain in the footer.

## Preview and validation

```powershell
npm ci
npm run build
node node_modules/next/dist/bin/next start --hostname 127.0.0.1 --port 3005
```

Open http://127.0.0.1:3005. For development use `npm run dev`; scripts use Webpack because this Windows environment failed during Turbopack CSS subprocess startup. `npm run typecheck` verifies TypeScript. Message checks: `node --experimental-strip-types scripts/check-quote.cjs` on Node 22.

## Before publishing

- Confirm the exact domain spelling and ownership. `asmetalfabiractors.com` was supplied as an example only; it is not treated as a verified live domain.
- Set `SITE_URL` to the confirmed HTTPS origin, without a trailing path, and rebuild. `.env.example` documents the value. Vercel's production-domain environment variable is the fallback on Vercel; localhost is the local fallback. Review canonical tags, `/sitemap.xml`, `/robots.txt` and `/opengraph-image` on the real domain.
- Working days are Every day. Exact opening/closing times remain visibly To be confirmed. Location is Lakhnaur Sahib, Ambala; no unverified map pin is published.
- Confirm the business WhatsApp is active. Test on a real phone, including manually attaching a reference. Automated checks did not send enquiries.
- Provide actual ordering stages if a How ordering works section is wanted. No deposit, installation, delivery, turnaround or warranty claims have been invented.
- Catalogue images are illustrations, not completed installations. Genuine workshop/installed-work photos are welcome. Supplied logo is PNG; a vector is optional for future large-format use.
- Opening announcement expires automatically at 00:00 Asia/Kolkata on 24 November 2026. This does not assert that the opening occurred. Keep the Demo tag until the client approves live presentation.

## Deploy to Vercel

1. Put the project in the intended Git repository and import it in Vercel. Select this directory as the project root and Next.js as the framework.
2. Use Node 22, install with `npm ci`, build with `npm run build`; leave output directory at the Next.js default. No database, upload storage or API secrets are needed.
3. Set `SITE_URL` for the confirmed public origin before the production build. Add and verify the domain in Vercel, then follow the DNS records Vercel provides. Do not assume the sample domain is registered.
4. Review the deployment first: all four routes, collection filters, source PDFs, selected-design quote text, phone/Instagram, favicon and social preview. Check on an actual phone and verify file attachment instructions.
5. Approve the live content and Demo-tag treatment before promoting/publishing. Re-run Lighthouse on the public deployment; local scores are lab measurements, not a promise about all networks/devices.

Nothing has been deployed by this task. Reference: [Vercel system environment variables](https://vercel.com/docs/environment-variables/system-environment-variables).

## Maintenance and data

Design records are in `app/gallery/designs.json`, including source filename, PDF page and printed folio. Preserve AMF codes, project-specific material qualifications and illustrative labels. Keep source PDFs in `catalogue/`; public copies serve downloads. Source extraction details are in `research/catalogue/`.

Quote references stay on the visitor's device. Only a filename reminder enters the prepared WhatsApp text; the visitor attaches the file and presses Send in WhatsApp. The site has no upload endpoint, CRM submission or browser persistence of personal data. A direct WhatsApp alternative skips the form.

`scripts/build_home.py`, `gallery_data.py` and other migration scripts are historical helpers; do not rerun them over the refactored app. Read AGENTS.md, DESIGN.md and PROGRESS.md before changing the implementation.

## Final verification evidence

See `output/final/` for browser/accessibility checks and `output/lighthouse/` for mobile Lighthouse reports. The baseline reports are retained separately so improvements are reviewable. Remaining client details above are not fabricated to complete a checklist.
