---
title: "xterm compatibility"
group: "The terminal"
order: 2
beta: false
summary: "Emulation by JetBrains' JediTerm, so vim, htop and less behave: mouse reporting in all four wire formats, application cursor keys, the kitty keyboard protocol, 256 colors and 24-bit color, and double-width characters."
---

xterm compatibility is a long tail of cursor modes, character sets, scroll regions and a hundred escape sequences, and getting one wrong shows up as a program subtly misbehaving. Termina leaves that to JediTerm, the emulator inside JetBrains' IDEs, and draws its screen.

- **Full-screen programs work.** vim, htop, less, man and the rest, on the alternate screen, with bracketed paste and scroll regions.
- **Mouse reporting** in the four xterm wire formats (X10, UTF-8, URXVT and SGR), chosen by the program, including pointer motion for programs that ask for it. `Shift` bypasses it. See [Selection and the mouse](/docs/selection-and-mouse).
- **Keys are encoded by the emulator**, so Up is `ESC O A` in application-cursor mode and `ESC [ A` otherwise, whichever the program asked for. Dead keys, AltGr and input methods work, and Alt can act as Meta for readline's `M-b` and `M-f` (Settings → Terminal → Input).
- **The kitty keyboard protocol**, all of it, for a program that asks: Neovim, Vim, Helix, fish and nushell get Escape on its own, `Ctrl+I` apart from Tab and `Shift+Enter` as a key. A program that does not ask gets the usual bytes.
- **Requests a program makes**: declared links (OSC 8), the clipboard (OSC 52, write only), desktop notifications (OSC 9 and 777) and shell integration marks (OSC 133). See [What programs can ask for](/docs/programs).
- **Color**: the 16 ANSI colors from the theme, the 256-color palette and 24-bit color.
- **Double-width characters** such as CJK and emoji take two cells, and Nerd Font icons stay on the grid, drawn by the bundled Symbols Nerd Font Mono when no Nerd Font is installed, so `eza --icons` columns line up.
- **An idle terminal lets the machine idle.** A tab repaints only while there is something to draw, and a tab that is not selected paints its output when you switch to it rather than every frame.

Not there yet: inline images and ligatures. See [What programs can ask for](/docs/programs).
