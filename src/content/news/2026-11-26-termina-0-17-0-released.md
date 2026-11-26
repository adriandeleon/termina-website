---
title: Termina 0.17.0 released
description: "Termina's other windows open centred on the window you opened them from, one click on a menu row runs it, and Termina is built to Pauta 6.2.0."
date: 2026-11-26
version: 0.17.0
---

Termina 0.17.0 is a small release: two things that should simply work now do, and the move to
the newest edition of [Pauta](/docs/pauta).

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.17.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel, or
  `brew install --cask adriandeleon/tap/termina` on Apple Silicon.
- **Windows (x64)**: the MSI. A winget package, `AdrianDeLeon.Termina`, has been submitted and is
  waiting for Microsoft's review.
- **Linux (x64 and ARM64)**: `sudo apt install termina` from the
  [Vitrina](/docs/installing#linux) apt repository on Debian and Ubuntu, or the DEB, the RPM, an
  AppImage or a `tar.gz`.

## Changed

- **Termina's other windows open centred on the window you opened them from.** Settings, About,
  Doctor, Report a Problem, Pauta Conformance, Keyboard Shortcuts and the Debug Log used to open
  near that window's top-left corner. Dialogs were already centred.
- **Built to Pauta 6.2.0**, still at Core conformance. The edition makes a status bar optional and
  gives the echo area, the line that says how the last action went, a rule of its own. Termina's
  echo area is the left side of its status bar, as before, so nothing you see changes.

## Fixed

- **One click on a menu row runs it.** A click on a row's edge, or on the space between its title
  and its shortcut, highlighted the row and did nothing, so a command often took two clicks.
