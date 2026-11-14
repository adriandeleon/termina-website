---
title: Settings
description: The Settings window and its search, what applies live and what waits, every page in brief (Appearance, Terminal, Profiles, Keyboard, Sync, Advanced), settings in the command palette, exporting and importing a configuration, the two settings files and where they live, hand-editing and clamping, Reset to Defaults, the debug log and the update check.
category: Customization
order: 10
---

## The Settings window

![The Settings window on its Appearance page: a search field, the pages on the left, and the theme, font and window rows](/screenshots/docs/settings.png "Settings. Every change applies as you make it; there is no OK button.")

`Ctrl+,` (`Cmd+,` on macOS) opens it, as does **File → Settings…** or **Settings…** on the
right-click menu, which is the way in when the menu bar is hidden. On macOS it is also in the
application menu, under About. There is one Settings window, shared by every terminal window.

- **A sidebar of pages in two groups**: General (Appearance, Terminal, Profiles, Keyboard) and
  System (Sync, Advanced). The arrow keys move between pages and skip the group headings.
- **Search settings** at the top narrows both the rows and the pages to what matches. Every word
  you type has to be found. A match in a row's title beats one in its description, then one in a
  section heading, and that row's page opens. Rows also answer to other words ("transparency"
  finds Window opacity) and to what their controls show: a theme's name finds the Theme row, and
  "import" finds the rows with an Import button.
- **There is no OK button.** Every control applies the moment it changes, to every window, and the
  file is written a moment later. Choosing a font or a theme is a judgement about how it looks, and
  a dialog that holds the result back makes you guess.
- **The on and off rows are switches.** `Tab` reaches each one, and `Space` or `Enter` flips it.
- **Each page ends with a link to its help**: Help for Appearance, Help for Profiles and so on,
  which opens that page of this guide.
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

- **Theme**: the color theme, and **Import…** for a scheme another terminal wrote (iTerm2,
  Terminal, Windows Terminal, Ghostty, kitty, Alacritty). **Remove** deletes an imported one.
- **Font**: family and size.
- **Tabs**: hide the tab bar with a single tab, and show the tab bar at all.
- **Window**: show the menu bar, put it in the title bar, interface zoom, show a scrollbar, show
  the status bar, window opacity.
- **Language**: the interface language.
- **Preview**: a sample drawn with the real palette and font.

**Terminal**:

- **Display**: cursor shape, **Padding** (4 pixels by default, up to 32), visual bell, and **Desktop notifications** (on by default), which
  lets a program's notification (OSC 9, OSC 777) reach the desktop. Its tab is marked either way.
- **Session**: **Ask before closing a running tab** (on by default; asks only when the shell has
  something running in it), **Reopen tabs at launch** (on by default; see
  [Tabs and windows](/docs/tabs-and-windows#the-last-session-comes-back)), **Scrollback lines**
  (5,000 by default, 0 to 200,000, held in memory), and **Shell** (empty uses `$SHELL`, started as
  a login shell). The shell field says "Not found" or "Not executable" as you type, and a path that
  fails is not saved: the setting keeps the last one that passed.
- **Links**: **Open files with**, a command for opening a clicked file path, such as
  `editora {file}:{line}`. Empty uses the desktop's own opener. See [Links](/docs/links).
- **Input**: **Use Alt as Meta** (on by default; see
  [Keyboard](/docs/keyboard#alt-option-and-altgr)), **Copy on select** (off by default), **Let
  programs copy to the clipboard** (OSC 52, on by default), and **Predict typing over slow SSH**
  (on by default). The question before a paste of several lines has no row here; see
  [Selection and the mouse](/docs/selection-and-mouse#pasting-is-checked-first).

**Profiles**: the profile new tabs open with, and the list of profiles: the ones found on the
machine, which you can hide, and your own, which you can add, edit and remove. Any profile, the
system shell included, can have its own font, size and theme; Default follows the global one. See
[Shell profiles](/docs/shell-profiles).

**Keyboard**: every command under the menu it lives in, with its shortcut. Click one and press the
new chord; Reset and Reset All Shortcuts put them back. The command palette's chord is the Command
Palette… row here, under Help. See [Changing a shortcut](/docs/keyboard#changing-a-shortcut).

**Sync** ([details](/docs/sync)):

- **Repository**: the URL of a private Git repository you own, the branch (`main` by default), and
  **Connect** / **Disconnect**. With the GitHub CLI signed in, **Create on GitHub…** makes the
  repository and connects to it. The URL and the branch are saved as you type them, on `Enter` or
  when you leave the field; one that cannot work is marked and not saved.
- **Syncing**: a status that reads Off, Synced, Syncing or Problem, how the last sync went, with
  **Sync Now**; **Sync automatically** (on by default); and the interval, 15 minutes by default.
- **What is synced**: the key bindings, and nothing else.

**Advanced**:

- **Files**: where the settings file is, with **Export** and **Import…** for the whole
  configuration (below). If a save fails, the reason shows here in red.
- **Updates**: **Check for updates at startup** (below).
- **Diagnostics**: **Show Debug Log…** (below).

## Settings in the command palette

Every setting is also in the [command palette](/docs/keyboard#the-command-palette). Type part of
its name:

- **A switch** says which way it will go: "Show a scrollbar: turn off".
- **A choice** lists its options, one row each: "Cursor shape: Bar".
- **A value** asks for a new one: "Scrollback lines…".

The buttons of the Settings window are commands too, in the palette and on the Keyboard page:
Reset All Settings…, Import Theme…, Remove Theme…, Reset All Shortcuts…, Add Profile, Remove
Profile… and the Sync page's. Each opens Settings on its page and does what the button does,
questions included. One that cannot run is greyed in the palette, with the reason.

## Export and Import Configuration

**Settings → Advanced → Files** has **Export** and **Import…**, and the palette has **Export
Configuration** and **Import Configuration…**.

- **Export** saves your settings, key bindings, shell profiles and imported themes as one `.zip`
  in your home folder, named `termina-config-<date>-<time>.zip`. Logs and the settings-sync clone
  are left out.
- **Import…** restores from such a file. It lists the parts the file holds (preferences, key
  bindings, shell profiles, imported themes) and replaces only the ones you leave ticked. Window
  positions and the settings-sync connection stay as they are.
- **Your current configuration is copied first**, to `backups/` in the settings folder.
- **A file that is not an export, or that a newer Termina wrote, is refused** before anything
  changes.

## Reset to Defaults

The button at the bottom of the window asks first, then puts appearance, terminal, window and
update settings back to how they shipped, and every shortcut back on its default. Your profiles,
their fonts and themes, the default profile and the hidden profiles are kept, and settings sync
stays connected; the shortcuts the reset puts back are synced like any other change.

Cancel is the default button on this question and on every other one that removes something:
`Enter` does not confirm it. The same goes for removing a theme or a profile, resetting the
shortcuts and importing a configuration.

## The settings files

Settings are two JSON files in `~/.termina` on every platform (`C:\Users\<you>\.termina` on
Windows):

| File | Holds |
| --- | --- |
| `settings.json` | Your preferences, key bindings and shell profiles |
| `workspace-state.json` | The window's place and size, the tabs of the last session, and the update check's memory |

To use another folder, start Termina with `--config-dir DIR` or set the `TERMINA_CONFIG_DIR`
environment variable; `--config-dir` wins when both are given. `--dev` uses `~/.termina-dev`. See
[Command line](/docs/command-line). The debug log's files are written
to the same folder, imported themes are in `themes/` there, and settings sync keeps its copy of
the repository and its backups in `sync/`.

Each file names its `schemaVersion` and keeps its values in one flat `settings` object, one key
per line, sorted:

```json
{
  "schemaVersion": 3,
  "settings": {
    "font.family": "JetBrains Mono",
    "font.size": 13,
    "terminal.scrollback": 10000,
    "ui.showMenuBar": true
  }
}
```

A setting you never changed has no line.

- **Hand-editing is fine while Termina is closed.** Termina reads the files at startup and writes
  them a moment after a change, so an edit made while it runs can be overwritten.
- **A bad value costs only that key.** A value that does not parse falls back to its default, and
  the rest of the file is read as usual.
- **A file that is not valid JSON is set aside**, copied beside itself with `.corrupt.bak` added
  to its name, and Termina opens on defaults. Nothing in it is lost.
- **A file a newer Termina wrote is not read.** It is kept as `settings.json.v<n>.bak`, where `n`
  is its schema version, and Termina opens on defaults.
- **It is JSON, so a backslash is doubled.** A Windows path is written
  `"C:\\Windows\\System32\\wsl.exe"`.
- **Two Termina processes share the files safely.** Each writes only the settings it changed,
  merged into what is on disk under a lock, so one never undoes the other's changes.
- **Values are clamped on read as well as on write**, so a hand-edited two-million-line scrollback
  becomes 200,000 rather than exhausting memory at the next launch.
- **Writes are atomic.** Termina writes a temporary file beside it and moves it into place, so a
  crash or a power cut mid-write leaves either the old file or the new one, never an empty one.

### Coming from a version before 0.15

Until 0.15 there was one file, `settings.properties`. The first time 0.15 starts it imports that
file into the two JSON files and keeps the old one beside them, with `.migrated` added to its
name. Key bindings are carried over to the commands' new ids.

### The keys

The keys most worth knowing, all in `settings.json`:

| Key | Values | Default |
| --- | --- | --- |
| `theme` | `editora-dark`, `editora-light`, `clear-dark`, `clear-light`, `high-contrast`, `system` to follow the system, or `user-<name>` for an imported theme | `editora-dark` |
| `font.family` | A font family name; empty picks the best available | empty (JetBrains Mono) |
| `font.size` | 7 to 40 | `13` |
| `font.zoom` | 0.3 to 3.0, multiplies the size | `1.0` |
| `terminal.cursorShape` | `BLOCK`, `UNDERLINE`, `BAR` | `BLOCK` |
| `terminal.padding` | 0 to 32 pixels | `4` |
| `terminal.bell` | `true`, `false` | `true` |
| `terminal.notifications` | `true`, `false` | `true` |
| `terminal.scrollback` | 0 to 200000 | `5000` |
| `terminal.shell` | A shell's path; empty uses `$SHELL` | empty |
| `input.altIsMeta` | `true`, `false` | `true` |
| `input.confirmPaste` | `true`, `false`; has no row in Settings | `true` |
| `input.copyOnSelect` | `true`, `false` | `false` |
| `terminal.clipboardWrite` | `true`, `false` | `true` |
| `terminal.typeAhead` | `true`, `false` | `true` |
| `keys.<command>` | A chord such as `Ctrl+Shift+Y` or `Shortcut+Shift+P`, or `none` | no line (the default) |
| `ui.hideTabBarWhenSingle` | `true`, `false` | `true` |
| `ui.showTabBar` | `true`, `false` | `true` |
| `ui.showMenuBar` | `true`, `false` | `true` |
| `window.menuBarInTitleBar` | `true`, `false` | `true` |
| `ui.showScrollBar` | `true`, `false` | `true` |
| `ui.showStatusBar` | `true`, `false` | `true` |
| `ui.zoom` | 80 to 200, the interface zoom in percent | `100` |
| `ui.confirmClose` | `true`, `false` | `true` |
| `session.restore` | `true`, `false` | `true` |
| `window.opacity` | 0.6 to 1.0 | `1.0` |
| `ui.language` | `en`, `it`, `es`, `fr`, `pt`, `de`; empty follows the system | empty |
| `links.openCommand` | A command with `{file}`, `{line}`, `{column}`; empty uses the desktop | empty |
| `updates.check` | `true`, `false` | `true` |
| `sync.repository` | A Git repository's URL, without a password or token | empty |
| `sync.branch` | A branch name | `main` |
| `sync.auto` | `true`, `false` | `true` |
| `sync.intervalMinutes` | 5 to 1440 | `15` |

The `keys.*` lines are described under
[In the settings file](/docs/keyboard#in-the-settings-file) on the Keyboard page; the palette's is
`keys.palette.show`. The `sync.*` keys are set by the Sync page, which
refuses a URL with a password or token in it. The profiles are the `profile.*` keys with
`profiles.default` and `profiles.hidden`, and a profile's own font, size and theme the
`profileStyle.<id>.*` keys; their format is on
[Shell profiles](/docs/shell-profiles). The window's size and position, the last session's tabs
and the update check's timestamps are in `workspace-state.json`, which Termina maintains itself.

## The debug log

**Help → Debug Log**, or **Settings → Advanced → Diagnostics → Show Debug Log…**, shows everything
Termina logged in this session, and anything that crashed. A packaged application has no console,
so without it the one line saying why something failed would go nowhere.

- **Find…**, or `Ctrl+F` (`Cmd+F` on macOS), opens a find bar across the top of the window. It
  searches as you type and counts the matches; `Enter` and `Shift+Enter` step through them and
  `Esc` closes it. A query in lower case matches either case; a capital makes it exact.
- **Refresh**, **Copy**, **Export…** and **Clear** are buttons, rows of the window's right-click
  menu, and commands in the palette. Copy takes the selected text when there is some. Export saves
  the log to a file.
- **Report a Problem…** builds a bug report from it. See
  [Troubleshooting](/docs/troubleshooting#reporting-a-bug).

The same lines are written to `termina-session.log` in the settings folder, started afresh at each
launch, and the log of the session before is kept beside it as `termina-session.prev.log`. A
second Termina running at the same time writes `termina-session-<pid>.log` instead, so it does not
wipe the first one's log. The file stays under 8 MB: past that it is cut back to its newest half.
The log stays on your machine. See [Troubleshooting](/docs/troubleshooting#the-debug-log).

The **Message Log** is the shorter list: what Termina reported to you this session, newest first,
with the time of each. **Help → Message Log**, or a click on the status bar, opens it as a card
over the terminal, with a filter field and Copy and Clear. `Esc` closes it.

## Updates

**Settings → Advanced → Updates → Check for updates at startup** (on by default): at most once a
day, Termina asks the GitHub releases API for the newest version. Nothing is sent beyond the
request itself. When a newer release exists, the Help menu says so (**Version X is available…**)
and opens the release page, and the status bar names the version in a link that opens it.
**Help → Check for Updates…** asks at any time, and **Help → About** says when the last check
ran. Turn the switch off, or set `"updates.check": false` in `settings.json`, to stop the startup
check.
