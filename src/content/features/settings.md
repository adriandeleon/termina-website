---
title: "Settings that apply live"
group: "Make it yours"
order: 2
beta: false
summary: "A Settings window with no OK button: every control applies as it changes. A search box over every row, every setting in the command palette, and a plain <code>settings.json</code> you can edit by hand."
---

- **No OK button.** A theme, a font or the opacity changes the terminal the moment you pick it, and the Appearance page shows a live preview.
- **It says what waits.** The two settings that cannot apply live, the scrollback size and the shell, say so on their own rows and apply to new tabs.
- **Search** narrows the rows and the pages at once, and opens the page with the best match. It finds a row by its title, its description, or what its controls show.
- **Every setting is in the palette.** A switch says which way it will go ("Show a scrollbar: turn off"), a choice lists its options, and a value asks for a new one.
- **Files you can read.** Preferences are in `~/.termina/settings.json`, and what Termina remembers by itself (the window's place, the last session's tabs) in `workspace-state.json`. A bad value costs only that key, values are clamped when read as well as written, and a file is replaced atomically, so a crash cannot leave it empty.
- **Export and Import Configuration.** Settings → Advanced saves your settings, key bindings, profiles and imported themes as one `.zip`, and restores the parts you tick from one.
- **Help on every page.** Each page ends with a link to its page of the user guide.
- **Reset to Defaults asks first, and keeps your profiles.**
- **Sidebar icons** since 0.10.0: Appearance, Terminal, Profiles and Advanced each have their own.

See [Settings](/docs/settings).
