---
title: "Command palette"
group: "Keyboard"
order: 1
beta: false
summary: "Every command, open tab, shell profile, theme and setting, found by typing part of its name: <kbd>Ctrl+Shift+P</kbd>, or <kbd>Cmd+Shift+P</kbd> on a Mac. Each row shows its chord, so the palette teaches the keys as you use it."
---

The palette is Editora's, ported: the same card, the same matcher and ranking, and the same Emacs keys by default.

- **Type part of a name.** "nw" finds New Window, "tab close" finds Close Tab with the words in either order, and "file new" reaches New Tab through the menu it lives in. The matched letters are bold, and a line under the list says what the highlighted command does.
- **More than the menus.** Every open tab is listed by its title, with the chord that reaches it; every shell profile as "New Tab: zsh"; every theme as "Theme: Clear Light"; and every setting, as "Show a scrollbar: turn off" or "Cursor shape: Bar".
- **Greyed, not hidden.** A command that cannot run right now, such as Reopen Closed Tab with nothing closed, stays in the list with the reason as its tooltip, and the cursor steps over it.
- **Emacs keys.** `Ctrl+N` and `Ctrl+P` move, `Ctrl+V` and `Alt+V` page, `Ctrl+G` cancels, and the query field takes `Ctrl+A`, `Ctrl+E`, `Ctrl+K` and the rest. The arrows, `PageUp`, `PageDown`, `Enter` and `Esc` work too.
- **The movement keys can be changed.** Line Down, Line Up, Page Down and Page Up are commands under Settings → Keyboard. Their keys act only in a list, so in the terminal `Ctrl+N` and `Ctrl+P` still reach the shell.
- **Help for a command.** `Ctrl+H` opens the highlighted command's page of the user guide; `F1` or `Shift+F1` if a command has taken `Ctrl+H`.
- **Your chord.** The palette's chord is a shortcut like any other: Settings → Keyboard, under Help, takes another one. Click it and press the new chord.

The commands are the menu bar's own actions, not a separate list, so the palette and the menus cannot disagree. See [Keyboard](/docs/keyboard#the-command-palette).
