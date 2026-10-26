---
title: What programs can ask for
description: The requests a program running in the terminal can make and what Termina does with each — declared links (OSC 8), the clipboard (OSC 52), desktop notifications (OSC 9 and 777), the directory on Windows (OSC 7), prompt marks (OSC 133), the kitty keyboard protocol — and type-ahead prediction over slow SSH, with the setting that turns each off.
category: Using Termina
order: 6.7
---

A program talks to the terminal in escape sequences mixed into its output. Most of them move the
cursor or change a color, and JediTerm handles those. A few ask the terminal itself for something:
open this link, copy this text, tell me when you are done. Termina honors these, within limits,
because the program asking may be on the far side of an `ssh` and its output is whatever it chose
to print.

| Request | What Termina does | Setting |
| --- | --- | --- |
| A declared link (OSC 8) | Makes the text a link, and shows its target on hover | — |
| The clipboard (OSC 52) | Lets the program set it, never read it | Terminal → Input |
| A notification (OSC 9, OSC 777) | Marks the tab, and posts to the desktop | Terminal → Display |
| The directory (OSC 7) | Gives the tab a directory, on Windows only | — |
| Prompt marks (OSC 133) | Prompt navigation and failed-command marks | — |
| The kitty keyboard protocol | Unambiguous keys, while the program asks | — |

## Declared links (OSC 8)

`ls --hyperlink`, compilers and `gh` can mark text as a link to somewhere else. `Ctrl`+click
(`Cmd`+click on macOS) opens it like any other link, and with the modifier held a line at the
bottom of the pane shows where it really goes, since the text can say anything. Only the schemes a
link in the text may use are accepted, so a `javascript:` target is not a link. See
[Links](/docs/links#links-a-program-declares).

## The clipboard (OSC 52)

A program can put text on the clipboard. This is how vim and tmux copy across SSH, where the
program has no other way to reach your machine's clipboard.

- **Write only.** A program can never read the clipboard. Answering would hand whatever was last
  copied, passwords and tokens included, to anything that asked, including a program on another
  machine.
- **Only text.** Data that is not valid base64 or not UTF-8 is dropped rather than put on the
  clipboard garbled.
- **Settings → Terminal → Input → Let programs copy to the clipboard** (on by default) turns
  writing off too.

## Notifications (OSC 9, OSC 777)

A program can post a notification, such as a build saying it has finished. Both common forms are
read:

```bash
printf '\e]777;notify;Build;done\a'   # urxvt's, which foot, kitty and WezTerm read too
printf '\e]9;done\a'                  # iTerm2's
```

- **The tab is marked** with a magenta dot, which outranks the bell's yellow and the activity
  mark's cyan, in the theme's own rendering of each. A tab you are looking at is not marked.
- **It reaches the desktop unless you are looking at that pane**: the window in front, the tab
  selected and the pane active. On Linux it goes through `notify-send`, or straight to the session
  bus with `gdbus` where `notify-send` is not installed; on macOS through `osascript`; on Windows as
  a PowerShell toast. A notification with no title is titled after its tab.
- **One per pane every two seconds** reaches the desktop, so a loop printing OSC 9 cannot fill the
  screen with bubbles. Every one still marks the tab.
- **The text is treated as hostile.** Control characters are stripped, the title and the message
  are cut to a length a bubble can show, and they reach each command as an argument or an
  environment variable, never inside a script.
- **Not every OSC 9 is a notification.** ConEmu numbers subcommands under it (`9;4` is a progress
  bar, `9;9` a directory), and those are not posted.
- **Settings → Terminal → Display → Desktop notifications** (on by default) turns the desktop half
  off. The tab is marked either way.

## The directory (OSC 7)

A shell can report its working directory, as a `file://` URI. On Linux and macOS Termina ignores
the report and asks the operating system instead, which works whatever your prompt does, and which
a shell inside `ssh` cannot claim for another machine's path.

On Windows the operating system is asked too: the directory is read out of the shell process. But
a WSL tab's process is `wsl.exe`, whose directory says nothing about where the Linux shell inside
it is, so there a directory the shell reports wins. The same holds for a program started as
`bash.exe`, which is how Git Bash runs. Both OSC 7 and ConEmu's `OSC 9;9`, the form
PowerShell and Command Prompt send when set up for Windows Terminal, are read, and only for a
directory on this machine that exists. A tab with a directory opens new tabs there and turns relative paths into
[links](/docs/links#windows).

## Prompt marks (OSC 133)

A shell that marks where its prompts and commands begin and end gets Previous Prompt and Next
Prompt, Select Last Output, and a red bar beside a command that failed. See
[Search and prompts](/docs/search-and-prompts), which has the lines to add to bash and zsh.

## The kitty keyboard protocol

In a terminal, several keys send the same bytes as others: Escape is the start of every escape
sequence, `Ctrl+I` is Tab, and `Shift+Enter` is Enter. A program can ask for the
[kitty keyboard protocol](https://sw.kovidgoyal.net/kitty/keyboard-protocol/) to tell them apart,
and Termina implements all of it. Neovim, Vim, Helix, fish and nushell ask by themselves.

- **The first flag** gives unambiguous keys: Escape on its own, `Ctrl+I` apart from Tab, and
  `Shift+Enter` as a key of its own.
- **The further flags** add repeats and releases, the shifted key, every key including the
  modifiers, and the text each key types.
- **Nothing changes for a program that does not ask.** Every key keeps its usual bytes. Under the
  first flag plain typing, Enter, Tab and Backspace keep them too, so `reset` can still be typed
  after a program that crashed with the protocol on. `reset` clears it.
- **Each screen keeps its own mode**, so an editor on the alternate screen can change it without
  disturbing the shell's, and a program's query is answered.

Three things are approximated, because JavaFX does not report them: a right-hand modifier is
reported as the left one, the optional base-layout key is never sent, and AltGr is told apart from
`Ctrl+Alt` by whether the press composed a character.

## Type-ahead over slow SSH

While `ssh` is the program running in the tab and the far end is slow to echo, what you type
appears at once, dimmed and underlined until the echo confirms it, and the cursor moves ahead with
it.

- **JediTerm's predictor decides when.** It measures the delay, learns what echoes, notices a
  password prompt, and backs off when it guesses wrong. It shows nothing until the median echo
  takes longer than 100 ms, so on a fast link it stays out of the way.
- **A local shell is never predicted.** It echoes within a frame, and a prediction there could
  only ever be wrong.
- **Settings → Terminal → Input → Predict typing over slow SSH** (on by default) turns it off.
