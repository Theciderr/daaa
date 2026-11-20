"use client";
import { useRef, useState } from "react";
import Image from "next/image";
import { Icon } from "../ui-icon";
import {
  buildMessage,
  validateQuote,
  whatsappUrl,
  requirements,
  QuoteValues,
} from "./message";
type Selected = {
  code: string;
  name: string;
  offer: string;
  image: string;
  width: number;
  height: number;
};
export function QuoteForm({
  selected,
  unknown,
}: {
  selected: Selected | null;
  unknown: boolean;
}) {
  const [design, setDesign] = useState(selected);
  const [values, setValues] = useState<QuoteValues>({
    name: "",
    phone: "",
    need: selected?.offer || "",
    size: "",
    material: "",
    message: "",
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [file, setFile] = useState<File | null>(null);
  const [fileError, setFileError] = useState("");
  const [prepared, setPrepared] = useState("");
  const [copyStatus, setCopyStatus] = useState("");
  const fileInput = useRef<HTMLInputElement>(null);
  const messageRef = useRef<HTMLTextAreaElement>(null);
  const change = (key: keyof QuoteValues, value: string) => {
    setValues((v) => ({ ...v, [key]: value }));
    setPrepared("");
    setCopyStatus("");
    setErrors((e) => ({ ...e, [key]: "" }));
  };
  const selectFile = (chosen: File | null) => {
    setPrepared("");
    setFile(null);
    setFileError("");
    if (!chosen) return;
    if (!/\.(jpe?g|png|webp|pdf)$/i.test(chosen.name)) {
      setFileError("Choose a JPG, PNG, WebP or PDF reference.");
      if (fileInput.current) fileInput.current.value = "";
      return;
    }
    setFile(chosen);
  };
  const removeDesign = () => {
    setDesign(null);
    setPrepared("");
    const url = new URL(location.href);
    url.searchParams.delete("design");
    history.replaceState({}, "", url);
  };
  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const invalid = validateQuote(values);
    setErrors(invalid);
    if (Object.keys(invalid).length) {
      document.getElementById("quote-" + Object.keys(invalid)[0])?.focus();
      return;
    }
    if (fileError) {
      fileInput.current?.focus();
      return;
    }
    const text = buildMessage(values, design, file?.name);
    setPrepared(text);
    setCopyStatus("");
    window.open(whatsappUrl(text), "_blank", "noopener,noreferrer");
  };
  const direct = whatsappUrl(
    buildMessage({ ...values, name: "", phone: "", need: "" }, design)
      .split("\n")
      .filter(
        (line) =>
          !line.startsWith("Name:") &&
          !line.startsWith("Phone:") &&
          !line.startsWith("Requirement:") &&
          !line.startsWith("Approximate") &&
          !line.startsWith("Material:"),
      )
      .join("\n"),
  );
  return (
    <>
      <div className="quote-heading">
        <p className="eyebrow">WHATSAPP ENQUIRY</p>
        <h1>Tell us what you have in mind.</h1>
        <p>
          A catalogue design, your own reference or a few words about your
          space. Size and material can stay undecided.
        </p>
        <a
          className="detail-link"
          href={direct}
          target="_blank"
          rel="noopener noreferrer"
        >
          Prefer to chat directly? Open WhatsApp <Icon name="arrow" />
        </a>
      </div>
      <div className="quote-layout">
        <div>
          {unknown && (
            <p className="quote-notice" role="status">
              That catalogue code was not found. Choose a design or tell us
              about your own reference.
            </p>
          )}
          {design ? (
            <div className="selected-design">
              <Image
                src={design.image}
                width={design.width}
                height={design.height}
                sizes="100px"
                alt={design.name + " catalogue illustration"}
              />
              <div>
                <small>Selected design · catalogue illustration</small>
                <h2>{design.name}</h2>
                <p>{design.code}</p>
                <div className="row">
                  <a href={"/gallery?design=" + design.code}>Change design</a>
                  <button type="button" onClick={removeDesign}>
                    Remove
                  </button>
                </div>
              </div>
            </div>
          ) : (
            <div className="quote-notice">
              <p>No catalogue design selected. Your own idea is welcome.</p>
              <a href="/gallery">
                Choose a catalogue design <Icon name="arrow" />
              </a>
            </div>
          )}
          <form noValidate onSubmit={submit} className="quote-form">
            <p className="form-hint">
              Name, phone and requirement are required.
            </p>
            <div className="form-grid">
              <label htmlFor="quote-name">
                Name <span className="required-label">Required</span>
                <input
                  id="quote-name"
                  name="name"
                  autoComplete="name"
                  required
                  maxLength={100}
                  value={values.name}
                  onChange={(e) => change("name", e.target.value)}
                  aria-invalid={!!errors.name}
                  aria-describedby={errors.name ? "name-error" : undefined}
                />
                {errors.name && (
                  <span className="field-error" id="name-error">
                    {errors.name}
                  </span>
                )}
              </label>
              <label htmlFor="quote-phone">
                Phone <span className="required-label">Required</span>
                <input
                  id="quote-phone"
                  name="phone"
                  type="tel"
                  inputMode="tel"
                  autoComplete="tel"
                  required
                  maxLength={40}
                  value={values.phone}
                  onChange={(e) => change("phone", e.target.value)}
                  aria-invalid={!!errors.phone}
                  aria-describedby={errors.phone ? "phone-error" : "phone-help"}
                />
                {errors.phone ? (
                  <span className="field-error" id="phone-error">
                    {errors.phone}
                  </span>
                ) : (
                  <small id="phone-help">
                    Include country code if outside India.
                  </small>
                )}
              </label>
            </div>
            <label htmlFor="quote-need">
              What do you need? <span className="required-label">Required</span>
              <select
                id="quote-need"
                name="need"
                required
                value={values.need}
                onChange={(e) => change("need", e.target.value)}
                aria-invalid={!!errors.need}
                aria-describedby={errors.need ? "need-error" : undefined}
              >
                <option value="">Choose a requirement</option>
                {requirements.map((r) => (
                  <option key={r}>{r}</option>
                ))}
              </select>
              {errors.need && (
                <span className="field-error" id="need-error">
                  {errors.need}
                </span>
              )}
            </label>
            <div className="form-grid">
              <label htmlFor="quote-size">
                Approximate size{" "}
                <span className="optional-label">Optional</span>
                <input
                  id="quote-size"
                  name="size"
                  maxLength={120}
                  value={values.size}
                  onChange={(e) => change("size", e.target.value)}
                  placeholder="Width × height, with units"
                  aria-describedby="size-help"
                />
                <small id="size-help">
                  Use ft, inches or mm. Leave blank if unsure.
                </small>
              </label>
              <label htmlFor="quote-material">
                Material <span className="optional-label">Optional</span>
                <select
                  id="quote-material"
                  name="material"
                  value={values.material}
                  onChange={(e) => change("material", e.target.value)}
                >
                  <option value="">Not sure, please advise</option>
                  <option>MDF</option>
                  <option>HDF</option>
                  <option>HDHMR</option>
                  <option>Customer-supplied sheet, review required</option>
                  <option>Other, see message</option>
                </select>
              </label>
            </div>
            <div className="reference-field">
              <label htmlFor="quote-file">
                Photo or design <span className="optional-label">Optional</span>
              </label>
              <input
                ref={fileInput}
                id="quote-file"
                type="file"
                accept="image/jpeg,image/png,image/webp,application/pdf"
                onChange={(e) => selectFile(e.target.files?.[0] || null)}
                aria-invalid={!!fileError}
                aria-describedby={fileError ? "file-error" : "file-help"}
              />
              <p id="file-help">
                JPG, PNG, WebP or PDF. This file stays on your device. Attach it
                manually in WhatsApp after opening the chat.
              </p>
              {fileError && (
                <p className="field-error" id="file-error" role="alert">
                  {fileError}
                </p>
              )}
              {file && (
                <div className="file-summary" role="status">
                  <span>{file.name}</span>
                  <button
                    type="button"
                    onClick={() => {
                      selectFile(null);
                      if (fileInput.current) fileInput.current.value = "";
                    }}
                  >
                    Remove file
                  </button>
                </div>
              )}
            </div>
            <label htmlFor="quote-message">
              Message <span className="optional-label">Optional</span>
              <textarea
                id="quote-message"
                name="message"
                maxLength={1200}
                value={values.message}
                onChange={(e) => change("message", e.target.value)}
                placeholder="Intended use, quantity, finish or anything else we should know."
              />
            </label>
            <button type="submit" className="button primary">
              <Icon name="chat" />
              Get a Quote on WhatsApp
            </button>
            <p className="form-hint">
              Opens your prepared message in WhatsApp. Review it and press Send
              there.{file ? " Remember to attach your selected file." : ""}
            </p>
            <p className="form-hint">
              This form does not upload files or save your details on this
              website.
            </p>
          </form>
          {prepared && (
            <div
              className="prepared-message"
              role="region"
              aria-label="Prepared WhatsApp message"
            >
              <h2>Your message is ready.</h2>
              <p role="status">
                If WhatsApp did not open, use the link below or copy the
                message. Your enquiry is sent only when you press Send in
                WhatsApp.
              </p>
              {file && (
                <p className="attachment-reminder">
                  Attach <strong>{file.name}</strong> in the WhatsApp chat.
                </p>
              )}
              <label htmlFor="prepared-text">
                Prepared message
                <textarea
                  ref={messageRef}
                  id="prepared-text"
                  readOnly
                  value={prepared}
                />
              </label>
              <div className="row">
                <a
                  className="button primary"
                  href={whatsappUrl(prepared)}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Open WhatsApp <Icon name="arrow" />
                </a>
                <button
                  type="button"
                  onClick={async () => {
                    try {
                      await navigator.clipboard.writeText(prepared);
                      setCopyStatus("Message copied. Paste it into WhatsApp.");
                    } catch {
                      messageRef.current?.focus();
                      messageRef.current?.select();
                      setCopyStatus(
                        "Message selected. Use your device’s Copy command.",
                      );
                    }
                  }}
                >
                  Copy message
                </button>
              </div>
              <p role="status">{copyStatus}</p>
            </div>
          )}
        </div>
        <aside className="quote-aside">
          <h2>What helps us review it?</h2>
          <p>
            A reference, intended use and approximate size. Add quantity and
            finish if you know them.
          </p>
          <h3>Material undecided?</h3>
          <p>
            Leave it open. Board, thickness, carve depth and finish are
            confirmed for the design and setting.
          </p>
          <a href="/gallery?view=custom-work">
            See custom work &amp; materials <Icon name="arrow" />
          </a>
          <div className="quote-local">
            <h3>Talk to Akash Sharma</h3>
            <p>
              Lakhnaur Sahib, Ambala
              <br />
              Haryana, India
            </p>
            <a href="tel:+918950917008">
              <Icon name="phone" /> +91 89509 17008
            </a>
          </div>
        </aside>
      </div>
    </>
  );
}
