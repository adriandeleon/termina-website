---
title: Termina 0.16.0 released
description: "Opening Termina again can open a tab in the window you used last, and two quick launches make one Termina."
date: 2026-11-15
version: 0.16.0
---

Termina 0.16.0 is about what happens when you open Termina while it is already running.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.16.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel, or
  `brew install --cask adriandeleon/tap/termina` on Apple Silicon.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB, the RPM, an AppImage or a `tar.gz`.

## New

- **Opening Termina again can open a tab instead of a window.** Settings → Terminal → *When Termina
  is opened again* chooses: a new window (as before), a new tab in the window you used last, or a
  separate Termina. It applies from the next launch, with no restart, and a minimised window that
  gets the tab is brought back up. See [Command line](/docs/command-line#one-termina-per-settings-folder).

## Changed

- **The About window lists its links one to a line.** Side by side they read as one run of
  underlined text.

## Fixed

- **Two launches in quick succession make one Termina.** A double click used to start a second
  Termina while the first was still opening its window; the second launch now waits for the first.
- **The socket a second launch uses to reach the first is yours alone.** It could end up in the
  shared temp folder; it now goes in `$XDG_RUNTIME_DIR` or a private folder of your own.
