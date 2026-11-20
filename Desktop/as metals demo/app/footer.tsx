import { Brand } from "./brand";
import { Icon } from "./ui-icon";
export function Footer() {
  return (
    <footer className="site-footer">
      <div className="wrap">
        <div className="footer-top">
          <div>
            <Brand footer />
            <p>
              Custom CNC cutting & carving.
              <br />
              Made to your specifications.
            </p>
            <address>
              Lakhnaur Sahib, Ambala
              <br />
              Haryana, India 133008
            </address>
            <a className="footer-phone" href="tel:+918950917008">
              +91 89509 17008
            </a>
          </div>
          <nav aria-label="Footer collections">
            <h3>Collections</h3>
            <a href="/gallery?offer=Jali%20%26%20partitions">
              Jali & partitions
            </a>
            <a href="/gallery?offer=Carved%20doors">Carved doors</a>
            <a href="/gallery?offer=Relief%20wall%20panels">
              Relief wall panels
            </a>
            <a href="/gallery?offer=Sculpted%20wall%20art">Sculpted wall art</a>
          </nav>
          <nav aria-label="Footer information">
            <h3>Explore</h3>
            <a href="/gallery">Full catalogue</a>
            <a href="/gallery?view=custom-work">Custom work & materials</a>
            <a href="/contact">About & contact</a>
            <a href="https://www.instagram.com/asmetalfab/">Instagram</a>
          </nav>
          <div>
            <h3>Have a project?</h3>
            <p>Share a design code, reference or requirement.</p>
            <a className="button primary" href="/quote">
              <Icon name="chat" />
              Get a Quote on WhatsApp
            </a>
            <details>
              <summary>PDF catalogues</summary>
              <a href="/catalogue/A.S.%20Metal%20Fabricators%20-%20Decorative%20CNC%20Panel%20Catalogue.pdf">
                Decorative panels · 43 MB
              </a>
              <br />
              <a href="/catalogue/A.S%20Metal%20Fabricators%20-%203D%20Relief%20Catalogue.pdf">
                3D relief · 33 MB
              </a>
            </details>
          </div>
        </div>
        <div className="footer-bottom">
          <small>© 2026 A.S Metal Fabricators</small>
          <small>
            Website by <a href="https://expandr.in">ExpandR</a>{" "}
            <span className="demo-tag">Demo</span>
          </small>
        </div>
      </div>
    </footer>
  );
}
