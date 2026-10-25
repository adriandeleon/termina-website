---
title: "The menu bar in the title bar"
group: "Tabs & windows"
order: 4
beta: false
summary: "One row for the menus, the window title and the window buttons, so the terminal gains a line. Or hide the menu bar with <kbd>Ctrl+Shift+M</kbd>. On macOS the menus are in the screen menu bar."
---

- **One row instead of two.** Settings → Appearance → Window → **Put the menu bar in the title bar** draws the window's own title bar, with the menus at the left, the title in the middle and the platform's window buttons at the right. It is built on JavaFX 27's extended stage style. Off by default.
- **Still a title bar.** The empty space and the title drag the window, and the menus stay clickable.
- **For new windows.** A window's style is fixed when it is first shown, so the setting applies to windows opened after the change, and its row says so.
- **Hide the menu bar** with `Ctrl+Shift+M`, View → Hide Menu Bar or Settings → Appearance → Window. Every command stays reachable by its chord and the palette, and the right-click menu's **Show Menu Bar** brings it back. Inside the title bar the title and buttons stay, so the window can still be moved.
- **macOS** puts the menus in the screen menu bar, so there is nothing in the window to merge or hide, and both settings are disabled there with the reason on their row. The title-bar option is also disabled, with its reason, on any platform that cannot draw its own title bar.
