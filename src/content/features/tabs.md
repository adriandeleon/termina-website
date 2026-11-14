---
title: "Tabs"
group: "Tabs & windows"
order: 1
beta: false
summary: "Tabs that tile the window, hide when there is only one, and can be dragged, renamed, pinned, duplicated and reopened. They come back at the next launch, and a mark says when something happened in a tab you are not looking at."
---

- **A strip that stays out of the way.** It hides while one tab is open, giving the row to the shell, and tiles the window's width once there are more.
- **Keys for all of it.** `Ctrl+Shift+T` opens and `Ctrl+Shift+W` closes (`Cmd+T` and `Cmd+W` on macOS); `Alt+1`…`Alt+9` (`Cmd+1`…`Cmd+9`) jump, with `9` always the last tab; `Ctrl+Shift+Left` and `Right` move one.
- **Rename, duplicate, reopen.** Double-click a tab to name it. Duplicate Tab opens the same shell where this one is now, beside it. Reopen Closed Tab (`Ctrl+Shift+Z`, `Cmd+Z` on macOS) brings back the last of twenty closed tabs, in its directory and its old place, and a split tab with every pane arranged as it was. A closed pane or a closed window comes back the same way.
- **Pinned tabs.** Pin Tab moves a tab to a group at the left and marks it with a pin. Close Other Tabs and the other bulk closes leave it open.
- **The last session comes back.** The tabs and panes open when Termina ends reopen at the next launch, each as a new shell in the folder it was in, with their names and pins. Settings → Terminal → Reopen tabs at launch turns it off.
- **Activity, bell and notification marks.** A background tab shows a dot when output arrives after a quiet spell, such as a build finishing, a ring for a bell, and a diamond for a program's notification, each in its own color.
- **Closing asks only when it matters.** A tab whose shell is running something, a background job included, names it and asks before ending it. An idle prompt closes at once.
- **Panes inside.** A tab can be [split](/features/split-panes); it is titled after the pane you are typing in, and `Ctrl+Shift+W` (`Cmd+W`) then closes that pane rather than the tab.
- **A menu per tab.** Right-click a tab for New Tab With, Duplicate, Rename, Pin, Close Others, Close to the Left or Right, and Move.

See [Tabs and windows](/docs/tabs-and-windows).
