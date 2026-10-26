---
title: Termina 0.11.0 released
description: "Split panes, search in the scrollback, shell integration, shortcuts that can all be changed and synced, what programs ask a terminal for, imported themes, and an idle terminal that lets the machine idle."
date: 2026-10-25
version: 0.11.0
---

Termina 0.11.0 is the largest release yet. It brings split panes, search in the scrollback, shell
integration, and shortcuts that can all be changed. Four things programs ask of a terminal are now
honored instead of dropped: a declared link, the clipboard, the directory and a notification.
Typing over a slow SSH link is predicted, programs can turn on the kitty keyboard protocol, and an
idle terminal lets the machine idle.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.11.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel. Open it and drag
  Termina to Applications.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB (`sudo apt install ./Termina-*.deb`) or the RPM
  (`sudo dnf install ./Termina-*.rpm`).

Each bundles its own Java runtime. The builds are unsigned: on macOS allow the first launch under
System Settings → Privacy & Security → Open Anyway, and on Windows choose More info → Run anyway if
SmartScreen warns. Verify a download against `checksums_sha256.txt`.

## Panes and search

- **Split panes.** Window → Split Right and Split Down (`Ctrl+Shift+E` and `Ctrl+Shift+O`, `Cmd+D`
  and `Cmd+Shift+D` on a Mac) open another shell beside or below the one you are in: the same shell,
  in the same directory. `Ctrl+Alt+Shift+arrows` (`Cmd+Option+arrows`) or a click moves between
  them, and the pane being typed in is outlined. Close Tab's chord closes the active pane of a split
  tab.
- **Maximize and move panes.** Maximize Pane (`Ctrl+Shift+Enter`, `Cmd+Shift+Enter`) shows the
  active pane alone while the others keep running. Move Pane to New Tab, the palette, or a drag with
  `Ctrl+Alt+Shift` (`Cmd+Option+Shift`) moves a pane to another tab. Reopen Closed Tab brings back a
  split tab with every pane, arranged as it was.
- **Search the scrollback.** Edit → Find… (`Ctrl+Shift+F`, `Cmd+F`) opens a find bar in the pane.
  Every match is highlighted as you type, Enter steps up to older output and Shift+Enter steps down.
  The search uses smart case, can take a regular expression, and finds a word that wrapped at the
  edge of the window.
- **Shell integration (OSC 133).** A shell that marks its prompts gets Previous Prompt and Next
  Prompt (`Ctrl+Shift+Up`/`Down`, `Cmd+Up`/`Down`), Select Last Output, and a red bar beside the
  prompt of a command that failed. [Search and prompts](/docs/search-and-prompts) has the lines
  to add to bash and zsh.

## Keys

- **Every shortcut can be changed.** Settings has a Keyboard page listing each command under its
  menu. Click a shortcut and press the new one. Commands without a shortcut, such as Rename Tab, can
  be given one. The menus, the palette, the shortcut reference and the tab strip's tooltips change
  with it.
- **Shortcuts sync between computers** through a private Git repository you own (Settings → Sync).
  Macs share one list, and Windows and Linux share another. Bindings a sync replaces are backed up
  first, and a sync that would remove most of them stops and asks.
- **The kitty keyboard protocol.** Neovim, Vim, Helix, fish and nushell ask for it, and now get
  unambiguous keys: Escape on its own, Ctrl+I apart from Tab, and Shift+Enter as a key of its own.
  Nothing changes for a program that does not ask.

## What programs ask for

- **Declared links (OSC 8)** from `ls --hyperlink`, compilers and `gh` can be clicked. Hovering one
  shows where it really goes, and a `javascript:` target is not a link.
- **Copying to the clipboard (OSC 52)**, which is how vim and tmux copy over SSH. A program can
  write the clipboard, never read it.
- **Desktop notifications (OSC 9 and OSC 777)** mark their tab with a dot. They reach the desktop
  unless you are already looking at that pane.
- **Type-ahead over slow SSH.** When the far end echoes slower than 100 ms, typed characters appear
  at once, dimmed and underlined until they are confirmed.
- **Windows tabs know their directory** without help from the shell, so new tabs open there and
  relative paths become links.

## Selection, themes and profiles

- **Selection does more.** Dragging past the top or bottom edge scrolls. Alt+drag (Option on a Mac)
  selects a block. Copy on select is a setting, and on Linux a selection is the PRIMARY selection
  that a middle-click pastes. A keystroke no longer clears the selection.
- **Import a theme** from iTerm2, Terminal, Windows Terminal, Ghostty, kitty or Alacritty, with
  the colors exactly as written.
- **A profile can have its own font, size and theme**, and the pane keeps them through splits,
  moves and reopening.
- **Icons draw without a Nerd Font installed.** Symbols Nerd Font Mono is bundled, so
  `eza --icons` draws correctly on any machine.

## Quieter

- **An idle terminal lets the machine idle.** With one tab sitting at a prompt, CPU use went from
  2–3% of a core to 0.8%.
- **Background tabs stop painting** until you switch to them, so a build in another tab no longer
  slows down the one you are typing in.
- **The first window paints sooner**, and a tab's title costs microseconds to work out where it
  used to cost milliseconds.

## Fixed, among others

- **Closing one window of several left its shells running** until the app quit.
- **A stray `¯` or `ÿ` before some icons** in `eza` and `ls` listings.
- **A drag held while output scrolled** extended the selection from the wrong text.

The [changelog](https://github.com/adriandeleon/Termina/blob/master/CHANGELOG.md) has the full
list.
