// Hand-written highlights for the home page's "What's new" block. Update them with each release,
// from that release's news post. `next` is what is in development and not in a release yet: keep
// it to what has landed in the repository, and empty it as each item ships.

export type NewsItem = { title: string; detail: string };

export const whatsNew: NewsItem[] = [
  { title: "Termina 0.10.0", detail: "Out for macOS (Apple Silicon and Intel), Windows (x64) and Linux (x64 and ARM64), each installer with its own Java runtime." },
  { title: "Icons in Settings", detail: "Each page of the settings sidebar has a glyph, muted at rest and in the accent colour when its page is open." },
  { title: "Editora's command palette", detail: "Since 0.9.0: the same matcher and ranking, Emacs keys to drive it, <kbd>Alt+X</kbd> to open it, and the chord is a setting." },
  { title: "The menu bar in the title bar", detail: "Since 0.8.0: one row for the menus, the title and the window buttons, on Linux and Windows." },
  { title: "Tabs that remember", detail: "Since 0.8.0: rename a tab, reopen one you closed, duplicate one where it is, and see a dot when a background tab has news." },
  { title: "A paste that asks", detail: "Since 0.8.0: a paste with a line break shows what it will send first, and control characters are stripped from every paste." },
];

export const next: NewsItem[] = [
  { title: "Split panes", detail: "Another shell beside or below the one you are in, in the same directory." },
  { title: "Find in the scrollback", detail: "Every match highlighted as you type, smart case, regular expressions." },
  { title: "Shell integration", detail: "Jump between prompts, select the last command's output, and a mark beside a command that failed (OSC 133)." },
  { title: "Every shortcut rebindable", detail: "A Keyboard page in Settings, and the bindings synced between computers through a Git repository of yours." },
  { title: "What programs ask for", detail: "Declared links (OSC 8), copying to the clipboard over SSH (OSC 52), and type-ahead on a slow SSH link." },
  { title: "Icons without a Nerd Font", detail: "Symbols Nerd Font Mono bundled, so <code>eza --icons</code> draws right on any machine." },
];
