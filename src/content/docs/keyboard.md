---
title: Keyboard
description: Why Termina's chords leave the shell its keys, changing any shortcut on Settings → Keyboard and the keys.* lines in the settings file, the command palette, its movement keys and its help key, the Keyboard Shortcuts reference, Alt as Meta, AltGr, the keys the terminal handles itself, and jumping to a tab.
category: Customization
order: 8
---

A terminal's keyboard belongs to the program running in it. Termina keeps its own commands to
chords a shell does not use, and handles a few keys itself where every terminal does.

The full list of commands and their default chords is on the [Keybindings](/keybindings) page.
This page covers the rules behind them, and how to change any of them.

## Chords that leave the shell its keys

- **On Linux and Windows an application chord is `Ctrl+Shift+key`, never `Ctrl+key`.** The plain
  form belongs to the shell: `Ctrl+T` is readline's transpose, `Ctrl+W` deletes a word, `Ctrl+C`
  is SIGINT, `Ctrl+V` is literal-next. So New Tab is `Ctrl+Shift+T`, Copy is `Ctrl+Shift+C` and
  Paste is `Ctrl+Shift+V`.
- **On macOS it is `Cmd+key`**, because Cmd is not a key a shell ever sees: `Cmd+T`, `Cmd+C`,
  `Cmd+V`.
- **A few chords are the platform's own.** Settings is `Ctrl+,` (`Cmd+,` on macOS). Zoom is
  `Ctrl+=`, `Ctrl+-` and `Ctrl+0` (`Cmd+=`, `Cmd+-`, `Cmd+0`). Full Screen is `F11`
  (`Ctrl+Cmd+F` on macOS). Close Window is `Ctrl+Shift+Q`, as in GNOME Terminal and Konsole
  (`Cmd+Shift+W` on macOS).
- **The chords work with the menu bar hidden.** They are handled by the window, ahead of the
  terminal, not by the menus.
- **Focus Menu Bar is `Ctrl+Shift+F10`** (`Cmd+Shift+F10` on macOS). It puts the keyboard in the
  menu bar and opens its first menu, so the arrow keys walk it. A plain `F10` and a lone `Alt`,
  which do this in most apps, belong to the program running in the terminal.

Every default chord is declared once, with its menu item, and the menu, the palette and the
reference all read the chord a command is on now, so none of them can advertise a chord that does
nothing.

## The command palette

![The Command Palette over the terminal with the query "tab", listing the commands that match and their shortcuts](/screenshots/docs/palette.png "The palette lists every command; the letters you typed are marked in each title.")

`Ctrl+Shift+P` (`Cmd+Shift+P` on macOS) opens it, as in Windows Terminal and VS Code.

- **Type part of a name.** "nw" finds New Window, "tab close" finds Close Tab with the words in
  either order, and "file new" reaches New Tab through the menu it lives in. The matched letters are
  shown in bold, and each row shows its chord.
- **Everything is there**: every command, every open tab by its title (with the chord that
  reaches it), every shell profile ("New Tab: zsh"), every theme ("Theme: Clear Dark"), and every
  setting ("Show a scrollbar: turn off", "Cursor shape: Bar", "Scrollback lines…").
- **A command that cannot run right now is greyed, not hidden.** Reopen Closed Tab, with nothing
  closed yet, stays in the list with the reason as its tooltip, and the cursor steps over it. At
  an equal match, a command that can run comes before one that cannot.
- **A line under the list** says what the highlighted command does, and a legend names the keys.
- `Enter` or a click runs the command; `Esc` closes the palette, and the keyboard goes back to
  where it was.

The keys in it are the arrows and Emacs's:

| Key | Does |
| --- | --- |
| `↑` `↓` | Move a line |
| `PageUp` / `PageDown` | Move a page |
| `Ctrl+N` / `Ctrl+P` | Line Down / Line Up |
| `Ctrl+V` / `Alt+V` (`Ctrl+V` / `Option+V`) | Page Down / Page Up |
| `Alt+<` / `Alt+>` | First / last row |
| `Ctrl+H` | Open the highlighted command's page of this guide |
| `Ctrl+G`, `Esc` | Close |

The query field takes the Emacs editing keys: `Ctrl+A`, `Ctrl+E`, `Ctrl+F`, `Ctrl+B`, `Alt+F`,
`Alt+B`, `Ctrl+D`, `Ctrl+K`, `Alt+D`, `Ctrl+W`, `Alt+W`, `Ctrl+Y` and `Ctrl+/`.

### Moving in the list

Moving a line and a page are four commands: **Line Down**, **Line Up**, **Page Down** and **Page
Up**. They are listed under View on **Settings → Keyboard**, where each can be given another
shortcut.

- **The defaults are Emacs's**: `Ctrl+N`, `Ctrl+P`, `Ctrl+V` and `Alt+V`, the same on every
  system.
- **The arrows, `PageUp` and `PageDown` always work**, whatever the four commands are on.
- **Their keys act only in a list.** With the terminal in front, `Ctrl+N` and `Ctrl+P` still
  reach the shell.
- **Run from the palette, the four scroll the terminal's history** a line or a page.

### Help for a command

`Ctrl+H` in the palette opens the page of this guide for the highlighted command. If you have
given `Ctrl+H` to a command, the help key is `F1`, and `Shift+F1` if that is taken too. The legend
under the list names the key in use.

### The palette's own chord

`Ctrl+Shift+P` is the palette's default, and like every other chord it can be changed: it is the
**Command Palette…** row under Help on **Settings → Keyboard** (below). The command is also the
first row of the Help menu.

Until 0.13.0 the default was `Alt+X`, Emacs's `M-x`. That took `M-x` from Emacs and zsh running in
the terminal, so it moved. If you had chosen a chord yourself, Alt+X included, you keep it.

## Changing a shortcut

![The Keyboard page of Settings, searched for "new tab": the commands that match, each with the shortcut it is on and a Reset button](/screenshots/docs/settings-keyboard.png "Settings → Keyboard, narrowed here by a search for new tab.")

**Settings → Keyboard** lists every command under the menu it lives in, each with the shortcut it
is on now. The settings search finds them by name, such as "close tab".

- **Click a shortcut and press the new one.** It applies at once, in every window.
- **A shortcut another command has is not taken on the first press.** The row names the command
  that has it. Press it again to move it, and that command is left with none.
- **`Backspace` or `Delete` removes a shortcut**, leaving the command on no key.
- **Reset** puts one command back on its default. If another command has that chord now, it asks
  first, because that command is left with none. **Reset All Shortcuts** asks, then puts every
  command back.
- **Commands with no shortcut of their own can be given one**: Rename Tab, Duplicate Tab, Pin
  Tab, Move Pane to New Tab and Sync Settings Now among them.
- **`Tab` and `Shift+Tab` leave the field**, as they do everywhere; `Ctrl+Tab` is a chord.

Two rules come from the shell's side of the keyboard:

- **A bare key is refused.** A function key works on its own; any other key needs `Ctrl`, `Alt`
  or `Cmd`, or the shell would lose it. Shift alone does not count, since `Shift+X` is a capital
  letter.
- **On Linux and Windows, `Ctrl` with a letter is allowed, with a note** that the shell no longer
  receives it. Bound to a command, `Ctrl+R` is no longer readline's history search. None of the
  defaults are `Ctrl+letter` for that reason.

Everything that shows a chord shows the one in force: the menus, the right-click menus, the
palette's rows and its opener, the
[Keyboard Shortcuts reference](#the-keyboard-shortcuts-reference), and the tooltips on the tab
strip's `+` and close buttons. A command with no shortcut shows none. The
[Keybindings](/keybindings) page on this site lists the defaults.

The shortcuts you change can follow you to your other computers: see
[Settings sync](/docs/sync).

### In the settings file

Each changed command is one line in `settings.json`, keyed by `keys.` and the command's id:

```json
{
  "schemaVersion": 3,
  "settings": {
    "keys.file.closeTab": "none",
    "keys.file.newTab": "Ctrl+Shift+Y"
  }
}
```

- **A command with no line is on its default**, unless another command has been given that
  chord. `none` leaves a command on no key.
- **The name is the command's id**: its area, a dot, and its name (`file.newTab`,
  `file.closeTab`, `pane.splitRight`, `palette.show`). Sync Settings Now is `sync.now`. The tab
  chords are `tab.goTo1` to `tab.goTo9` (`tab.goTo9` is the last tab), and the profile chords
  `file.newTabProfile1` to `file.newTabProfile9`.
- **Ids from before 0.15 are carried forward.** They had no area (`keys.newTab`,
  `keys.commandPalette`), and a file that still has them is brought up to date when it is read.
- **A chord is written with `Ctrl`, `Alt`, `Shift`, `Meta` (Cmd) and `Shortcut`**, which is Cmd on
  macOS and Ctrl elsewhere, then the key: `Shortcut+Shift+T`, `Alt+X`, `F9`.
- **A line that does not parse, or takes a chord another line already took, is ignored**, as if
  it were not there. A line naming a command this version does not have is left in the file.

## The Keyboard Shortcuts reference

![The Keyboard Shortcuts window, a searchable list of shortcuts grouped by menu](/screenshots/docs/shortcuts.png "Help → Keyboard Shortcuts shows the shortcuts in force, your own changes included.")

**Help → Keyboard Shortcuts…** (`Ctrl+Shift+/`, `Cmd+/` on macOS) lists every chord the window
answers to, grouped by menu, with a search box. It is built from the window's own binding table,
with the chords in force, so it cannot list a chord that does not work and shows a changed
shortcut as you changed it. It also lists the keys and gestures the terminal
handles itself (below). `Esc` closes it.

## Alt, Option and AltGr

- **Alt is Meta** by default. `Alt+F` sends `ESC f`, which readline reads as `M-f` (forward a
  word), and the same goes for `M-b`, `M-d` and the rest.
- **On macOS, Option is Meta too**, and the key is sent as itself. Option is normally a compose key
  (Option+F types `ƒ`), and Termina sends `ESC f`, not an escape followed by `ƒ`.
- **Turn it off** under **Settings → Terminal → Input → Use Alt as Meta** to let Alt or Option
  compose characters instead, which is what macOS Option normally does.
- **AltGr is left alone.** On Windows and Linux AltGr arrives as Ctrl+Alt, and layouts use it to
  type `@`, `{` and `€`. A character AltGr composes reaches the shell as that character.
- **`Ctrl+Alt+letter` sends Meta plus the control byte**, readline's `C-M-f` and `C-M-b`, when the
  key is the plain letter and not an AltGr character. Like Meta, this needs Alt as Meta on.
- **Printable characters come from what the system composed**, so dead keys and input methods work.

## Keys the terminal handles itself

| Key | Does |
| --- | --- |
| `Shift+PageUp` / `Shift+PageDown` | Scroll back / forward a page |
| `Shift+Home` / `Shift+End` | Scroll to the top of the scrollback / to the bottom |
| `Shift+Insert` | Paste |
| `Ctrl+Insert` | Copy |
| Middle-click | Paste the selection, on Linux |
| `Shift+F10`, the Menu key | Open the right-click menu |
| `Tab`, `Shift+Tab`, `Esc` | Always go to the shell, never to the window |

- **The four scrolling keys go to the program on the alternate screen.** A full-screen program
  such as `less` has no scrollback behind it, and binds `Shift+PageUp` itself.
- **Middle-click pastes the PRIMARY selection on Linux**, and the clipboard there when no tool
  for the selection is installed. On macOS and Windows it pastes nothing. See
  [Selection and the mouse](/docs/selection-and-mouse#middle-click).
- **`Shift+F10` and the Menu key open the terminal's right-click menu**, under the cursor. A plain
  `F10` still reaches the program.
- **`Esc` does not leave full screen.** Only `F11` (`Ctrl+Cmd+F` on macOS) does, so `Esc` in vim
  leaves insert mode as it should.
- `Ctrl+3` through `Ctrl+8` send xterm's control bytes, and the keypad arrows work with NumLock off.

The mouse gestures, such as `Shift`+drag to select inside a program that has the mouse, are on
[Selection and the mouse](/docs/selection-and-mouse).

## Tabs from the keyboard

These are the defaults; each can be changed on Settings → Keyboard.

| Key | Does |
| --- | --- |
| `Alt+1` … `Alt+8` (`Cmd+1` … `Cmd+8`) | Go to that tab |
| `Alt+9` (`Cmd+9`) | Go to the last tab, however many are open |
| `Ctrl+Shift+]` / `Ctrl+Shift+[` (`Cmd+Shift+]` / `Cmd+Shift+[`) | Next / previous tab |
| `Ctrl+Shift+←` / `Ctrl+Shift+→` (`Cmd+Shift+←` / `Cmd+Shift+→`) | Move the tab left / right |
| `Ctrl+Shift+1` … `Ctrl+Shift+9` (`Cmd+Option+1` … `Cmd+Option+9`) | New tab with profile 1 to 9 |

A number past the last tab selects nothing, so that you do not land on the wrong tab. See
[Tabs and windows](/docs/tabs-and-windows) and [Shell profiles](/docs/shell-profiles).
