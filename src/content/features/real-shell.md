---
title: "A real shell"
group: "The terminal"
order: 1
beta: false
summary: "Your own shell on a real pseudo-terminal: <code>forkpty</code> on macOS and Linux, ConPTY on Windows. Started as a login shell, so <code>PATH</code> is what it is everywhere else."
---

Termina does not imitate a shell. Each tab starts your shell on a pseudo-terminal, through JetBrains' pty4j, and moves bytes between it and the screen.

- **Real PTYs on every platform.** `forkpty` on macOS and Linux, Windows' ConPTY on Windows. One library carries every platform's native code, so nothing is built per OS.
- **A login shell.** An application started from the dock or a menu inherits a stripped `PATH`, with no Homebrew and no version-manager directories. The shell is started with `-l`, so your profile puts them back.
- **A UTF-8 locale when you have none.** If none of `LC_ALL`, `LC_CTYPE` or `LANG` is set, the shell gets `LANG=C.UTF-8`, which exists on minimal systems where `en_US.UTF-8` often does not. Any of the three set by you is left alone.
- **It says what it is.** `TERM=xterm-256color`, `COLORTERM=truecolor` and `TERM_PROGRAM=Termina`.
- **One shell per tab**, never shared. Closing a tab ends its shell and everything in it, and a shell that exits closes its own tab.

Which shell a tab runs is a [profile](/features/shell-profiles).
