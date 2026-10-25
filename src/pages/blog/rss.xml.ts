import rss from "@astrojs/rss";
import { getCollection } from "astro:content";
import type { APIContext } from "astro";
import { href, site } from "../../lib/site";

export async function GET(context: APIContext) {
  const posts = (await getCollection("blog")).sort(
    (a, b) => b.data.date.valueOf() - a.data.date.valueOf(),
  );
  return rss({
    title: "Termina Blog",
    description: "Articles and deep-dives about Termina.",
    site: new URL(href("/"), context.site ?? site).href,
    items: posts.map((p) => ({
      title: p.data.title,
      description: p.data.description ?? "",
      pubDate: p.data.date,
      link: href(`/blog/${p.id}/`),
    })),
  });
}
