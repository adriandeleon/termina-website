import rss from "@astrojs/rss";
import { getCollection } from "astro:content";
import type { APIContext } from "astro";
import { href, site } from "../../lib/site";

// Newest first. Several items can share a calendar day and dates are date-only,
// so ties are broken by release version (higher first, a versioned item ahead of
// an unversioned one) and then by id.
const versionParts = (v?: string) => (v ? v.split(".").map((n) => Number.parseInt(n, 10) || 0) : []);
const byNewest = (
  a: { id: string; data: { date: Date; version?: string } },
  b: { id: string; data: { date: Date; version?: string } },
) => {
  const byDate = b.data.date.valueOf() - a.data.date.valueOf();
  if (byDate !== 0) return byDate;
  const va = versionParts(a.data.version);
  const vb = versionParts(b.data.version);
  for (let i = 0; i < Math.max(va.length, vb.length); i++) {
    const d = (vb[i] ?? -1) - (va[i] ?? -1);
    if (d !== 0) return d;
  }
  return b.id.localeCompare(a.id);
};

export async function GET(context: APIContext) {
  const items = (await getCollection("news")).sort(byNewest);
  return rss({
    title: "Termina News",
    description: "Release announcements and updates from the Termina project.",
    site: new URL(href("/"), context.site ?? site).href,
    items: items.map((p) => ({
      title: p.data.title,
      description: p.data.description ?? "",
      pubDate: p.data.date,
      link: href(`/news/${p.id}/`),
    })),
  });
}
