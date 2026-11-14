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
section only renders when it exists. `scripts/screenshots.sh` takes all of them, at 2×, with
Termina's capture harness (`-Dtermina.capture=out.png …`; the Termina repository's
`docs/testing.md` lists the options): `scripts/screenshots.sh hero feature-tabs` retakes those two.
Capture from a build of the release the site describes. The script uses a shell set-up, a home
folder and a demo project of its own, so nothing private is in a picture.

The captures can run headless, with no window on anyone's desktop: JavaFX 27's own headless
platform, `-Dglass.platform=Headless -Dprism.order=sw`, with `DISPLAY` unset. Three things differ
from a desktop run. Its screen is a fixed 1000×1000 and Termina clamps the window to the screen, so
a larger shot needs the stage resized after it opens. A popup (a context menu, the profile list)
is photographed on its own, and its transparent margin comes out white unless its root is
snapshotted on a transparent fill; composite it onto the window at the position it reports. And
the platform cannot draw a title bar, so Settings → Appearance says so on the title-bar row: take
that page on a desktop, or show another.

**The user guide's own pictures** are `public/screenshots/docs/<name>.png`, taken in the app's
light theme, each with a `<name>.dark.png` twin taken in the dark one, both at 2×.
`scripts/screenshots.sh` retakes all of them, headless, from a Termina checkout
(`TERMINA_REPO`, else `../Termina`): it writes its own shell start-up file, home folder and demo
project, so the same Termina gives the same pictures on any machine. `scripts/screenshots.sh panes
tabs` retakes only those. A docs page shows one with an image on a line of its own:

```md
![What the picture shows](/screenshots/docs/panes.png "The caption under it")
```

`src/lib/rehype-doc-figures.mjs` turns that into a figure, reads its size from the file, links the
full-size picture, and lets the site's theme pick the light or the dark one. Alt text is required;
a missing file fails the build. Retake them after any change to how the window looks (DOCS-18).

## What is generated from the app

Four things come from the Termina repository, and their output is committed so the site builds
without it (DOCS-04, PAUTA-14). Each script takes the checkout as its argument (else
`TERMINA_REPO`, else `../Termina`) and reads it at a git ref: the newest `vX.Y.Z` tag, or
`--ref=<tag|branch>`. `--as=0.15.0` makes the site ready ahead of a release, from a branch whose
pom still says `-SNAPSHOT`.

| Run | Writes | From |
| --- | --- | --- |
| `java scripts/GenCommands.java` | `src/lib/commands.ts`, `src/lib/version.ts` | `messages.properties`, the key maps, `pom.xml` |
| `java scripts/GenChangelog.java` | `src/content/docs/whats-new.md`, `roadmap.md`, `src/lib/whatsnew.ts` | `CHANGELOG.md`, `TODO.md` |
| `java scripts/GenPauta.java` | `src/content/docs/pauta.md` | `pauta.json`, `pom.xml` |
| `scripts/screenshots.sh` | `public/screenshots/` | the built app, driven headless |

`commands.ts` feeds the Commands page, a page for each command, Keybindings, the home page's keys
and the palette demo. After a release, run all four from the tag and commit the result.

`npm run check:docs` then checks what the hand-written pages quote: a backticked command id must
be a real command, and a key in a table beside a command's title must not be another command's
key. `npm run build` runs it first and stops there if it fails (DOCS-04), so the workflow that
deploys the site cannot publish a guide that names a command the app does not have. The **Check**
workflow runs the build on every pull request.

## The guide is served under its version

The user guide is at `/docs/v-<version>/…`, the address the app of that version opens from its
Help menu, its Settings pages and its palette (DOCS-03). Bare `/docs` and every unversioned slug
redirect to the current version (`astro.config.mjs`), so write links as `/docs/links`, in Markdown
and in templates alike: a rehype plugin puts a Markdown link under the version, and the rest go
through the redirect. A release changes `src/lib/version.ts` and nothing else.

Only the current version is served. Before generating the version after this one, either keep
the old pages (Editora's site archives them under `src/content/docs-archive/<version>/`) or add a
redirect from each `/docs/v-<old>/…` address to the current one: an installed app of the old
version still opens those addresses.

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

`npm run build` runs the docs check, then `scripts/gen-images.mjs`: the favicon and touch icons from
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
