---
title: Troubleshooting
description: Why the title shows the directory, icons that draw the wrong glyph, full screen misplaced under GNOME on Wayland, opening an unsigned build on macOS and Windows, the debug log, diagnosing a freeze, and reporting a bug.
category: Help
order: 11
---

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

If you want the prompt's title, make your prompt emit OSC 0. On Windows Termina cannot read the
shell's directory yet, so there the title is only what the shell sets.

## Icons show the wrong glyph

File-type icons from `eza --icons`, `lsd` or a prompt theme are Nerd Font characters, in Unicode's
private-use area. Termina 0.10.0 does not bundle a Nerd Font, so:

- **Install a Nerd Font**, a monospace font patched with the icons such as JetBrainsMono Nerd
  Font, and choose it under **Settings →
  Appearance → Font → Font family**. When the terminal's own face has the icons, nothing has to be
  looked up elsewhere.
- **If you keep a font without icons**, each icon is looked up in the system's fallback fonts, and
  the first face that has *something* at that code point wins. On Linux, fonts such as Arial
  (from msttcorefonts) and MathJax place glyphs of their own in that area ahead of a Nerd Font,
  so a few icons draw as a stray `¯` or `ÿ` while the rest are right. Choosing the Nerd Font as the
  terminal font avoids the lookup.

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

## macOS refuses to open Termina

The macOS build is not signed with an Apple Developer ID and is not notarized, so the first launch
of a downloaded copy is refused. Allow it once under **System Settings → Privacy & Security →
Open Anyway**. Later launches open normally.

## Windows SmartScreen warns about the installer

The Windows installer is not signed, so SmartScreen may warn on the first run. Choose
**More info**, then **Run anyway**. Each release lists its files' SHA-256 checksums in
`checksums_sha256.txt`, if you want to check the download first.

## The debug log

**Settings → Advanced → Diagnostics → Show Debug Log…** shows this session's warnings, errors and
anything that crashed, with Copy. The same lines are in `termina-session.log` in the settings
folder (`~/.termina` unless you moved it), started afresh at each launch, so the file from a run
that failed is still there until you start Termina again. It never leaves your machine.

## Termina froze

"It froze" has two causes that look the same from outside: Termina's interface thread was
blocked, or the program in the terminal had nothing to say yet. Only the first is Termina's. The
stall log tells them apart: started with `-Dtermina.stallLog=<milliseconds>`, Termina prints a
line to standard error whenever the interface goes longer than that without drawing a frame:

```
[termina] UI thread blocked for 1840ms
```

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

Open an issue at
[github.com/adriandeleon/Termina/issues](https://github.com/adriandeleon/Termina/issues) with:

- **The version.** `termina --version` prints it without opening a window. From a packaged app,
  run the launcher directly: `/Applications/Termina.app/Contents/MacOS/Termina --version` on
  macOS, `/opt/termina/bin/Termina --version` on Linux. **Help → About** shows it too.
- **Your platform**: the operating system, the desktop on Linux (and whether it is Wayland), and
  the shell.
- **What you did and what happened**, with the steps to make it happen again.
- **The debug log**: Copy from the Debug Log window, or `termina-session.log`.
