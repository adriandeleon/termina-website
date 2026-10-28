---
title: Tabs and windows
description: Opening and closing tabs and windows, the tab strip and its menu, reordering, renaming, reopening and duplicating tabs, what changes when a tab is split into panes, activity, bell and notification marks, jumping to a tab, the close prompt, titles and the directory, remembered window geometry, and full screen.
category: Using Termina
order: 3
---

A window is a menu bar over a strip of tabs. Each tab owns its own shell on its own
pseudo-terminal, or several when it is [split into panes](/docs/panes); no two tabs share a
session. Windows share the settings, the theme and the Settings window, and nothing else.

## Tabs

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
- **Rename Tab…**
- **Close Tab**, **Close Other Tabs**, **Close Tabs to the Right**
- **Move Tab Left**, **Move Tab Right**

Items that cannot apply, such as Move Tab Left on the first tab, are disabled. The Window menu
also carries Rename Tab… and Duplicate Tab for the selected tab, so the keyboard and the palette
can reach them.

## Rename, reopen and duplicate

- **Rename.** A name you give a tab outranks everything else the tab would show: the shell's
  title, the running program and the directory. The window title follows it too. Leave the field
  empty to go back to the automatic title. In a split tab the name belongs to the pane that was
  active when you gave it, since the tab is titled after the active pane.
- **Reopen Closed Tab** brings back the last tab you closed in this window: the same shell or
  profile, the directory it was in when it closed, its name and its old place in the strip. A split
  tab comes back with every pane, arranged as it was. Its scrollback is gone with the process. The
  last ten are kept. A shell that exited by itself is not among them, since nobody closed it.
  - **A pane closed on its own** comes back too, as a tab of its own beside the one it left.
  - **A closed window** comes back as a window, with each of its tabs where it was and the same one
    selected. The last five are kept.
  - **Reopen undoes the most recent close**, wherever it happened: if a window closed after the
    last tab, the window comes back first. Tabs closed together, by Close Other Tabs or Close Tabs to
    the Right, come back in their old order.
- **Duplicate Tab** opens another tab running the same shell or profile, in the directory this one
  is in now, right beside it. In a split tab it duplicates the active pane, as one pane.

## Activity, bell and notification marks

A tab you are not looking at shows a dot after its title when something happened in it, in any of
its panes:

- **Activity**: output that arrived after two quiet seconds, such as a build finishing or a prompt
  coming back. A tab that streams continuously is marked once.
- **Bell**: a program rang the bell. It outranks activity and uses a different color.
- **Notification**: a program posted a notification (OSC 9 or OSC 777). It outranks the bell.

The colors are the theme's cyan, yellow and magenta. Selecting the tab clears the mark, and a new
tab's own first prompt does not count.

## Closing asks when something is running

Closing a tab whose shell has a child process asks first, and names what is running: `vim`, an
`ssh` session, a build, or a background job such as `sleep 60 &`, which dies with the tab just the
same. An idle prompt closes at once. **Cancel** is the default button.

Every deliberate close goes through the same question: the close button, the menu items, the
chord, Close Other Tabs and Close Tabs to the Right (one question for the whole set), and closing
the window. A split tab asks once for all of its panes. In a split tab the chord closes only the
active pane, and asks only about what is running in it; see
[Split panes](/docs/panes#closing-a-pane). **Settings → Terminal → Session → Ask before closing a
running tab** turns it off.

Quitting with `Cmd+Q` on macOS does not ask yet. On Linux and Windows there is no Quit item, so
quitting is closing the last window, which does ask.

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
On Windows the directory is read out of the shell process too, and a directory the shell reports
itself (OSC 7) wins there, since a WSL tab's process is `wsl.exe`.

## Window size and position

The window's size, position and maximized state are remembered across launches. A size saved on a
larger display is clamped to the screen that is actually there, and a position on a monitor that
has gone is pulled back onto one that exists, so the title bar is never left out of reach.

## Full screen

`F11` (`Ctrl+Cmd+F` on macOS) enters and leaves full screen. So does the full-screen button on the
zoom row in the View and right-click menus, and **Full Screen** in the command palette.

`Escape` does not leave full screen. It belongs to the program in the terminal: the first `Escape`
in vim leaves insert mode, as it should.

Under GNOME's fractional scaling on Wayland, a full-screen window can be sized and placed wrongly,
because JavaFX runs through XWayland there. Maximizing is unaffected.
