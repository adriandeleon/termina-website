// @ts-check
import { defineConfig } from "astro/config";
import sitemap from "@astrojs/sitemap";
import pagefind from "astro-pagefind";
import { site, base, href } from "./src/lib/site.ts";

// Served at GitHub Pages' project address for now (src/lib/site.ts), so everything lives under
// `base`. Templates build links with href(); Markdown is authored with plain root-relative links
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

//
// Unlike Editora's site the docs are not versioned yet: no Termina release deep-links into them.
// When one does, port Editora's /docs/v-<version>/ routes and archive instead of inventing a scheme.
export default defineConfig({
  site,
  base,
  markdown: {
    rehypePlugins: [rehypeBaseLinks],
  },
  // pagefind() runs the Pagefind indexer inside `astro build` (so it works however CI invokes the
  // build) and serves the index in `astro dev`.
  integrations: [sitemap(), pagefind()],
});
