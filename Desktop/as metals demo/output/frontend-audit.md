# Frontend quality audit

**Project:** A.S Metal Fabricators  
**Audit date:** 21 November 2026  
**Scope:** Next.js App Router production pages `/`, `/gallery`, `/quote`, and `/contact`, shared header/footer, responsive CSS, motion, metadata, and existing audit evidence.  
**Audit mode:** Initial audit findings with a follow-up hardening pass recorded below.

## Design read

This is a redesign-preserve audit for a local fabrication showroom serving homeowners, designers, architects, and contractors. The accepted direction is premium, dark, catalogue-led industrial craft with restrained editorial typography and warm metal accents.

**Dials:** DESIGN_VARIANCE 7, MOTION_INTENSITY 5, VISUAL_DENSITY 4.

## Follow-up hardening

The v1 hardening pass applied and verified these audit actions:

- **H-02 resolved:** the shared header now uses an `IntersectionObserver` sentinel instead of a raw scroll listener.
- **M-01 resolved:** em-dash and en-dash characters were removed from application copy and metadata.
- **M-03 resolved:** gallery animation import failures are reported explicitly while preserving filter functionality.
- **M-04 resolved:** the unused partial light-theme block was removed; the accepted site contract remains dark-only.
- Added a zero-height-impact sentinel rule and reran typecheck, production build, responsive/axe/SEO checks, and gallery reduced-motion checks.

The mobile Lighthouse performance gate **remains open**: the previously measured scores are still Home 71, Gallery 83, Quote 81, and Contact 85. M-02, L-01, and L-02 remain maintainability follow-ups rather than blockers for this hardening pass.

## Anti-patterns verdict

**Pass, with targeted exceptions.** The site does not read as generic AI-generated work. It uses real catalogue imagery, a coherent dark stone/gold palette, a useful catalogue-to-quote path, restrained motion, explicit illustrative-image labels, and no purple SaaS gradients, fake testimonials, fake metrics, glassmorphism, or div-based product screenshots.

The main anti-pattern exceptions are:

- The page contains visible em-dash/en-dash characters, which fail the project's loaded anti-slop pre-flight rule.
- The stylesheet has a large accumulation of literal colors and duplicate token declarations, making the visual system harder to maintain.
- The sticky-header effect uses a React state update from every scroll event, which is a performance anti-pattern even though the visual effect is restrained.
- The accepted serif pairing is justified by the existing catalogue/brand direction and is not considered an AI tell here.
- The four collection links are content-driven and do not count as a generic three-card feature row.

## Executive summary

| Severity | Count |
|---|---:|
| Critical | 0 |
| High | 2 |
| Medium | 4 |
| Low | 3 |

### Top issues

1. Mobile Lighthouse performance is below the project target on every audited route: Home 71, Gallery 83, Quote 81, Contact 85.
2. `Header` updates React state from a raw `scroll` listener on every scroll event.
3. Visible em-dash/en-dash characters remain in user-facing strings and metadata.
4. Most component styling bypasses the small set of design tokens with literal colors, while a second token block overrides the first.
5. Gallery's lazy motion import catches and discards failures, hiding a runtime degradation.

### Overall quality score

**7.8/10 for the current phase.** Accessibility, semantic structure, content honesty, responsive layout, and SEO evidence are strong. Performance and final design-system cleanup prevent final handover.

## Verification performed

- `npm run typecheck`: passed.
- `node scripts/final-browser-check.cjs`: passed at 375px and 1280px for all four routes.
- `node scripts/check-filter-motion.cjs`: passed, including reduced-motion behavior and filter counts.
- Browser checks found no page errors, no horizontal overflow, one H1 per page, no missing image alt attributes, and no axe WCAG/best-practice violations in the exercised pages.
- Lighthouse evidence in `output/lighthouse/summary.json` reports performance scores of 71/83/81/85 for Home/Gallery/Quote/Contact. Accessibility, best practices, and SEO were 100 on all four routes.
- The current browser script does not exercise a real operating-system file picker. Quote attachment behavior was covered with synthetic `File`/`DataTransfer` fixtures in the existing verification evidence.

## Detailed findings

### Critical issues

None verified.

### High-severity issues

#### H-01: Mobile performance misses the explicit project target

- **Location:** `output/lighthouse/summary.json`; all four routes.
- **Severity:** High.
- **Category:** Performance.
- **Description:** Latest mobile Lighthouse scores are Home 71, Gallery 83, Quote 81, and Contact 85, below the required 90 target. Home has 3.3 s LCP; Gallery and Quote have 2.6 s LCP. Lighthouse flags main-thread work, LCP, unused JavaScript, back/forward-cache prevention, DOM size, and image delivery on one or more pages.
- **Impact:** Slower first interaction and delayed visual completion for the local-search/mobile audience. This is the only explicit quality gate currently failing.
- **Standard:** Project requirement: mobile Lighthouse >=90 in all four categories. Core Web Vitals target: LCP <2.5 s, INP <200 ms, CLS <0.1.
- **Recommendation:** Inspect the latest Lighthouse traces before making another change. Compare the inline-CSS experiment against the preserved baseline, reduce route-level client JavaScript and DOM work, and re-measure all four routes under the same isolated mobile profile. Do not claim completion from intermediate scores.
- **Suggested command:** `/optimize`

#### H-02: Raw scroll listener drives React state on every scroll event

- **Location:** `app/components.tsx:7-22`.
- **Severity:** High.
- **Category:** Performance.
- **Description:** `Header` registers `window.addEventListener("scroll", scroll, { passive: true })`; the callback reads `window.scrollY` and calls `setScrolled` for every scroll event.
- **Impact:** Repeated React updates can consume main-thread time, especially on mobile. The header shadow/condensed padding effect is small but global because `Header` renders on every route.
- **Standard:** Frontend motion guardrail: do not use raw scroll listeners for continuous UI updates; prefer IntersectionObserver, CSS scroll-driven behavior, or a batched motion primitive.
- **Recommendation:** Replace the continuous state update with an IntersectionObserver sentinel or a CSS-only sticky-header treatment. If a runtime observer remains necessary, ensure it only changes state when the threshold actually crosses and verify the result in a trace.
- **Suggested command:** `/optimize`

### Medium-severity issues

#### M-01: Visible em-dash and en-dash characters violate the loaded typography rule

- **Location:** `app/quote/quote-form.tsx:250-255`, `app/quote/message.ts:27,39`, `app/site.ts:30`, `app/opengraph-image.tsx:3`.
- **Severity:** Medium.
- **Category:** Content / design-system compliance.
- **Description:** User-facing options and generated messages include strings such as `Not sure — please advise`, `Customer-supplied sheet — review required`, and `Design: ... — ...`. Metadata and validation copy also contain em-dash/en-dash characters.
- **Impact:** Fails the project's explicit zero-dash pre-flight rule and creates inconsistent punctuation across visible UI, WhatsApp output, and social metadata.
- **Standard:** Loaded frontend-design rule 9.G: zero em-dash and separator en-dash characters.
- **Recommendation:** Rewrite with periods, commas, parentheses, or regular hyphens where a range/compound is actually intended. Re-run a repository-wide visible-string scan after editing.
- **Suggested command:** `/harden`

#### M-02: Design tokens are bypassed by widespread literal colors

- **Location:** `app/globals.css:621-1950`, plus inline SVG/style colors in `app/gallery/comparison.tsx:21` and `app/opengraph-image.tsx:12-34`.
- **Severity:** Medium.
- **Category:** Theming / maintainability.
- **Description:** The file defines semantic variables at `app/globals.css:3-8` and overrides them again at `app/globals.css:621-626`, but most later rules use literal hex values and alpha hex values instead of the variables.
- **Impact:** Contrast tuning, palette changes, and future light/dark support require editing many unrelated rules. Literal values can drift from the accepted stone/gold system.
- **Standard:** Design-system token consistency and color consistency lock.
- **Recommendation:** Consolidate the duplicate root declarations and promote repeated surface, border, text, muted, accent, focus, error, and overlay colors to semantic variables. Keep intentionally unique illustration/OG-image colors documented as exceptions.
- **Suggested command:** `/normalize`

#### M-03: Runtime failure from lazy gallery animation is silently discarded

- **Location:** `app/gallery/gallery.tsx:28-37`.
- **Severity:** Medium.
- **Category:** Reliability / performance.
- **Description:** The dynamic `framer-motion/dom/mini` import ends with `.catch(() => {})`.
- **Impact:** If the chunk fails to load, the filter still works but the animation silently disappears. This masks deployment/chunk failures and makes diagnosis harder.
- **Standard:** Project rule: surface errors explicitly; no broad silent catches or success-shaped fallbacks.
- **Recommendation:** Treat the animation as optional but observable. Remove the silent catch or report a narrowly scoped development/telemetry diagnostic while preserving the functional filter path.
- **Suggested command:** `/harden`

#### M-04: Dark-only implementation does not provide a complete theme contract

- **Location:** `app/globals.css:21-26`, `app/layout.tsx:5-18`.
- **Severity:** Medium.
- **Category:** Theming / accessibility.
- **Description:** A `body.light` token block exists, but there is no theme toggle, no `prefers-color-scheme` handling, and no code path that applies `body.light`. The shipped pages are effectively dark-only.
- **Impact:** The dead light token block suggests unsupported behavior and can mislead future contributors. Users who require a light presentation receive no supported alternative.
- **Context:** The brand brief explicitly accepts a dark warm-gold showroom, so this is not a brand-direction failure. It is a completeness and maintainability gap against the loaded skill's dual-mode guardrail.
- **Recommendation:** Either formally document dark-only as an intentional brand constraint and remove the unused light block, or implement and test a complete theme strategy. Do not leave a partial theme implementation.
- **Suggested command:** `/normalize`

### Low-severity issues

#### L-01: Filter control group has weak semantic grouping

- **Location:** `app/gallery/gallery.tsx:119-136`.
- **Severity:** Low.
- **Category:** Accessibility / semantics.
- **Description:** `.offering-tabs` is a `div` with `aria-label="Filter by offering"` and buttons using `aria-pressed`. The label is not attached to a semantic group role, heading, or fieldset.
- **Impact:** Axe does not report a violation, but some screen-reader users may not get the intended context before the toggle buttons.
- **Recommendation:** Add a visible or visually hidden group label with a semantic grouping pattern, or use a fieldset/legend if the controls are treated as a filter set. Preserve `aria-pressed` because these are filters, not tabs.
- **Suggested command:** `/harden`

#### L-02: Repeated CSS blocks and stale declarations increase override risk

- **Location:** `app/globals.css:1-620`, `app/globals.css:621-900`, and later component-specific overrides.
- **Severity:** Low.
- **Category:** Maintainability / theming.
- **Description:** Base rules are followed by refinement rules that redefine the same selectors, including `body`, `h1`, `.brand-image`, `.site-header`, `.button`, `.collection-grid`, `.site-footer`, and `button:disabled`.
- **Impact:** Correctness depends on source order rather than a single clear component contract. Future changes can unintentionally affect only one breakpoint or an earlier page.
- **Recommendation:** Consolidate rules by component after performance work is stable. Preserve the current visual output while removing dead or superseded declarations.
- **Suggested command:** `/normalize`

#### L-03: Final audit evidence should include the latest full browser pass after performance changes

- **Location:** `output/final/browser-checks.json`, `scripts/final-browser-check.cjs`, and the latest Lighthouse run.
- **Severity:** Low.
- **Category:** Verification.
- **Description:** The current browser and interaction scripts pass, but the final handover remains correctly blocked because the newest performance experiment changed resource-loading behavior. The final interaction script should be rerun after the next performance revision.
- **Impact:** Prevents stale evidence from being mistaken for final evidence.
- **Recommendation:** After the next approved optimization, rerun typecheck, production build, final browser checks, reduced-motion interaction checks, and Lighthouse under the same profile. Preserve the resulting reports separately from prior runs.
- **Suggested command:** `/optimize`

## Patterns and systemic issues

- **Token drift:** 128 literal color matches in `app/globals.css` indicate the component system is mostly hand-tuned rather than token-driven.
- **Runtime work in shared chrome:** the header scroll listener affects every route, so a small interaction cost is multiplied across the site.
- **Single-theme ambiguity:** the site is visually coherent as a dark showroom, but dead light-theme CSS creates an unfinished contract.
- **Punctuation compliance:** dash characters appear in generated WhatsApp text, metadata, and UI options, so a single copy rule is not enforced centrally.
- **Audit maturity:** the project has unusually strong evidence scripts and honest notes about what was not exercised. Keep that discipline after the next performance pass.

## Positive findings

- Real catalogue assets and source-backed descriptions lead the experience; no fabricated project claims, testimonials, prices, certifications, or performance promises were found.
- The catalogue-to-quote continuity is clear: design code and name are preserved into the WhatsApp message.
- The attachment flow is honest. The site does not pretend a local file was uploaded or silently transferred.
- `next/image` is used with explicit dimensions, responsive `sizes`, and eager loading for the hero image.
- Labels sit above form controls, required and optional states are explicit, errors are inline, invalid fields receive focus, and the prepared WhatsApp message has a recovery path.
- The current browser checks pass at 375px and 1280px with no horizontal overflow, no missing image alt text, no page errors, and one H1 per route.
- Native dialog behavior, Escape handling, focus return, browser Back handling, reduced-motion filter behavior, sitemap, robots, OG image, favicon, and 404 endpoints are covered by existing checks.
- The dark stone/gold visual language is consistent, restrained, and materially appropriate for the catalogue. It avoids the common purple/blue SaaS and glassmorphism defaults.
- The footer and mobile quick-contact bar provide clear conversion paths without inventing operational details.

## Recommendations by priority

### Immediate

1. Resolve the Lighthouse performance gap with trace-led work, starting from the latest versus intermediate reports.
2. Remove or replace the raw scroll listener in the shared header.
3. Re-run the full production browser and Lighthouse matrix after those changes.

### Short-term

1. Remove forbidden em-dash/en-dash characters from visible strings, generated messages, metadata, and social image copy.
2. Decide whether the site is intentionally dark-only. Remove dead light-theme CSS or implement a complete tested theme contract.
3. Replace the silent gallery animation catch with an explicit, narrowly scoped failure path.

### Medium-term

1. Consolidate `globals.css` into semantic tokens and component-level rules.
2. Improve filter-group semantics without changing the existing filter behavior.
3. Re-run the real OS file-picker test when the browser harness root configuration permits it.

### Long-term

1. Remove temporary audit tooling/dependencies only after preserving final reports and checking production dependency health.
2. Complete the provisional handover only when measured performance meets target and client-owned details remain clearly listed.

## Suggested command map

| Command | Findings addressed |
|---|---|
| `/optimize` | H-01, H-02, L-03 |
| `/normalize` | M-02, M-04, L-02 |
| `/harden` | M-01, M-03, L-01 |

## Pre-flight result

**Not ready for final handover.** Accessibility and responsive checks pass, but the measured mobile performance gate fails and the typography/token/runtime issues above remain. No production deployment should be claimed from this audit.
