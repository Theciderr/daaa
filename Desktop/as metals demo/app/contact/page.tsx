import { pageMetadata } from "../site";
import type { Metadata } from "next";
import { Header } from "../components";
import { Footer } from "../footer";
import { Icon } from "../ui-icon";

export const metadata: Metadata = pageMetadata(
  "About & Contact | A.S Metal Fabricators, Lakhnaur Sahib, Ambala",
  "Contact Akash Sharma at A.S Metal Fabricators in Lakhnaur Sahib, Ambala for designer jali, CNC-carved doors, relief panels and custom CNC work. Call +91 89509 17008.",
  "/contact",
);
const directions =
  "https://wa.me/918950917008?text=" +
  encodeURIComponent(
    "Hello A.S Metal Fabricators, please share your workshop location and visiting hours.",
  );

export default function Contact() {
  return (
    <>
      <a className="skip" href="#main">
        Skip to content
      </a>
      <Header current="contact" />
      <main className="wrap" id="main">
        <section className="contact-intro">
          <div>
            <p className="eyebrow">LAKHNAUR SAHIB · AMBALA</p>
            <h1>Let’s talk about your space.</h1>
            <p>
              Designer jali, carved doors and sculpted wall panels, made to your
              specifications.
            </p>
            <div className="row">
              <a className="button primary" href="/quote">
                <Icon name="chat" />
                Get a Quote on WhatsApp
              </a>
              <a className="button" href="tel:+918950917008">
                <Icon name="phone" />
                Call +91 89509 17008
              </a>
            </div>
          </div>
          <div className="contact-person">
            <span className="contact-label">YOUR CONTACT</span>
            <h2>Akash Sharma</h2>
            <p>Proprietor · A.S Metal Fabricators</p>
            <a href="tel:+918950917008" className="contact-number">
              +91 89509 17008 <Icon name="phone" />
            </a>
            <a
              className="contact-social"
              href="https://www.instagram.com/asmetalfab/"
              target="_blank"
              rel="noopener noreferrer"
            >
              Instagram · @asmetalfab <Icon name="arrow" />
            </a>
          </div>
        </section>
        <section className="contact-about">
          <div>
            <h2>
              Custom CNC work.
              <br />A local conversation.
            </h2>
          </div>
          <div>
            <p>
              A.S Metal Fabricators is based in Lakhnaur Sahib, Ambala, Haryana.
              We offer custom 3-axis CNC cutting and carving for decorative
              jali, doors, relief wall panels and sculpted wall art.
            </p>
            <p>
              Choose a catalogue reference or share a sketch or photo of your
              own. Design, dimensions, board suitability and finish are reviewed
              for your requirement.
            </p>
            <nav
              className="contact-offerings"
              aria-label="Explore our offerings"
            >
              <a href="/gallery?offer=Jali%20%26%20partitions">
                Jali &amp; partitions <Icon name="arrow" />
              </a>
              <a href="/gallery?offer=Carved%20doors">
                Carved doors <Icon name="arrow" />
              </a>
              <a href="/gallery?offer=Relief%20wall%20panels">
                Relief wall panels <Icon name="arrow" />
              </a>
              <a href="/gallery?offer=Sculpted%20wall%20art">
                Sculpted wall art <Icon name="arrow" />
              </a>
            </nav>
            <a className="detail-link" href="/gallery?view=custom-work">
              Materials, finishes &amp; custom work <Icon name="arrow" />
            </a>
          </div>
        </section>
        <section className="contact-visit">
          <div>
            <h2>Find us in Lakhnaur Sahib.</h2>
            <address>Lakhnaur Sahib, Ambala</address>
            <p className="muted">
              Call for workshop directions before visiting.
            </p>
            <a
              className="button"
              href={directions}
              target="_blank"
              rel="noopener noreferrer"
            >
              Ask for directions <Icon name="arrow" />
            </a>
          </div>
          <div className="visit-details">
            <div>
              <h3>Working days</h3>
              <p className="working-days">Every day</p>
            </div>
            <div>
              <h3>Opening &amp; closing times</h3>
              <span className="pending-detail">To be confirmed</span>
              <p>Please call to confirm visiting hours.</p>
              <a className="detail-link" href="tel:+918950917008">
                <Icon name="phone" />
                +91 89509 17008
              </a>
            </div>
          </div>
        </section>
        <section className="compact-prompt">
          <div>
            <h2>Bring your idea into the conversation.</h2>
            <p>
              Share a design code or reference and your approximate dimensions.
              We can review the material and finish with you.
            </p>
          </div>
          <div className="row">
            <a className="button" href="/gallery">
              View Designs <Icon name="arrow" />
            </a>
            <a className="button primary" href="/quote">
              <Icon name="chat" />
              Get a Quote on WhatsApp
            </a>
          </div>
        </section>
      </main>
      <Footer />
      <nav className="bottom" aria-label="Quick contact">
        <a className="button primary" href="/quote">
          <Icon name="chat" />
          WhatsApp Quote
        </a>
        <a className="button" href="tel:+918950917008">
          <Icon name="phone" />
          Call
        </a>
      </nav>
    </>
  );
}
