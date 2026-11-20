export function Icon({
  name,
  className = "",
}: {
  name: "arrow" | "chat" | "phone" | "menu" | "close";
  className?: string;
}) {
  const paths = {
    arrow: (
      <>
        <path d="M5 12h14M13 6l6 6-6 6" />
      </>
    ),
    chat: (
      <>
        <path d="M20 11.5a8 8 0 0 1-8 8 9 9 0 0 1-3.5-.8L4 20l1.3-4.5A8 8 0 1 1 20 11.5Z" />
        <path d="M8 11h8M8 14h5" />
      </>
    ),
    phone: (
      <path d="m7 3 3 4-2 3a15 15 0 0 0 6 6l3-2 4 3-1 4C10 22 2 14 3 4Z" />
    ),
    menu: (
      <>
        <path d="M4 8h16M4 16h16" />
      </>
    ),
    close: <path d="m6 6 12 12M6 18 18 6" />,
  };
  return (
    <svg
      className={"icon " + className}
      width="18"
      height="18"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="1.5"
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden="true"
      focusable="false"
    >
      {paths[name]}
    </svg>
  );
}
