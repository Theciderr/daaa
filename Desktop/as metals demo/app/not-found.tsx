import { Header } from "./components";
import { Footer } from "./footer";
export default function NotFound() {
  return (
    <>
      <Header current="" />
      <main className="wrap">
        <section>
          <h1>That page could not be found.</h1>
          <p>Browse the catalogue or tell us what you would like to make.</p>
          <div className="row">
            <a className="button" href="/gallery">
              View Designs
            </a>
            <a className="button primary" href="/quote">
              Get a Quote on WhatsApp
            </a>
          </div>
        </section>
      </main>
      <Footer />
    </>
  );
}
