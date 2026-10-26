---
title: "Tabs"
group: "Tabs & windows"
order: 1
beta: false
summary: "Tabs that tile the window, hide when there is only one, and can be dragged, renamed, duplicated and reopened. A dot says when something happened in a tab you are not looking at."
---

- **A strip that stays out of the way.** It hides while one tab is open, giving the row to the shell, and tiles the window's width once there are more.
- **Keys for all of it.** `Ctrl+Shift+T` opens and `Ctrl+Shift+W` closes (`Cmd+T` and `Cmd+W` on macOS); `Alt+1`…`Alt+9` (`Cmd+1`…`Cmd+9`) jump, with `9` always the last tab; `Ctrl+Shift+Left` and `Right` move one.
- **Rename, duplicate, reopen.** Double-click a tab to name it. Duplicate Tab opens the same shell where this one is now, beside it. Reopen Closed Tab (`Ctrl+Shift+Z`, `Cmd+Z` on macOS) brings back the last of ten closed tabs, in its directory and its old place, and a split tab with every pane arranged as it was.
- **Activity, bell and notification marks.** A background tab shows a dot when output arrives after a quiet spell, such as a build finishing, another color for a bell, and a third for a program's notification.
- **Closing asks only when it matters.** A tab whose shell is running something, a background job included, names it and asks before ending it. An idle prompt closes at once.
- **Panes inside.** A tab can be [split](/features/split-panes); it is titled after the pane you are typing in, and `Ctrl+Shift+W` (`Cmd+W`) then closes that pane rather than the tab.
- **A menu per tab.** Right-click a tab for New Tab With, Duplicate, Rename, Close Others, Close to the Right and Move.

See [Tabs and windows](/docs/tabs-and-windows).
