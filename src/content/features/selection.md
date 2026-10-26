---
title: "Selection and safe paste"
group: "The terminal"
order: 4
beta: false
summary: "Drag, double-click for a word, triple-click for a line, <kbd>Alt</kbd> (<kbd>Option</kbd>) to select a block, and past the edge to scroll. On Linux a selection is the PRIMARY selection. A paste with a line break shows you what it will run first."
---

- **Select** by dragging, a word by double-clicking, a line by triple-clicking. What is highlighted is exactly what copying gives, and a selection stays on its text as output arrives. Typing does not clear it; it goes when the text under it changes.
- **Past the edge.** Drag above or below the pane and the scrollback scrolls while the selection grows, faster the further out, even with the pointer held still.
- **Blocks.** `Alt`+drag (`Option`+drag on macOS, `Ctrl+Alt`+drag where the window manager takes `Alt`) selects the same columns on every row, copied a row per line.
- **Copy on select**, off by default, under Settings → Terminal → Input.
- **PRIMARY on Linux.** Every selection is also the PRIMARY selection, and middle-click pastes it, through `wl-paste`/`wl-copy` or `xclip`/`xsel`. Without them, and on macOS and Windows, middle-click pastes the clipboard.
- **Copy and paste** with `Ctrl+Shift+C` and `Ctrl+Shift+V` (`Cmd+C` and `Cmd+V` on macOS), so plain `Ctrl+C` is still SIGINT. `Ctrl+Insert` and `Shift+Insert` work as on any Linux terminal.
- **A paste with a line break asks first**, showing the text, since at a prompt each line runs the moment it lands. Paste is the default button, and Settings → Terminal → Input turns the question off. Pasted text is sanitized: escape and other control characters are stripped, so a clipboard cannot close a bracketed paste early.
- **`Shift` bypasses mouse reporting**, the xterm convention, which is how you copy text out of htop. `Shift`+right-click opens the menu inside such a program.

See [Selection and the mouse](/docs/selection-and-mouse).
