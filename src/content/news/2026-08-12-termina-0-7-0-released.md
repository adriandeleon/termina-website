---
title: Termina 0.7.0 released
description: "Shell profiles: a tab can open as any shell the machine has, found rather than configured: both PowerShells, Command Prompt, Git Bash and every WSL distribution on Windows."
date: 2026-08-12
version: 0.7.0
---

Termina 0.7.0 adds shell profiles. A terminal on Windows is not one program: Command Prompt,
Windows PowerShell, PowerShell 7 and every installed WSL distribution are separate shells, and a
single "path to your shell" setting can only ever name one of them.

## Profiles

A profile is a name, a command and optionally a starting directory, and a window offers all of
them: the chevron beside the `+` drops the list, **File → New Tab With** carries the same entries,
the command palette lists them by name, and `Ctrl+Shift+1` to `9` (`Cmd+Shift+1` to `9` on a Mac)
open the first nine.

## Found, not configured

- **On Windows**: both PowerShells, 7 first, then Command Prompt, Git Bash and each WSL
  distribution, leaving out Docker Desktop's and Rancher's internal ones.
- **On macOS and Linux**: the shells named in `/etc/shells` and on the path (zsh, bash, fish,
  nushell and the rest), one entry per shell rather than one per binary, each started with the
  login flag it understands.
- **Nothing found is written down**, so uninstalling a shell removes it from the menu instead of
  leaving an entry that fails to start.

Your own shell is always first and cannot be hidden. Anything else can be, and you can add
profiles of your own, such as `wsl.exe -d Ubuntu` or a shell on a server, under
**Settings → Profiles**. Each entry carries its shell's mark, from Simple Icons.

The Windows half has been driven in tests with the filesystem and `wsl.exe` replaced, but not yet
run on Windows by a person: reports are welcome.

[Download Termina 0.7.0](https://github.com/adriandeleon/Termina/releases/tag/v0.7.0), or the
[latest release](https://github.com/adriandeleon/Termina/releases/latest).
