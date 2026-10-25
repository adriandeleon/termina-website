---
title: Termina 0.8.0 released
description: "Tabs that can be renamed, reopened and duplicated, a searchable shortcut reference, a paste that asks first, the menu bar in the title bar, and JavaFX 27."
date: 2026-10-21
version: 0.8.0
---

Termina 0.8.0 is the largest release since the first: the fixes from a full review of the
application, and the features that fell out of it. It also moves to JavaFX 27.

## Tabs

- **Rename, reopen and duplicate a tab.** Double-click a tab to name it; the name wins over the
  shell's title and the running program, and an empty name brings the automatic title back.
  **Reopen Closed Tab** (`Ctrl+Shift+Z`, `Cmd+Z` on a Mac) brings back the last tab you closed:
  the same shell or profile, in the directory it was in, with its name and its place in the strip.
  **Duplicate Tab** opens another tab like this one, beside it.
- **Tabs are named after the running program**, `vim`, `ssh` or `npm`, when the shell has not set
  a title, and only then after the directory.
- **A background tab says when something happened in it**: a dot after the title, in the accent
  color for output after two quiet seconds and in the warning color for a bell.

## Keys and paste

- **Help → Keyboard Shortcuts** (`Ctrl+Shift+/`, `Cmd+/` on a Mac) lists every chord the window
  answers to, searchable, generated from the menus and the binding table.
- **A paste with a line break asks first**, showing what it will send, because at a prompt every
  line of a paste runs the moment it lands. Control characters are stripped from every paste, so
  the clipboard cannot end a bracketed paste early.
- **The Linux reflexes**: `Shift+PageUp` and `Shift+PageDown` scroll, `Shift+Insert` pastes,
  `Ctrl+Insert` copies, and a middle click pastes the clipboard.

## The window

- **The menu bar can share a row with the title bar** (Appearance → Window), on Linux and
  Windows: menus at the left, the title in the middle, the window buttons at the right. It is
  JavaFX 27's extended stage style.
- **The window's position is remembered** with its size, and pulled back onto a screen that still
  exists.

## Fixed, among many

- **Escape no longer leaves full screen.** JavaFX's default exit key is Escape, so the first
  Escape in vim dropped the window out of full screen. `F11` (`Ctrl+Cmd+F` on a Mac) is now the
  only way in and out.
- **Close Window's chord works**: off macOS it was the same chord as Close Tab, which won. It is
  now `Ctrl+Shift+Q`.
- **A selection stays on its text while output arrives**, and a drag highlights exactly what it
  copies.

The [changelog](https://github.com/adriandeleon/Termina/blob/master/CHANGELOG.md) has the full
list. [Download Termina 0.8.0](https://github.com/adriandeleon/Termina/releases/tag/v0.8.0).
