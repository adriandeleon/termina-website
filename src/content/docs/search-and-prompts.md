---
title: Search and prompts
description: The find bar and how it steps through the scrollback, smart case and regular expressions, Find Next and Find Previous, searching for the selection, and shell integration with OSC 133 — jumping between prompts, selecting the last command's output, the mark beside a failed command, and the lines to add to bash and zsh.
category: Using Termina
order: 6.5
---

## The find bar

**Edit → Find…**, `Ctrl+Shift+F` (`Cmd+F`), opens a bar at the top right of the pane. It searches
the whole scrollback and the screen as you type, highlights every match in the theme's yellow, and
outlines the current one. In a split tab each pane has a bar of its own, and the chord opens the
active pane's.

| Key | In the bar |
| --- | --- |
| `Enter` or `Up` | The next match up, in older output |
| `Shift+Enter` or `Down` | The next match down, toward the prompt |
| `Escape` | Close the bar and give the keyboard back to the shell |

The search starts at the newest match, at the bottom of what you are looking at, and works up:
a terminal is usually searched for something recent. Stepping wraps at either end. A match out of
view is scrolled to the middle of the pane.

The bar shows where you are, such as `3 of 17`, or `No matches`. The count stops at 1000 and says
`1000+`. Closing the bar drops the highlights.

## What matches

- **Case is smart.** A query in lower case matches either case; a capital anywhere makes it exact,
  so `error` finds `Error` and `ERROR`, and `Error` finds only `Error`.
- **`.*`** in the bar switches to a regular expression. One that does not compile yet, because you
  are still typing it, says `Invalid` rather than reporting no matches.
- **A word that wrapped at the right edge** is found as one word.
- **Double-width characters**, such as CJK, match as the text they are.
- **Matches stay on their text** as output arrives, and are found again after the pane is resized.

## Find Next and Find Previous

| Key | Does |
| --- | --- |
| `Ctrl+Shift+G` (`Cmd+G`) | Find Next: the next match up, in older output |
| `Ctrl+Shift+H` (`Cmd+Shift+G`) | Find Previous: the next match down, toward the prompt |

They work from anywhere, not only from the bar, and open it when it is closed. Off macOS the pair
is GNOME Terminal's; on macOS it is the system's, since `Cmd+H` hides the application.

**Select something first** and Find uses it: a selection on one line, up to 200 characters, becomes
the query.

## Shell integration

A shell can tell the terminal where each prompt starts, where a command's output starts, and how
the command ended, with **OSC 133** marks. With them, three commands in the Edit menu work:

| Key | Does |
| --- | --- |
| `Ctrl+Shift+Up` (`Cmd+Up`) | Previous Prompt: scroll up to the prompt before this one |
| `Ctrl+Shift+Down` (`Cmd+Down`) | Next Prompt: scroll down to the next prompt, and back to the live screen after the last |
| | Select Last Output: select everything the last command printed, ready to copy |

A prompt you jump to lands at the top of the pane. Select Last Output has no chord; it is in the
Edit menu and the command palette, and the Keyboard page of Settings can give it one.

**A command that failed**, one that exited with a status other than 0, gets a short bar in the
theme's red in the left margin of its prompt, so the one that broke can be found in a screen of
output. Commands that succeeded get nothing.

Without marks, the three commands are greyed in the Edit menu, and the command palette lists them
greyed with the reason. The marks are recorded on the normal screen, not while a full-screen
program such as vim has the alternate one, and they stay on their prompts when the pane is resized.

## Setting up bash and zsh

Some shells and prompt frameworks send the marks by themselves. For bash, add this to
`~/.bashrc`:

```bash
PS0='\e]133;C\a'
PROMPT_COMMAND='printf "\e]133;D;%s\a\e]133;A\a" "$?"'"${PROMPT_COMMAND:+;$PROMPT_COMMAND}"
```

For zsh, add this to `~/.zshrc`:

```zsh
_termina_precmd() { local s=$?; print -n "\e]133;D;$s\a\e]133;A\a" }
_termina_preexec() { print -n "\e]133;C\a" }
precmd_functions=(_termina_precmd $precmd_functions)
preexec_functions+=(_termina_preexec)
```

Both keep whatever your prompt already runs before each prompt. Open a new tab for the change to
take effect; the shells already running have not read it.

- **`A`** marks where a prompt starts.
- **`C`** marks where a command's output starts, sent just before the command runs.
- **`D`** marks where it ended, with its exit status.
