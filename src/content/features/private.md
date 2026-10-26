---
title: "Private by default"
group: "Your desktop"
order: 3
beta: false
summary: "No account and no telemetry. The one request Termina makes on its own is a daily update check against GitHub's releases, and one switch turns it off."
---

- **No account, no telemetry, no analytics.** Nothing to sign up for, and nothing reports how you use it.
- **One request of its own.** At startup, at most once a day, Termina asks the GitHub releases API whether a newer version exists. Nothing is sent beyond the request itself.
- **One switch.** Settings → Advanced → Updates → Check for updates at startup, or `updates.check=false` in the file. Help → Check for Updates asks only when you choose to.
- **Sync goes only where you point it.** Settings sync is off until you connect it to a Git repository of your own, and then carries only your key bindings there, signing in with your own credential helper or SSH key. A repository URL with a password or token in it is refused.
- **Programs cannot read your clipboard.** A program may copy to it (OSC 52), which one switch also turns off, but a request to read it is never answered, so nothing on the far side of an `ssh` can take what you copied.
- **A plain settings file.** Everything is in `~/.termina/settings.properties`, readable and editable, with no database and no server.
- **The debug log stays local.** It lives in `termina-session.log` in the same folder, and goes nowhere unless you copy it into a bug report.

See [Settings](/docs/settings#updates).
