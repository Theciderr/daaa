import type { Metadata } from "next";
export const siteUrl = (
  process.env.SITE_URL ||
  (process.env.VERCEL_PROJECT_PRODUCTION_URL
    ? "https://" + process.env.VERCEL_PROJECT_PRODUCTION_URL
    : "http://127.0.0.1:3005")
).replace(/\/$/, "");
export const siteName = "A.S Metal Fabricators";
export function pageMetadata(
  title: string,
  description: string,
  path: string,
): Metadata {
  return {
    title,
    description,
    alternates: { canonical: siteUrl + path },
    openGraph: {
      title,
      description,
      url: siteUrl + path,
      siteName,
      locale: "en_IN",
      type: "website",
      images: [
        {
          url: siteUrl + "/opengraph-image",
          width: 1200,
          height: 630,
          alt: "A.S Metal Fabricators, custom CNC cutting and carving in Ambala",
        },
      ],
    },
    twitter: {
      card: "summary_large_image",
      title,
      description,
      images: [siteUrl + "/opengraph-image"],
    },
  };
}
export const businessSchema = {
  "@context": "https://schema.org",
  "@type": "LocalBusiness",
  name: siteName,
  url: siteUrl,
  telephone: "+918950917008",
  logo: siteUrl + "/brand/as-metal-fabricators-gold.png",
  description:
    "Custom CNC cutting and carving, designer jali, CNC-carved doors, relief panels and sculpted wall art.",
  address: {
    "@type": "PostalAddress",
    addressLocality: "Lakhnaur Sahib, Ambala",
    addressRegion: "Haryana",
    postalCode: "133008",
    addressCountry: "IN",
  },
  sameAs: ["https://www.instagram.com/asmetalfab/"],
};
