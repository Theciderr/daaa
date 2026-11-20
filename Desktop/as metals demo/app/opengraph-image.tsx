import { ImageResponse } from "next/og";
export const alt =
  "A.S Metal Fabricators, custom CNC cutting and carving in Ambala";
export const size = { width: 1200, height: 630 };
export const contentType = "image/png";
export default function Image() {
  return new ImageResponse(
    <div
      style={{
        width: "100%",
        height: "100%",
        background: "#191a18",
        color: "#efeee6",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        padding: "70px",
        borderTop: "12px solid #cfb782",
      }}
    >
      <div style={{ display: "flex", fontSize: 28, color: "#cfb782" }}>
        A.S METAL FABRICATORS
      </div>
      <div
        style={{
          display: "flex",
          fontSize: 76,
          lineHeight: 1.1,
          maxWidth: 1000,
        }}
      >
        Custom CNC cutting &amp; carving.
      </div>
      <div style={{ display: "flex", fontSize: 30, color: "#b6b5ad" }}>
        Jali · Carved doors · Relief panels · Sculpted art
      </div>
      <div style={{ display: "flex", fontSize: 24 }}>
        Lakhnaur Sahib, Ambala · +91 89509 17008
      </div>
    </div>,
    size,
  );
}
