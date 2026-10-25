---
title: "xterm compatibility"
group: "The terminal"
order: 2
beta: false
summary: "Emulation by JetBrains' JediTerm, so vim, htop and less behave: mouse reporting in all four wire formats, application cursor keys, 256 colors and 24-bit color, and double-width characters."
---

xterm compatibility is a long tail of cursor modes, character sets, scroll regions and a hundred escape sequences, and getting one wrong shows up as a program subtly misbehaving. Termina leaves that to JediTerm, the emulator inside JetBrains' IDEs, and draws its screen.

- **Full-screen programs work.** vim, htop, less, man and the rest, on the alternate screen, with bracketed paste and scroll regions.
- **Mouse reporting** in the four xterm wire formats (X10, UTF-8, URXVT and SGR), chosen by the program, including pointer motion for programs that ask for it. `Shift` bypasses it. See [Selection and the mouse](/docs/selection-and-mouse).
- **Keys are encoded by the emulator**, so Up is `ESC O A` in application-cursor mode and `ESC [ A` otherwise, whichever the program asked for. Dead keys, AltGr and input methods work, and Alt can act as Meta for readline's `M-b` and `M-f` (Settings → Terminal → Input).
- **Color**: the 16 ANSI colors from the theme, the 256-color palette and 24-bit color.
- **Double-width characters** such as CJK and emoji take two cells, and icons from an installed Nerd Font stay on the grid, so `eza --icons` columns line up.

Not there yet: inline images, the kitty keyboard protocol, and links declared with OSC 8.
