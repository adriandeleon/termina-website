---
title: Termina 0.10.0 released
description: "Icons beside each page of the settings sidebar, and the first release with a website of its own."
date: 2026-10-23
version: 0.10.0
---

Termina 0.10.0 is a small release: the settings sidebar has icons. It is also the first release
with a website, the one you are reading.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.10.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel. Open it and drag
  Termina to Applications.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB (`sudo apt install ./Termina-*.deb`) or the RPM
  (`sudo dnf install ./Termina-*.rpm`).

Each bundles its own Java runtime. The builds are unsigned: on macOS allow the first launch under
System Settings → Privacy & Security → Open Anyway, and on Windows choose More info → Run anyway if
SmartScreen warns. Verify a download against `checksums_sha256.txt`.

## What changed

- **The settings sidebar has icons.** Appearance has a palette, Terminal a prompt window, Profiles
  stacked layers and Advanced sliders: Material glyphs, the same family as the context menu's. They
  are muted at rest and take the accent color when their page is selected, and each sits in a box
  of fixed width so the labels beside them line up.

New user-visible behavior, so the minor number moves. The full list is in the
[changelog](https://github.com/adriandeleon/Termina/blob/master/CHANGELOG.md).
