---
title: "Selection and safe paste"
group: "The terminal"
order: 4
beta: false
summary: "Drag, double-click for a word, triple-click for a line, and <kbd>Shift</kbd> to select inside a program that owns the mouse. A paste with a line break shows you what it will run first."
---

- **Select** by dragging, a word by double-clicking, a line by triple-clicking. What is highlighted is exactly what copying gives, and a selection stays on its text as output arrives.
- **Copy and paste** with `Ctrl+Shift+C` and `Ctrl+Shift+V` (`Cmd+C` and `Cmd+V` on macOS), so plain `Ctrl+C` is still SIGINT. `Ctrl+Insert`, `Shift+Insert` and middle-click work as on any Linux terminal.
- **A paste with a line break asks first**, showing the text, since at a prompt each line runs the moment it lands. Paste is the default button, and Settings → Terminal → Input turns the question off.
- **Pasted text is sanitized.** Escape and other control characters are stripped, so a clipboard cannot close a bracketed paste early and run the rest as keystrokes.
- **`Shift` bypasses mouse reporting**, the xterm convention, which is how you copy text out of htop. `Shift`+right-click opens the menu inside such a program.

See [Selection and the mouse](/docs/selection-and-mouse).
