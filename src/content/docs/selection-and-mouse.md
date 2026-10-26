---
title: Selection and the mouse
description: Selecting, scrolling while you drag, block selection with Alt (Option), copy on select, the Linux PRIMARY selection and middle-click, copying and pasting, the prompt before a multi-line paste, mouse reporting to programs and the Shift bypass, who owns right-click, the wheel and keyboard scrollback, and the right-click menu.
category: Using Termina
order: 5
---

The mouse does two jobs in a terminal: selecting text for you, and clicking for the program
running inside it. Termina gives it to the program when the program asks, and `Shift` always takes
it back.

## Selecting

- **Drag** to select. The cell under the pointer is included once the pointer passes its middle,
  and the highlight is exactly what copying will give you.
- **Double-click** selects a word, **triple-click** a line.
- **Select All** (`Ctrl+Shift+A`, `Cmd+A` on macOS) selects the scrollback and the screen.
- **A plain click** clears the selection.

A selection stays on its text as output arrives, even once the scrollback is full, so what you
selected a moment ago is still what gets copied. A line the terminal wrapped is copied as one line,
without a line break in the middle.

### Past the edge

Drag above the top or below the bottom of the pane and the scrollback scrolls, with the selection
still growing. The further out the pointer is, the faster it goes, and it keeps going while the
pointer is held still. The point you started the drag from moves with its line as output scrolls,
so a drag held while a build prints still extends from the text you pressed on.

### Blocks

`Alt`+drag (`Option`+drag on macOS) selects a block: the same columns on every row, rather than a
run of text from one point to another. Where the window manager takes `Alt`+drag to move windows,
as GNOME and KDE do, use `Ctrl+Alt`+drag. A block is copied one row per line, whether or not a row
wrapped, without the blanks at the ends of the rows. It is measured in columns, so it stays
straight over wide characters and Nerd Font icons.

### Typing keeps it

A keystroke does not clear the selection. It goes when the text under it changes, such as a line
editor redrawing the prompt, since copying a highlight over redrawn text would give you something
you never selected.

### Copy on select

**Settings → Terminal → Input → Copy on select** (off by default) puts a selection on the clipboard
as soon as it is made, with no Copy needed.

### The PRIMARY selection on Linux

On Linux every selection is also the **PRIMARY** selection, whether or not copy on select is on,
and a middle-click pastes PRIMARY, as on any X11 terminal. JavaFX cannot reach PRIMARY itself, so
Termina goes through the tools for it: `wl-paste` and `wl-copy` under Wayland, `xclip` or `xsel`
under X11. Without one of them installed, and on macOS and Windows, which have no PRIMARY, a
middle-click pastes the clipboard and selecting sets nothing.

## Copy and paste

| Key | Does |
| --- | --- |
| `Ctrl+Shift+C` (`Cmd+C`) | Copy the selection |
| `Ctrl+Shift+V` (`Cmd+V`) | Paste the clipboard |
| `Ctrl+Insert` | Copy |
| `Shift+Insert` | Paste |
| Middle-click | Paste PRIMARY on Linux, the clipboard elsewhere |
| `Ctrl+Shift+K` (`Cmd+K`) | Clear Scrollback, keeping the screen |

Plain `Ctrl+C` is still SIGINT and plain `Ctrl+V` is still readline's literal-next; both belong to
the shell.

## Pasting is checked first

- **A paste with a line break asks first.** At a prompt every line of a paste runs the moment it
  lands, which is how a copied snippet with a stray newline becomes a command you did not mean to
  run. The prompt shows the text that will be sent, with **Paste** as the default button.
  **Settings → Terminal → Input → Ask before pasting several lines** turns it off. A middle-click
  paste is checked the same way.
- **Control characters are stripped.** Escape and the other control characters are removed before
  the paste is sent, keeping tabs and line breaks. A clipboard cannot end a bracketed paste early
  and have the rest run as typing.
- **Bracketed paste** is used whenever the shell has turned it on.

## Mouse reporting

When a program turns on mouse reporting, clicks, drags and the wheel go to it before anything local
happens: vim moves its cursor, htop responds to clicks, and a program that asks for every pointer
movement gets it. JediTerm encodes the events in whichever of the four xterm wire formats the
program chose.

**Hold `Shift` to bypass it.** `Shift`+drag selects text inside a program that has the mouse, which
is the only way to copy anything out of htop. That is the xterm convention.

## Right-click

Right-click is wanted by both sides: you want it for Copy and Paste, and a mouse-aware program such
as Midnight Commander wants it as a button. The rule:

- With no program holding the mouse, right-click opens the menu.
- While a program holds the mouse, right-click goes to the program.
- **`Shift`+right-click** always opens the menu.
- The keyboard's menu key always opens the menu.

`Escape`, a click anywhere in the terminal, or a click outside the window closes it.

## The wheel and scrollback

- **The wheel** scrolls the scrollback by distance, so a trackpad's many small events do not fling
  it past. Typing returns to the live screen.
- **On the alternate screen** a full-screen program owns the view and there is no scrollback
  behind it. The wheel turns into arrow keys there, so `less` and `man` scroll with it even though
  they never turn on mouse reporting.
- **From the keyboard**: `Shift+PageUp` and `Shift+PageDown` move a page, `Shift+Home` and
  `Shift+End` go to the top and the bottom. On the alternate screen these go to the program instead.

**Settings → Appearance → Window → Show a scrollbar** (on by default) adds a bar that shows and
drags the scrollback. It takes a column of its own rather than covering the last one, and hides on
the alternate screen. **Settings → Terminal → Session → Scrollback lines** sets how much history is
kept, from the next tab on.

## Middle-click

On Linux, middle-click pastes the PRIMARY selection where `wl-paste`, `xclip` or `xsel` is
installed (see [above](#the-primary-selection-on-linux)), and the clipboard otherwise. On macOS and
Windows it pastes the clipboard. Like any click, it goes to the program when the program has the
mouse, unless `Shift` is held.

## The right-click menu

- **Open Link** and **Copy Link Address**, when you right-click a URL or a file path. See
  [Links](/docs/links).
- **New Tab**, **New Window**
- **Copy**, **Paste** (disabled when there is nothing to copy or paste), **Select All**
- **Clear Scrollback**
- **Zoom**: the row of `−`, the percentage, `+` and full screen. See [Zoom](/features/zoom).
- **Show Menu Bar**, a checkbox, on Linux and Windows. It is how the menu bar comes back once
  hidden.
- **Settings…**
