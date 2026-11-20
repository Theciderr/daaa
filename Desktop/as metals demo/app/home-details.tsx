import Image from "next/image";
import { Icon } from "./ui-icon";
const quote = "/quote";
export function HomeDetails() {
  return (
    <>
      <section className="design-focus" id="catalogue-highlights">
        <div className="focus-copy">
          <h2>From open patterns to sculpted detail.</h2>
          <p>
            Explore geometric jali, architectural curves, botanical relief and
            wall-art compositions. Every reference has its own AMF code to make
            your enquiry specific.
          </p>
          <a className="button" href="/gallery">
            View Designs <Icon name="arrow" />
          </a>
          <div className="pdf-links">
            <a href="/catalogue/A.S.%20Metal%20Fabricators%20-%20Decorative%20CNC%20Panel%20Catalogue.pdf">
              Decorative panels PDF <span>29 designs · 43 MB</span>
            </a>
            <a href="/catalogue/A.S%20Metal%20Fabricators%20-%203D%20Relief%20Catalogue.pdf">
              3D relief PDF <span>25 designs · 33 MB</span>
            </a>
          </div>
        </div>
        <figure>
          <Image
            src="/research/catalogue/images/AMF-3DR-14-2.jpg"
            width={1507}
            height={1627}
            sizes="(min-width:800px) 50vw,100vw"
            loading="lazy"
            alt="Floral Sweep sculpted botanical wall relief catalogue illustration"
          />
          <figcaption>
            Floral Sweep · AMF-3DR-14 · Catalogue illustration
          </figcaption>
        </figure>
        <div className="focus-bottom">
          <p>Choose a catalogue reference or share a design of your own.</p>
          <a className="button primary" href={quote}>
            <Icon name="chat" />
            Get a Quote on WhatsApp
          </a>
        </div>
      </section>
      <section className="work-options" id="custom-work">
        <div>
          <h2>Made around your requirement.</h2>
          <p>
            Custom 3-axis CNC cutting and carving for your design, dimensions
            and intended use.
          </p>
          <a href="/gallery?view=custom-work" className="detail-link">
            Custom work &amp; materials <Icon name="arrow" />
          </a>
        </div>
        <dl>
          <div>
            <dt>Cut-through jali</dt>
            <dd>
              Openings routed through suitable sheet material for decorative
              panels and partitions.
            </dd>
          </div>
          <div>
            <dt>Surface relief</dt>
            <dd>
              Designs carved into suitable boards and door blanks. Carve depth
              is reviewed per project.
            </dd>
          </div>
          <div>
            <dt>Your board or reference</dt>
            <dd>
              Share a sketch or photo. For supplied material, include its type,
              grade, thickness and dimensions.
            </dd>
          </div>
          <div>
            <dt>CNC capacity</dt>
            <dd>
              4 × 8 ft and 6 × 12 ft machine beds. Finished dimensions are
              confirmed for each project.
            </dd>
          </div>
        </dl>
      </section>
      <section className="material-notes">
        <h2>The right board. The right finish.</h2>
        <div className="material-columns">
          <div>
            <h3>Board &amp; sheet options</h3>
            <p>
              MDF, HDF and HDHMR options, with thickness and suitability
              reviewed for the design.
            </p>
            <p className="muted">
              For ACP, aluminium and HPL Compact, customer-supplied sheets are
              preferred. Sourcing can be considered on request.
            </p>
          </div>
          <div>
            <h3>Finish options</h3>
            <ul>
              <li>Raw cut or carved board</li>
              <li>Primed, ready for painting</li>
              <li>Duco painting by arrangement</li>
              <li>Laminated boards for suitable 2D work</li>
              <li>Relief colour matching by request</li>
            </ul>
          </div>
        </div>
        <p className="material-footnote">
          Material, finish and suitability are confirmed for each order.
          Catalogue scenes are illustrative applications.
        </p>
      </section>
    </>
  );
}
