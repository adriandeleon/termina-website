---
title: Introduction
description: What Termina is, the three layers it is built from, what version 0.15.0 can do, and how the documentation is organized.
category: Getting started
order: 1
---

**Termina** is a cross-platform terminal emulator. It runs your shell on a real pseudo-terminal
and draws the screen with JavaFX, with tabs, split panes, several windows, a menu bar and a command
palette.
It is free and open source under the MIT License.

It runs on macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and arm64). The
installers bundle their own Java runtime, so there is nothing else to install.

This guide describes Version 0.15.0. Downloads are on the
[releases page](https://github.com/adriandeleon/Termina/releases), and
[Installing](/docs/installing) says how to install each one.

![The Termina window: a menu bar, two tabs, and a shell showing a directory listing with file-type icons and colour](/screenshots/docs/window.png "Termina, with two tabs open.")

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

## What is in 0.15.0

- **Tabs, panes and windows.** A tab is named after the program running in it, or the directory it
  is in. You can rename it with a double-click, drag it to reorder, reopen it after closing, and
  duplicate it, and pin it at the left. Reopen brings back a closed pane or window too. A tab can
  be split into panes, each its own shell, and a pane can be maximized or moved to another tab. A
  mark on a background tab says that something happened in it. Closing a tab that is still running
  something asks first.
- **The last session comes back.** The tabs and panes open when Termina ends reopen at the next
  launch, each as a new shell in the folder it was in.
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
- **A command palette** on `Ctrl+Shift+P` (`Cmd+Shift+P` on macOS), listing every command, open
  tab, profile, theme and setting.
- **Keys that leave the shell alone.** Application chords are `Ctrl+Shift` on Linux and Windows,
  so `Ctrl+W`, `Ctrl+T` and `Ctrl+C` still reach the shell. Every shortcut can be changed, and the
  changes can be synced between computers through a Git repository of yours.
- **Five themes, one that follows the system, and any you import**, five bundled monospace fonts
  and a Nerd Font for icons, zoom for the text and a separate zoom for the interface, padding,
  window opacity and six interface languages. The pane that has the keyboard has the solid cursor;
  the others draw it hollow.
- **Settings you can read.** They are two JSON files in one folder, and Settings can export the
  whole configuration to a `.zip` and import it again.
- **A status bar, a Message Log and a Doctor.** The status bar says what the last command
  reported, the Message Log keeps everything said this session, and **Help → Doctor** checks what
  Termina relies on. **Help → Report a Problem** builds a bug report for you to read and send.
- **Safer pastes.** A paste that contains a line break asks first, and control characters are
  stripped before the text is sent.
- **Native installers** for every platform, with Homebrew and winget packages and an AppImage and
  a tarball for Linux, and a command line (`-e`, `-d`, `-T`, `--config-dir`). The macOS app is
  signed and notarized, and the Linux downloads are OpenPGP-signed. The Linux packages put
  `termina` on the path and offer it as an `x-terminal-emulator`.

Not in 0.15.0: key sequences of more than one chord, images in the terminal, and ligatures.

## Start here

- **[Getting started](/docs/getting-started)**: install Termina, open it, and learn the handful
  of keys worth knowing.
- **[Installing](/docs/installing)**: every download, how to check one, and what it puts on your
  computer.
- **[Keyboard](/docs/keyboard)**: why the chords are what they are, the palette, and the keys the
  terminal handles itself. The full list is on the [Keybindings](/keybindings) page.

## By area

Using it:

- [Tabs and windows](/docs/tabs-and-windows): opening, naming, pinning, moving and closing tabs,
  the tabs that come back at launch, and windows.
- [Split panes](/docs/panes): splitting a tab, moving between panes, maximizing and moving them.
- [Shell profiles](/docs/shell-profiles): which shells are found, and adding your own.
- [Selection and the mouse](/docs/selection-and-mouse): selecting, copying, pasting, and programs
  that use the mouse.
- [Links](/docs/links): opening URLs and file paths from the screen.
- [Search and prompts](/docs/search-and-prompts): the find bar, and shell integration.
- [What programs can ask for](/docs/programs): declared links, the clipboard, notifications, the
  kitty keyboard protocol and type-ahead.
- [The command line](/docs/command-line): `-e`, `-d`, `--config-dir`, `--no-session`, `--report`
  and `--version`.

Customization:

- [Keyboard](/docs/keyboard): chords, changing them, the command palette, Meta and AltGr.
- [Appearance](/docs/appearance): themes, fonts, zoom, the cursor, the window and the language.
- [Settings](/docs/settings): the Settings window, every page, the settings files, and exporting
  and importing a configuration.
- [Sync](/docs/sync): carrying your shortcuts between computers through a Git repository.

Help:

- [Troubleshooting](/docs/troubleshooting): the Doctor and the Message Log, titles, icons, full
  screen, the Windows installer's warning, and the debug log.
- [FAQ](/docs/faq).
