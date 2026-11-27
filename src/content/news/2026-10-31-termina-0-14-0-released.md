---
title: Termina 0.14.0 released
description: "Signed and notarized macOS downloads, OpenPGP-signed Linux downloads, an AppImage and a tarball, a proper GNOME Software listing, and Ctrl+click on the URLs in Markdown links."
date: 2026-10-31
version: 0.14.0
---

Termina 0.14.0 is about the downloads: every one of them is now signed, and Linux gets two more
ways to run it.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.14.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel, or
  `brew install --cask adriandeleon/tap/termina` on Apple Silicon.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB, the RPM, an AppImage or a `tar.gz`.

[Installing](/docs/installing) has the steps for each, and how to check a download.

## New

- **The macOS app is signed and notarized.** Both DMGs carry a Developer ID signature and Apple's
  notarization, so Termina opens on first launch with no Gatekeeper warning and no trip to System
  Settings → Privacy & Security.
- **The Linux downloads are signed.** Each has an OpenPGP signature beside it (`.asc`), made with the
  key attached to the release, and the RPM and DEB carry one inside as well, for `rpm -K`, `dnf`
  and `debsig-verify`.
- **An AppImage and a tarball for Linux**, on x64 and arm64, for distributions with neither package
  manager and for installing without root.

## Fixed

- **GNOME Software shows Termina properly.** It listed the DEB as a bare package with a generic
  icon and an unknown license; it now shows the description, the MIT license, the icon and the
  release notes.
- **Ctrl+click opens the URL in a Markdown link** (Cmd+click on macOS). `[docs](https://…)` read
  as a file path that did not exist, so the click did nothing.

The Windows installer is still unsigned. The full list is in [What's new](/docs/whats-new).
