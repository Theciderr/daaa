"use client";
import { useState } from "react";
export function Comparison() {
  const [value, setValue] = useState(50);
  return (
    <div className="process-comparison">
      <h2>Cut through or carve into?</h2>
      <p>
        Move the comparison handle to explore two different processes. These
        diagrams explain the section of a panel; they are not a before-and-after
        or technical drawing.
      </p>
      <div className="comparison-stage">
        <div className="comparison-layer comparison-relief">
          <strong>3D relief</strong>
          <svg viewBox="0 0 600 180" aria-hidden="true">
            <path
              d="M20 130 60 55 100 130 140 55 180 130 220 55 260 130 300 55 340 130 380 55 420 130 460 55 500 130 540 55 580 130V160H20Z"
              fill="currentColor"
            />
            <path d="M20 160H580" stroke="currentColor" />
          </svg>
          <span>Carve into the surface</span>
        </div>
        <div
          className="comparison-layer comparison-cut"
          style={{ clipPath: `inset(0 ${100 - value}% 0 0)` }}
        >
          <strong>2D cutting</strong>
          <svg viewBox="0 0 600 180" aria-hidden="true">
            <path
              d="M20 40H580V160H20Z M80 40V160H140V40Z M200 40V160H260V40Z M320 40V160H380V40Z M440 40V160H500V40Z"
              fill="currentColor"
              fillRule="evenodd"
            />
          </svg>
          <span>Openings through the sheet</span>
        </div>
        <div
          className="comparison-divider"
          style={{ left: value + "%" }}
          aria-hidden="true"
        />
      </div>
      <label htmlFor="process-compare">
        Compare 2D cutting and 3D relief
        <input
          id="process-compare"
          type="range"
          min="0"
          max="100"
          value={value}
          onChange={(e) => setValue(Number(e.target.value))}
          aria-valuetext={`${value}% of 2D cutting diagram revealed`}
        />
      </label>
      <div className="comparison-labels">
        <span>3D relief diagram</span>
        <span>2D cut-through diagram</span>
      </div>
      <details>
        <summary>Read the side-by-side explanation</summary>
        <div className="material-columns">
          <div>
            <h3>2D cutting</h3>
            <p>
              Openings pass through the sheet for decorative patterns and jali.
            </p>
          </div>
          <div>
            <h3>3D relief</h3>
            <p>
              The design is carved into a suitable surface, creating depth and
              texture.
            </p>
          </div>
        </div>
      </details>
      <p className="muted">
        Material and edge treatment require review for damp or sheltered
        settings.
      </p>
    </div>
  );
}
