# Quote verification — Checkpoint 5

20 November 2026. Production build at http://127.0.0.1:3005.

- Previous production build and TypeScript checks passed; no application source changes during this verification turn.
- Earlier validation/message checks passed via scripts/check-quote.cjs; Unicode, ampersands and optional-value defaults survive the WhatsApp URL round trip.
- Browser checked at 375×812 and 1280×900: no horizontal overflow or button clipping, two-column desktop form/sidebar, readable mobile form without overlaying bottom bar.
- Gallery AMF-GEO-01 detail → Quote link carries Moroccan Star Lattice and Jali & partitions correctly. AMF-3DR-01 carries Solstice / Carved doors.
- Empty required fields prevent handoff and focus first invalid input (verified previous turn).
- File testing used browser-generated synthetic File/DataTransfer fixtures. No operating-system file picker or local disk read was used because the tool rejects project paths. Unsupported .exe fixture produced linked error/invalid state and cleared input. Supported PDF fixture produced filename summary; it was not parsed or uploaded.
- Valid submission with window.open intercepted produced selected design/name, requirement, undecided size/material defaults, message and filename reminder. No WhatsApp tab or enquiry sent. Fallback link matched intercepted URL.
- User text containing script-like markup stayed text, with no script DOM created.
- Simulated clipboard denial selected the full prepared textarea and displayed manual Copy instructions.
- Remove file cleared file input/summary and removed stale prepared message. Remove design cleared query/selection while retaining form values.
- Unknown design code displayed recovery rather than a fabricated design.
- Final console had no warnings/errors.

Scope: application reference-handling logic and handoff tested. Actual device file-picker behaviour and WhatsApp sending remain native browser/app actions, not exercised by these tests. Lighthouse and final SEO/accessibility audit remain Phase 4.
