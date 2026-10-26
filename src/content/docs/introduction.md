---
title: Introduction
description: What Termina is, the three layers it is built from, what version 0.11.0 can do, and how the documentation is organized.
category: Getting started
order: 1
---

**Termina** is a cross-platform terminal emulator. It runs your shell on a real pseudo-terminal
and draws the screen with JavaFX, with tabs, split panes, several windows, a menu bar and a command
palette.
It is free and open source under the MIT License.

It runs on macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and arm64). The
installers bundle their own Java runtime, so there is nothing else to install.

Version 0.11.0, released on 2026-10-25, is the latest release. Downloads are on the
[releases page](https://github.com/adriandeleon/Termina/releases).

## How it is built

A terminal does three jobs, and Termina writes only one of them.

| Layer | What it does | Who does it |
| --- | --- | --- |
| PTY | Starts the shell on a pseudo-terminal and moves bytes in and out | [pty4j](https://github.com/JetBrains/pty4j) |
| Emulation | Reads the escape sequences programs write and keeps the screen and scrollback | [JediTerm](https://github.com/JetBrains/jediterm) |
| Rendering and input | Paints the screen, encodes keys and the mouse, and is everything you click | Termina |

- **The emulator is JediTerm's**, the one inside JetBrains' IDEs. xterm compatibility is a long
  tail of cursor modes, character sets, scroll regions and mouse formats, and a mistake there shows
  up as a program quietly misbehaving. JediTerm has already been through that.
- **The PTY is pty4j's.** Java has no pseudo-terminal API, and the platforms disagree on how one
  works (`forkpty` on macOS and Linux, ConPTY on Windows). One pty4j library covers all of them.
- **The rest is Termina's**: the renderer, the key and mouse encoding, the tabs and windows, the
  settings and the shell profiles.

Programs running inside see `TERM=xterm-256color` and `COLORTERM=truecolor`.

## What is in 0.11.0

- **Tabs, panes and windows.** A tab is named after the program running in it, or the directory it
  is in. You can rename it with a double-click, drag it to reorder, reopen it after closing, and
  duplicate it. A tab can be split into panes, each its own shell, and a pane can be maximized or
  moved to another tab. A dot marks a background tab where something happened. Closing a tab that
  is still running something asks first.
- **Search and shell integration.** A find bar searches the scrollback as you type. A shell that
  marks its prompts lets you jump between them and select the last command's output.
- **Shell profiles.** Termina finds the shells a machine has. On macOS and Linux that means the
  shells in `/etc/shells` and on the path. On Windows it means both PowerShells, Command Prompt,
  Git Bash and each WSL distribution. You can add profiles of your own, and give any profile its
  own font, size and theme.
- **Selection and the mouse.** Drag to select, double-click for a word, triple-click for a line,
  Alt+drag for a block. vim, htop and other mouse-aware programs get the mouse; holding Shift takes
  it back.
- **Links.** `Ctrl`-click (`Cmd`-click on macOS) opens a URL, or a file path that exists,
  including a `Foo.java:42` out of a stack trace, and a link a program declares.
- **What programs ask for**: the clipboard over SSH, desktop notifications, the kitty keyboard
  protocol, and type-ahead on a slow SSH link.
- **A command palette** on `Alt+X`, Emacs's `M-x`, listing every command, open tab, profile and
  theme.
- **Keys that leave the shell alone.** Application chords are `Ctrl+Shift` on Linux and Windows,
  so `Ctrl+W`, `Ctrl+T` and `Ctrl+C` still reach the shell. Every shortcut can be changed, and the
  changes can be synced between computers through a Git repository of yours.
- **Four themes and any you import**, five bundled monospace fonts and a Nerd Font for icons, zoom,
  window opacity and six interface languages.
- **Safer pastes.** A paste that contains a line break asks first, and control characters are
  stripped before the text is sent.
- **Native installers** for every platform, and a command line (`-e`, `-d`, `--config-dir`).

Not in 0.11.0: key sequences of more than one chord, images in the terminal, and ligatures.

## Start here

- **[Getting started](/docs/getting-started)**: install Termina, open it, and learn the handful
  of keys worth knowing.
- **[Keyboard](/docs/keyboard)**: why the chords are what they are, the palette, and the keys the
  terminal handles itself. The full list is on the [Keybindings](/keybindings) page.

## By area

Using it:

- [Tabs and windows](/docs/tabs-and-windows): opening, naming, moving and closing tabs, and
  windows.
- [Split panes](/docs/panes): splitting a tab, moving between panes, maximizing and moving them.
- [Shell profiles](/docs/shell-profiles): which shells are found, and adding your own.
- [Selection and the mouse](/docs/selection-and-mouse): selecting, copying, pasting, and programs
  that use the mouse.
- [Links](/docs/links): opening URLs and file paths from the screen.
- [Search and prompts](/docs/search-and-prompts): the find bar, and shell integration.
- [What programs can ask for](/docs/programs): declared links, the clipboard, notifications, the
  kitty keyboard protocol and type-ahead.
- [The command line](/docs/command-line): `-e`, `-d`, `--config-dir` and `--version`.

Customization:

- [Keyboard](/docs/keyboard): chords, changing them, the command palette, Meta and AltGr.
- [Appearance](/docs/appearance): themes, fonts, zoom, the cursor, the window and the language.
- [Settings](/docs/settings): the Settings window, every page, and the settings file.
- [Sync](/docs/sync): carrying your shortcuts between computers through a Git repository.

Help:

- [Troubleshooting](/docs/troubleshooting): titles, icons, full screen, opening an unsigned
  build, and the debug log.
- [FAQ](/docs/faq).
