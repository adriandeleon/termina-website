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
- **`--config-dir DIR`** reads and writes the settings in another folder, as does the `TERMINA_CONFIG_DIR` environment variable. **`--dev`** uses `~/.termina-dev`, so a build being tried out leaves your own settings alone.
- **`--no-session`** starts without the tabs that were open last time.
- **`--version` and `--help`** answer without opening a window or needing a display, so `termina --version` is what goes into a bug report.
- **`--report`** writes a problem report to a file and opens a GitHub issue for it, also without a window, for when the window is what will not start.
- **One Termina per settings folder.** Launching it again opens a window in the one already running, with that launch's `-d`, `-e` and `-T`. `--new-instance` starts a separate one.
- **On Windows, `termina-cli.exe`** is the launcher to type in a terminal: it prints `--help`, `--version` and `--report` there, which `Termina.exe`, a window program, cannot.
- **An unknown option is refused**, not ignored, because an option dropped without a word looks exactly like one that does nothing.

`-d`, `-T` and `-e` apply to the first tab of the window the launch opens. See [The command line](/docs/command-line).
