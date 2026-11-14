---
title: Getting Started
description: Install Termina, open it for the first time, learn the keys worth knowing first, find the settings file, and build it from source.
category: Getting started
order: 2
---

Install Termina, open a tab, and learn the few keys that do most of the work.

## Install

Download the installer for your system from the
[releases page](https://github.com/adriandeleon/Termina/releases), or use a package manager. Each
download carries its own Java runtime, so you need nothing else.

| System | Quickest way |
| --- | --- |
| macOS, Apple Silicon | `brew install --cask adriandeleon/tap/termina`, or the `macos-arm64` `.dmg` |
| macOS, Intel | The `macos-x64` `.dmg`: open it and drag Termina to Applications |
| Windows | `winget install AdrianDeLeon.Termina`, or run the `.msi` |
| Debian, Ubuntu | `sudo apt install ./Termina-*.deb` |
| Fedora, RHEL, openSUSE | `sudo dnf install ./Termina-*.rpm` |
| Any other Linux | The `AppImage`, or the `tar.gz` if you have no root |

[Installing](/docs/installing) has every download, how to check one against its signature, and
what the installers put on your computer.

## First launch

The window opens with one tab running your login shell, started with `-l` so your profile sets up
`PATH` the way it does in any other terminal. A few things to know:

- **The tab strip is hidden while there is only one tab.** Open a second and it appears; the row is
  given back to the terminal when you are down to one again.
- **The menu bar** is in the window on Linux and Windows, and in the screen menu bar on macOS.
- **Right-click** gives Copy and Paste, New Tab and New Window, Select All, Clear Scrollback, zoom,
  the command palette, Settings and About, unless a program running in the terminal has taken the
  mouse. Hold `Shift` to get the menu anyway.
- **An update check** asks GitHub for the newest release at most once a day.
  **Settings → Advanced → Updates** turns it off.

## The keys to learn first

Termina's own commands use `Ctrl+Shift` on Linux and Windows, because plain `Ctrl+letter` belongs
to the shell: `Ctrl+C` is still SIGINT and `Ctrl+W` still deletes a word. On macOS they use `Cmd`.

| Key | Does |
| --- | --- |
| `Ctrl+Shift+P` (`Cmd+Shift+P` on macOS) | The command palette: every command, found by typing part of its name |
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
The file is UTF-8, and a backslash is just a backslash, so a Windows path can be typed as it is. A
file that cannot be read at all opens on defaults and is first copied aside as
`settings.properties.bad-<time>`.

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
