---
title: "Keyboard shortcuts"
group: "Keyboard"
order: 2
beta: false
summary: "Application chords that leave the shell its keys: <kbd>Ctrl+Shift+T</kbd> on Linux and Windows, <kbd>Cmd+T</kbd> on macOS. Help → Keyboard Shortcuts lists every one of them, searchable."
---

- **`Ctrl+Shift`, never `Ctrl`, off macOS.** `Ctrl+T` is readline's transpose, `Ctrl+W` deletes a word and `Ctrl+C` is SIGINT, so Termina's chords add Shift: `Ctrl+Shift+T` for New Tab, `Ctrl+Shift+C` and `Ctrl+Shift+V` to copy and paste. On macOS they are `Cmd` chords, which a shell never sees.
- **A searchable reference.** Help → Keyboard Shortcuts (`Ctrl+Shift+/`, `Cmd+/` on macOS) lists every chord the window answers to, grouped by menu, with a search box.
- **Generated, not typed.** The reference is built from the window's own binding table, so it cannot list a chord that does nothing. It also lists the keys the terminal handles itself: `Shift+PageUp` and friends, `Shift+Insert`, `Ctrl+Insert`, middle-click, the link modifier, `Shift`+drag, and double-clicking a tab to rename it.
- **One chord per command.** A chord is declared once, with its menu item, and the chord table is tested for duplicates on every platform, so a menu can never advertise a key that a different command takes.
- **They work without the menu bar.** The chords are handled by the window ahead of the terminal, so hiding the menu bar strands nothing.

The [keybindings page](/keybindings) has the full table. See [Keyboard](/docs/keyboard).
