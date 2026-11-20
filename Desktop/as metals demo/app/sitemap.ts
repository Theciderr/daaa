import type { MetadataRoute } from "next";
import { siteUrl } from "./site";
export default function sitemap(): MetadataRoute.Sitemap {
  return ["", "/gallery", "/quote", "/contact"].map((path) => ({
    url: siteUrl + path,
  }));
}
