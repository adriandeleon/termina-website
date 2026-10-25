---
title: Appearance
description: The four themes and how their ANSI colors are chosen, fonts, size and zoom, the cursor and the visual bell, window opacity, the scrollbar, hiding the tab strip and the menu bar, the menu bar in the title bar, the interface language, and the settings sidebar.
category: Customization
order: 9
---

Everything here is under **Settings → Appearance** or **Settings → Terminal**, and applies the
moment it changes. The Appearance page has a **Preview** drawn with the real palette and the real
font, so you can see a theme or a font before you look back at the terminal.

## Themes

**Settings → Appearance → Theme → Color theme**, or type "theme" in the
[command palette](/docs/keyboard#the-command-palette). The theme applies to every window, the
terminal palette and Termina's own dialogs.

| Theme | From |
| --- | --- |
| **Editora Dark** (the default) | Editora's "Caret & Ink": Caret teal on Ink navy |
| **Editora Light** | Editora's light counterpart |
| **Clear Dark** | macOS Terminal's Clear Dark profile |
| **Clear Light** | macOS Terminal's Clear Light profile |

- **The Editora themes' ANSI colors come from Editora's editor themes.** Each color takes a role
  from the matching syntax palette: red is a keyword, green an escape, yellow a type, magenta a
  function, and cyan is the Caret teal. A shell's `ls` colors stay in the same family as the
  editor's code colors.
- **On Editora Light, ANSI white is drawn dark.** A program that sets color 7 on a white
  background would otherwise write invisible text. For the same reason its yellow is a dark
  amber. Termina's tests check the contrast of every color in both Editora themes.
- **The Clear themes were decoded from Apple's own `.terminal` profiles**, not matched by eye.
  Apple ships them translucent over a blurred desktop; Termina draws them opaque.
- **Clear Light is low-contrast in four places**, as Apple made it: white, bright yellow, bright
  cyan and bright white against its white background. This is kept on purpose, because a port that
  changes its source is no longer that theme. If a program writes in one of those colors, choose
  Editora Light.
- The Clear themes borrow the Editora control styling of the same brightness for the Settings
  window and dialogs.

## Font and size

**Settings → Appearance → Font**:

- **Font family.** The five bundled families come first: **JetBrains Mono** (the default),
  Cascadia Code, Fira Code, IBM Plex Mono and Source Code Pro. Every monospace face installed on
  the machine follows. Bundled fonts look the same on every platform. Proportional faces are not
  offered, because the renderer places every glyph on a fixed grid of cells.
- **Font size**, 7 to 40 (13 by default).

Zoom is separate from the size. **Zoom In** `Ctrl+=` (`Cmd+=`), **Zoom Out** `Ctrl+-` (`Cmd+-`)
and **Actual Size** `Ctrl+0` (`Cmd+0`) step through a browser's ladder from 30% to 300% of the
size you set, which stays as it is. On Linux and Windows the View menu shows zoom as one row,
`Zoom − 100% + ⤢`, which stays open while you press it; the percentage resets it, and the last
button is Full Screen. The right-click menu has the same row on every platform. On macOS the
View menu lists Zoom In, Zoom Out, Actual Size and Full Screen as ordinary items.

Wide characters (CJK, emoji) take two cells. Fira Code is bundled, but ligatures are not drawn.
Termina has no Nerd Font of its own in 0.10.0: for file-type icons from `eza` or a prompt theme,
install a Nerd Font (see [Troubleshooting](/docs/troubleshooting#icons-show-the-wrong-glyph)).

The interface font is Inter, bundled.

## Cursor and bell

**Settings → Terminal → Display**:

- **Cursor shape**: Block (the default), Underline or Bar. A program can still ask for its own
  shape while it runs (vim does); when it resets the terminal, your choice comes back. A block
  cursor shows the character under it.
- **Visual bell** (on by default) flashes the screen when a program rings the bell. There is no
  audible bell. A bell in a background tab also marks that tab, whether or not the flash is on.

## The window

**Settings → Appearance → Window**:

- **Show the menu bar** (on by default). Hiding it keeps every command on its chord, and the
  right-click menu has **Show Menu Bar** to bring it back. **View → Hide Menu Bar** is
  `Ctrl+Shift+M`. Not offered on macOS, where the menus are in the screen menu bar.
- **Put the menu bar in the title bar** (off by default). One row holds the menus at the left, the
  window title in the middle and the window buttons at the right, and the terminal gains the row
  the system title bar took. The platform keeps its own window buttons, and the empty part of the
  row and the title drag the window. It applies to windows opened after the change. It is not
  offered on macOS, nor on a platform that cannot draw its own title bar.
- **Show a scrollbar** (on by default). It takes a column of width so that it never covers text,
  uses the terminal palette's colors, and hides itself on the alternate screen, where a
  full-screen program owns the view. The wheel scrolls the scrollback either way.
- **Window opacity**, 60% to 100%. Plain transparency, with no blur, so the text fades along with
  the background. It applies to terminal windows only, and stops at 60% so that a window never
  becomes too faint to undo it.

**Settings → Appearance → Tabs → Hide the tab bar with a single tab** (on by default). The row is
given to the terminal, and the shell gains a line. The strip comes back with the second tab.

Window size and position are remembered, and pulled back onto a screen that still exists.

## Language

**Settings → Appearance → Language → Interface language**: Automatic (the default), English,
Italiano, Español, Français, Português or Deutsch. Automatic follows the operating system, and
uses English when the system's language is not one of the six. A string missing from a
translation shows in English. The change takes effect after a restart.

## The settings sidebar

The Settings window's sidebar has two groups, **General** (Appearance, Terminal, Profiles) and
**System** (Advanced). Since 0.10.0 each page has an icon: a palette for Appearance, a prompt
window for Terminal, stacked layers for Profiles and sliders for Advanced. The icons are muted
until their page is selected, then take the accent color. See [Settings](/docs/settings).
