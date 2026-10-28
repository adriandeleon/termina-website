---
title: Shell profiles
description: What a profile is, which shells are discovered on each platform, the profile dropdown, New Tab With and the number chords, choosing the default, hiding and adding profiles, a profile's own font, size and theme, and the profile keys in settings.properties.
category: Using Termina
order: 4
---

A profile is a name, a command and, optionally, a starting directory. It is what a tab is opened
as. A single "path to your shell" setting can name only one program, and a machine often has
several: zsh and fish, or on Windows Command Prompt, two PowerShells and every WSL distribution.

## Where profiles come from

The list has three parts, always in this order:

1. **Default Shell**: your own login shell, as the system reports it. It is always first and is the
   one profile that cannot be hidden, so a window can always open a tab.
2. **Discovered**: shells found installed on this machine, re-detected at every launch.
3. **Yours**: profiles you add in Settings or in the settings file.

Nothing discovered is written down. Uninstall a shell and it leaves the list rather than becoming
an entry that fails to start. Discovery runs in the background at launch, so a menu opened in the
first moment may be shorter until it finishes.

## What is discovered

### macOS and Linux

The shells named in `/etc/shells`, plus those found on your `PATH` and in the usual package
directories (`/opt/homebrew/bin`, `/usr/local/bin` and others). Only these are listed, in this
order:

| Profile | Started as |
| --- | --- |
| Zsh, Bash, Fish, Ksh, Tcsh | a login shell, with `-l` |
| Nushell, Elvish, Xonsh | plain, since they do not all take `-l` |

- **One entry per shell.** A machine with Homebrew has two zsh executables; the menu offers one.
  Two names for the same binary also count as one.
- **Your own shell is not listed twice.** If your login shell is zsh, the Default Shell entry is it,
  and no separate Zsh entry appears.
- **`sh`, `dash` and `rbash` are not offered.** Add a profile if you want one.

The Default Shell is `$SHELL` with `-l`, or the path set under **Settings → Terminal → Session →
Shell**, falling back to `/bin/zsh` on macOS and `/bin/bash` on Linux.

### Windows

| Profile | Command |
| --- | --- |
| PowerShell | `pwsh.exe -NoLogo` (PowerShell 7), listed first |
| Windows PowerShell | `powershell.exe -NoLogo` |
| Command Prompt | `cmd.exe` |
| Git Bash | `bash.exe --login -i`, from Git for Windows |
| *Name* (WSL) | `wsl.exe -d Name`, one per distribution |

The WSL list comes from `wsl.exe --list --quiet`. Docker Desktop's and Rancher Desktop's internal
pseudo-distributions are left out. The Default Shell on Windows is PowerShell 7, then Windows
PowerShell, then Command Prompt, whichever is found first.

## Opening a tab with a profile

- **The chevron** beside the `+` drops the list. Each entry has its shell's mark, the default has a
  tick, and **Configure Profiles…** at the bottom opens Settings.
- **File → New Tab With**, and **New Tab With** in a tab's right-click menu, carry the same list.
- **`Ctrl+Shift+1`…`Ctrl+Shift+9`** (`Cmd+Option+1`…`Cmd+Option+9` on macOS) open the first nine
  profiles in the list. A number past the end does nothing.
- **The command palette** lists every profile as **New Tab: *name***.

A profile with no starting directory opens in the current tab's directory, like a plain new tab.
The default is marked rather than moved to the top, so the numbers bound to chords do not shift.

## The default profile

**Settings → Profiles → Profile for new tabs** chooses what `+` and New Tab open. It applies to the
next tab. Profiles are keyed by a stable id, not by position, so installing a shell never changes
which profile the `+` opens. A default that names a shell since removed falls back to the first
profile.

If the default profile's command cannot start, the error names the command, and the Default Shell
opens in its place so the window is never left empty.

## Hiding and adding profiles

**Settings → Profiles** lists every profile, hidden ones included:

- **Show in the profile menu** hides or shows a discovered profile or one of yours.
- **Add** creates a profile of your own with a **Name**, a **Command** and a **Starting
  directory** (leave it empty to follow the current tab). The fields check as you type and say
  **Not found**, **Not executable** or **Not a directory**.
- **Remove** deletes one of yours.

Discovered profiles cannot be edited, since they are re-detected at every launch. To run one
differently, add a profile of your own: a shell with other arguments, `ssh build.example.com`, or a
shell in a directory you keep coming back to.

## A profile's own font and theme

Every profile, the Default Shell and the discovered ones included, can have its own **Font**,
**Font size** and **Theme**, under **Settings → Profiles**. Each is **Default** until set, which
follows the global setting under Appearance. A server you reach with `ssh` can have a theme of its
own, so its tab never looks like a local one.

- **The font list** is the same monospace faces as the global one, and the theme list has the
  built-in themes and any you have [imported](/docs/appearance#importing-a-theme).
- **The pane keeps it.** Splitting a pane, duplicating or reopening its tab, and moving it to
  another tab keep the profile it was opened as, and with it the profile's look.
- **The size goes through zoom**, like the global size.
- **With several themes in one window**, the window's own colors (behind the panes, the window
  buttons, the tab marks) follow the pane being typed in.

## In settings.properties

The whole list lives in `~/.termina/settings.properties`, which you can edit by hand while Termina
is closed:

```properties
# The default profile, by id. Empty means the first profile.
profiles.default=shell-fish

# Discovered or user profiles to leave out of the menus, by id.
profiles.hidden=shell-tcsh,wsl-ubuntu

# Your own profiles, numbered from 1.
profile.1.id=user-build
profile.1.name=Build server
profile.1.command=ssh build.example.com
profile.2.name=Notes
profile.2.command=/bin/zsh -l
profile.2.directory=/home/me/notes
```

- **User profiles are numbered from 1**, up to 64. A gap is skipped rather than ending the list, so
  deleting one block removes one profile.
- **`name` and `command` are required.** `command` is split as a shell would split it, honoring
  single and double quotes. `directory` is optional.
- **`id` is optional** and defaults to `user-N`. Give one if anything points at the profile: the
  default and the hidden list refer to profiles by id, so a stable id survives renaming.
- **Discovered ids** are `system` for the Default Shell, `shell-zsh`, `shell-bash` and so on for
  Unix shells, and `pwsh`, `powershell`, `cmd`, `git-bash` and `wsl-<name>` on Windows.

A profile's font, size and theme are kept apart from the numbered block, keyed by the profile's
id, because the Default Shell and the discovered profiles have no block of their own:

```properties
profileStyle.user-build.theme=clear-light
profileStyle.system.font=Fira Code
profileStyle.system.fontSize=15
```

A missing key means Default. The size is clamped to 7 to 40 when read, like the global one. A
built-in theme's id is `editora-dark`, `editora-light`, `clear-dark` or `clear-light`, and an
imported one's is `user-` and the name of its file in `~/.termina/themes`, without `.properties`
(`user-solarized-dark`).

**Reset to Defaults** in Settings keeps your profiles, their fonts and themes, the default profile
and the hidden list.

A command given on the command line with `-e` still replaces the shell, and `-d` still wins over a
profile's own directory. See [Command line](/docs/command-line).
