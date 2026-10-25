---
title: "Bundled fonts"
group: "The terminal"
order: 5
beta: false
summary: "Five monospace families ship inside the app, with JetBrains Mono the default, and Inter for the interface, so a terminal looks the same on every machine. Zoom is separate from the size you chose."
---

A picker over whatever is installed gives Menlo on macOS, Consolas on Windows and DejaVu on Linux: three sets of metrics, and no way to describe a setup to anyone else. So the fonts come with Termina.

- **Five monospace families**: JetBrains Mono (the default), Cascadia Code, Fira Code, IBM Plex Mono and Source Code Pro. **Inter** draws the interface.
- **Monospace faces only.** The renderer places every glyph on a fixed cell grid, so a proportional face would misalign every column. The bundled families lead the picker, and every installed monospace face is listed after them.
- **Zoom is not the font size.** Settings → Appearance → Font sets the size; zooming multiplies it, from 30% to 300%, and Actual Size returns to the size you chose rather than discarding it. See [Zoom](/features/zoom).
- **A live preview** on the Appearance page renders the real font with the real theme as you change either.
