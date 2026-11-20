import type { NextConfig } from "next";
const config: NextConfig = {
  experimental: { inlineCss: true },
  outputFileTracingRoot: process.cwd(),
  turbopack: { root: process.cwd() },
  images: { formats: ["image/avif", "image/webp"] },
  devIndicators: false,
};
export default config;
