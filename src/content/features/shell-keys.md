---
title: "Keys that reach the shell"
group: "Keyboard"
order: 3
beta: false
summary: "A Meta key that works, Option on macOS included, AltGr left alone for the layouts that need it, and the scrollback and paste keys a Linux hand reaches for."
---

- **Meta that works.** Alt sends `ESC` and the key, so readline's `M-f`, `M-b` and `M-d` move and delete by word. On macOS, Option is a compose key (Option+F types `ƒ`), and Termina sends the key itself, not an escape followed by `ƒ`. Settings → Terminal → Input → Use Alt as Meta turns it off when you want Option to compose.
- **AltGr left alone.** On Windows and Linux AltGr arrives as Ctrl+Alt, and layouts use it for `@`, `{` and `€`. What it composes reaches the shell as typed, and dead keys and input methods work.
- **`Ctrl+Alt+letter`** sends Meta over the control byte, readline's `C-M-f`, when it is the plain letter and not an AltGr character.
- **The Linux reflexes.** `Shift+Insert` pastes, `Ctrl+Insert` copies, and middle-click pastes the clipboard.
- **Scrollback from the keyboard.** `Shift+PageUp`, `Shift+PageDown`, `Shift+Home` and `Shift+End` move through the history, and go to the program instead on the alternate screen, where `less` uses them.
- **`Tab` and `Esc` always go to the shell.** `Esc` does not leave full screen, so in vim it leaves insert mode.

See [Keyboard](/docs/keyboard#alt-option-and-altgr).
