---
title: Links
description: Opening URLs and file paths from terminal output, the modifier key, what counts as a link, file:line:column positions, which URL schemes open, Open Link and Copy Link Address, opening files in your editor with {file} and {line}, and the Windows limitation.
category: Using Termina
order: 6
---

`Ctrl`+click (`Cmd`+click on macOS) opens what is under the pointer: a URL in your browser, or a
file path in whatever opens that file.

## The gesture

- **Hold the modifier** and a link under the pointer underlines itself and the pointer becomes a
  hand, so you can see what a click would open before you click.
- **Click** with the modifier held to open it.
- **A plain click is never a link.** It starts a selection, or belongs to the program when the
  program has the mouse. The modifier click is checked first, and only takes the click when there
  is a link under the pointer, so the program keeps the modifier the rest of the time.

On macOS the modifier is `Cmd`, not `Ctrl`, because `Ctrl`+click there is how a one-button mouse
opens a context menu.

## What counts as a link

**URLs** with one of six schemes: `http`, `https`, `ftp`, `ftps`, `file` and `mailto`. Terminal
output is whatever a program printed, and a registered handler is a program on your machine, so
`javascript:`, `data:` and custom `app://` schemes are never opened. A URL that wraps at the right
edge is one link, not two.

**File paths**, and only when they name something that **exists**. Any word under the pointer is a
candidate — a bare filename out of `ls`, a directory, `src/main/Foo.java`, `~/notes.txt` — and it
becomes a link only if it resolves to a file or directory on disk. That check is what keeps a
screen of prose from underlining itself. It runs once per hover, for the word under the pointer.

- **Relative paths resolve against the tab's own directory**, the one its shell is in, not
  Termina's. A path in a build's output means that file in the directory the build ran in.
- **`~` and `~/…`** mean your home directory.
- **`file:line` and `file:line:column`** are recognized at the end of a path, as in a compiler
  error or a stack trace: `Foo.java:42` or `main.rs:12:5`. The position is passed to your editor
  when one is set (below).

Sentence punctuation after a link is not part of it, and a closing bracket is kept only when the
link opened it, so `(see https://example.com)` and Wikipedia's `…/Foo_(bar)` both come out right.

## Open Link and Copy Link Address

Right-click a link for **Open Link** and **Copy Link Address**, with no modifier needed. Copy Link
Address copies the URL, or for a path the full path of the file it resolved to.

## Opening files in your editor

By default a file opens in whatever your desktop opens it with: `open` on macOS, `xdg-open` on
Linux, the shell's file handler on Windows. None of those can take a line number, so the file opens
at the top.

**Settings → Terminal → Links → Open files with** takes a command instead. Three placeholders are
filled in:

| Placeholder | Becomes |
| --- | --- |
| `{file}` | The full path of the file |
| `{line}` | The line number, or `1` when there is none |
| `{column}` | The column, or `1` when there is none |

For example `code --goto {file}:{line}:{column}`, or `subl {file}:{line}`.

- **The command is split before the placeholders are filled**, so a file whose path has a space in
  it stays one argument. Quotes are for the parts you write yourself, such as an editor whose path
  has a space.
- **A bare command name is looked up on your login shell's `PATH`**, not on the stripped one an
  application launched from the desktop inherits, so a command that works in your terminal works
  from a click.
- **The command has to be a program.** An application's display name is not one: on macOS give
  the path to the executable inside the bundle. When the command cannot be found or will not start,
  Termina says so.

URLs always go to the desktop's own handler, the browser or the mail client, whatever this is set
to.

## Windows

Windows cannot report a shell's working directory yet, so relative paths are never links there.
Absolute paths and URLs still work. A file opens through the shell's file handler rather than
`cmd /c start`, so `&`, `^` and `%` in a name are not interpreted. The Windows side is built by CI
but has not yet been run by a person.

A link a program declares with OSC 8, such as `ls --hyperlink`, is not honored yet; it is
clickable only when its visible text is itself the URL or path.
