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
- **A plain settings file.** Everything is in `~/.termina/settings.properties`, readable and editable, with no database and no server.
- **The debug log stays local.** It lives in `termina-session.log` in the same folder, and goes nowhere unless you copy it into a bug report.

See [Settings](/docs/settings#updates).
