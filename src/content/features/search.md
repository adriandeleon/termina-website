---
title: "Scrollback search"
group: "The terminal"
order: 3.5
beta: false
summary: "<kbd>Ctrl+Shift+F</kbd> (<kbd>Cmd+F</kbd> on macOS) opens a find bar that searches the whole scrollback as you type, every match highlighted. <kbd>Enter</kbd> steps up to older output."
---

- **As you type.** Every match in the scrollback and on the screen is highlighted, the current one outlined, and the bar counts them.
- **Newest first.** The search starts at the bottom of what you are looking at. `Enter` steps up to older output and `Shift+Enter` down, wrapping at the ends.
- **Smart case.** `error` finds `Error`; `Error` finds only `Error`. `.*` switches to a regular expression.
- **Wrapped lines are one line.** A word that reached the right edge and carried on is still found.
- **From anywhere.** Find Next and Find Previous, `Ctrl+Shift+G` and `Ctrl+Shift+H` (`Cmd+G` and `Cmd+Shift+G`), work from anywhere and open the bar when it is closed. A short selection becomes the query.
- **One per pane.** In a split tab each pane has its own bar.

See [Search and prompts](/docs/search-and-prompts).
