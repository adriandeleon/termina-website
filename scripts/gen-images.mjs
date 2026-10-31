#!/usr/bin/env node
// Builds the raster images the site serves from the two sources it keeps by hand: the app's icon
// (public/termina-icon.svg, a copy of the Termina repo's branding/termina-icon.svg) and the
// screenshots under public/screenshots/.
//
// - favicon.png, apple-touch-icon.png and icon-512.png, from the icon;
// - a social card per key page (public/og/<slug>.png, and og-image.png as the shared one), drawn
//   as SVG in Caret & Ink and rasterised, as Editora's site does; a page opts in with
//   <Layout ogImage="og/<slug>.png">;
// - a WebP beside every PNG screenshot, which the pages offer first.
//
// sharp comes with Astro. Existing outputs are kept unless their source is newer.
//
//   node scripts/gen-images.mjs [--force]
import { mkdirSync, existsSync, readFileSync, readdirSync, statSync } from "node:fs";
import { join, dirname, extname } from "node:path";
import { fileURLToPath } from "node:url";
import sharp from "sharp";

const root = join(dirname(fileURLToPath(import.meta.url)), "..");
const pub = join(root, "public");
const force = process.argv.includes("--force");
const stale = (out, src) => force || !existsSync(out) || statSync(out).mtimeMs < statSync(src).mtimeMs;

let built = 0;
let skipped = 0;
const make = async (out, src, render) => {
  if (!stale(out, src)) return skipped++;
  mkdirSync(dirname(out), { recursive: true });
  await render(out);
  built++;
};

// --- icons ---
const iconPath = join(pub, "termina-icon.svg");
const icon = readFileSync(iconPath);
for (const [name, size] of [["favicon.png", 64], ["apple-touch-icon.png", 180], ["icon-512.png", 512]]) {
  await make(join(pub, name), iconPath, (out) => sharp(icon, { density: 300 }).resize(size, size).png().toFile(out));
}

// --- social cards ---
// Editora Dark (Caret & Ink), the site's default theme.
const C = { bg: "#1b1e2a", panel: "#232736", border: "#3a3f54", text: "#e8eaf3", accent: "#43dec5", faint: "#6c7288" };

const cards = [
  { slug: "default", kicker: "Terminal emulator", title: "One terminal for every desktop", out: "og-image.png" },
  { slug: "features", kicker: "Features", title: "Tabs, profiles, links and a palette" },
  { slug: "docs", kicker: "Documentation", title: "Termina documentation" },
  { slug: "keybindings", kicker: "Reference", title: "Every command and its key" },
  { slug: "blog", kicker: "Blog", title: "Notes from building Termina" },
  { slug: "news", kicker: "News", title: "What's new in Termina" },
];

const esc = (s) => s.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");

// Rough character-count wrap: the card font is fixed, so this is good enough and avoids a
// text-measurement dependency.
function wrap(text, max = 26, maxLines = 2) {
  const lines = [];
  let cur = "";
  for (const w of text.split(/\s+/)) {
    if ((cur + " " + w).trim().length > max && cur) {
      lines.push(cur);
      cur = w;
    } else cur = (cur + " " + w).trim();
  }
  if (cur) lines.push(cur);
  return lines.slice(0, maxLines);
}

// The icon is nested as an <image>; its own viewBox keeps it crisp at any size.
const iconHref = `data:image/svg+xml;base64,${icon.toString("base64")}`;

const card = ({ kicker, title }) => {
  const lines = wrap(title);
  const startY = 330 - (lines.length - 1) * 34;
  const tspans = lines.map((l, i) => `<tspan x="80" y="${startY + i * 68}">${esc(l)}</tspan>`).join("");
  return `<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="1200" height="630" viewBox="0 0 1200 630" font-family="Helvetica, Arial, sans-serif">
  <rect width="1200" height="630" fill="${C.bg}"/>
  <circle cx="1010" cy="120" r="300" fill="${C.accent}" opacity="0.10"/>
  <image x="72" y="62" width="68" height="68" href="${iconHref}" xlink:href="${iconHref}"/>
  <text x="152" y="107" fill="${C.text}" font-size="30" font-weight="700">Termina</text>
  <text x="80" y="196" fill="${C.accent}" font-size="21" font-weight="700" letter-spacing="2.5">${esc(kicker.toUpperCase())}</text>
  <text fill="${C.text}" font-size="58" font-weight="700">${tspans}</text>
  <rect x="80" y="536" width="132" height="4" rx="2" fill="${C.accent}"/>
  <text x="80" y="590" fill="${C.faint}" font-size="23">termina-project.dev</text>
</svg>`;
};

const self = fileURLToPath(import.meta.url);
for (const c of cards) {
  const out = join(pub, c.out ?? `og/${c.slug}.png`);
  await make(out, self, (o) => sharp(Buffer.from(card(c))).png().toFile(o));
}

// --- WebP beside each screenshot ---
function* pngs(dir) {
  if (!existsSync(dir)) return;
  for (const f of readdirSync(dir, { withFileTypes: true })) {
    const p = join(dir, f.name);
    if (f.isDirectory()) yield* pngs(p);
    else if (extname(f.name).toLowerCase() === ".png") yield p;
  }
}
for (const src of pngs(join(pub, "screenshots"))) {
  await make(src.replace(/\.png$/i, ".webp"), src, (o) => sharp(src).webp({ quality: 82 }).toFile(o));
}

console.log(`Images: built ${built}, kept ${skipped}.`);
