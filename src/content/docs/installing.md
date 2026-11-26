---
title: Installing
description: Every download Termina has, how to install each one, how to check that a download is the one that was published, and what the installers put on your computer.
category: Getting started
order: 2.5
---

Every release is on the
[releases page](https://github.com/adriandeleon/Termina/releases), as an installer built on the
system it is for. Each one carries its own Java runtime, so there is nothing else to install.

## The downloads

| System | File | How to install |
| --- | --- | --- |
| macOS, Apple Silicon | `Termina-<version>-macos-arm64.dmg` | Open it and drag Termina to Applications |
| macOS, Intel | `Termina-<version>-macos-x64.dmg` | Open it and drag Termina to Applications |
| Windows | `Termina-<version>-windows-x64.msi` | Run it |
| Debian, Ubuntu | `…-linux-x64.deb`, `…-linux-arm64.deb` | `sudo apt install ./Termina-*.deb`, or `sudo apt install termina` from [Vitrina](#linux) |
| Fedora, RHEL, openSUSE | `…-linux-x64.rpm`, `…-linux-arm64.rpm` | `sudo dnf install ./Termina-*.rpm` |
| Any Linux | `…-linux-x64.AppImage`, `…-linux-arm64.AppImage` | `chmod +x` it and run it (needs FUSE) |
| Any Linux, without root | `…-linux-x64.tar.gz`, `…-linux-arm64.tar.gz` | Unpack it and run `termina-<version>/bin/Termina` |

### macOS

With [Homebrew](https://brew.sh), on Apple Silicon:

```bash
brew install --cask adriandeleon/tap/termina
```

Or download the `.dmg`, open it, and drag Termina to Applications. Intel Macs have no Homebrew
cask; use the `macos-x64` `.dmg`.

The app is signed with a Developer ID and notarized by Apple, so it opens the first time like any
other app.

### Windows

Download the `.msi` and run it. The installer is not signed yet, so SmartScreen may warn the
first time: choose **More info → Run anyway**.

The installer puts two programs in Termina's folder. `Termina.exe` is the one the Start menu opens.
`termina-cli.exe` is the same program for use from a terminal, where `--help`, `--version` and
`--report` need a console to print in. See [Command line](/docs/command-line).

Termina's winget package, `AdrianDeLeon.Termina`, has been submitted and is waiting for
Microsoft's review. Until it is accepted, `winget install AdrianDeLeon.Termina` does not find it.

### Linux

On Debian, Ubuntu and their derivatives, Termina is in the
[Vitrina](https://vitrina-project.dev) apt repository, for x64 and arm64, signed with the same key
as the downloads. Add the repository once:

```bash
sudo install -d -m 0755 /etc/apt/keyrings
curl -fsSL https://vitrina-project.dev/vitrina.asc | sudo tee /etc/apt/keyrings/vitrina.asc > /dev/null
sudo tee /etc/apt/sources.list.d/vitrina.sources > /dev/null <<'EOF'
Types: deb
URIs: https://vitrina-project.dev/apt
Suites: stable
Components: main
Architectures: amd64 arm64
Signed-By: /etc/apt/keyrings/vitrina.asc
EOF
sudo apt update
```

Then install Termina; `sudo apt upgrade` keeps it up to date:

```bash
sudo apt install termina
```

Or install a download directly:

```bash
# Debian, Ubuntu
sudo apt install ./Termina-*.deb

# Fedora, RHEL, openSUSE
sudo dnf install ./Termina-*.rpm
```

The `.deb` installs on Debian 12, Ubuntu 22.04 and anything newer. Both packages bring in the
libraries the interface needs (GTK 3, OpenGL, fontconfig and the rest), so a minimal system works
too.

Without either package manager, or without root, the `AppImage` runs as it is once you have made
it executable, and the `tar.gz` unpacks to a folder whose `bin/Termina` starts it.

## Checking a download

Every release has a `checksums_sha256.txt` beside its files:

```bash
sha256sum --check --ignore-missing checksums_sha256.txt
```

Each Linux download is also signed. Beside every file there is an OpenPGP signature, `<file>.asc`,
made with the key attached to the release as `termina-signing-key.asc`:

```bash
gpg --import termina-signing-key.asc
gpg --verify Termina-<version>-linux-x64.deb.asc Termina-<version>-linux-x64.deb
```

`gpg` says **Good signature** when the file is the one that was published. It also warns that the
key is not certified by anyone you trust, which is expected for a key you have just imported.

The two packages carry a signature inside as well:

- **The `.rpm`.** After `sudo rpm --import termina-signing-key.asc`, `rpm -K Termina-*.rpm` checks
  it, and so does `dnf` when it installs.
- **The `.deb`.** `debsig-verify` checks its origin signature, once it has a policy for the key.
  `apt` and `dpkg` check nothing about a `.deb` you downloaded yourself, so for a `.deb` the `.asc`
  file is the check to make.

The macOS downloads are signed and notarized, which macOS checks for you when you open the app.
The Windows installer is not signed yet.

## What the installers put on your computer

On every system the application is one folder with its own runtime inside, and it comes with its
`LICENSE` and `NOTICE` files.

On Linux, the `.deb` and `.rpm` also install:

- **The `termina` command**, at `/usr/bin/termina`.
- **A launcher entry** in the `TerminalEmulator` category, so Termina is in the application menu
  and the desktop can offer it where a terminal is wanted.
- **A listing for the software center**, so GNOME Software and KDE Discover show Termina with its
  description, license, release notes and icon.
- **An `x-terminal-emulator` choice** (the `.deb` only). Termina is offered without taking over:
  its priority is below GNOME Terminal's and Konsole's. To make it the default, run
  `sudo update-alternatives --config x-terminal-emulator`.

[Command line](/docs/command-line#what-the-linux-packages-install) has the details of the launcher
entry.

## Updating and removing

Termina checks for a newer release once a day and says so in the status bar; **Help → Check for
Updates** checks now. It never downloads or installs anything by itself. To update, install the
newer download over the old one, or use `brew upgrade`, or `sudo apt upgrade` with the Vitrina
repository.

To remove it, use the system's own way: drag the app to the Trash on macOS, **Apps → Installed
apps** on Windows, `sudo apt remove termina` or `sudo dnf remove termina` on Linux. Your settings
stay in `~/.termina` until you delete that folder.
