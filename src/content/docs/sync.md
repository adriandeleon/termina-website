---
title: Settings sync
description: Syncing key bindings between computers through a private Git repository you own, setting it up, when it runs, what is synced and what is not, one list per platform family, a shortcut changed on two computers, backups and the stop on a large removal, signing in, several GitHub CLI accounts, and a settings folder inside a dotfiles repository.
category: Customization
order: 10.5
---

Termina can keep your shortcuts the same on every computer you use, through a private Git
repository you own. Git carries the data: there is no Termina account and no server of Termina's.
What is synced is the shortcuts you change on [Settings → Keyboard](/docs/keyboard#changing-a-shortcut),
and nothing else.

## Setting it up

1. **Make a private repository** on GitHub, GitLab or any other Git host, empty or already used by
   another of your computers.
2. **Settings → Sync → Repository**: its URL, such as `git@github.com:you/termina-sync.git` or an
   `https://` one. **Branch** is `main` unless the repository also holds something else.
3. **Connect.** The first sync runs at once, and it keeps what is here and what is in the
   repository: with nothing synced before, nothing looks removed.

Git has to be installed. Termina looks for it on your login shell's path as well as its own, so a
git installed with Homebrew is found when Termina is started from the Dock or a desktop launcher.

**Disconnect** keeps the bindings on both sides; only this computer's copy of the repository is
removed. Connecting again, or to another repository, starts as a first sync.

## When it runs

With **Sync automatically** on, which it is by default:

- **A few seconds after startup.**
- **Half a minute after you change a shortcut.**
- **Every 15 minutes while a Termina window is in front.** **Interval (minutes)** takes 5 to 1440.
  A terminal nobody is looking at does not talk to the network.

And whenever you ask:

- **Sync Now** on the Sync page, which shows how the last sync went: "Last synced at 14:02: 3
  received, 1 sent", or why it did not finish. **Cancel** stops a sync before anything on this
  computer is written.
- **File → Sync Settings Now**, also in the [command palette](/docs/keyboard#the-command-palette),
  syncs from anywhere and says how it went. It has no shortcut by default; the Keyboard page can
  give it one.

Only one Termina on a settings folder syncs. A second one started on the same folder says so on its
Sync page and leaves the repository to the first.

## What is synced

- **The shortcuts you have changed**, one line per command. A command on its default has no line,
  and `none` is a command you took off every key.
- **One list for Macs, another for Windows and Linux**: `keybindings/macos.properties` and
  `keybindings/windows-linux.properties`. A chord means a different key on each: the defaults are
  `Cmd` chords on macOS and `Ctrl+Shift` ones elsewhere, and a chord recorded on Linux names the
  Control key, which on a Mac is a different key. Each computer syncs only its own family's file and
  leaves the other as it is, so a Mac and a Linux machine can share a repository. Windows and Linux
  share a file because they use the same modifiers.
- **Not synced**: everything else in `settings.properties`, including the theme, the font and the
  profiles, which name shells by their path on each computer. Imported themes are not synced
  either.

The files are plain text, in the same notation as the `keys.*` lines of
[the settings file](/docs/keyboard#in-the-settings-file), and editing one in the repository by hand
is fine: every connected computer picks the change up on its next sync. Termina also writes a
`README.md`, a `termina-sync.properties` that records the repository's format, and a
`.gitattributes`. Each sync that changes something is one commit, "Sync from" and the computer's
name. A repository written by a newer Termina stops the sync, with a message to update Termina on
this computer.

## A shortcut changed on two computers

The merge is per command, so two computers that each changed a different shortcut both keep their
change. When the same command was changed differently on both, **the computer that syncs keeps its
version**, and the other stays in the repository's history. The status line names the commands it
happened to: "changed on both computers, kept from this one: …".

Termina never asks git to merge. It keeps its own clone, sets it to what the repository has, writes
the merged files over it and pushes that, so there are no conflict markers and no half-finished
rebase to recover from. A sync interrupted anywhere is repeated by the next one.

## Safety nets

- **Backups.** Before a sync replaces this computer's bindings, they are copied to
  `sync/backups/<date and time>/` in the settings folder. The newest ten are kept.
- **A sync that would remove most of your shortcuts stops and asks.** When the merge would leave
  fewer than half of what either side has, and that side has ten or more, nothing is changed and
  the Sync page says so. **Sync Anyway** goes ahead, if that is what you meant. An emptied or
  damaged file on one computer would otherwise be carried to every other one.
- **A shortcut changed while a sync runs is not overwritten.** If the sync was about to replace
  your bindings, it stops without changing anything and tries again half a minute later.

## Signing in

Termina signs in through git, with your credential helper or SSH key, and stores no password of its
own. For GitHub over `https://`, `gh auth setup-git` makes the GitHub CLI git's credential helper.

- **An automatic sync never prompts.** It runs git with no terminal, no password dialog, and a
  credential helper told not to ask, so a repository that needs a sign-in fails with git's message
  on the Sync page instead of waiting on a prompt nobody can see. A sync you start, by connecting
  or with Sync Now, can use your credential helper's own prompt.
- **A URL with a password or token in it is refused**, such as `https://me:TOKEN@github.com/…` or
  `https://ghp_…@github.com/…`. It would sit in plain text in `settings.properties` and in the
  clone's configuration. Connect offers the URL without it. If one was written into the settings
  file by hand, the Sync page says that it is not syncing, with **Remove from URL**. An SSH user
  name, as in `git@github.com:…`, is not a secret and is accepted.

### Several GitHub CLI accounts

With two GitHub accounts and `gh auth switch` between them, the credential helper hands git the
token of whichever account is active, so a repository of your personal account stops answering
while the work account is active.

For an `https://` repository on a host the GitHub CLI has accounts for, sync finds out which of
them can read it, trying the one it remembers first, then the active one, then the rest, and
remembers the first that is let in. From then on it signs in as that account whichever one is active, and the
Sync page names it. Termina never sees the token: git asks `gh` for it. If the repository stops
answering, the next sync looks again. This needs GitHub CLI 2.81 or newer. An SSH URL signs in
exactly as your git does.

## A settings folder inside a dotfiles repository

If the settings folder is inside a Git repository of yours, as in a dotfiles setup, the sync
clone would sit inside that repository's work tree, and `git add -A` there would pick it up. Unless
that repository ignores `sync/`, sync refuses rather than nest it, and says why. Add `sync/` to its
`.gitignore` and sync again; the check is made on every sync.

## Where it is kept

In `settings.properties`, the `sync.*` keys: `sync.repository`, `sync.branch` (`main`),
`sync.auto` (`true`) and `sync.intervalMinutes` (15, from 5 to 1440), with `sync.enabled` set by
Connect and Disconnect. [Reset to Defaults](/docs/settings#reset-to-defaults) leaves them alone,
so sync stays connected.

In the settings folder, `sync/` holds the clone (`sync/repo`), the backups (`sync/backups`) and
the lock that keeps a second Termina from syncing.
