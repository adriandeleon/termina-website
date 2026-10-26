---
title: "Titles that say where you are"
group: "Tabs & windows"
order: 3
beta: false
summary: "Tabs and the title bar show the shell's directory, read from the operating system rather than from your prompt, and the program running. A new tab opens where the current one is."
---

The usual way a terminal learns its shell's directory is an escape sequence the prompt sends, and every prompt framework that replaces `PS1` drops it. Termina asks the operating system instead, so it works with Starship, powerlevel10k, Oh My Bash or no framework at all.

- **The title bar** shows the full path, with your home written as `~`: `~/src/Termina`.
- **The tab** shows the program running, such as `vim`, `ssh` or `npm`, and at an idle prompt the directory's last segment. A title the shell or a program sets still wins, and a name you give the tab wins over that.
- **Hover a tab** for its directory in full, even when a program has set the title.
- **A new tab inherits the directory** of the current one, and relative paths in output resolve against it when [clicked](/features/links).

- **Windows too.** The directory is read out of the shell process there as well. A WSL tab's process is `wsl.exe`, which cannot say where the Linux shell inside it is, so there a directory the shell reports (OSC 7) wins, as long as it is on this machine and exists.

See [Tabs and windows](/docs/tabs-and-windows) and [What programs can ask for](/docs/programs#the-directory-osc-7).
