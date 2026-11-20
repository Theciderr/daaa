import { pageMetadata } from "../site";
import type { Metadata } from "next";
import { Header } from "../components";
import { Footer } from "../footer";
import { Gallery } from "./gallery";
export const metadata: Metadata = pageMetadata(
  "Design Catalogue | Jali, Carved Doors & 3D Panels | A.S Metal Fabricators",
  "Browse 54 catalogue designs for jali, carved doors, relief panels and sculpted wall art. Custom CNC cutting and carving in Ambala.",
  "/gallery",
);
export default async function Page({
  searchParams,
}: {
  searchParams: Promise<Record<string, string | string[] | undefined>>;
}) {
  const query = await searchParams;
  const initial = Object.fromEntries(
    Object.entries(query).map(([k, v]) => [
      k,
      Array.isArray(v) ? v[0] : v || "",
    ]),
  );
  return (
    <>
      <a className="skip" href="#main">
        Skip to content
      </a>
      <Header current={initial.view === "custom-work" ? "custom" : "gallery"} />
      <main className="wrap" id="main">
        <Gallery initial={initial} />
      </main>
      <Footer />
      <nav className="bottom" aria-label="Quick contact">
        <a className="button primary" href="/quote">
          WhatsApp Quote
        </a>
        <a className="button" href="tel:+918950917008">
          Call
        </a>
      </nav>
    </>
  );
}
