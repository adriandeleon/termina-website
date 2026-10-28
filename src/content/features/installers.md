---
title: "Native installers"
group: "Your desktop"
order: 1
beta: false
summary: "A <code>.dmg</code> for Apple Silicon and Intel Macs, an <code>.msi</code> for Windows, and <code>.deb</code> and <code>.rpm</code> packages for Linux on x64 and arm64, each with its own Java runtime."
---

- **Every platform**: macOS on Apple Silicon and Intel (`.dmg`), Windows on x64 (`.msi`), and Linux on x64 and arm64 (`.deb` and `.rpm`), from the [releases page](https://github.com/adriandeleon/Termina/releases).
- **Nothing else to install.** Each installer bundles the Java runtime it needs.
- **A terminal to the Linux desktop.** The packages add a menu entry in the `TerminalEmulator` category, so desktops offer Termina where a terminal is wanted, and Termina accepts `-e command`, which is what they call a terminal with. They put `termina` on your path and list Termina in GNOME Software and KDE Discover, and the `.deb` offers it as an `x-terminal-emulator`.
- **Older Linux too.** The `.deb` installs on Debian 12 and Ubuntu 22.04 and later, and the packages bring in the libraries the interface needs, so a minimal system works.
- **Checksums.** Each release lists its files' SHA-256 checksums in `checksums_sha256.txt`.
- **Not signed yet.** macOS refuses the first launch until you allow it under Privacy &amp; Security, and Windows SmartScreen may warn.

Homebrew and winget packages are planned but not published yet. See [Getting started](/docs/getting-started).
