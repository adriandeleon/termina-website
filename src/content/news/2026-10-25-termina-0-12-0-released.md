---
title: Termina 0.12.0 released
description: "Settings sync can create its private repository on GitHub, and the shortcut reference names what a middle-click pastes on Linux."
date: 2026-10-25
version: 0.12.0
---

Termina 0.12.0 is a small release, out the same day as 0.11.0. Setting up
[settings sync](/docs/sync) no longer starts with a trip to the browser.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.12.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel. Open it and drag
  Termina to Applications.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB (`sudo apt install ./Termina-*.deb`) or the RPM
  (`sudo dnf install ./Termina-*.rpm`).

Each bundles its own Java runtime. The builds are unsigned: on macOS allow the first launch under
System Settings → Privacy & Security → Open Anyway, and on Windows choose More info → Run anyway if
SmartScreen warns. Verify a download against `checksums_sha256.txt`.

## What changed

- **Create the sync repository from Settings.** With the [GitHub CLI](https://cli.github.com)
  installed and signed in, Settings → Sync has **Create on GitHub…** beside **Connect**. It asks for
  a name (`termina-sync` by default), makes a private repository with `gh repo create`, and connects
  to it: over SSH if that is the protocol `gh` is set to, HTTPS otherwise. Termina never sees a
  token.
- **The shortcut reference names what a middle-click pastes.** On Linux a middle-click pastes the
  PRIMARY selection, and Help → Keyboard Shortcuts said "Paste the clipboard". It says "Paste the
  selection" there now.

New user-visible behavior, so the minor number moves. The full list is in the
[changelog](https://github.com/adriandeleon/Termina/blob/master/CHANGELOG.md).
