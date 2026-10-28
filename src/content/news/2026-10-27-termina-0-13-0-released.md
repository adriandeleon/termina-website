---
title: Termina 0.13.0 released
description: "Padding, a hollow cursor in panes without the keyboard, Reopen for panes and windows, -T for the first tab, the palette on Ctrl+Shift+P, and Linux packages that install on Debian 12 and Ubuntu 22.04."
date: 2026-10-27
version: 0.13.0
---

Termina 0.13.0 is the result of a full review of the app, its installers and its release process.
Most of it is fixes, a few of them the kind that cost you work, and a handful of things that were
missing.

## Download

From the [release page](https://github.com/adriandeleon/Termina/releases/tag/v0.13.0):

- **macOS**: the `macos-arm64` DMG for Apple Silicon, `macos-x64` for Intel. Open it and drag
  Termina to Applications.
- **Windows (x64)**: the MSI.
- **Linux (x64 and ARM64)**: the DEB (`sudo apt install ./Termina-*.deb`) or the RPM
  (`sudo dnf install ./Termina-*.rpm`).

Each bundles its own Java runtime. The builds are unsigned: on macOS allow the first launch under
System Settings → Privacy & Security → Open Anyway, and on Windows choose More info → Run anyway if
SmartScreen warns. Verify a download against `checksums_sha256.txt`. Each installer now carries a
build-provenance attestation too: `gh attestation verify <file> --repo adriandeleon/Termina`.

## New

- **Padding around the text.** Terminal → Display → Padding, 4 pixels by default, up to 32.
- **A hollow cursor where your keys are not going.** In an unfocused pane or window a block cursor
  is drawn as an outline, and a bar or underline is dimmed, so the pane that takes your typing is
  the one with the solid block.
- **Reopen Closed Tab brings back a closed pane and a closed window**, not only a tab. A pane comes
  back as a tab of its own beside the one it left; a window comes back as a window with its tabs.
- **`-T title`** (`--title`) names the first tab, as Debian expects of an `x-terminal-emulator`.
- **The right-click menu has the command palette and About**, beside Settings.
- **The Linux packages** put `termina` on your path, list Termina in GNOME Software and KDE Discover,
  and the `.deb` adds it to the `x-terminal-emulator` choices without making it the default.

## Changed

- **The command palette opens with `Ctrl+Shift+P`** (`Cmd+Shift+P` on a Mac), as in Windows
  Terminal and VS Code. `Alt+X` was Emacs's and zsh's `M-x`, taken from every program in the
  terminal. A chord you chose yourself is kept.
- **On macOS, New Tab With Profile 1–9 is `Cmd+Option+1`…`9`.** `Cmd+Shift+3`, `4` and `5` are the
  system's screenshot keys, so three of the nine never reached Termina.
- **The settings file takes backslashes literally and is UTF-8**, so a Windows path is typed as it
  is. Files written by earlier versions read the same.
- **Shells no longer inherit the identity of the terminal Termina was started from**: `TMUX`,
  kitty's, WezTerm's, iTerm2's and Windows Terminal's variables, `COLUMNS` and `LINES` are left out.

## Fixed

Installing:

- **The `.deb` installs on Debian 12 and Ubuntu 22.04 again.** It depended on a sound library only
  newer systems have, for sound Termina never plays. The packages now declare the libraries the
  interface needs, so a minimal system installs them rather than failing at start.

The terminal:

- **Colors written with colons** (`ESC[38:2::255:0:0m`, the form newer programs use for 24-bit
  color) printed the colons and lost the color. They read like their semicolon forms now.
- **An image sent with the kitty graphics protocol** appeared as a line of base64. It is discarded,
  as xterm does.
- **The cursor drifted a cell right for each emoji or Nerd Font icon on its line**, and text after
  a combining mark or `⚠️` shifted a cell left.
- **Hovering over a link no longer touches the disk while holding the terminal**, which froze the
  window on a slow mount.

Settings and sessions:

- **A malformed settings file no longer stops Termina opening.** It opens on defaults and keeps the
  file as `settings.properties.bad-<time>`.
- **Two Termina processes no longer overwrite each other's settings.**
- **When the shell cannot start, the window stays open and says why**, rather than the app quitting.
- **A sync retried after another computer pushed sent back the older binding it had just
  received.** It keeps the newer one now, and a sync that sent nothing no longer reports success.

Windows, tabs and keys:

- **Chords typed into the command palette or the find bar reached the menus behind them**:
  `Ctrl+Shift+W` in the palette closed the tab.
- **Many tabs in a narrow window** clipped the first and hid the selected tab's name.
- **Closed windows stayed in memory until the app quit.**

## Security

- **A `file:` link to another machine, or a Windows network path, is never opened or looked up.**
  On Windows that lookup is a connection to the other machine that sends it a hash of your
  password, and hovering over such a link made one.
- **A crafted `.terminal` theme can no longer hang or crash Termina** on import.

New user-visible behavior, so the minor number moves. The full list is in the
[changelog](https://github.com/adriandeleon/Termina/blob/master/CHANGELOG.md).
