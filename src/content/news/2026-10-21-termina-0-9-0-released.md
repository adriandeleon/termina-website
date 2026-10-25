---
title: Termina 0.9.0 released
description: "The command palette is now Editora's: the same matcher and ranking, Emacs keys to drive it, and Alt+X to open it."
date: 2026-10-21
version: 0.9.0
---

Termina 0.9.0 replaces the command palette with Editora's, so the two applications find and rank
commands the same way.

## The palette is Editora's

- **The same card.** The query row is led by a chip naming the chord that opened it, eight rows
  show the matched characters in bold, a line under the list says what the highlighted command
  does, and a legend names the keys.
- **The same matcher.** "tab close" finds Close Tab with the words in either order, and "file new"
  reaches New Tab through the menu it lives in. Ties go to the shorter label, then the
  alphabetical one, so equal rows stop shuffling between keystrokes.
- **Commands that cannot run are shown, not dropped.** Reopen Closed Tab with nothing to reopen is
  listed greyed, with the reason as its tooltip, and the cursor steps over it.

## Alt+X, and a chord you can change

The palette opens on `Alt+X` (`Option+X` on a Mac), Emacs's `M-x` and the chord Editora's palette
answers to, instead of `Ctrl+Shift+P`. **Settings → Terminal → Keyboard** takes another: click the
field and press it. A bare key is refused because the shell owns it, and so is a chord another
command already has.

Inside the palette, Emacs keys work alongside the arrows: `Ctrl+N` and `Ctrl+P` move, `Ctrl+V` and
`Alt+V` page, `Alt+<` and `Alt+>` jump to the ends, `Ctrl+G` cancels, and the query field takes
`Ctrl+A`, `Ctrl+E`, `Ctrl+K` and the rest of the editing chords.

## Fixed

- On macOS the chord that opens the palette no longer leaks its typed character: Option+X also
  composes "≈", which landed in the query field the chord had just focused.

[Download Termina 0.9.0](https://github.com/adriandeleon/Termina/releases/tag/v0.9.0), or the
[latest release](https://github.com/adriandeleon/Termina/releases/latest).
