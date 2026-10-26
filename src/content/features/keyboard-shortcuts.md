---
title: "Keyboard shortcuts"
group: "Keyboard"
order: 2
beta: false
summary: "Application chords that leave the shell its keys: <kbd>Ctrl+Shift+T</kbd> on Linux and Windows, <kbd>Cmd+T</kbd> on macOS. Every one can be changed in Settings → Keyboard, and Help → Keyboard Shortcuts lists them, searchable."
---

- **`Ctrl+Shift`, never `Ctrl`, off macOS.** `Ctrl+T` is readline's transpose, `Ctrl+W` deletes a word and `Ctrl+C` is SIGINT, so Termina's chords add Shift: `Ctrl+Shift+T` for New Tab, `Ctrl+Shift+C` and `Ctrl+Shift+V` to copy and paste. On macOS they are `Cmd` chords, which a shell never sees.
- **Every one can be changed.** Settings → Keyboard lists each command under its menu: click its shortcut and press the new one. A shortcut another command has moves on a second press, `Backspace` removes one, and Reset or Reset All Shortcuts puts them back. Commands with no shortcut of their own, such as Rename Tab, can be given one.
- **A bare key is refused.** A function key works alone; any other key needs `Ctrl`, `Alt` or `Cmd`, or the shell would lose it. `Ctrl` with a letter is allowed off macOS, with a note that the shell no longer receives it.
- **A searchable reference.** Help → Keyboard Shortcuts (`Ctrl+Shift+/`, `Cmd+/` on macOS) lists every chord the window answers to, grouped by menu, with a search box.
- **Generated, not typed.** The reference is built from the window's own binding table, with the chords in force, so it cannot list a chord that does nothing. The menus, the palette and the tab strip's tooltips follow a change at once too. The reference also lists the keys the terminal handles itself: `Shift+PageUp` and friends, `Shift+Insert`, `Ctrl+Insert`, middle-click, the link modifier, `Shift`+drag, and double-clicking a tab to rename it.
- **One chord per command.** The defaults are declared once, with their menu items, and tested for duplicates on every platform, and the Keyboard page moves a chord rather than sharing it, so a menu can never advertise a key that a different command takes.
- **They work without the menu bar.** The chords are handled by the window ahead of the terminal, so hiding the menu bar strands nothing.
- **On every computer.** The shortcuts you change can sync between computers through a Git repository of yours: see [Settings sync](/features/settings-sync).

The [keybindings page](/keybindings) has the defaults. See [Keyboard](/docs/keyboard).
