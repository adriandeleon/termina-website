---
title: Command line
description: The termina command and each of its options, how help, version, unknown options and -e behave, why the command line applies to the first tab only, and what the Linux packages install.
category: Using Termina
order: 7
---

```
termina [options] [-e command [args...]]

  -h, --help                     Show this help and exit
  -v, --version                  Show the version and exit
  -d, --working-directory=DIR    Start the shell in DIR
  -T, --title=TITLE              Name the first tab TITLE
  -e, --command CMD [args...]    Run CMD instead of the shell; must come last
      --config-dir=DIR           Read and write settings in DIR
```

The option names follow the terminals people already have: `-e` and `-T` are xterm's and GNOME
Terminal's, `--working-directory` and `--title` are GNOME Terminal's spelling.

## The options

- **`-h`, `--help`** prints the usage and exits.
- **`-v`, `-V`, `--version`** prints `Termina 0.13.0` and exits.
- **`-d DIR`, `--working-directory DIR`** (or `--working-directory=DIR`) starts the shell in `DIR`.
  It wins over a profile's own starting directory. A directory that does not exist falls back to
  your home directory rather than failing, since the argument usually comes from another program.
- **`-T TITLE`, `--title TITLE`** (or `--title=TITLE`) names the first tab, as a double-click
  rename would: the name stays when a program sets a title of its own. Debian asks this of every
  `x-terminal-emulator`, alongside `-e`.
- **`-e CMD [args...]`, `--command CMD [args...]`** runs `CMD` instead of your shell.
- **`--config-dir DIR`** (or `--config-dir=DIR`) reads and writes the settings in `DIR` instead of
  `~/.termina`. The `TERMINA_CONFIG_DIR` environment variable does the same; the option wins when
  both are given.

## How it behaves

- **`--help` and `--version` work without a display.** They are answered before the toolkit
  starts, so they work over SSH and in scripts. `--version` is the line to paste into a bug report.
- **An unknown option is refused**, not ignored, with a message and exit status 2. A positional
  argument is refused the same way, with a hint to use `-e`. An option that is silently dropped
  looks exactly like one that exists and does nothing.
- **`-e` takes the rest of the line**, which is why it must come last: `termina -e ls -l` passes
  `-l` to `ls` rather than rejecting it.
- **The command replaces the shell** rather than running inside one, so `termina -e vim notes.txt`
  is vim, and its tab closes when vim exits.
- **It applies to the first tab of the first window only.** Every later tab and window opens your
  default profile as usual. A second tab re-running `-e vim`, or opening in a directory you have
  since left, is not what anybody means by it.

The command line sets up a launch and nothing else. The shell, colors, fonts and the rest are
configured in Settings.

## Where the launcher is

| Platform | Launcher |
| --- | --- |
| macOS | `/Applications/Termina.app/Contents/MacOS/Termina` |
| Linux | `/opt/termina/bin/Termina`, and `termina` on your `PATH` |

The Linux packages install `/usr/bin/termina`, a link to the launcher. On macOS and Windows there is
no `termina` command; link or alias the launcher to call it that way.

## What the Linux packages install

Besides the application under `/opt/termina` and the `termina` command, the `.deb` and `.rpm`
install:

- **A desktop entry**, described below.
- **AppStream metadata**, so GNOME Software and KDE Discover list Termina with its description.
- **An `x-terminal-emulator` alternative** (the `.deb` only). Termina is offered as a choice without
  taking over: its priority is below GNOME Terminal's and Konsole's. To make it the default, run
  `sudo update-alternatives --config x-terminal-emulator`.

### The desktop entry

It has:

- **`Categories=System;TerminalEmulator;`**, so desktops and file managers that look for a terminal
  can offer Termina. That works because Termina accepts `-e command`, which is the interface they
  expect of a terminal.
- **`StartupWMClass=com.termina.App`**, the window class JavaFX derives from the main class. It
  lets the desktop match a running window to its launcher, so the dock highlights the entry rather
  than showing a second, generic icon beside it.
- **`Keywords=shell;prompt;command line;console;`**, so searching the application menu for any of
  them finds it.
- **`X-TerminalArgExec=-e`**, which tells `xdg-terminal-exec` how to hand Termina a command.
