# Phase 2 — strategy and proposed design system

Status: review draft. “Continue” authorised Phase 2; choices below require Checkpoint 2 approval.

## Visitors and the questions to answer
| Visitor | Top three questions | Reading order |
|---|---|---|
| Homeowner | What could suit my home? Can it fit my space? How do I ask for a price? | Home imagery → Gallery → material guidance → Quote |
| Interior designer | Which patterns suit my concept? Which finishes are available? Can I send a reference? | Gallery → design detail → Quote |
| Architect | Is this cut-through or relief? What boards and machine capacities are available? Can my dimensions/material be reviewed? | Home comparison → material guidance → Gallery → Quote |
| Contractor | What exactly is supplied? Can I supply a board? Who can confirm the requirement? | material guidance → Quote → About/contact |

## Sitemap
- `/` Home: establish product, locality and visual quality; guide selection and enquiry.
- `/gallery` Designs: filter 2D jali, carved doors, relief panels and sculpted art; optional pattern-family filter. Search real name/code. Details in accessible dialog with shareable query parameter, preserving back navigation and selected design. No extra thin product pages initially.
- `/quote` Quote: selected code carried by query parameter; editable short form and direct WhatsApp alternative.
- `/contact` About & contact: verified owner/location, phone, Instagram, directions once confirmed, hours placeholder. Combined because a separate company-history page has insufficient verified content.

## Home order and responsive layout
1. Compact opening notice (before 24 November only), header and clear local identity.
2. Hero: “Jali panels, carved doors & wall art. Made for your space.” Locality + catalogue reference + primary quote and View Designs. Mobile: text and actions first, portrait image next. Desktop: 5/7 split text/image. Never put text over busy craft detail.
3. Selected designs: one large feature and two smaller references, not repeated service cards. Real names/codes, illustrative labels and quote after gallery. Mobile stacks in a readable sequence.
4. 2D vs 3D: explain cut-through openings versus surface relief. Label each unrelated sample clearly; range control changes emphasis, not an implied before/after. Provide keyboard and static reduced-motion views.
5. Materials and finish: compact text/table; raw, primed, Duco and suitable laminated 2D options. Project-specific feasibility retained.
6. Ordering section reserved in wireframe only: client-confirmed stages needed. Omit from production until confirmed.
7. Local contact and final quote prompt, then ExpandR/Demo footer.
Mobile bottom bar remains visible, with content padding and safe-area allowance. Shrinking header changes internal padding without pushing document content.

## Quote flow
Entry via general CTA or selected design → short form → review message → user opens WhatsApp → user sends. Opening WhatsApp is not proof of a sent enquiry; never show “Quote received”.

Name and phone required; need dropdown required (four verified offers plus Custom CNC requirement); approximate size accepts “Not sure”; optional material, reference file and message. Preserve AMF code. Use explicit unit hint and allow free text. No invented estimate. Keep direct WhatsApp available without form completion.

Attachment decision: **A recommended:** local file picker/preview, with clear “Attach this file in WhatsApp after opening the chat”; nothing uploaded to a server, file name can enter message. **B:** hosted upload with secure reference link, requiring chosen provider, retention policy and privacy copy. A is simpler for this demo. Neither should claim automatic attachment. No live enquiry is sent by the wireframe.

## Two visual options
**A — Dark catalogue gallery (recommended).** Warm charcoal framing, ivory text, restrained gold controls, serif headings, large isolated craft images. Closest to supplied cover/poster and gives warm relief imagery room.

**B — Ivory editorial catalogue.** Ivory reading surfaces dominate; dark hero/header and gold accents anchor identity. Better for dense specifications, with a lighter overall impression. Same hierarchy and conversion route.

## Proposed tokens for A
| Role | Value |
|---|---|
| Canvas / raised surface | `#191714` / `#25211C` |
| Primary / secondary text | `#F4EFE5` / `#C8BFAF` |
| Gold / hover gold | `#D3AE68` / `#E2C58D` |
| Button text | `#191714` |
| Divider | `#51483A` (decorative only) |
| Input outline / focus | `#9B8B70` / `#F0CF86` |
| Error | `#FFB4A8`, plus written error message |

Colours are proposed interpretations of the collateral, not claimed exact logo swatches. Option B uses `#F4EFE5` canvas with `#191714` text.

Typography: proposed Georgia display + Arial body (two locally available families, no font downloads). Georgia echoes the catalogue serif; Arial keeps compact forms/navigation clear. Display 40/44px mobile, 72/76 desktop; H2 30/36 → 48/54; H3 24/30; body 16/26; lead 18/28; labels 14/20. Avoid tiny uppercase body copy.

Spacing scale: 4, 8, 12, 16, 24, 32, 48, 64, 96, 128px. Sections 64px mobile / 112px desktop. Container max 1280px, gutters 20px mobile / 48px desktop. Four-column mobile and 12-column desktop grid, gaps 16/24px; content collapses naturally below 768px. Text max 62ch.

Controls: minimum 48px target; 4px radius, solid gold primary, outlined secondary, visible 3px focus with offset. Forms use permanent labels, 16px input text, inline errors linked by aria-describedby; required state explained in text. Single mobile column, two desktop columns only for short related fields. No placeholder-only labels.

Images: portrait art usually 4:5 or intrinsic ratio; wide scenes only if source resolution supports crop. Never crop essential motifs or upscale tiny views. Reserve dimensions; load hero eagerly and lower images lazily; original source mapping retained. Caption “Catalogue illustration”.

Motion: Framer Motion proposed for build. Hero opacity/12px translate 400ms; reveals 300ms once; hover scale ≤1.025 over 200ms; filter transition 180ms; no parallax, autoplay or scroll hijack. Reduced motion removes translation, zoom and animation; comparison remains usable. Only transform/opacity for animated content.

## Review and verification
Wireframe: `wireframes/index.html`, responsive mobile-first Home and Quote with selectable A/B colour direction. Deliberately low fidelity; placeholders identify image slots and unconfirmed ordering content. Submit previews a message locally and does not contact the business. Final page-level browser tests and Lighthouse occur during build; no performance scores claimed here.
