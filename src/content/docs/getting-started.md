---
title: Getting Started
description: Install Termina, open it for the first time, learn the keys worth knowing first, find the settings file, and build it from source.
category: Getting started
order: 2
---

Install Termina, open a tab, and learn the few keys that do most of the work.

## Install

Download the package for your platform from the
[releases page](https://github.com/adriandeleon/Termina/releases). Each one bundles its own Java
runtime, so you need nothing else. Verify a download against `checksums_sha256.txt` on the same
page.

| Platform | File |
| --- | --- |
| macOS, Apple Silicon | `Termina-0.11.0-macos-arm64.dmg` |
| macOS, Intel | `Termina-0.11.0-macos-x64.dmg` |
| Windows x64 | `Termina-0.11.0-windows-x64.msi` |
| Debian, Ubuntu (x64 / ARM64) | `Termina-0.11.0-linux-x64.deb` / `Termina-0.11.0-linux-arm64.deb` |
| Fedora, RHEL, openSUSE (x64 / ARM64) | `Termina-0.11.0-linux-x64.rpm` / `Termina-0.11.0-linux-arm64.rpm` |

Homebrew and winget packages are planned but not published yet; releases are on GitHub only.

### Linux

```bash
# Debian / Ubuntu
sudo apt install ./Termina-*.deb

# Fedora
sudo dnf install ./Termina-*.rpm
```

The packages add a launcher entry in the `TerminalEmulator` category, so Termina appears in the
application menu and desktops can offer it where a terminal is wanted. See
[Command line](/docs/command-line#the-linux-desktop-entry).

### macOS

Open the `.dmg` and drag Termina to Applications.

The app is not yet signed with an Apple Developer ID or notarized, so macOS refuses to open it the
first time. Allow it once under **System Settings → Privacy & Security → Open Anyway**.

### Windows

Run the `.msi`. It installs for the current user and adds a Start menu entry. The installer is
unsigned, so SmartScreen may warn on the first run: choose **More info → Run anyway**.

## First launch

The window opens with one tab running your login shell, started with `-l` so your profile sets up
`PATH` the way it does in any other terminal. A few things to know:

- **The tab strip is hidden while there is only one tab.** Open a second and it appears; the row is
  given back to the terminal when you are down to one again.
- **The menu bar** is in the window on Linux and Windows, and in the screen menu bar on macOS.
- **Right-click** gives Copy, Paste, Select All, Clear Scrollback, zoom and Settings, unless a
  program running in the terminal has taken the mouse. Hold `Shift` to get the menu anyway.
- **An update check** asks GitHub for the newest release at most once a day.
  **Settings → Advanced → Updates** turns it off.

## The keys to learn first

Termina's own commands use `Ctrl+Shift` on Linux and Windows, because plain `Ctrl+letter` belongs
to the shell: `Ctrl+C` is still SIGINT and `Ctrl+W` still deletes a word. On macOS they use `Cmd`.

| Key | Does |
| --- | --- |
| `Alt+X` (`Option+X` on macOS) | The command palette: every command, found by typing part of its name |
| `Ctrl+Shift+T` (`Cmd+T`) | New tab |
| `Ctrl+Shift+W` (`Cmd+W`) | Close tab (asks first if something is running in it) |
| `Ctrl+Shift+E` / `Ctrl+Shift+O` (`Cmd+D` / `Cmd+Shift+D`) | Split the pane right / down |
| `Ctrl+Shift+C` / `Ctrl+Shift+V` (`Cmd+C` / `Cmd+V`) | Copy / paste |
| `Ctrl+Shift+F` (`Cmd+F`) | Find in the scrollback |
| `Ctrl`+click (`Cmd`+click) | Open the URL or file path under the pointer |
| `Alt+1`…`Alt+9` (`Cmd+1`…`Cmd+9`) | Go to a tab; `9` is always the last one |
| `Ctrl+,` (`Cmd+,`) | Settings |
| `Ctrl+Shift+/` (`Cmd+/`) | Keyboard Shortcuts: every chord the window answers to, searchable |

You do not need to memorize more. The palette shows each command's chord beside its name, and
**Help → Keyboard Shortcuts** lists the rest, including the keys the terminal handles itself. The
full table is on the [Keybindings](/keybindings) page. Any of them can be changed under
**Settings → Keyboard**.

## Where settings live

Settings are one plain properties file, `~/.termina/settings.properties`, on every platform
(`%USERPROFILE%\.termina` on Windows). The Settings window writes it as you change things — there
is no OK button — and the file is meant to be readable and editable by hand while Termina is closed.
A value that does not parse falls back to its default, and numbers are clamped when they are read.

- **Settings → Advanced → Files** shows the path, and **Help → About** names it too.
- **Another folder**: start Termina with `--config-dir DIR`, or set `TERMINA_CONFIG_DIR`.
- **The debug log** (Settings → Advanced → Diagnostics) keeps this session's warnings and errors
  and mirrors them to `termina-session.log` beside the settings file.
- **Reset to Defaults** asks first, and keeps your shell profiles.

## Build from source

Termina needs JDK 25. Everything else, Maven included, is fetched by the wrapper.

```bash
git clone https://github.com/adriandeleon/Termina.git
cd Termina
./mvnw javafx:run      # run it
./mvnw test            # run the tests
```

To build a self-contained app image with its own runtime:

```bash
./mvnw clean -Pdist -DskipTests package     # => target/dist/Termina (Termina.app on macOS)
```

`clean` is required, not optional. jpackage builds only for the platform it runs on, so each
platform's image has to be built there.
