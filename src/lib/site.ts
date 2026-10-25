// Where the site is served: GitHub Pages' project address,
// https://adriandeleon.github.io/termina-website/, so every page lives under `base`.
// Moving to a domain: set `site` to it, `base` to "/", put the host in public/CNAME and in
// public/robots.txt, and set the custom domain under the repository's Settings → Pages.
export const site = "https://adriandeleon.github.io";
export const base = "/termina-website";

/**
 * A root-relative path ("/docs/links", "/#features", "og/docs.png") as it is served under
 * `base`. Every internal link and asset in a template goes through this; links in Markdown are
 * rewritten the same way at build time (astro.config.mjs). External URLs pass through unchanged.
 */
export function href(path: string): string {
  if (/^[a-z]+:/i.test(path) || path.startsWith("//")) return path;
  const joined = `${base}/${path.replace(/^\/+/, "")}`;
  return joined.replace(/\/{2,}/g, "/");
}
