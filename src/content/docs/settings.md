---
title: Settings
description: The Settings window and its search, what applies live and what waits, every page in brief, the settings file and where it lives, hand-editing and clamping, Reset to Defaults, the debug log and the update check.
category: Customization
order: 10
---

## The Settings window

`Ctrl+,` (`Cmd+,` on macOS) opens it, as does **File → Settings…** or **Settings…** on the
right-click menu, which is the way in when the menu bar is hidden. There is one Settings window,
shared by every terminal window.

- **A sidebar of pages in two groups**: General (Appearance, Terminal, Profiles) and System
  (Advanced). The arrow keys move between pages and skip the group headings.
- **Search settings** at the top narrows both the rows and the pages to what matches. A match in a
  row's title beats one in its description, and that row's page opens. Rows also answer to other
  words: "transparency" finds Window opacity.
- **There is no OK button.** Every control applies the moment it changes, to every window, and the
  file is written at once. Choosing a font or a theme is a judgement about how it looks, and a
  dialog that holds the result back makes you guess.
- **It follows changes made elsewhere.** A theme picked from the palette, the menu bar hidden by
  its chord or a zoom step shows in the window while it is open.
- `Esc` closes it.

### What does not apply live

Two settings cannot, and each says so on its own row:

- **Scrollback lines** sizes a buffer that already exists, so it applies to new tabs.
- **Shell** is a process that is already running, so it applies to new tabs.

A few others apply at a later point, and say when: the **interface language** after a restart,
**Put the menu bar in the title bar** to windows opened afterwards, and the **profile for new
tabs** on the next tab.

## The pages

**Appearance** ([details](/docs/appearance)):

- **Theme**: the color theme.
- **Font**: family and size.
- **Tabs**: hide the tab bar with a single tab.
- **Window**: show the menu bar, put it in the title bar, show a scrollbar, window opacity.
- **Language**: the interface language.
- **Preview**: a sample drawn with the real palette and font.

**Terminal**:

- **Display**: cursor shape and visual bell.
- **Session**: **Ask before closing a running tab** (on by default; asks only when the shell has
  something running in it), **Scrollback lines** (5,000 by default, 0 to 200,000, held in memory),
  and **Shell** (empty uses `$SHELL`, started as a login shell). The shell field says "Not found"
  or "Not executable" as you type.
- **Links**: **Open files with**, a command for opening a clicked file path, such as
  `editora {file}:{line}`. Empty uses the desktop's own opener. See [Links](/docs/links).
- **Input**: **Use Alt as Meta** (on by default) and **Ask before pasting several lines** (on by
  default). See [Keyboard](/docs/keyboard#alt-option-and-altgr).
- **Keyboard**: the command palette's chord. See
  [Changing the palette's chord](/docs/keyboard#changing-the-palettes-chord).

**Profiles**: the profile new tabs open with, and the list of profiles: the ones found on the
machine, which you can hide, and your own, which you can add, edit and remove. See
[Shell profiles](/docs/shell-profiles).

**Advanced**:

- **Files**: where the settings file is. If a save fails, the reason shows here in red.
- **Updates**: **Check for updates at startup** (below).
- **Diagnostics**: **Show Debug Log…** (below).

## Reset to Defaults

The button at the bottom of the window asks first, then puts appearance, terminal, window and
update settings back to how they shipped. Your profiles, the default profile and the hidden
profiles are kept.

## The settings file

Every setting is in one plain properties file, `settings.properties`, in `~/.termina` on every
platform (`C:\Users\<you>\.termina` on Windows). To use another folder, start Termina with
`--config-dir DIR` or set the `TERMINA_CONFIG_DIR` environment variable; `--config-dir` wins
when both are given. The debug log's file is written to the same folder.

- **Hand-editing is fine while Termina is closed.** Termina reads the file at startup and writes
  the whole file on every change, so an edit made while it runs can be overwritten.
- **A bad line costs only that key.** A value that does not parse falls back to its default, and
  the rest of the file is read as usual.
- **Values are clamped on read as well as on write**, so a hand-edited two-million-line scrollback
  becomes 200,000 rather than exhausting memory at the next launch.
- **Writes are atomic.** Termina writes `settings.properties.tmp` beside the file and moves it into
  place, so a crash or a power cut mid-write leaves either the old file or the new one, never an
  empty one.

The keys most worth knowing:

| Key | Values | Default |
| --- | --- | --- |
| `theme` | `editora-dark`, `editora-light`, `clear-dark`, `clear-light` | `editora-dark` |
| `font.family` | A font family name; empty picks the best available | empty (JetBrains Mono) |
| `font.size` | 7 to 40 | `13` |
| `font.zoom` | 0.3 to 3.0, multiplies the size | `1.0` |
| `terminal.cursorShape` | `BLOCK`, `UNDERLINE`, `BAR` | `BLOCK` |
| `terminal.bell` | `true`, `false` | `true` |
| `terminal.scrollback` | 0 to 200000 | `5000` |
| `terminal.shell` | A shell's path; empty uses `$SHELL` | empty |
| `input.altIsMeta` | `true`, `false` | `true` |
| `input.confirmPaste` | `true`, `false` | `true` |
| `keys.commandPalette` | A chord such as `Alt+X` or `Shortcut+Shift+P` | `Alt+X` |
| `ui.hideTabBarWhenSingle` | `true`, `false` | `true` |
| `ui.showMenuBar` | `true`, `false` | `true` |
| `window.menuBarInTitleBar` | `true`, `false` | `false` |
| `ui.showScrollBar` | `true`, `false` | `true` |
| `ui.confirmClose` | `true`, `false` | `true` |
| `window.opacity` | 0.6 to 1.0 | `1.0` |
| `ui.language` | `en`, `it`, `es`, `fr`, `pt`, `de`; empty follows the system | empty |
| `links.openCommand` | A command with `{file}`, `{line}`, `{column}`; empty uses the desktop | empty |
| `updates.check` | `true`, `false` | `true` |

A `keys.commandPalette` line that does not parse, or names a chord another command has, falls back
to `Alt+X`, so the palette always has a way in. The profiles are the `profile.*` keys with
`profiles.default` and `profiles.hidden`; their format is on
[Shell profiles](/docs/shell-profiles). The `window.*` size and position keys and the
`updates.*` timestamps are bookkeeping that Termina maintains itself.

## The debug log

**Settings → Advanced → Diagnostics → Show Debug Log…** shows every warning and error logged in
this session, and anything that crashed, with Refresh, Copy and Clear. A packaged application has
no console, so without it the one line saying why something failed would go nowhere.

The same lines are written to `termina-session.log` in the settings folder, started afresh at each
launch. A second Termina running at the same time writes `termina-session-<pid>.log` instead, so
it does not wipe the first one's log. The log stays on your machine. See
[Troubleshooting](/docs/troubleshooting#the-debug-log).

## Updates

**Settings → Advanced → Updates → Check for updates at startup** (on by default): at most once a
day, Termina asks the GitHub releases API for the newest version. Nothing is sent beyond the
request itself. When a newer release exists, the Help menu says so (**Version X is available…**)
and opens the release page. **Help → Check for Updates…** asks at any time, and **Help → About**
says when the last check ran. Turn the switch off, or set `updates.check=false`, to stop the
startup check.
