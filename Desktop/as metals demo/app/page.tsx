import { Footer } from "./footer";
import { Icon } from "./ui-icon";
import { HomeDetails } from "./home-details";
import Image from "next/image";
import { Header, Reveal } from "./components";
export const dynamic = "force-dynamic";
export default function Home() {
  return (
    <>
      <a className="skip" href="#main">
        Skip to content
      </a>
      {Date.now() < Date.parse("2026-11-24T00:00:00+05:30") && (
        <div className="notice" role="region" aria-label="Opening announcement">
          Grand opening · 24 November 2026
        </div>
      )}
      <Header />
      <main id="main" className="wrap">
        <section className="hero">
          <div>
            <p className="eyebrow">LAKHNAUR SAHIB · AMBALA</p>
            <h1>
              A distinctive detail.
              <br />
              Made for your space.
            </h1>
            <p>
              Designer jali, CNC-carved doors and sculpted wall panels, made to
              your specifications.
            </p>
            <div className="row">
              <a className="button primary" href="/quote">
                <Icon name="chat" />
                Get a Quote on WhatsApp
              </a>
              <a className="button" href="/gallery">
                View Designs
                <Icon name="arrow" />
              </a>
            </div>
          </div>
          <figure>
            <Image
              src="/research/catalogue/images/AMF-3DR-01-2.jpg"
              width={1507}
              height={1627}
              alt="Solstice radial relief design on paired doors"
              sizes="(min-width: 1200px) 560px, (min-width: 800px) 50vw, calc(100vw - 40px)"
              loading="eager"
              fetchPriority="high"
            />
            <figcaption>
              Solstice · AMF-3DR-01 · Catalogue illustration
            </figcaption>
          </figure>
        </section>
        <section id="choose">
          <Reveal>
            <div className="section-title">
              <div>
                <h2>Choose your collection.</h2>
              </div>
              <a href="/gallery">
                View all 54 designs
                <Icon name="arrow" />
              </a>
            </div>
            <div className="collection-grid">
              <a
                className="collection-link"
                href="/gallery?offer=Jali%20%26%20partitions"
              >
                <Image
                  src="/research/catalogue/images/AMF-GEO-01-2.jpg"
                  loading="lazy"
                  alt="Jali & partitions catalogue illustration"
                  width={1413}
                  height={1525}
                  sizes="(min-width: 1000px) 25vw, 50vw"
                />
                <h3>Jali & partitions</h3>
                <span>
                  View collection <Icon name="arrow" />
                </span>
              </a>
              <a
                className="collection-link"
                href="/gallery?offer=Carved%20doors"
              >
                <Image
                  src="/research/catalogue/images/AMF-3DR-01-2.jpg"
                  loading="lazy"
                  alt="Carved doors catalogue illustration"
                  width={1507}
                  height={1627}
                  sizes="(min-width: 1000px) 25vw, 50vw"
                />
                <h3>Carved doors</h3>
                <span>
                  View collection <Icon name="arrow" />
                </span>
              </a>
              <a
                className="collection-link"
                href="/gallery?offer=Relief%20wall%20panels"
              >
                <Image
                  src="/research/catalogue/images/AMF-3DR-17-2.jpg"
                  loading="lazy"
                  alt="Relief wall panels catalogue illustration"
                  width={1507}
                  height={1627}
                  sizes="(min-width: 1000px) 25vw, 50vw"
                />
                <h3>Relief wall panels</h3>
                <span>
                  View collection <Icon name="arrow" />
                </span>
              </a>
              <a
                className="collection-link"
                href="/gallery?offer=Sculpted%20wall%20art"
              >
                <Image
                  src="/research/catalogue/images/AMF-3DR-30-2.jpg"
                  loading="lazy"
                  alt="Sculpted wall art catalogue illustration"
                  width={1507}
                  height={1627}
                  sizes="(min-width: 1000px) 25vw, 50vw"
                />
                <h3>Sculpted wall art</h3>
                <span>
                  View collection <Icon name="arrow" />
                </span>
              </a>
            </div>
            <p className="muted">
              Catalogue illustrations. Material, finish and suitability
              confirmed per project.
            </p>
            <a className="button primary" href="/quote">
              <Icon name="chat" />
              Get a Quote on WhatsApp
            </a>
          </Reveal>
        </section>
        <HomeDetails />
        <section className="compact-prompt">
          <div>
            <h2>Your own design or material?</h2>
            <p>
              Explore custom CNC cutting, board options and finishes, or bring a
              reference directly.
            </p>
          </div>
          <div className="row">
            <a className="button" href="/gallery?view=custom-work">
              Custom work & materials
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
