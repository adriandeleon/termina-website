---
title: The window that looked ready
description: "A terminal that opened, drew its prompt and a blinking cursor, and sent every keystroke somewhere else. Why it happened only on a cold launch, why opening a second tab seemed to fix it, and why the fix is a rule rather than a patch."
date: 2026-10-25
tags: [javafx, focus, debugging]
---

Early in Termina's life a bug report said nothing could be typed into the first window. The
window opened, the shell drew its prompt, the cursor blinked, and every key went nowhere. Opening
a second tab fixed it. So did nothing at all, most of the time: the report could not be
reproduced.

## Where the keys went

A JavaFX scene gives initial focus to the first node it finds that is *focus-traversable*, the
flag that says a control takes part in Tab navigation. Termina's terminal view asks for focus
explicitly when a tab opens. So does the scene, on its own schedule, and the first traversable node
in Termina's scene was not the terminal. It was the tab strip: a `TabPane` is focus-traversable by
default.

Two requests for focus, and whichever ran last won. On a warm start the terminal's request came
last. On a cold first launch, with a JVM to warm up and a login shell to start, the timing moved
and the tab strip won. The window looked exactly as it does when it is working, because nothing on
screen shows which node has the keyboard, and every keystroke went to the tab strip, which did
nothing with it.

Opening a second tab appeared to fix it for a reason that had nothing to do with the bug: by then
the strip already had focus, and selecting the new tab gave it away to the new terminal.

The first fix, in 0.2.0, was to make nothing in the window's chrome focus-traversable. A terminal
wants that anyway, because `Tab` belongs to the shell, not to navigation between controls.

## It came back

Then it came back, in other shapes. The flag stops a node being *traversed* to. It does not stop
anything calling `requestFocus`, which ignores the flag entirely. A click on the tab that is
already selected takes focus without changing the selection, so the code that hands focus to the
terminal after a tab switch never runs. The strip's empty space does the same. So does a drag that
reorders nothing, and the menu bar after its menu closes.

Each of those is its own path, and each presents identically: a window that looks ready and
swallows typing. Finding them one at a time is a game with no last move, since the next control
added to the window brings its own.

## A rule instead

So 0.3.0 stopped chasing paths. The terminal takes focus back whenever something else has it and
nothing has a claim on it. The rule is small enough to state as a function:

```java
static boolean shouldReclaim(boolean hasTerminal, boolean ownerIsTerminal, boolean overlayWantsKeys) {
    return hasTerminal && !ownerIsTerminal && !overlayWantsKeys;
}
```

The claim is the part that matters. The command palette has a text field, an open menu moves with
the arrow keys, and a context menu is a popup that closes when focus leaves it. Reclaiming under
any of those would break them, and the last would make right-click unusable: the menu would close
on its way to opening. While one of them is open, focus is left exactly where it is.

## Why nothing caught it

Every check Termina had at the time went *around* focus. The capture harness, which renders the
real window and types into the real shell, wrote its keystrokes straight into the pseudo-terminal,
and its mouse and key options fired events at the terminal view by name. All of them passed in a
window where nothing a person typed reached the shell, and so did the screenshot.

What finds this bug is an option that types at whatever owns the keyboard and reports whether the
shell received it, plus a line that prints which node the focus owner is. Both exist now, and a
check that "the terminal works" without going through focus is no longer counted as one.
