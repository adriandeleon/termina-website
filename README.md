# Termina website

The marketing + documentation site for [Termina](https://github.com/adriandeleon/Termina), a
cross-platform terminal emulator. Served at
**[termina-project.dev](https://termina-project.dev)**.

The domain is GitHub Pages' custom domain for this repository (`public/CNAME`, and Settings →
Pages). Its DNS is on Cloudflare, as Editora's is: the apex and `www` are CNAMEs to
`adriandeleon.github.io`, **DNS only** — proxied, Cloudflare would answer GitHub's certificate
check itself and Pages could not issue or renew the HTTPS certificate.

The site still routes everything through an Astro `base` (`src/lib/site.ts`), now `"/"`, from when
it was served under `/termina-website/`. Templates build every internal link and asset with
`href()` from that file; Markdown is written with plain root-relative links (`/docs/links`) and a
rehype plugin in `astro.config.mjs` puts them under the base. Keep doing so: it is what makes
moving the host or the path a one-file change. Changing the host: `site` in `src/lib/site.ts`,
`public/CNAME`, `public/robots.txt`, the social cards (`scripts/gen-images.mjs`, then `--force`),
and the custom domain under Settings → Pages.

Built with [Astro](https://astro.build) and deployed to GitHub Pages via
`.github/workflows/pages.yml` on every push to `master`. The stack, the layout and the look are
[Torrenta's site](https://github.com/adriandeleon/torrenta-website)'s, which are
[Editora's](https://github.com/adriandeleon/editora-website)'s, ported: the same components (the
Pagefind search modal, the page index rail, the palette demo), the same page shapes, the same
lockfile, with Termina's themes in place of Torrenta's.

Termina is built with the help of AI coding tools.

## Develop

```bash
npm install
npm run dev       # http://localhost:4321/
npm run build     # production build to dist/
npm run preview   # serve the build (site search only works here, after a build)
```

## Content

Most content is Markdown under `src/content/`:

- `docs/` — documentation pages (sidebar grouped by `category` + `order`; `introduction` is `/docs`)
- `blog/` — blog posts
- `news/` — release-style announcements
- `features/` — one page per feature (also drives the home-page grid; `summary` is HTML)

Add a Markdown file with the right frontmatter and it appears automatically; lists, the sitemap
and RSS regenerate on the next build.

**The site describes the latest release, not the development branch.** Check a claim against the
tag (`git -C ../Termina show v0.10.0:README.md`, `…:src/main/java/…`), not the working tree, which
usually carries unreleased work. What is in development goes in `next` in `src/lib/whatsnew.ts`,
under the home page's "In development" heading, and nowhere else.

Feature screenshots go in `public/screenshots/features/<slug>.png` (the slug matches the page
URL, e.g. `/features/tabs` → `tabs.png`); without one the card and the page show a placeholder.
The window screenshot is `public/screenshots/hero/terminal.png`, and the "A look at the window"
section only renders when it exists. Termina's capture harness makes them:
`scripts/dev-run.sh -Dtermina.capture=out.png …` in the Termina repository, whose README lists the
options. Capture from a build of the release tag, run with a scratch `--config-dir`, and in a
directory with nothing private in it.

The captures can run headless, with no window on anyone's desktop: JavaFX 27's own headless
platform, `-Dglass.platform=Headless -Dprism.order=sw`, with `DISPLAY` unset. Three things differ
from a desktop run. Its screen is a fixed 1000×1000 and Termina clamps the window to the screen, so
a larger shot needs the stage resized after it opens. A popup (a context menu, the profile list)
is photographed on its own, and its transparent margin comes out white unless its root is
snapshotted on a transparent fill; composite it onto the window at the position it reports. And
the platform cannot draw a title bar, so Settings → Appearance says so on the title-bar row: take
that page on a desktop, or show another.

## Themes

The site's palettes are the app's four themes (`com.termina.ui.Theme`), so the picker in the header
recolours the site the way Settings → Appearance recolours the app: Editora Dark (the default,
"Caret & Ink") and Editora Light, and Clear Dark and Clear Light, ported from macOS Terminal. Each
block in `src/layouts/Layout.astro` carries the site tokens (`--bg`, `--accent`, …) and the
terminal's own surface and sixteen ANSI colours (`--term-bg`, `--term-fg`, `--ansi-0` to
`--ansi-15`), which the palette demo draws with through the `.a0`–`.a15` classes. The Clear themes
borrow Editora's chrome tokens, as the app does. Clear Light's four low-contrast ANSI colours are
Apple's and are kept.

## Generated data

`npm run build` first runs `scripts/gen-images.mjs`: the favicon and touch icons from
`public/termina-icon.svg` (a copy of the app's `branding/termina-icon.svg`), a social card per
key page in `public/og/`, and a WebP beside every PNG screenshot. Outputs are kept unless their
source is newer; `--force` rebuilds them.

`src/lib/commands.ts` (the Keybindings page, the home page's keys, the palette demo) and
`src/lib/version.ts` are generated by `scripts/GenCommands.java` from Termina's source **at the
newest release tag**: the `item("menu.…", chord, …)` calls in `TerminalWindow.buildMenuBar()`, their
labels in `messages.properties`, and the pom's version. Each chord is evaluated for both platform
branches, since Termina's are `Ctrl+Shift+key` off macOS and `Cmd+key` on it; a chord expression the
script does not understand stops it rather than being guessed. It is a JDK 25 compact source file
(JEP 512), run with `java`, no build step, from the site's root:

```bash
java scripts/GenCommands.java                       # ../Termina, newest vX.Y.Z tag
java scripts/GenCommands.java /path/to/Termina
java scripts/GenCommands.java --ref=HEAD            # preview a branch; don't commit its output
```

or `TERMINA_REPO`. Which group a command is listed under is a table in the script, because the
View menu's actions are built before the menu and some chords are registered from loops; a command
missing from it is listed under "Other" and reported, and two commands on one chord are reported
too. The output is committed, so the site build needs no access to the Termina repository (it isn't
checked out in CI). Re-run it after each release.

`src/lib/whatsnew.ts` (the home "What's new" block) is hand-written: a handful of highlights from
the recent releases' news posts, and `next` for what is in development. Update it with each
release.

## Not ported (yet)

Editora's site has versioned docs (`/docs/v-<version>/…`, archived copies per release), a
generated roadmap, per-command pages and a comparison page; Torrenta's has a comparison table.
Termina's docs are unversioned because nothing in the app links into them yet; when it does, port
Editora's routes and archive layout rather than inventing another scheme. Cloudflare Web Analytics
is left out until the site has its own token.
