---
title: "Bundled fonts"
group: "The terminal"
order: 5
beta: false
summary: "Five monospace families ship inside the app, with JetBrains Mono the default, Inter for the interface, and a Nerd Font for icons, so a terminal looks the same on every machine. Zoom is separate from the size you chose."
---

A picker over whatever is installed gives Menlo on macOS, Consolas on Windows and DejaVu on Linux: three sets of metrics, and no way to describe a setup to anyone else. So the fonts come with Termina.

- **Five monospace families**: JetBrains Mono (the default), Cascadia Code, Fira Code, IBM Plex Mono and Source Code Pro. **Inter** draws the interface.
- **Icons without installing a Nerd Font.** Symbols Nerd Font Mono 3.5.1 is bundled and draws the icons from `eza --icons`, `lsd` and prompt themes when the terminal's face has no Nerd Font build installed. Its icons are a cell and a half wide, the size the Nerd Font builds draw theirs, and Powerline separators span the line so a prompt's segments meet. It has no letters, so it is not in the picker.
- **Monospace faces only.** The renderer places every glyph on a fixed cell grid, so a proportional face would misalign every column. The bundled families lead the picker, and every installed monospace face is listed after them.
- **A font per profile.** Settings → Profiles gives any profile its own face and size, and every other profile keeps the global one.
- **Zoom is not the font size.** Settings → Appearance → Font sets the size; zooming multiplies it, from 30% to 300%, and Actual Size returns to the size you chose rather than discarding it. See [Zoom](/features/zoom).
- **A live preview** on the Appearance page renders the real font with the real theme as you change either.

See [Appearance](/docs/appearance#font-and-size).
