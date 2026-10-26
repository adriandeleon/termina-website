// Hand-written highlights for the home page's "What's new" block. Update them with each release,
// from that release's news post. `next` is what is in development and not in a release yet: keep
// it to what has landed in the repository, and empty it as each item ships.

export type NewsItem = { title: string; detail: string };

export const whatsNew: NewsItem[] = [
  { title: "Termina 0.11.0", detail: "Out for macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and ARM64), each installer with its own Java runtime." },
  { title: "Split panes", detail: "Another shell beside or below the one you are in, in the same directory. Maximize one, move it to another tab, and reopen a closed split tab as it was." },
  { title: "Find in the scrollback", detail: "<kbd>Ctrl+Shift+F</kbd> (<kbd>Cmd+F</kbd> on a Mac): every match highlighted as you type, smart case, regular expressions." },
  { title: "Shell integration", detail: "Jump between prompts, select the last command's output, and a mark beside a command that failed (OSC 133)." },
  { title: "Every shortcut rebindable", detail: "A Keyboard page in Settings, and the bindings synced between computers through a Git repository of yours." },
  { title: "What programs ask for", detail: "Declared links, the clipboard over SSH, desktop notifications, the kitty keyboard protocol, and type-ahead on a slow SSH link." },
  { title: "Themes from other terminals", detail: "Import iTerm2, Terminal, Windows Terminal, Ghostty, kitty and Alacritty schemes, and give a profile its own font and theme." },
];

export const next: NewsItem[] = [];
