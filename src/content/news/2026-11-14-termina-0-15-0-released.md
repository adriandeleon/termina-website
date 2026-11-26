---
title: Termina 0.15.0 released
description: "Built to Pauta at Core conformance: a status bar and Message Log, the last session back at launch, pinned tabs, pickers, window modes, a High Contrast theme, interface zoom, settings in JSON with export and import, and a terminal a screen reader can read."
date: 2026-11-14
version: 0.15.0
---

Termina 0.15.0 is the biggest release so far. Most of it came from building Termina to
[Pauta](/docs/pauta), the guidelines it shares with Editora and its other sibling apps, so that
they look and work the same on Windows, macOS and Linux. 0.15.0 meets every MUST rule of
Pauta 6.0.1, which makes it **Core** conformance. **Help → Pauta Conformance** lists the rules not
met yet, and why.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.15.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel, or
  `brew install --cask adriandeleon/tap/termina` on Apple Silicon.
- **Windows (x64)**: the MSI, which now also installs `termina-cli.exe` for use from a console.
- **Linux (x64 and ARM64)**: the DEB, the RPM, an AppImage or a `tar.gz`.

## New

- **The last session comes back.** The tabs and panes open when Termina ends reopen at the next
  launch, each as a new shell in the folder it was in.
- **A status bar and a Message Log.** The status bar says what the last command reported; the
  Message Log keeps everything said this session, with a filter.
- **Pinned tabs**, and Close Other Tabs, Close Tabs to the Left and Close Tabs to the Right.
- **Pickers**: a list to type into whenever a command asks you to choose from a list, and Cancel
  List on `Ctrl+G` to back out of any of them.
- **Three window modes** in the View menu, and **Hide Tab Bar**.
- **A High Contrast theme**, a theme that follows the system's light or dark setting, and an
  **interface zoom** from 80% to 200%.
- **Every setting is in the command palette**, and the palette lists every command.
- **Help → Doctor** checks what Termina relies on, and **Help → Report a Problem** builds a bug
  report for you to read before you send it.
- **Export and Import Configuration**, to move your whole set-up to another computer.
- **A user guide for your version.** Help → Documentation, and each Settings page's help link,
  open the guide on this site for the version you are running.

## Changed

- **Settings are JSON**: `~/.termina/settings.json`, with a schema version. Older settings are
  carried over.
- **Settings sync carries a changed shortcut to every system.**
- **A screen reader can read the terminal**, and every control has a name it can announce.
- **A greyed menu row says why** when you rest the pointer on it.
- **Only one Termina runs per settings folder.** Launching it again opens a window in the one
  already running; `--new-instance` starts a separate one.

The rest, well over a hundred entries, is in [What's new](/docs/whats-new).
