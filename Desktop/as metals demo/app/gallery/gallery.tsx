"use client";
import { useEffect, useRef, useState } from "react";
import Image from "next/image";
import designs from "./designs.json";
import { Comparison } from "./comparison";
import { Icon } from "../ui-icon";
const offers = [
  "Jali & partitions",
  "Carved doors",
  "Relief wall panels",
  "Sculpted wall art",
];
const families = [...new Set(designs.map((d) => d.family))].sort();
const quote = (code = "") =>
  "/quote" + (code ? "?design=" + encodeURIComponent(code) : "");
export function Gallery({ initial }: { initial: Record<string, string> }) {
  const [state, setState] = useState(initial);
  const [copied, setCopied] = useState(false);
  const grid = useRef<HTMLDivElement>(null);
  const initialRender = useRef(true);
  useEffect(() => {
    if (initialRender.current) {
      initialRender.current = false;
      return;
    }
    if (window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;
    let cancelled = false;
    import("framer-motion/dom/mini")
      .then(({ animate }) => {
        if (!cancelled && grid.current)
          animate(grid.current, { opacity: [0.65, 1] }, { duration: 0.18 });
      })
      .catch((error: unknown) => {
        if (!cancelled) {
          console.error("Gallery filter animation unavailable.", error);
        }
      });
    return () => {
      cancelled = true;
    };
  }, [state.offer, state.family, state.q, state.page]);
  const dialog = useRef<HTMLDialogElement>(null);
  const trigger = useRef<HTMLElement | null>(null);
  const update = (patch: Record<string, string>, push = false) => {
    const next = { ...state, ...patch };
    const params = new URLSearchParams();
    Object.entries(next).forEach(([k, v]) => {
      if (v) params.set(k, v);
    });
    window.history[push ? "pushState" : "replaceState"](
      {},
      "",
      "/gallery" + (params.size ? "?" + params : ""),
    );
    setState(next);
    setCopied(false);
  };
  useEffect(() => {
    const pop = () =>
      setState(Object.fromEntries(new URLSearchParams(location.search)));
    window.addEventListener("popstate", pop);
    return () => window.removeEventListener("popstate", pop);
  }, []);
  const active = designs.find((d) => d.code === state.design);
  const custom = state.view === "custom-work";
  useEffect(() => {
    if (active && !custom) {
      if (!dialog.current?.open) dialog.current?.showModal();
      const old = document.body.style.overflow;
      document.body.style.overflow = "hidden";
      return () => {
        document.body.style.overflow = old;
      };
    } else {
      dialog.current?.close();
      trigger.current?.focus();
    }
  }, [active, custom]);
  const filtered = designs.filter(
    (d) =>
      (!state.offer || d.offer === state.offer) &&
      (!state.family || d.family === state.family) &&
      (!state.q ||
        (d.name + " " + d.code)
          .toLowerCase()
          .includes(state.q.trim().toLowerCase())),
  );
  const pages = Math.max(1, Math.ceil(filtered.length / 12));
  const page = Math.max(1, Math.min(pages, Number(state.page) || 1));
  function close() {
    update({ design: "" });
  }
  return (
    <>
      <div className="gallery-heading">
        <p className="eyebrow">A.S METAL FABRICATORS · AMBALA</p>
        <h1>
          {custom
            ? "Custom work, made around your idea."
            : "Find a design for your space."}
        </h1>
        <p>
          Jali, carved doors, relief panels and sculpted art. Choose a catalogue
          reference or bring your own design.
        </p>
        <div className="row">
          <a className="button primary" href={quote()}>
            <Icon name="chat" />
            Get a Quote on WhatsApp
          </a>
          <a
            className="button"
            href={custom ? "/gallery" : "/gallery?view=custom-work"}
          >
            {custom ? "View Designs" : "Custom work & materials"}
            <Icon name="arrow" />
          </a>
        </div>
      </div>
      {!custom ? (
        <>
          <div className="gallery-controls">
            <div className="offering-tabs" aria-label="Filter by offering">
              <button
                aria-pressed={!state.offer}
                onClick={() => update({ offer: "", page: "", design: "" })}
              >
                All designs · 54
              </button>
              {offers.map((o) => (
                <button
                  key={o}
                  aria-pressed={state.offer === o}
                  onClick={() => update({ offer: o, page: "", design: "" })}
                >
                  {o}
                </button>
              ))}
            </div>
            <div className="filter-row">
              <label>
                Search designs
                <input
                  type="search"
                  value={state.q || ""}
                  placeholder="Name or AMF code"
                  onChange={(e) =>
                    update({ q: e.target.value, page: "", design: "" })
                  }
                />
              </label>
              <label>
                Pattern family
                <select
                  value={state.family || ""}
                  onChange={(e) =>
                    update({ family: e.target.value, page: "", design: "" })
                  }
                >
                  <option value="">All families</option>
                  {families.map((f) => (
                    <option key={f}>{f}</option>
                  ))}
                </select>
              </label>
              <button
                onClick={() =>
                  update({ offer: "", family: "", q: "", page: "", design: "" })
                }
              >
                Clear filters
              </button>
            </div>
          </div>
          <div className="results-summary">
            <p role="status">
              {filtered.length} {filtered.length === 1 ? "design" : "designs"}
              {state.offer ? " · " + state.offer : ""}
            </p>
            <p>Catalogue illustrations · made to order</p>
          </div>
          {state.design && !active && (
            <p role="status">
              That design code was not found. Browse below or share your own
              reference.
            </p>
          )}
          {!filtered.length ? (
            <div className="empty-designs">
              <h2>No matching designs.</h2>
              <p>
                Try another name or code, clear the filters, or send us your own
                reference.
              </p>
              <button
                onClick={() =>
                  update({ offer: "", family: "", q: "", page: "" })
                }
              >
                Clear filters
              </button>
              <a className="button primary" href={quote()}>
                Get a Quote on WhatsApp
              </a>
            </div>
          ) : (
            <div
              ref={grid}
              className="design-grid"
              key={[state.offer, state.family, state.q, page].join("|")}
            >
              {filtered.slice((page - 1) * 12, page * 12).map((d) => (
                <article key={d.code} className="design-card">
                  <button
                    className="design-image"
                    onClick={(e) => {
                      trigger.current = e.currentTarget;
                      update({ design: d.code }, true);
                    }}
                    aria-label={"View " + d.name + " details"}
                  >
                    <Image
                      src={d.image}
                      width={d.width}
                      height={d.height}
                      sizes="(min-width:1000px) 30vw,(min-width:650px) 45vw,100vw"
                      alt={d.name + " catalogue illustration"}
                      loading="lazy"
                    />
                  </button>
                  <small>
                    {d.code} · {d.family}
                  </small>
                  <h2>
                    <button
                      onClick={(e) => {
                        trigger.current = e.currentTarget;
                        update({ design: d.code }, true);
                      }}
                    >
                      {d.name}
                      <Icon name="arrow" />
                    </button>
                  </h2>
                  <p>{d.offer}</p>
                </article>
              ))}
            </div>
          )}
          {pages > 1 && (
            <nav className="pagination" aria-label="Catalogue pages">
              <button
                disabled={page === 1}
                onClick={() => update({ page: String(page - 1) })}
              >
                Previous
              </button>
              <span>
                Page {page} of {pages}
              </span>
              <button
                disabled={page === pages}
                onClick={() => update({ page: String(page + 1) })}
              >
                Next
              </button>
            </nav>
          )}
          <section className="catalogue-downloads">
            <h2>Keep the catalogue handy.</h2>
            <div className="pdf-links">
              <a href="/catalogue/A.S.%20Metal%20Fabricators%20-%20Decorative%20CNC%20Panel%20Catalogue.pdf">
                Decorative panels PDF <span>29 designs · 43 MB</span>
              </a>
              <a href="/catalogue/A.S%20Metal%20Fabricators%20-%203D%20Relief%20Catalogue.pdf">
                3D relief PDF <span>25 designs · 33 MB</span>
              </a>
            </div>
          </section>
        </>
      ) : (
        <section className="custom-view">
          <div className="work-options">
            <div>
              <h2>Your reference. Your dimensions.</h2>
              <p>
                Send a sketch, reference photo or catalogue code for custom
                3-axis CNC cutting or surface carving.
              </p>
              <p>
                Machine beds: 4 × 8 ft and 6 × 12 ft. Finished sizes and carve
                depth are reviewed for each project.
              </p>
            </div>
            <div>
              <h3>Boards & sheet materials</h3>
              <p>
                MDF, HDF and HDHMR options depend on the design, thickness and
                intended use. For customer-supplied material, share type, grade,
                thickness and dimensions.
              </p>
              <p>
                For ACP, aluminium and HPL Compact, customer-supplied sheets are
                preferred. Sourcing can be considered on request.
              </p>
              <h3>Finishes</h3>
              <p>
                Raw or primed board; Duco painting by arrangement; suitable
                laminated 2D boards; relief colour matching by request.
              </p>
            </div>
          </div>
          <Comparison />
        </section>
      )}
      <section className="compact-prompt">
        <div>
          <h2>Have a design of your own?</h2>
          <p>
            Share a reference, intended use and approximate dimensions. Material
            undecided? Ask for guidance.
          </p>
        </div>
        <a className="button primary" href={quote()}>
          <Icon name="chat" />
          Get a Quote on WhatsApp
        </a>
      </section>
      <dialog
        ref={dialog}
        className="design-dialog"
        aria-labelledby="design-title"
        onCancel={(e) => {
          e.preventDefault();
          close();
        }}
        onClick={(e) => {
          if (e.target === dialog.current) close();
        }}
      >
        <div className="dialog-top">
          <span>Design reference</span>
          <button onClick={close} aria-label="Close design details">
            <Icon name="close" />
            Close
          </button>
        </div>
        {active && (
          <div className="design-detail">
            <figure>
              <Image
                src={active.image}
                width={active.width}
                height={active.height}
                sizes="(min-width:800px) 45vw,90vw"
                alt={active.name + " catalogue illustration"}
              />
              <figcaption>Catalogue illustration · not to scale</figcaption>
            </figure>
            <div>
              <p className="eyebrow">{active.code}</p>
              <h2 id="design-title">{active.name}</h2>
              <p>{active.description}</p>
              <p className="muted">
                {active.offer} · {active.family}
              </p>
              <h3>Suggested applications</h3>
              <p>{active.applications.join(" · ")}</p>
              <details>
                <summary>Material guidance from catalogue</summary>
                <p className="source-materials">{active.materials}</p>
              </details>
              <p>
                Size, material, thickness, finish and suitability are confirmed
                for your project.
              </p>
              <a className="button primary" href={quote(active.code)}>
                <Icon name="chat" />
                Get a Quote on WhatsApp
              </a>
              <div className="detail-tools">
                <button
                  onClick={async () => {
                    try {
                      await navigator.clipboard.writeText(location.href);
                      setCopied(true);
                    } catch {
                      setCopied(false);
                    }
                  }}
                >
                  {copied ? "Link copied" : "Copy design link"}
                </button>
                <a
                  href={
                    "/catalogue/" +
                    encodeURIComponent(active.source) +
                    "#page=" +
                    active.pdfPage
                  }
                >
                  View source PDF · page {active.pdfPage}
                </a>
              </div>
              <p className="muted">
                PDF page {active.pdfPage} · printed folio {active.printedPage}.
                Share the AMF code with your enquiry.
              </p>
            </div>
          </div>
        )}
      </dialog>
    </>
  );
}
