---
title: Split panes
description: Splitting a tab into panes, moving between them, the active pane and what acts on it, closing a pane, maximizing one, moving a pane to a new tab or another tab by command or by dragging, and reopening a split tab as it was.
category: Using Termina
order: 3.5
---

A tab can hold more than one shell. Each pane runs its own shell on its own pseudo-terminal, as a
tab does; splitting never shares a session.

## Splitting

| Key | Does |
| --- | --- |
| `Ctrl+Shift+E` (`Cmd+D`) | Split Right: a new pane beside this one |
| `Ctrl+Shift+O` (`Cmd+Shift+D`) | Split Down: a new pane below this one |

Both are in the Window menu. A new pane starts like [Duplicate Tab](/docs/tabs-and-windows#rename-reopen-and-duplicate):
the same shell or profile, in the directory the pane you split is in now. It takes the keyboard at
once.

- **Splitting the way the panes already run adds a pane to the row** rather than nesting a split
  inside a split, so three panes side by side share the width evenly.
- **Drag a divider** to give one pane more room.
- **A profile's own font and theme** go with its panes. With several themes in one window, the
  window's own colors follow the pane you are typing in.

## Moving between panes

| Key | Does |
| --- | --- |
| `Ctrl+Alt+Shift+Left` / `Right` / `Up` / `Down` (`Cmd+Option+Left` / `Right` / `Up` / `Down`) | Move the keyboard to the pane on that side |

A click in a pane does the same. Off macOS the chord has `Shift` in it because `Ctrl+Alt+arrows`
switch workspaces in GNOME, and `Ctrl+Shift+arrows` already move tabs and jump between prompts.

The four chords have no menu items. They are in the command palette as **Focus Pane Left**,
**Focus Pane Right**, **Focus Pane Above** and **Focus Pane Below**, and on the Keyboard page of
Settings, where they can be changed like any other.

## The active pane

The pane being typed in is outlined in the theme's blue while the tab has more than one. It is the
one the rest of the window means:

- **The tab is titled after it**, and so is the window, and the title changes as you move between
  panes. A name given with Rename Tab… belongs to the pane that was active when you gave it.
- **Copy, Find, the prompt jumps and Duplicate Tab** act on it, and a new tab starts in its
  directory.
- **Selecting the tab** gives the keyboard back to it.

## Closing a pane

Close Tab's chord, `Ctrl+Shift+W` (`Cmd+W`), closes the **active pane** of a split tab, and the
tab with its last pane. While it does, the File menu and the command palette call it **Close
Pane**. It asks first when something is running in that pane, as closing a tab does.

The tab's own close button and its right-click menu still close the whole tab, every pane in it,
asking once if anything is running in any of them.

A shell that exits closes its own pane. When the active pane goes, the keyboard goes to the pane
that grew into its space.

## Maximizing a pane

**Window → Maximize Pane**, `Ctrl+Shift+Enter` (`Cmd+Shift+Enter`), shows the active pane on its
own, filling the tab, with "Pane 2 of 3" in its corner. The same chord puts it back. The other
panes keep running while they are out of sight.

Splitting, moving to another pane or closing one puts the panes back first.

## Moving a pane

A pane can change tabs without its shell noticing: it keeps running throughout, and only its place
changes.

- **Window → Move Pane to New Tab** gives the active pane a tab of its own, right after the one it
  was in.
- **Move Pane to Tab** in the command palette, one row per other tab, by number and title, moves
  it into that tab beside its active pane. The rows are listed while the selected tab is split.
- **Drag it.** Hold `Ctrl+Alt+Shift` (`Cmd+Option+Shift`) and drag from inside the pane. Drop it
  on a tab to join that tab, or on the empty part of the tab strip to make it a tab of its own.

A pane has no title bar to grab, and a plain drag is a selection, so the drag needs three
modifiers: every smaller set already means something. `Alt` (`Option`) alone selects a block,
`Shift` alone bypasses a program's mouse reporting, and `Ctrl` (`Cmd`) alone opens a link.

## Reopening a split tab

[Reopen Closed Tab](/docs/tabs-and-windows#rename-reopen-and-duplicate), `Ctrl+Shift+Z` (`Cmd+Z`),
brings a split tab back as it was: every pane, each in its last directory and with its name,
arranged the same, with the same one active. The shells are new ones, so the scrollback is gone.

A pane closed on its own is not kept, only a closed tab is.
