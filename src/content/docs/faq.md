---
title: FAQ
description: Licensing, data collection and what sync sends, platforms, signing, why JavaFX, why the emulator is JediTerm, tmux, vim and ssh, the kitty keyboard protocol and notifications, ligatures and images, CPU use when idle, the relationship to Editora, and reporting issues.
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
Advanced → Updates → Check for updates at startup**, or set `updates.check=false` in the settings
file.

Everything else that reaches the network is something you started: the programs you run, the
links you click, and [settings sync](/docs/sync) once you connect it, which pushes and pulls your
key bindings to the Git repository you named and nowhere else. The debug log stays in the settings
folder, and a program's desktop notification goes to your own desktop.

## Which platforms does it run on?

macOS on Apple Silicon and Intel, Windows on x64, and Linux on x64 and arm64. The installers
bundle their own Java runtime, so you do not need Java installed. They are on the
[releases page](https://github.com/adriandeleon/Termina/releases): a `.dmg` for each Mac, an `.msi`
for Windows, and a `.deb` and an `.rpm` for each Linux architecture. Homebrew and winget packages
are planned but not published yet.

## The app is unsigned. How do I open it?

Nothing is signed yet. The macOS app is not signed with an Apple Developer ID and is not
notarized, so the first launch is refused: allow it once under **System Settings → Privacy &
Security → Open Anyway**. On Windows, SmartScreen may warn on the first run: **More info**, then
**Run anyway**. Each release lists its files' SHA-256 checksums in `checksums_sha256.txt`. See
[Troubleshooting](/docs/troubleshooting).

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
is no image protocol (sixel or kitty graphics).

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
  ported as they are, so the two palettes rank alike. Both open on `Alt+X`.
- **The bundled fonts**: the same five monospace families and Inter.
- **The Settings window's shape**: a grouped sidebar, search, and no OK button.
- **Settings sync**, ported from Editora's: in Termina it carries the key bindings between
  computers, through a private Git repository you own.
- **The icon**, which keeps Editora's tile and colors with a shell prompt on it.

Settings are separate. A file path in the terminal can open in Editora with **Open files with**
set to `editora {file}:{line}`; see [Links](/docs/links).

## Where are my settings?

In `~/.termina/settings.properties` on every platform, unless moved with `--config-dir` or
`TERMINA_CONFIG_DIR`. It is a plain properties file you can edit while Termina is closed. See
[Settings](/docs/settings#the-settings-file).

## How do I report a bug or ask for a feature?

Open an issue at
[github.com/adriandeleon/Termina/issues](https://github.com/adriandeleon/Termina/issues). For a
bug, include the output of `termina --version`, your platform and shell, what you did and what
happened, and the debug log (**Settings → Advanced → Diagnostics → Show Debug Log…**, then Copy).
[Troubleshooting](/docs/troubleshooting#reporting-a-bug) says how to run `--version` from a
packaged app.
