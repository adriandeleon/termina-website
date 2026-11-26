---
title: "Native installers"
group: "Your desktop"
order: 1
beta: false
summary: "A signed and notarized <code>.dmg</code> for Apple Silicon and Intel Macs, an <code>.msi</code> for Windows, and <code>.deb</code>, <code>.rpm</code>, AppImage and <code>tar.gz</code> downloads for Linux on x64 and arm64, each with its own Java runtime. Homebrew and an apt repository install it too."
---

- **Every platform**: macOS on Apple Silicon and Intel (`.dmg`), Windows on x64 (`.msi`), and Linux on x64 and arm64 (`.deb`, `.rpm`, an `AppImage`, and a `tar.gz` that needs no root), from the [releases page](https://github.com/adriandeleon/Termina/releases).
- **Package managers.** `brew install --cask adriandeleon/tap/termina` on an Apple Silicon Mac, and `sudo apt install termina` on Debian and Ubuntu from the [Vitrina](https://vitrina-project.dev) apt repository. A winget package is waiting for Microsoft's review.
- **Nothing else to install.** Each installer bundles the Java runtime it needs.
- **A terminal to the Linux desktop.** The packages add a menu entry in the `TerminalEmulator` category, so desktops offer Termina where a terminal is wanted, and Termina accepts `-e command`, which is what they call a terminal with. They put `termina` on your path and list Termina in GNOME Software and KDE Discover, and the `.deb` offers it as an `x-terminal-emulator`.
- **Older Linux too.** The `.deb` installs on Debian 12 and Ubuntu 22.04 and later, and the packages bring in the libraries the interface needs, so a minimal system works.
- **Signed on macOS.** The app is signed with a Developer ID and notarized by Apple, so it opens the first time like any other app.
- **Signed on Linux.** Every Linux download has an OpenPGP signature beside it, and the `.deb` and the `.rpm` are signed inside as well.
- **Checksums.** Each release lists its files' SHA-256 checksums in `checksums_sha256.txt`.
- **Windows is not signed yet**, so SmartScreen may warn on the first run.

See [Installing](/docs/installing) for each download and how to check it.
