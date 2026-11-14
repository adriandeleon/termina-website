---
title: Troubleshooting
description: The Doctor and the Message Log, why the title shows the directory, icons that draw the wrong glyph, middle-click pasting the clipboard instead of the selection, notifications that never reach the desktop, full screen misplaced under GNOME on Wayland, the Windows installer's SmartScreen warning, settings that came up as defaults, the debug log, diagnosing a freeze, and reporting a bug with Report a Problem.
category: Help
order: 11
---

## Start with the Doctor and the Message Log

**Help → Doctor** checks what Termina relies on: the shell it starts, its settings folder, Git and
the GitHub CLI for settings sync, and on Linux the tools for the selection clipboard, notifications
and opening links. A row that is not OK says what to install. **Copy as text** gives you the rows
for a bug report.

![The Doctor window: one row for each thing Termina relies on, each marked OK or Missing, with what was found](/screenshots/docs/doctor.png "Help → Doctor. Here the selection clipboard's tool is missing, and the row says which package to install.")

The status bar at the bottom of the window shows the last thing Termina reported. Click it, or
choose **Help → Message Log**, to see everything it has said since it started, newest first.

![The Message Log, a card over the terminal just above the status bar, with a filter field and Copy and Clear buttons](/screenshots/docs/message-log.png "The Message Log opens over the terminal. Escape closes it.")

## The title shows the directory, not what my prompt sets

That is intended. A shell names its window with an escape sequence (OSC 0 or OSC 2), and sending
it is the **prompt's** job. Any prompt framework that replaces `PS1`, such as Oh My Bash,
Starship or powerlevel10k, can drop it without a word, and every tab would then be called
"Termina". Other terminals often hide this because a distribution script re-adds the sequence
for them; on GNOME, `/etc/profile.d/vte-2.91.sh` does it only inside VTE terminals.

So Termina reads the shell's directory from the operating system instead:

- **The window title** shows the full path, with your home folder as `~` (`~/src/project`).
- **The tab** shows what is running in it (`vim`, `ssh`, `npm`), and the directory's last segment
  when the shell is idle. Hovering a tab shows the full directory.
- **A title the shell or a program sets still wins**, and when a program clears it again (vim, on
  exit), the directory comes back.
- **A name you give a tab** (double-click its header) outranks all of these.

If you want the prompt's title, make your prompt emit OSC 0. On Windows the directory is read from
the shell process too, except in a WSL tab, whose process is `wsl.exe`, and in Git Bash, which runs
as `bash.exe` as WSL's old launcher does; there it comes from the shell's own report (OSC 7 or
OSC 9;9), if it sends one. See
[What programs can ask for](/docs/programs#the-directory-osc-7).

## Icons show the wrong glyph

File-type icons from `eza --icons`, `lsd` or a prompt theme are Nerd Font characters, in Unicode's
private-use area. Since 0.11.0 Termina bundles **Symbols Nerd Font Mono** and draws them in a Nerd
Font whether or not one is installed, so the stray `¯` or `ÿ` some Linux machines showed beside
`eza`'s symlink icons is gone. Before, each icon went to the system's fallback fonts, and the first
face with *something* at that code point won: Arial (from msttcorefonts) and MathJax place glyphs
of their own there, ahead of a Nerd Font.

If an icon still looks wrong:

- **When the terminal font is a Nerd Font**, its own icons are drawn, so an icon that is wrong
  there is that font's. Choose another under **Settings → Appearance → Font → Font family** to
  compare.
- **Icons spill into the next cell.** The bundled face's icons are drawn a cell and a half wide,
  the size the Nerd Font builds draw theirs, so they lean into the space after them. That is
  intended.

See [Appearance](/docs/appearance#icons) for which face draws an icon.

## Middle-click pastes the clipboard, not the selection

On Linux, selecting sets the PRIMARY selection and middle-click pastes it, but JavaFX cannot reach
PRIMARY, so Termina uses a tool: `wl-paste` and `wl-copy` under Wayland, `xclip` or `xsel` under
X11. With none of them installed, middle-click pastes the clipboard. Install one, such as
`wl-clipboard` on a Wayland desktop or `xclip` on X11, and start Termina again. **Help → Doctor**
has a row for it, Selection clipboard, that says which package is missing. On macOS and Windows,
which have no PRIMARY, middle-click pastes nothing.

## Notifications never reach the desktop

A program's notification (OSC 9 or 777) marks its tab whenever the tab is in the background, but
reaches the desktop only when:

- **Settings → Terminal → Display → Desktop notifications** is on (the default);
- **you are not looking at that pane**: a notification from the pane you are typing in, in the
  window in front, is not posted;
- **it is the first from that pane in two seconds.** The rest only mark the tab.

On Linux it is posted with `notify-send`, or with `gdbus` straight to the session bus where
`notify-send` is not installed. To see whether one was sent, watch the bus from another terminal:

```bash
dbus-monitor --session "interface='org.freedesktop.Notifications'"
```

On macOS it goes through `osascript`, and on Windows it is a PowerShell toast. See [What programs can ask for](/docs/programs#notifications-osc-9-osc-777).

A notification that could not be shown says why in the [debug log](#the-debug-log): no
`notify-send` or `gdbus` to post it with, a tool that did not answer in five seconds, or one that
exited with an error. **Help → Doctor** has a Notifications row too.

## Full screen is misplaced or clipped on GNOME (Wayland)

Under GNOME on Wayland with fractional scaling, and more so with mutter's experimental
`xwayland-native-scaling`, a full-screen window can come out smaller than the screen, offset, or
clipped. JavaFX has no Wayland backend and runs through XWayland, and there the compositor and
JavaFX disagree about the size of the window. JavaFX reports a correct, screen-filling window
throughout, which is why nothing inside Termina can detect it.

- **Maximize instead.** Maximizing is not affected.
- **Use an integer scale** (100% or 200%), or turn off `xwayland-native-scaling`. To see what is
  set: `gsettings get org.gnome.mutter experimental-features`.
- **To compare sizes**, `xrandr` shows the size the panel really has.

## Windows SmartScreen warns about the installer

The Windows installer is not signed, so SmartScreen may warn on the first run. Choose
**More info**, then **Run anyway**. Each release lists its files' SHA-256 checksums in
`checksums_sha256.txt`, if you want to check the download first.

The macOS app has been signed and notarized since 0.14.0, so it opens the first time like any
other app. If macOS refuses a copy older than that, download the current one. See
[Installing](/docs/installing).

## Termina opened on its default settings

Since 0.15 the settings are in `settings.json`, not `settings.properties`. The old file is
imported the first time 0.15 starts and kept beside the new one with `.migrated` added to its
name, so nothing in it is lost.

If Termina opens on defaults all the same, look in the settings folder (`~/.termina` unless you
moved it):

- **A file ending in `.corrupt.bak`** is a settings file that was not valid JSON, set aside as it
  was. Fix the JSON and put it back under its own name while Termina is closed.
- **A file ending in `.v<n>.bak`** was written by a newer Termina than the one you are running.
  Update Termina.

The [debug log](#the-debug-log) says which file was not read and where it was kept. See
[Settings](/docs/settings#the-settings-files).

## The debug log

**Help → Debug Log** shows everything Termina logged this session, and anything that crashed.
**Find…** searches it, **Copy** and **Export…** take it out, and **Report a Problem…** builds a bug
report from it. The same lines are in `termina-session.log` in the settings folder (`~/.termina`
unless you moved it), started afresh at each launch, and the log of the session before is kept
beside it as `termina-session.prev.log`. It never leaves your machine.

## Termina froze

"It froze" has two causes that look the same from outside: Termina's interface thread was
blocked, or the program in the terminal had nothing to say yet. Only the first is Termina's. The
stall log tells them apart: started with `-Dtermina.stallLog=<milliseconds>`, Termina prints a
line to standard error whenever the interface goes longer than that without drawing a frame:

```
[termina] UI thread blocked for 1840ms
```

Before that line it prints where the interface was stuck, as a stack trace, once for each stall.

A packaged Termina takes Java options through `JAVA_TOOL_OPTIONS`. Start it from another terminal,
so that you can read what it prints:

```bash
# macOS
JAVA_TOOL_OPTIONS=-Dtermina.stallLog=400 /Applications/Termina.app/Contents/MacOS/Termina

# Linux (deb or rpm)
JAVA_TOOL_OPTIONS=-Dtermina.stallLog=400 /opt/termina/bin/Termina
```

If no such line appears while the window seems stuck, the interface was not blocked, and the
program running in the terminal is the place to look. The stall log is off by default because
it keeps the interface drawing 60 frames a second even when nothing changes.

## Reporting a bug

**Help → Report a Problem** does most of it for you. It builds a report of the session: the
version, Java, the operating system, the settings worth knowing and the end of the debug log.
Your home folder, user name, addresses, credentials and tokens are taken out.

- **You read it first.** The report is shown for you to read and edit.
- **Copy and Open GitHub** copies it and opens a GitHub issue in your browser with the bug form
  filled in. Nothing is sent until you submit the issue there.
- **A report too long for the link** stays on the clipboard, and a note in the issue's "Problem
  report" field says to paste it.

**If the window will not start**, run `termina --report` from a terminal. It builds the same
report from the last session's log, writes it to a file in the current folder (the temp folder
where that one cannot be written), prints the file's path and opens the issue. It needs no
display.

To write the issue yourself, open one at
[github.com/adriandeleon/Termina/issues](https://github.com/adriandeleon/Termina/issues) with:

- **The version.** `termina --version` prints it, with the build, Java and the operating system,
  without opening a window. From a packaged app, run the launcher directly:
  `/Applications/Termina.app/Contents/MacOS/Termina --version` on macOS,
  `/opt/termina/bin/Termina --version` on Linux, `termina-cli.exe --version` in Termina's folder on
  Windows. **Help → About** shows the same, with **Copy details**.
- **Your platform**: the operating system, the desktop on Linux (and whether it is Wayland), and
  the shell.
- **What you did and what happened**, with the steps to make it happen again.
- **The debug log**: Copy or Export from the Debug Log window, or `termina-session.log`.
- **The Doctor's rows**, where the problem is something Termina relies on: **Copy as text** in
  **Help → Doctor**.
