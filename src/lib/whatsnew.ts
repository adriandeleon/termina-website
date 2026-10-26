// Hand-written highlights for the home page's "What's new" block. Update them with each release,
// from that release's news post. `next` is what is in development and not in a release yet: keep
// it to what has landed in the repository, and empty it as each item ships.

export type NewsItem = { title: string; detail: string };

export const whatsNew: NewsItem[] = [
  { title: "Termina 0.12.0", detail: "Out for macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and ARM64), each installer with its own Java runtime." },
  { title: "A sync repository in one click", detail: "With the GitHub CLI signed in, Settings → Sync can create the private repository and connect to it." },
  { title: "Split panes", detail: "Since 0.11.0: another shell beside or below the one you are in, in the same directory. Maximize one, move it to another tab, and reopen a closed split tab as it was." },
  { title: "Find in the scrollback", detail: "Since 0.11.0: every match highlighted as you type, smart case and regular expressions, on <kbd>Ctrl+Shift+F</kbd> (<kbd>Cmd+F</kbd> on a Mac)." },
  { title: "Shell integration", detail: "Since 0.11.0: jump between prompts, select the last command's output, and a mark beside a command that failed (OSC 133)." },
  { title: "Every shortcut rebindable", detail: "Since 0.11.0: a Keyboard page in Settings, and the bindings synced between computers through a Git repository of yours." },
  { title: "What programs ask for", detail: "Since 0.11.0: declared links, the clipboard over SSH, desktop notifications, the kitty keyboard protocol, and type-ahead on a slow SSH link." },
];

export const next: NewsItem[] = [];
