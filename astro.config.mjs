// @ts-check
import { defineConfig } from "astro/config";
import sitemap from "@astrojs/sitemap";
import pagefind from "astro-pagefind";
import { site, base, href } from "./src/lib/site.ts";
import rehypeDocFigures from "./src/lib/rehype-doc-figures.mjs";
import { readdirSync } from "node:fs";
import { docsBase } from "./src/lib/termina.ts";

// The user guide is served under its version, /docs/v-<version>/… (DOCS-03, Pauta 6.0.1), which
// is what the app of that version opens. Bare /docs and every unversioned slug redirect to the
// current version, so a link in a page, in the README or in an older app never carries one and
// a release changes one generated file (src/lib/version.ts). A static build writes each redirect
// as a small page that forwards.
const docSlugs = readdirSync(new URL("./src/content/docs", import.meta.url))
  .filter((f) => f.endsWith(".md") && f !== "introduction.md")
  .map((f) => f.replace(/\.md$/, ""));
const docRedirects = Object.fromEntries([
  ["/docs", `${docsBase}/`],
  ["/docs/commands", `${docsBase}/commands/`],
  ...docSlugs.map((slug) => [`/docs/${slug}`, `${docsBase}/${slug}/`]),
]);

// Markdown is written with plain "/docs/links"; this puts such a link under the version, so the
// reader goes straight there and not through a redirect.
function rehypeVersionDocs() {
  return (tree) => {
    const rewrite = (node) => {
      const h = node.type === "element" && node.properties ? node.properties.href : undefined;
      if (typeof h === "string") {
        if (h === "/docs" || h === "/docs/") node.properties.href = `${docsBase}/`;
        else if (h.startsWith("/docs/") && !h.startsWith("/docs/v-")) node.properties.href = docsBase + h.slice("/docs".length);
      }
      if (node.children) for (const c of node.children) rewrite(c);
    };
    rewrite(tree);
  };
}

// Served at the root of termina-project.dev (src/lib/site.ts), so `base` is "/" and this is a
// pass-through; it is kept so moving under a path again stays a one-file change. Templates build links with href(); Markdown is authored with plain root-relative links
// ("/docs/links", "/features/tabs") and this rewrites them under `base` at build time, so content
// never names the base and moving to a domain changes one file.
function rehypeBaseLinks() {
  return (tree) => {
    const rewrite = (node) => {
      if (node.type === "element" && node.properties) {
        for (const attr of ["href", "src"]) {
          const v = node.properties[attr];
          if (typeof v === "string" && v.startsWith("/") && !v.startsWith("//") && !v.startsWith(base + "/"))
            node.properties[attr] = href(v);
        }
      }
      if (node.children) for (const c of node.children) rewrite(c);
    };
    rewrite(tree);
  };
}

// Only the current version is served. Before the version after 0.15.0 is generated, either keep
// the old pages (Editora's site archives them under src/content/docs-archive/<version>/ and serves
// both) or add a redirect from each /docs/v-<old>/… to the current one here: an app of the old
// version still opens those addresses.
export default defineConfig({
  site,
  base,
  redirects: docRedirects,
  markdown: {
    // Figures first: it reads each picture's size from public/ by the path the Markdown gives.
    rehypePlugins: [rehypeDocFigures, rehypeVersionDocs, rehypeBaseLinks],
  },
  // pagefind() runs the Pagefind indexer inside `astro build` (so it works however CI invokes the
  // build) and serves the index in `astro dev`.
  integrations: [sitemap(), pagefind()],
});
