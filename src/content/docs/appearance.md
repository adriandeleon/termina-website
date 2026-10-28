---
title: Appearance
description: The four built-in themes and how their ANSI colors are chosen, importing a theme from iTerm2, Terminal, Windows Terminal, Ghostty, kitty or Alacritty, fonts, size and zoom, Nerd Font icons and the bundled symbols font, a profile's own font and theme, the cursor, padding and the visual bell, window opacity, the scrollbar, hiding the tab strip and the menu bar, the menu bar in the title bar, the interface language, and the settings sidebar.
category: Customization
order: 9
---

Everything here is under **Settings → Appearance** or **Settings → Terminal**, and applies the
moment it changes. The Appearance page has a **Preview** drawn with the real palette and the real
font, so you can see a theme or a font before you look back at the terminal.

## Themes

**Settings → Appearance → Theme → Color theme**, or type "theme" in the
[command palette](/docs/keyboard#the-command-palette). The theme applies to every window, the
terminal palette and Termina's own dialogs. A [profile](#a-profiles-own-font-and-theme) can have a
theme of its own, which then wins in its panes. Four themes are built in, and any number can be
[imported](#importing-a-theme).

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

## Importing a theme

**Settings → Appearance → Theme → Import a theme → Import…** reads a color scheme another terminal
wrote:

| From | File |
| --- | --- |
| iTerm2 | `.itermcolors` |
| macOS Terminal | a `.terminal` profile |
| Windows Terminal | a scheme, or a whole `settings.json`, which imports every scheme in it |
| Ghostty | a theme file, usually with no extension |
| kitty | a theme or `.conf` |
| Alacritty | a TOML theme |

These are the formats the iTerm2-Color-Schemes collection publishes every scheme in. A file is recognized by its contents where its extension does not say,
and only the color keys are read, so a whole configuration file can be imported and its fonts and
key bindings are ignored.

- **Colors are taken exactly as the file has them**, the cursor and selection colors included
  where the file names them. Nothing is adjusted for contrast. The Settings window and the dialogs
  take the Editora styling that matches the theme's background.
- **The new theme is selected**, and listed after the built-in four, in Settings and in the
  palette. The row says what was imported, or why a file was not.
- **Importing a file again replaces** the theme of that name rather than adding a second copy.
- **Remove** deletes the imported theme chosen above it, and the color theme goes back to Editora
  Dark. The built-in four cannot be removed.
- **Each is a plain file** in `~/.termina/themes`, named after the theme in lower case with
  hyphens between the words (`solarized-dark.properties`), that you can read and edit.

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

### Icons

File-type icons from `eza --icons`, `lsd` or a prompt theme are Nerd Font characters, and they draw
with no Nerd Font installed: **Symbols Nerd Font Mono** 3.5.1 is bundled with the other faces.

- **Which face draws an icon.** When the terminal font is itself a Nerd Font, its own icons are
  used. Otherwise the Nerd Font build of the terminal's face if one is installed (JetBrainsMono
  Nerd Font beside JetBrains Mono), and failing that the bundled one.
- **The bundled face's icons are a cell and a half wide**, the size the Nerd Font builds draw
  theirs, so they spill into the space after them, as a listing is laid out to expect, and they
  are centered on the row.
- **Powerline separators span the line**, so a prompt's segments meet.
- **It is not in the font picker.** It has icons and no letters.

See also [Troubleshooting](/docs/troubleshooting#icons-show-the-wrong-glyph).

The interface font is Inter, bundled.

## A profile's own font and theme

**Settings → Profiles** gives every profile, the Default Shell and the discovered ones included, a
**Font**, a **Font size** and a **Theme** of its own. Each is **Default** until set, which follows
the global setting.

- **They follow the pane.** A split, a duplicated tab, a reopened tab and a pane moved to another
  tab keep the look of the profile they were opened as.
- **The size goes through zoom.** Zooming applies to every pane alike, whatever size its profile
  sets.
- **With several themes in one window**, the window's own colors, behind the panes, the window
  buttons and the tab marks, follow the pane being typed in.

See [Shell profiles](/docs/shell-profiles#a-profiles-own-font-and-theme).

## Cursor, padding and bell

**Settings → Terminal → Display**:

- **Cursor shape**: Block (the default), Underline or Bar. A program can still ask for its own
  shape while it runs (vim does); when it resets the terminal, your choice comes back. A block
  cursor shows the character under it.
- **Only the pane that takes your keys has a solid cursor.** In the other panes, and in a window
  that is not focused, a block cursor is drawn as an outline and a bar or underline is dimmed, so
  you can see where your typing will go before you type.
- **Padding**, 4 pixels by default and up to 32: the space between the text and the edges of the
  pane. It applies at once, to every pane.
- **Visual bell** (on by default) flashes the screen when a program rings the bell. There is no
  audible bell. A bell in a background tab also marks that tab, whether or not the flash is on.
- **Desktop notifications** (on by default) let a program's notification reach the desktop when
  you are not looking at its pane. Its tab is marked either way. See
  [What programs can ask for](/docs/programs#notifications-osc-9-osc-777).

## The window

**Settings → Appearance → Window**:

- **Show the menu bar** (on by default). Hiding it keeps every command on its chord, and the
  right-click menu has **Show Menu Bar** to bring it back. **View → Hide Menu Bar** is
  `Ctrl+Shift+M`. Not offered on macOS, where the menus are in the screen menu bar.
- **Put the menu bar in the title bar** (off by default). One row holds the menus at the left, the
  window title in the middle and the window buttons at the right, and the terminal gains the row
  the system title bar took. The platform keeps its own window buttons, and the empty part of the
  row and the title drag the window. It applies to windows opened after the change. It is not
  offered on macOS, nor on a platform that cannot draw its own title bar. In full screen the title
  and the window buttons go away, and the menus, if shown, stay as an ordinary row.
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

The Settings window's sidebar has two groups, **General** (Appearance, Terminal, Profiles,
Keyboard) and **System** (Sync, Advanced). Each page has an icon: a palette for Appearance, a
prompt window for Terminal, stacked layers for Profiles, a keyboard for Keyboard, two arrows
chasing each other for Sync and sliders for Advanced. The icons are muted until their page is
selected, then take the accent color. See [Settings](/docs/settings).
