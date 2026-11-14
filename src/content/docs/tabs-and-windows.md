---
title: Tabs and windows
description: Opening and closing tabs and windows, the tab strip and its menu, reordering, renaming, pinning, reopening and duplicating tabs, the tabs that come back at the next launch, what changes when a tab is split into panes, activity, bell and notification marks, jumping to a tab, the close prompt, quitting, titles and the directory, remembered window geometry, and full screen.
category: Using Termina
order: 3
---

A window is a menu bar over a strip of tabs. Each tab owns its own shell on its own
pseudo-terminal, or several when it is [split into panes](/docs/panes); no two tabs share a
session. Windows share the settings, the theme and the Settings window, and nothing else.

## Tabs

![A Termina window with four tabs, one of them renamed "server", and a directory listing in the tab in front](/screenshots/docs/tabs.png "Four tabs; the one named server was renamed by hand, and a dot marks a tab with output you have not seen.")

| Key | Does |
| --- | --- |
| `Ctrl+Shift+T` (`Cmd+T`) | New Tab, running the default profile |
| `Ctrl+Shift+W` (`Cmd+W`) | Close Tab, or the active pane of a split tab |
| `Ctrl+Shift+Z` (`Cmd+Z`) | Reopen Closed Tab |
| `Ctrl+Shift+]` / `Ctrl+Shift+[` (`Cmd+Shift+]` / `Cmd+Shift+[`) | Next / previous tab, wrapping at the ends |
| `Alt+1`…`Alt+8` (`Cmd+1`…`Cmd+8`) | Go to that tab |
| `Alt+9` (`Cmd+9`) | Go to the last tab, however many are open |
| `Ctrl+Shift+Left` / `Right` (`Cmd+Shift+Left` / `Right`) | Move the tab one place, stopping at the ends |

A new tab starts in the current tab's directory, read from the shell process, unless its profile
names a directory of its own. In a split tab that is the directory of the pane you are typing in.
A tab closes on its own when its shell exits, or in a split tab when the last pane's shell does.

The jump chords have no menu items. The command palette lists the open tabs by title, each with
the chord that reaches it. These are the default chords: the Keyboard page of Settings changes any
of them, and the menus, the palette and the tab strip's tooltips follow.

## The tab strip

- **It hides itself while one tab is open**, and the shell gains the row. **Settings → Appearance →
  Tabs → Hide the tab bar with a single tab** turns that off.
- **View → Hide Tab Bar** hides it however many tabs are open. The same command is in the palette,
  and **Settings → Appearance → Tabs → Show the tab bar** is the same switch. The tabs are still
  there: their shortcuts, the tab menu and the palette reach them.
- **Tabs tile the full width** and shrink as more are added, down to 60 pixels; past that the strip
  overflows rather than drawing tabs too narrow to read.
- **The `+` button** opens a tab with the default profile. The chevron beside it drops the list of
  [shell profiles](/docs/shell-profiles).
- **Drag a tab** to reorder it. An accent edge shows which side of the target it will land on.
  Reordering never touches the shell inside.
- **Hover a tab** to see its directory in full, with your home directory written as `~`.
- **Double-click a tab** to rename it.

## The tab menu

Right-click a tab for a menu that acts on that tab, not on the selected one:

- **New Tab**, **New Tab With** (the profile list), **Duplicate Tab**
- **Rename Tab…**, **Pin Tab**
- **Close Tab**, **Close Other Tabs**, **Close Tabs to the Left**, **Close Tabs to the Right**
- **Move Tab Left**, **Move Tab Right**

Items that cannot apply, such as Move Tab Left on the first tab, are greyed, and resting the
pointer on one says why. Each row shows the shortcut its command is on.

The same commands act on the selected tab from the menu bar and the palette: Rename Tab…,
Duplicate Tab, Pin Tab and the two moves are in the Window menu, and the closes are in the File
menu. **Tab Menu**, in the palette, opens the current tab's menu from the keyboard; it has no
shortcut until you give it one.

## Pinned tabs

**Pin Tab**, in the Window menu, a tab's own menu and the palette, moves a tab to the pinned group
at the left of the strip and marks it with a pin and a bold title. On a pinned tab the command
reads **Unpin Tab**.

- **The bulk closes pass over it.** Close Other Tabs, Close Tabs to the Left and Close Tabs to
  the Right leave pinned tabs open. Closing a pinned tab by itself still works.
- **It comes back pinned**, from Reopen Closed Tab and at the next launch.

## Rename, reopen and duplicate

- **Rename.** A name you give a tab outranks everything else the tab would show: the shell's
  title, the running program and the directory. The window title follows it too. Leave the field
  empty to go back to the automatic title. In a split tab the name belongs to the pane that was
  active when you gave it, since the tab is titled after the active pane.
- **Reopen Closed Tab** brings back the last tab you closed in this window: the same shell or
  profile, the directory it was in when it closed, its name and its old place in the strip. A split
  tab comes back with every pane, arranged as it was. Its scrollback is gone with the process. The
  last twenty are kept. A shell that exited by itself is not among them, since nobody closed it.
  - **A pane closed on its own** comes back too, as a tab of its own beside the one it left.
  - **A closed window** comes back as a window, with each of its tabs where it was and the same one
    selected. The last five are kept.
  - **Reopen undoes the most recent close**, wherever it happened: if a window closed after the
    last tab, the window comes back first. Tabs closed together, by Close Other Tabs or Close Tabs to
    the Left or Right, come back in their old order.
- **Duplicate Tab** opens another tab running the same shell or profile, in the directory this one
  is in now, right beside it. In a split tab it duplicates the active pane, as one pane.

## The last session comes back

The tabs open when Termina ends come back at the next launch.

- **What comes back**: each tab and each pane, as a new shell in the folder it was in, with its
  profile, the name you gave it, its place in the split and its pin, and the same tab in front.
- **What does not**: the scrollback, and the programs that were running. A tab started with `-e`
  comes back as a shell in the same folder; its command is not run again.
- **One window.** It is the last window's tabs when the last window closes, and the front
  window's when you quit.
- **Where a folder is gone**, its pane starts where its profile does.
- **With `-d`, `-T` or `-e` on the command line**, the session comes back and the launch opens a
  tab of its own after it.

**Settings → Terminal → Session → Reopen tabs at launch** turns it off, and starting Termina with
`--no-session` skips it for one launch. See [Command line](/docs/command-line).

If you end every shell with `exit`, the window closes with no tabs left, and the next launch
opens one tab as before.

## Activity, bell and notification marks

A tab you are not looking at shows a mark after its title when something happened in it, in any of
its panes. Each kind has its own shape as well as its own color:

- **Activity**, a dot: output that arrived after two quiet seconds, such as a build finishing or a
  prompt coming back. A tab that streams continuously is marked once.
- **Bell**, a ring: a program rang the bell. It outranks activity.
- **Notification**, a diamond: a program posted a notification (OSC 9 or OSC 777). It outranks
  the bell.

The colors are the theme's cyan, yellow and magenta. Selecting the tab clears the mark, and a new
tab's own first prompt does not count.

## Closing asks when something is running

Closing a tab whose shell has a child process asks first, and names what is running: `vim`, an
`ssh` session, a build, or a background job such as `sleep 60 &`, which dies with the tab just the
same. An idle prompt closes at once. **Cancel** is the default button.

Every deliberate close goes through the same question: the close button, the menu items, the
chord, Close Other Tabs and Close Tabs to the Left or Right (one question for the whole set), and
closing the window. A split tab asks once for all of its panes. In a split tab the chord closes only the
active pane, and asks only about what is running in it; see
[Split panes](/docs/panes#closing-a-pane). **Settings → Terminal → Session → Ask before closing a
running tab** turns it off.

**File → Quit Termina**, also in the palette, closes every window and ends Termina. If a program
is still running in any tab of any window it asks first, once, with **Cancel** as the default. On
macOS the application menu's Quit and the Dock's ask the same question.

## Windows

| Key | Does |
| --- | --- |
| `Ctrl+Shift+N` (`Cmd+N`) | New Window, with a tab of its own |
| `Ctrl+Shift+Q` (`Cmd+Shift+W`) | Close Window and every tab in it |

A new window opens offset from the window it was asked for from, on the same screen. Closing the
last window quits Termina.

## Titles and the directory

The title bar shows the selected tab's title and follows it live. A split tab is titled after the
pane you are typing in, and changes as you move between panes. Each one is chosen in this order:

| | Window title | Tab |
| --- | --- | --- |
| 1 | The name you gave the tab | The name you gave the tab |
| 2 | A title the shell or a program set | A title the shell or a program set |
| 3 | The directory, as a path (`~/src/Termina`) | The program running (`vim`, `ssh`, `npm`) |
| 4 | | The directory's last segment (`Termina`) |

The directory is read from the operating system rather than from the prompt, so it works with any
prompt framework and needs nothing configured. See [Working directory](/features/working-directory).
A program that sets a title and later clears it, as vim does on exit, gives the directory back.
It is read the same way on every system. A directory the shell reports itself (OSC 7) is used
only where the system has no answer, which is a WSL tab on Windows: its process is `wsl.exe`,
which cannot say where the Linux shell inside it is.

## Window size and position

The window's size, position and maximized state are remembered across launches. A size saved on a
larger display is clamped to the screen that is actually there, and a position on a monitor that
has gone is pulled back onto one that exists, so the title bar is never left out of reach.

## Full screen

`F11` (`Ctrl+Cmd+F` on macOS) enters and leaves full screen. So do **View → Full Screen**, the
full-screen button on the right-click menu's zoom row, and **Full Screen** in the command palette.

`Escape` does not leave full screen. It belongs to the program in the terminal: the first `Escape`
in vim leaves insert mode, as it should.

Under GNOME's fractional scaling on Wayland, a full-screen window can be sized and placed wrongly,
because JavaFX runs through XWayland there. Maximizing is unaffected.
