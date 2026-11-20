"use client";
import { Icon } from "./ui-icon";
import { Brand } from "./brand";
import { useEffect, useRef, useState } from "react";
export function Header({ current = "home" }: { current?: string }) {
  const [open, setOpen] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const sentinel = useRef<HTMLDivElement>(null);
  useEffect(() => {
    const element = sentinel.current;
    const key = (e: KeyboardEvent) => {
      if (
        e.key === "Escape" &&
        document
          .getElementById("menu-button")
          ?.getAttribute("aria-expanded") === "true"
      ) {
        setOpen(false);
        document.getElementById("menu-button")?.focus();
      }
    };
    const observer =
      element && "IntersectionObserver" in window
        ? new IntersectionObserver(
            ([entry]) => setScrolled(!entry.isIntersecting),
            { threshold: 0 },
          )
        : null;
    if (observer && element) observer.observe(element);
    window.addEventListener("keydown", key);
    return () => {
      observer?.disconnect();
      window.removeEventListener("keydown", key);
    };
  }, []);
  return (
    <>
      <div ref={sentinel} className="header-sentinel" aria-hidden="true" />
      <header className={"site-header " + (scrolled ? "scrolled" : "")}>
        <div className="wrap">
          <Brand />
          <button
            id="menu-button"
            className="menu-toggle"
            aria-expanded={open}
            aria-controls="site-nav"
            onClick={() => setOpen(!open)}
          >
            <Icon name={open ? "close" : "menu"} />
            {open ? "Close" : "Menu"}
          </button>
          <nav
            id="site-nav"
            className={open ? "is-open" : ""}
            aria-label="Main navigation"
          >
            <a href="/" aria-current={current === "home" ? "page" : undefined}>
              Home
            </a>
            <a
              href="/gallery"
              aria-current={current === "gallery" ? "page" : undefined}
            >
              Catalogue
            </a>
            <a
              href="/gallery?view=custom-work"
              aria-current={current === "custom" ? "page" : undefined}
            >
              Custom work
            </a>
            <a
              href="/contact"
              aria-current={current === "contact" ? "page" : undefined}
            >
              About &amp; contact
            </a>
            <a
              className="button primary"
              href={current === "quote" ? "#main" : "/quote"}
              aria-current={current === "quote" ? "page" : undefined}
            >
              <Icon name="chat" />
              WhatsApp Quote
            </a>
          </nav>
        </div>
      </header>
    </>
  );
}
export function Reveal({ children }: { children: React.ReactNode }) {
  const ref = useRef<HTMLDivElement>(null);
  useEffect(() => {
    const element = ref.current;
    if (
      !element ||
      window.matchMedia("(prefers-reduced-motion: reduce)").matches ||
      !("IntersectionObserver" in window)
    )
      return;
    element.classList.add("reveal-ready");
    const observer = new IntersectionObserver(
      (entries) => {
        if (entries.some((e) => e.isIntersecting)) {
          element.classList.add("reveal-entered");
          observer.disconnect();
        }
      },
      { threshold: 0.1 },
    );
    observer.observe(element);
    return () => observer.disconnect();
  }, []);
  return <div ref={ref}>{children}</div>;
}
