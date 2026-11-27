---
title: Termina 0.15.1 released
description: "Built to Pauta 6.1.0, a window that opens at the opacity you set on Linux, and find-bar arrows that match the menus."
date: 2026-11-14
version: 0.15.1
---

Termina 0.15.1 follows 0.15.0 the same day with two fixes, and moves to the next edition of
[Pauta](/docs/pauta).

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.15.1), or with
`brew upgrade` on a Mac that installed the cask.

## Changed

- **Built to Pauta 6.1.0**, still at Core conformance. The edition makes an icon on every menu row
  optional; Termina keeps one on every row, so its menus look as they did.

## Fixed

- **On Linux a window opens at the opacity set in Settings → Appearance.** Below 100% it came up
  opaque, and turned see-through only after a trip through full screen.
- **The find bar's arrows wear the Find Next and Find Previous icons** their menu rows show, and
  Find Next's now points up: it steps to older output, up the scrollback.
