// Hand-written highlights for the home page's "What's new" block. Update them with each release,
// from that release's news post. `next` is what is in development and not in a release yet: keep
// it to what has landed in the repository, and empty it as each item ships.

export type NewsItem = { title: string; detail: string };

export const whatsNew: NewsItem[] = [
  { title: "Termina 0.13.0", detail: "Out for macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and ARM64), each installer with its own Java runtime. The <code>.deb</code> installs on Debian 12 and Ubuntu 22.04 again." },
  { title: "Padding and a hollow cursor", detail: "Space around the text, and a solid cursor only in the pane that takes your keys: the others draw it hollow." },
  { title: "Reopen a pane or a window", detail: "Reopen Closed Tab brings back the last thing you closed, whether a tab, a single pane or a whole window." },
  { title: "The palette on <kbd>Ctrl+Shift+P</kbd>", detail: "<kbd>Cmd+Shift+P</kbd> on a Mac, as in Windows Terminal and VS Code, leaving <code>M-x</code> to Emacs and zsh. A chord you chose yourself is kept." },
  { title: "Split panes", detail: "Since 0.11.0: another shell beside or below the one you are in, in the same directory. Maximize one, move it to another tab, and reopen a closed split tab as it was." },
  { title: "Every shortcut rebindable", detail: "Since 0.11.0: a Keyboard page in Settings, and the bindings synced between computers through a Git repository of yours." },
];

export const next: NewsItem[] = [];
