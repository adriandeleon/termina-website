---
title: "A command line"
group: "Your desktop"
order: 2
beta: false
summary: "<code>termina -e htop</code> runs a program in place of the shell, <code>-d</code> picks the directory, <code>-T</code> names the tab, and <code>--config-dir</code> keeps a second set of settings."
---

- **`-e command [args...]`** runs the command instead of the shell, and its tab closes when it exits. Like xterm's, it takes the rest of the line, so `-e ls -l` passes `-l` to `ls`. It has to come last.
- **`-d DIR`** (`--working-directory`) starts the shell in that directory.
- **`-T TITLE`** (`--title`) names the first tab, as a rename would, so a program's own title does not replace it.
- **`--config-dir DIR`** reads and writes the settings in another folder, as does the `TERMINA_CONFIG_DIR` environment variable.
- **`--version` and `--help`** answer without opening a window or needing a display, so `termina --version` is what goes into a bug report.
- **An unknown option is refused**, not ignored, because an option dropped without a word looks exactly like one that does nothing.

The options apply to the first tab of the first window. See [The command line](/docs/command-line).
