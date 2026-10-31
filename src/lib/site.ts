// Where the site is served: https://termina-project.dev, GitHub Pages under a custom domain, at
// the root. Changing the host: `site` here, public/CNAME, public/robots.txt, the social cards'
// footer (scripts/gen-images.mjs, then --force), and the repository's Settings → Pages. `base`
// stays for the day the site moves under a path again; at "/" every href() is the path itself.
export const site = "https://termina-project.dev";
export const base = "/";

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
