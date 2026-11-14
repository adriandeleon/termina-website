---
title: Getting Started
description: Install Termina, open it for the first time, learn the keys worth knowing first, what the Help menu has, find the settings files, and build it from source.
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
- **The menu bar** is in the title bar on Linux and Windows, in one row with the window title and
  the window buttons, and in the screen menu bar on macOS.
- **The status bar** under the terminal says what the last command reported. Click it for the
  Message Log. **View → Hide Status Bar** takes it away.
- **Right-click** gives New Tab and New Window, Copy and Paste, Select All, Open Link and Copy
  Link Address, Clear Scrollback, zoom, the command palette, Settings and About, unless a program
  running in the terminal has taken the mouse. Hold `Shift` to get the menu anyway.
- **Your tabs come back.** The tabs and panes open when Termina ends reopen at the next launch,
  each as a new shell in the folder it was in. See
  [Tabs and windows](/docs/tabs-and-windows#the-last-session-comes-back).
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

## The Help menu

When something is unclear, the Help menu is where to look:

- **Command Palette…** finds any command by name.
- **Documentation** opens this guide, and **What's New** the release notes for your version.
- **Keyboard Shortcuts…** lists every shortcut, searchable.
- **Check for Updates…** asks GitHub whether a newer release exists.
- **Message Log** lists what Termina has reported this session, and **Debug Log** everything it
  logged.
- **Report a Problem…** builds a bug report for you to read and send as a GitHub issue.
- **Doctor** checks the programs and folders Termina relies on.
- **Pauta Conformance** says which edition of the [Pauta](https://pauta-project.dev/) guidelines
  Termina follows, and which rules it does not meet yet.
- **About Termina** shows the version, with **Copy details** for a bug report.

See [Troubleshooting](/docs/troubleshooting).

## Where settings live

Settings are two JSON files in the folder `~/.termina`, on every platform
(`%USERPROFILE%\.termina` on Windows):

- **`settings.json`** holds your preferences, key bindings and shell profiles.
- **`workspace-state.json`** holds what Termina remembers by itself: the window's place and size,
  the tabs of the last session, and when it last checked for an update.

The Settings window writes them as you change things — there is no OK button — and `settings.json`
is meant to be readable and editable by hand while Termina is closed. A value that does not parse
falls back to its default, and numbers are clamped when they are read. A file that is not valid
JSON is copied aside with `.corrupt.bak` added to its name, and Termina opens on defaults.

A `settings.properties` from a version before 0.15 is imported the first time 0.15 starts, and
kept beside the new files with `.migrated` added to its name.

- **Settings → Advanced → Files** shows the path, and **Help → About** names it too.
- **Another folder**: start Termina with `--config-dir DIR`, or set `TERMINA_CONFIG_DIR`.
- **Export and Import**, on the same page, save the whole configuration as a `.zip` and restore
  from one. See [Settings](/docs/settings#export-and-import-configuration).
- **The debug log** (**Help → Debug Log**) keeps everything Termina logged this session and
  mirrors it to `termina-session.log` beside the settings files.
- **Reset to Defaults** asks first, and keeps your shell profiles.

[Settings](/docs/settings#the-settings-files) has the shape of the files and the keys worth
knowing.

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
