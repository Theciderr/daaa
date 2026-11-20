# Phase 2 — revised strategy, system and wireframes

## Latest navigation revision — authoritative over earlier section orders
User requests less scrolling and a professional footer. Home is now hero → compact four-offering grid → custom-work enquiry prompt. Primary View Designs navigates directly to Catalogue, not down the Home page. Four collection tiles go to their respective catalogue filters. No four full-height collection sections, standalone Home material FAQ or duplicated Home catalogue download section.

Global desktop navigation: Home / Catalogue / Custom work / About & contact / WhatsApp Quote. Mobile: brand + labelled Menu button, expanded links in normal flow, aria-expanded, Escape close, visible focus and persistent WhatsApp/Call bar outside quote form. Every view has the same navigation and footer.

Keep four main production routes: `/`, `/gallery`, `/quote`, `/contact`. Custom work uses `/gallery?view=custom-work`, containing capabilities, materials, finishes, 2D/3D explanation and FAQ; catalogue grid is the alternate `/gallery` view. Prototype services.html represents this navigable view, not an authorised extra production page. Selection/query state and browser back behaviour must survive view changes.

Professional footer: four desktop groups (identity/locality/phone; collections; explore/contact/Instagram; quote/PDF resources), with a two-column mobile link area and full-width identity/quote groups. Bottom rule separates copyright and small Website by ExpandR / Demo credit. No fake certification, legal links, hours or map destination. Hours/map placeholders remain confined to About/contact. PDF downloads are optional disclosures with sizes. Footer links navigate to actual prototype destinations and collection filters.

## Latest structural revision — supersedes earlier Home hierarchy
User accepts the visual identity and asks to prioritise structure, all offerings and catalogue coverage. Keep existing visual treatment; do not reopen visual-direction approval.

Home order is now: local product hero → four illustrated offering sections → full catalogue gateway and two source PDFs → custom cutting/carving, capacity, material and finish capabilities → 2D/3D explanation → material FAQs → reference-led quote/local contact. Each offering has a catalogue entry and a quote route. Catalogue depth is central rather than limited to two featured examples.

The four offers map to 54 unique source entries: jali/partitions 29; carved doors 11; relief wall panels 10; sculpted art 4. Door assignments use the source applications; these are browsing groupings, not an assertion that designs cannot be used elsewhere. Source family names remain secondary filters. Retain exactly one record per AMF code.

Catalogue page order: title/context → search by name/code → offering filter → pattern-family filter → result count and clear-filters action → full design grid → custom-reference CTA. Each result carries name/code, offering, source family, illustrative label and enquiry link. Empty results provide recovery and custom enquiry. In production, accessible design details add materials, finish and application notes from the exact source page before quoting. Four-route sitemap remains unchanged; the Phase 2 catalogue HTML is a structure prototype, not Phase 3 Gallery implementation.

Custom work is separate from catalogue collections: CNC cutting/carving on supplied or customer-provided suitable material; machine beds 4×8 and 6×12 ft; MDF/HDF/HDHMR; ACP/aluminium/HPL Compact subject to feasibility and preferred customer-supplied sheets; raw/primed/Duco and applicable laminated/colour-match options. No added welding, installation or unrestricted metal-fabrication promises.

Home offers PDF access with visible approximate file sizes (43 MB decorative, 33 MB relief), so mobile visitors can choose browsing without downloading large files. No automatic PDF loading. Original documents retain all source qualifications.

20 November 2026. Supersedes the first Phase 2 draft. User requested reconsideration of the whole strategy. All new choices remain proposals for Checkpoint 2.

## Positioning and conversion decision
Present A.S as a local custom CNC cutting and carving business with a usable design catalogue. The main customer decision is whether to browse for inspiration or bring an existing requirement. Both paths end in a WhatsApp conversation. Do not position the business as a turnkey interior contractor or imply that catalogue scenes are completed installations.

**Recommended: enquiry-first showroom.** A short Home establishes what can be made, helps visitors find a category, shows two detailed references and answers material questions. Someone arriving from Instagram with a reference can bypass browsing immediately. Someone from local search sees Ambala and the actual products before choosing a category. This recommendation is a hypothesis informed by Phase 1, not a measured conversion result.

Alternative: catalogue-first home with a dense searchable grid. Useful for repeat specifiers who already understand materials, but puts more selection work on homeowners. Keep that depth on Gallery. Third option, project-portfolio-first, is unsuitable until actual installation photography and project evidence are supplied.

## Visitors: three questions and answer order
| Visitor | Three questions | Page/section order |
|---|---|---|
| Homeowner | What can go in my home? Can it fit my space? How do I ask what it costs? | Home product/locality → category → illustrative design → size-aware Quote |
| Interior designer | What aesthetic directions exist? Can I supply a reference? What finish can be discussed? | Gallery category and code search → detail → finish note → Quote with reference |
| Architect | Is this through-cut or surface relief? What board/blank is suitable? Can my dimensions and technical drawing be reviewed? | Home comparison → material FAQ → Gallery detail/source specs → Quote |
| Contractor | What is supplied? Can I provide the sheet? Who confirms feasibility? | Home materials/finish → Quote with grade/thickness/dimensions → About/contact |

These are working user needs inferred from the brief; no interviews or conversion analytics exist yet. Contractor delivery/installation/timing questions are acknowledged as client-information gaps, not answered with invented promises.

## Sitemap and navigation
| Route | Purpose | Minimum content / exit |
|---|---|---|
| `/` Home | Orient and route | product/locality → category → examples → process distinction → material FAQs → quote |
| `/gallery` Designs | Help choose | service filters, real name/code search, source-backed details → Quote with selected code |
| `/quote` Quote | Assemble enquiry | optional selected design + brief form or direct WhatsApp → user sends |
| `/contact` About & contact | Establish local identity | Akash Sharma, locality, phone, Instagram; confirmed map and hours → WhatsApp |

Use Home / Designs / About & contact plus the primary quote button on desktop. Mobile: compact brand/menu, sticky WhatsApp Quote/Call. No service microsites, shopping cart or account. No separate About page without sufficient verified content. Within Gallery, filter by customer-facing offer; a design may belong to more than one appropriate collection. Retain catalogue family as secondary metadata, not the first navigation layer. A detail dialog must have close/Escape, focus return and a shareable design URL; preserve the filter on back navigation. Empty results offer clear filters and the custom-reference route.

## Home hierarchy: mobile first
1. **Opening notice + header.** Small, factual opening date. Proposed expiry at 00:00 Asia/Kolkata on 24 November: remove notice automatically; do not claim the shop has opened without confirmation.
2. **Hero.** Locality, concise headline, explicit product sentence, primary WhatsApp-quote route and View Designs. Text and primary action precede the illustration on mobile; desktop pairs copy with a portrait reference. No unverified claim or image text overlay. Mobile CTA should remain within the first 812px viewport.
3. **Choose what to make.** Four text rows: jali/partitions, carved doors, relief wall panels, sculpted art. Each opens a Gallery filter in production. Labels explain the product, not a vague “Explore” action.
4. **Two selected references.** One 2D, one 3D, with name, AMF code, intended format and quote action. Alternate image/copy on desktop; image then copy on mobile. Gallery holds the complete catalogue. Do not imply the two examples represent the entire range.
5. **Understand the difference.** 2D cut-through vs 3D relief with labelled schematic/image comparison. Slider is an educational reveal of different processes, never before/after. Keyboard range input, visible labels and static side-by-side option. Wireframe reserves diagram locations.
6. **Material questions.** Three compact disclosures: board options, customer-supplied material/reference, finishes. Show project-specific qualifications. Technical thickness tables belong in details, not above the first CTA.
7. **Bring your own idea.** Final quote prompt and verified local identity. Footer contains ExpandR credit and Demo tag.

How ordering works is intentionally outside this layout until the client confirms the steps. A pending internal note must not look like a real customer-facing section. Reserve a possible insertion after FAQs only when evidence exists.

## Quote flow and decisions
Browse path: Home category → Gallery → chosen design → `/quote?design=AMF-...` → complete form → open prepared WhatsApp message → customer presses Send.

Ready path: hero or header → Quote → plain WhatsApp button OR complete form. Direct WhatsApp remains available with no mandatory form, including the mobile sticky bar in production. General primary CTAs may link to Quote; page text must explicitly explain that this prepares a WhatsApp enquiry.

**Remove the previous extra review step.** Form submission should open WhatsApp as requested in the brief; WhatsApp itself provides message review. The wireframe displays a safe local preview instead so reviewers can inspect the text without starting a business conversation. No “sent”, “received” or booking confirmation is shown.

| Field | Behaviour |
|---|---|
| Selected design | Source code/name carried from Gallery; not required for custom work; allow change/remove in production |
| Name | Required; trim whitespace |
| Phone | Required; international format accepted; clear inline error rather than India-only assumptions |
| Requirement | Four verified offers, custom CNC requirement, or Not sure yet |
| Approx size | Optional width × height with units in one free-text field; blank means undecided |
| Material | Optional; defaults to not sure; board family or customer-supplied sheet for review |
| Photo/design | Optional; explicit file handling decision below |
| Message | Optional use, quantity, finish and other notes; no long forced technical questionnaire |

Production failure states: required fields get linked text errors and first-invalid focus; unknown AMF codes are not fabricated; encode WhatsApp text safely; retain form contents on return; offer copy message + phone if WhatsApp cannot be used. Treat opening/copying a message as an intent event, never a confirmed lead. Do not persist personal details to localStorage by default.

**Attachment choice requiring approval:** A (recommended for this demo): reference picker stays local, filename included in text, visible instruction to attach manually in WhatsApp. This changes the requested upload into a local reference selection and must be explicitly approved. B: actual hosted upload with a link in WhatsApp; needs storage provider, access rules, expiry, deletion and client approval. Do not quietly implement A as if it fulfilled a hosted upload. Neither method silently attaches a local file to a WhatsApp text link.

## Visual directions to choose
**A — Dark showroom, recommended.** Warm charcoal surfaces, restrained gold controls, ivory type, serif headings and warm catalogue illustrations. The distinctive element is a labelled, large reference paired with its exact design code and enquiry action. The catalogue's contrast and imagery do the work; no decorative patterns behind body text.

**B — Editorial catalogue.** Ivory reading surfaces, dark brand framing, restrained gold and the same source photography. More useful for specification-heavy reading, less aligned with the dark poster. The same wireframe can preview both; final brand header remains dark in production for B.

These are two coherent treatments, not approved designs. Wireframe uses system font proxies so it opens locally without network dependencies. Proposed final typography remains Georgia for headings and Arial for body: two familiar families, serif/sans contrast matches collateral, no font download or shift. If a more distinctive licensed/webfont pairing is wanted, review it before build rather than introducing it silently.

## Proposed design tokens and rules
| Token | A | B |
|---|---|---|
| Canvas | #191714 | #F4EFE5 |
| Raised surface | #25211C | #E8DFD0 |
| Primary text | #F4EFE5 | #191714 |
| Secondary text | #C8BFAF | #51483A |
| Primary button | #D3AE68 with #191714 text | same |
| Hover | #E2C58D | same |
| Input border | #9B8B70 | #756751 |
| Focus | #F0CF86 | #73501C |
| Error | #FFB4A8 + text | #8B2218 + text |

Interpretations of source colours, not measured logo swatches. Verify actual contrast in production for each surface/state. Never use pale gold body text on ivory.

Type: mobile H1 40/44, H2 32/38, H3 25/30; desktop H1 64/68, H2 44/50; body 16/26, lead 18/28, labels 14/20. 62ch body maximum; avoid uppercase paragraph copy.

Spacing: 4/8/12/16/24/32/48/64/88/112px. Mobile section spacing 56px, desktop 88px. Grid: 4 mobile columns, 12 desktop; 20px mobile outer gutter, 48px desktop, maximum outer container 1280px. 16px mobile/24px desktop gaps. Split layouts at 800px only if content fits. Hero 5:6 plus gap, features 1:1. On 375px no horizontal page scroll. Bottom bar has safe-area padding and reserved page space; suppress it while editing the quote form to avoid covering controls.

Buttons: >=48px height and >=44px target; 3px corner radius; one filled gold action; outlined/text secondary; visible 3px focus and offset. Forms: persistent labels, native selects, 16px input text, clear optional fields, no placeholder-only labels. Errors keep submitted values and explain recovery.

Image treatment: contain full motifs in wireframe; use only reviewed crops in build. Catalogue illustration caption remains adjacent. Preserve dimensions/aspect ratio; priority hero, lazy lower images. Selected sources inspected: Solstice AMF-3DR-01-2.jpg (1507×1627), Moroccan Star Lattice AMF-GEO-01-2.jpg (1413×1525). These support bounded portrait displays; they are not proof of installed projects. 560–720px detail assets are limited to small placements. Request originals for oversized crops. Do not synthesize missing craftsmanship.

Motion: Framer Motion proposed. Hero fade/12px entrance ≤400ms, reveals once 300ms, image hover scale ≤1.025/200ms, filters 180ms. Fixed-height header wrapper prevents layout shift while its visual treatment shrinks. No autoplay, parallax or scroll hijack. Reduced motion removes movement/zoom/transitions. Slider remains keyboard usable. Do not animate content needed to use the form.

## Wireframe scope and acceptance
`wireframes/index.html` is the review board: Home/Quote, 375/1280 width controls and A/B treatment. `home.html` and `quote.html` also open independently. Real images provide scale; remaining schematic blocks are deliberate low fidelity. The prior draft is archived as phase-2-v1.html.

Category links currently demonstrate prefilled quote requirements and are explicitly labelled as a prototype shortcut; production must route through Gallery filters. No Gallery/About production pages have been built. Actual logo extraction, full mobile menu, animation, schema, map and ordering process remain build/client tasks.

Checkpoint 2 approvals: recommended journey + sitemap/section hierarchy; visual direction A or B; manual local reference selection versus hosted upload. Unknown order stages, map pin, hours and genuine workshop/install photos remain Needed from client. Next step after go is Home only, followed by its checkpoint.
