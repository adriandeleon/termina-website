---
title: FAQ
description: Licensing, data collection and what sync sends, platforms, signed downloads, why JavaFX, why the emulator is JediTerm, tmux, vim and ssh, the kitty keyboard protocol and notifications, ligatures and images, CPU use when idle, the relationship to Editora, and reporting issues.
category: Help
order: 12
---

## Is Termina free and open source?

Yes. It is free and released under the MIT License. The source is on
[GitHub](https://github.com/adriandeleon/Termina). The bundled fonts and the shell marks in the
profile menu carry their own licenses, listed in the repository's `NOTICE` file; Symbols Nerd Font
Mono's license is beside the font file.

## Does it collect data or phone home?

No account, no telemetry, no analytics. The one request Termina makes on its own is the update
check: at startup, at most once a day, it asks the GitHub releases API for the newest version, and
sends nothing about you or the machine beyond the request itself. Turn it off under **Settings →
Advanced → Updates → Check for updates at startup**, or set `"updates.check": false` in
`settings.json`.

Everything else that reaches the network is something you started: the programs you run, the
links you click, and [settings sync](/docs/sync) once you connect it, which pushes and pulls your
key bindings to the Git repository you named and nowhere else. The debug log stays in the settings
folder, and a program's desktop notification goes to your own desktop. **Help → Report a Problem**
builds a report and opens a GitHub issue in your browser; nothing is sent until you submit it
there.

## Which platforms does it run on?

macOS on Apple Silicon and Intel, Windows on x64, and Linux on x64 and arm64. The installers
bundle their own Java runtime, so you do not need Java installed. They are on the
[releases page](https://github.com/adriandeleon/Termina/releases): a `.dmg` for each Mac, an `.msi`
for Windows, and a `.deb`, an `.rpm`, an `AppImage` and a `tar.gz` for each Linux architecture.
There is a Homebrew cask for Apple Silicon Macs (`brew install --cask adriandeleon/tap/termina`)
and an apt repository for Debian and Ubuntu, [Vitrina](https://vitrina-project.dev); a winget
package is waiting for Microsoft's review. See
[Installing](/docs/installing).

## Are the downloads signed?

The macOS app is signed with a Developer ID and notarized by Apple, so it opens the first time
like any other app. Every Linux download has an OpenPGP signature beside it, and the `.deb` and
the `.rpm` are signed inside as well. The Windows installer is not signed yet, so SmartScreen may
warn on the first run: **More info**, then **Run anyway**. Each release lists its files' SHA-256
checksums in `checksums_sha256.txt`. [Installing](/docs/installing#checking-a-download) says how
to check a download.

## Why JavaFX?

Termina shares its toolkit with Editora, the same author's editor, which is what lets the themes,
the command palette, the fonts and the Settings window carry over between the two. It also gives
one codebase for macOS, Windows and Linux, and the emulator and PTY libraries Termina builds on are
JVM libraries. On Linux under Wayland, JavaFX runs through XWayland, since it has no Wayland
backend.

## Why not write the emulator?

Because the hard part of a terminal is being xterm: cursor modes, character sets, scroll regions,
four mouse reporting formats and about a hundred control sequences, where a mistake shows up as a
program quietly misbehaving rather than as a bug anyone can see. JetBrains'
[JediTerm](https://github.com/JetBrains/jediterm), the emulator inside their IDEs, has been
through that, and its core knows nothing about drawing. Termina does the drawing, the input and
everything around it. See [Introduction](/docs#how-it-is-built).

## Does it work with tmux, vim and ssh?

Yes. Programs see `TERM=xterm-256color` and `COLORTERM=truecolor`. Full-screen programs get the
alternate screen and mouse reporting, so vim places its cursor where you click, htop responds to
clicks, and `less` and `man` scroll with the wheel. tmux runs like any other program, with no
special integration, and vim and tmux can copy to your clipboard over ssh (OSC 52). Over ssh, the
tab is named `ssh` while it runs, closing it asks first, and on a slow connection what you type
appears at once, dimmed until the far end echoes it. See
[What programs can ask for](/docs/programs).

## Does it support the kitty keyboard protocol, notifications, ligatures or images?

The kitty keyboard protocol, yes: a program that asks for it, as Neovim, Vim, Helix, fish and
nushell do, gets Escape on its own, `Ctrl+I` apart from Tab and `Shift+Enter` as a key of its own,
and all of the protocol's further flags. Desktop notifications from programs (OSC 9 and 777) and
links they declare (OSC 8), yes. See [What programs can ask for](/docs/programs).

Ligatures and images, no: ligatures are not drawn (Fira Code is bundled, without them), and there
is no image protocol (sixel or kitty graphics). An image a program sends with the kitty graphics
protocol is discarded, as xterm does, rather than printed as a line of text.

## Does it use the CPU when nothing is happening?

Very little. A tab repaints only while there is something to draw, so with one tab sitting at a
prompt Termina measured 0.8% of a core, down from 2–3% before 0.11.0. A tab that is not selected
keeps its output and paints it when you switch to it, so a build in another tab does not compete
with the one you are typing in; its activity and bell marks still update. Knowing what a tab is
running costs microseconds, since Termina asks the operating system for the shell's children
rather than reading the whole process table.

## How is it related to Editora?

Editora is a text editor by the same author, and Termina takes several things from it:

- **The Editora Dark and Light themes**, "Caret & Ink", with the terminal's ANSI colors derived
  from Editora's syntax colors.
- **The command palette**: the same card, the same matcher and ranking, and the same Emacs keys,
  ported as they are, so the two palettes rank alike.
- **The bundled fonts**: the same five monospace families and Inter.
- **The Settings window's shape**: a grouped sidebar, search, and no OK button.
- **Settings sync**, ported from Editora's: in Termina it carries the key bindings between
  computers, through a private Git repository you own.
- **The icon**, which keeps Editora's tile and colors with a shell prompt on it.

Settings are separate. A file path in the terminal can open in Editora with **Open files with**
set to `editora {file}:{line}`; see [Links](/docs/links).

## What is Pauta?

[Pauta](https://pauta-project.dev/) is a set of guidelines for desktop apps that look and work the
same on Windows, macOS and Linux: the menus and their order, the keys, dialogs, settings,
accessibility, documentation and releases. Termina, Editora and their sibling apps are all built
to it. Each rule has an ID and a level, MUST or SHOULD, and each edition has a number.

Termina meets every MUST of the edition it is built to, which Pauta calls **Core** conformance.
[Pauta conformance](/docs/pauta) names the edition and lists the rules Termina does not meet yet,
each with its reason; **Help → Pauta Conformance** shows the same list, and **About** names the
edition.

## Where are my settings?

In the folder `~/.termina` on every platform, unless moved with `--config-dir` or
`TERMINA_CONFIG_DIR`: `settings.json` holds your preferences, key bindings and profiles, and
`workspace-state.json` the window's place and size and the tabs of the last session. Both are
JSON, and `settings.json` can be edited while Termina is closed. A `settings.properties` from a
version before 0.15 is imported the first time 0.15 starts. **Settings → Advanced** can export
the whole configuration as a `.zip` and import it on another computer. See
[Settings](/docs/settings#the-settings-files).

## How do I report a bug or ask for a feature?

For a bug, choose **Help → Report a Problem**. It builds a report of the session — the version,
Java, the operating system, the settings worth knowing and the end of the debug log — with your
home folder, user name, addresses, credentials and tokens taken out. You read it and can edit it,
and it opens as a GitHub issue with the bug form filled in. Nothing is sent until you submit the
issue. If the window will not start, `termina --report` does the same from a terminal, from the
last session's log.

For a feature, or to write the issue yourself, open one at
[github.com/adriandeleon/Termina/issues](https://github.com/adriandeleon/Termina/issues). For a
bug, say what you did and what happened. [Troubleshooting](/docs/troubleshooting#reporting-a-bug)
has more.
