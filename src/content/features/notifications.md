---
title: "Desktop notifications"
group: "Your desktop"
order: 2.5
beta: false
summary: "A program can say it has finished: <code>OSC 9</code> or <code>OSC 777</code> marks its tab with a dot and reaches the desktop, unless you are already looking at that pane."
---

- **Both common forms.** urxvt's `OSC 777;notify;title;body`, which foot, kitty and WezTerm read too, and iTerm2's `OSC 9;message`. ConEmu's numbered `OSC 9` subcommands, such as its progress bar, are not notifications and are not posted.
- **The tab is marked** with a magenta dot that outranks the bell's, so a tab in the background says that something spoke.
- **Only when you are not looking.** It reaches the desktop unless that pane is the one you are typing in, in the window in front. A notification with no title is titled after its tab.
- **Each platform's own way**: `notify-send`, or the session bus through `gdbus`, on Linux; `osascript` on macOS; a PowerShell toast on Windows.
- **No floods.** One per pane every two seconds reaches the desktop; the rest still mark the tab.
- **The text is treated as hostile.** Control characters are stripped, it is cut to a length a bubble can show, and it reaches each command as an argument or an environment variable, never inside a script.
- **One switch.** Settings → Terminal → Display → Desktop notifications turns the desktop half off; the tab is still marked.

See [What programs can ask for](/docs/programs#notifications-osc-9-osc-777).
