import Image from "next/image";

export function Brand({ footer = false }: { footer?: boolean }) {
  return (
    <a
      href="/"
      className={"brand brand-image" + (footer ? " footer-brand" : "")}
      aria-label="A.S Metal Fabricators home"
    >
      <span className="logo-crop">
        <Image
          src="/brand/as-metal-fabricators-gold.png"
          className="logo-art"
          width={2172}
          height={724}
          sizes={footer ? "300px" : "330px"}
          alt="A.S Metal Fabricators"
          loading={footer ? "lazy" : "eager"}
        />
      </span>
    </a>
  );
}
