export const requirements = [
  "Jali & partitions",
  "Carved doors",
  "Relief wall panels",
  "Sculpted wall art",
  "Custom CNC requirement",
  "Not sure yet",
];
export type QuoteValues = {
  name: string;
  phone: string;
  need: string;
  size: string;
  material: string;
  message: string;
};
export function validateQuote(values: QuoteValues) {
  const errors: Record<string, string> = {};
  if (!values.name.trim()) errors.name = "Enter your name.";
  const digits = values.phone.replace(/[^0-9]/g, "");
  if (
    !/^[+\d\s().-]+$/.test(values.phone) ||
    digits.length < 7 ||
    digits.length > 15
  )
    errors.phone =
      "Enter a phone number with 7-15 digits. Include your country code if outside India.";
  if (!requirements.includes(values.need))
    errors.need = "Choose what you need, or select Not sure yet.";
  return errors;
}
export function buildMessage(
  values: QuoteValues,
  design?: { code: string; name: string } | null,
  fileName?: string,
) {
  return [
    "Hello A.S Metal Fabricators, I would like a quote.",
    design ? "Design: " + design.code + " - " + design.name : null,
    "Name: " + values.name.trim(),
    "Phone: " + values.phone.trim(),
    "Requirement: " + values.need,
    "Approximate size: " + (values.size.trim() || "Not sure yet"),
    "Material: " + (values.material.trim() || "Please advise"),
    values.message.trim() ? "Message: " + values.message.trim() : null,
    fileName ? "Reference to attach in WhatsApp: " + fileName : null,
  ]
    .filter(Boolean)
    .join("\n");
}
export const whatsappUrl = (message: string) =>
  "https://wa.me/918950917008?text=" + encodeURIComponent(message);
