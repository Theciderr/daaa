import { pageMetadata } from "../site";
import type { Metadata } from "next";
import { Header } from "../components";
import { Footer } from "../footer";
import { QuoteForm } from "./quote-form";
import designs from "../gallery/designs.json";
export const metadata: Metadata = pageMetadata(
  "Request a WhatsApp Quote | A.S Metal Fabricators, Ambala",
  "Share a catalogue design, approximate dimensions or your own reference for custom jali, CNC-carved doors, relief panels and sculpted wall art.",
  "/quote",
);
export default async function Page({
  searchParams,
}: {
  searchParams: Promise<Record<string, string | string[] | undefined>>;
}) {
  const params = await searchParams;
  const code = typeof params.design === "string" ? params.design : "";
  const design = designs.find((d) => d.code === code);
  return (
    <>
      <a href="#main" className="skip">
        Skip to content
      </a>
      <Header current="quote" />
      <main id="main" className="wrap">
        <QuoteForm
          selected={
            design
              ? {
                  code: design.code,
                  name: design.name,
                  offer: design.offer,
                  image: design.image,
                  width: design.width,
                  height: design.height,
                }
              : null
          }
          unknown={!!code && !design}
        />
      </main>
      <Footer />
    </>
  );
}
