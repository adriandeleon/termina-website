// Generates src/lib/commands.ts (the Keybindings page, the home page's keys, the palette demo) and
// src/lib/version.ts from a Termina checkout: the menu actions in TerminalWindow.buildMenuBar(),
// their labels in messages.properties and their chords, for both platform branches.
//
//   java scripts/GenCommands.java [/path/to/Termina] [--ref=v0.10.0]
//
// The checkout is the argument, else $TERMINA_REPO, else ../Termina. Run it from the site's root.
// The files are read at a git ref, not from the working tree, so the site describes a release
// whatever branch the checkout is on: the newest vX.Y.Z tag by default, or --ref=<tag|branch|HEAD>.
// A JDK 25 compact source file (JEP 512): no build step, nothing outside java.base.

static final Path OUT = Path.of("src/lib");

static final String WINDOW = "src/main/java/com/termina/ui/TerminalWindow.java";
static final String CATALOGUE = "src/main/resources/com/termina/i18n/messages.properties";

// Which group of the Keybindings page each command sits in, in the page's order: the menu it lives
// in. The source builds the View menu's actions before the menu and registers some from loops, so
// position cannot answer this reliably. An id missing here lands in "Other" and is reported; ids
// listed here but absent at the ref (commands of a later release) are skipped quietly.
static final LinkedHashMap<String, List<String>> GROUPS = groups();

// A compact source file has no static initialiser blocks, hence the method.
static LinkedHashMap<String, List<String>> groups() {
    var GROUPS = new LinkedHashMap<String, List<String>>();
    GROUPS.put(
            "File",
            List.of(
                    "newTab",
                    "newTabProfileNumber",
                    "newWindow",
                    "closeTab",
                    "closeWindow",
                    "reopenClosedTab",
                    "syncNow",
                    "settings"));
    GROUPS.put(
            "Edit",
            List.of(
                    "copy",
                    "paste",
                    "selectAll",
                    "clearScrollback",
                    "find",
                    "findNext",
                    "findPrevious",
                    "previousPrompt",
                    "nextPrompt",
                    "selectLastOutput"));
    GROUPS.put(
            "View",
            List.of("zoomIn", "zoomOut", "actualSize", "fullScreen", "hideMenuBar", "commandPalette"));
    GROUPS.put(
            "Window",
            List.of(
                    "nextTab",
                    "previousTab",
                    "goToTab",
                    "moveTabLeft",
                    "moveTabRight",
                    "renameTab",
                    "duplicateTab",
                    "splitRight",
                    "splitDown",
                    "focusPane",
                    "maximizePane",
                    "movePaneToNewTab"));
    GROUPS.put("Help", List.of("keyboardShortcuts", "about", "checkForUpdates"));
    return GROUPS;
}

/** One command: what the menu calls it, and its chord off macOS and on it (null for none). */
record Cmd(String id, String title, List<String> pc, List<String> mac) {}

/** A chord expression evaluated for both platform branches. */
record Pair(List<String> pc, List<String> mac) {
    static final Pair NONE = new Pair(null, null);
}

void main(String[] args) throws IOException, InterruptedException {
    String repoArg = null;
    String ref = null;
    for (String a : args) {
        if (a.startsWith("--ref=")) ref = a.substring("--ref=".length());
        else repoArg = a;
    }
    Path repo = Path.of(repoArg != null
                    ? repoArg
                    : Objects.requireNonNullElse(System.getenv("TERMINA_REPO"), "../Termina"))
            .toAbsolutePath()
            .normalize();
    if (!Files.exists(repo.resolve(".git"))) {
        System.err.println("No Termina checkout at " + repo + " (pass its path, or set TERMINA_REPO)");
        System.exit(1);
    }
    if (ref == null) ref = newestTag(repo);

    Properties messages = new Properties();
    messages.load(new StringReader(show(repo, ref, CATALOGUE)));
    String source = show(repo, ref, WINDOW);
    int start = source.indexOf("private MenuBar buildMenuBar()");
    int end = source.indexOf("bar.getMenus().addAll(", start);
    if (start < 0 || end < 0) throw new IllegalStateException("buildMenuBar() not found in " + WINDOW + " at " + ref);
    String body = source.substring(start, end);

    Map<String, Cmd> byId = new LinkedHashMap<>();
    for (int at = body.indexOf("item("); at >= 0; at = body.indexOf("item(", at + 1)) {
        // item( must be a call of its own, not the tail of another name (toMenuItem, MenuItem).
        if (at > 0 && Character.isJavaIdentifierPart(body.charAt(at - 1))) continue;
        List<String> a = arguments(body, at + "item(".length());
        String key = unquote(a.get(0));
        if (key == null || !key.startsWith("menu.")) continue;
        String id = key.substring("menu.".length());
        Pair chord = a.size() >= 3 ? chord(a.get(1)) : Pair.NONE;
        byId.putIfAbsent(id, new Cmd(id, label(messages, key), chord.pc(), chord.mac()));
    }

    // About is built with MenuAction.of and the application's name rather than item().
    if (body.contains("\"menu.about\"")) byId.put("about", new Cmd("about", label(messages, "menu.about"), null, null));

    // The palette's chord is the user's choice, bound last; until one is chosen it is the default.
    Cmd palette = byId.get("commandPalette");
    if (palette != null && palette.pc() == null) {
        Pair d = chord("MenuAction.DEFAULT_PALETTE_CHORD");
        byId.put("commandPalette", new Cmd(palette.id(), palette.title(), d.pc(), d.mac()));
    }

    // Hide Menu Bar is not offered on macOS, where the menus are in the screen menu bar.
    Cmd hide = byId.get("hideMenuBar");
    if (hide != null && body.contains("offersMenuBarToggle(")) {
        byId.put("hideMenuBar", new Cmd(hide.id(), hide.title(), hide.pc(), null));
    }

    // Chords registered from loops, as one row each: a profile by number, a tab by number.
    if (body.contains("menu.newTabProfileNumber") && body.contains("shiftChord(digitKey(")) {
        byId.put(
                "newTabProfileNumber",
                new Cmd(
                        "newTabProfileNumber",
                        label(messages, "menu.newTabProfileNumber").replace("Termina", "1–9"),
                        List.of("Ctrl", "Shift", "1…9"),
                        List.of("Shift", "Cmd", "1…9")));
    }
    if (body.contains("MenuAction.tabChord(")) {
        byId.put(
                "goToTab",
                new Cmd(
                        "goToTab",
                        label(messages, "menu.goToTab").replace("Termina", "1–9") + " (9 is the last)",
                        List.of("Alt", "1…9"),
                        List.of("Cmd", "1…9")));
    }

    List<String> warnings = new ArrayList<>();
    Set<String> grouped = new HashSet<>();
    StringBuilder ts = new StringBuilder();
    ts.append("// AUTO-GENERATED by scripts/GenCommands.java — do not edit by hand.\n")
            .append("// Source: TerminalWindow.buildMenuBar() and messages.properties in the Termina repository, at ")
            .append(ref)
            .append(".\n")
            .append("// Re-run the script after a release, or after a command or a chord changes.\n\n")
            .append("// A chord is its keys in order, given separately for Linux and Windows (pc) and for macOS\n")
            .append("// (mac), because Termina's application chords are Ctrl+Shift+key off macOS and Cmd+key on it.\n")
            .append("// Modifiers are Ctrl, Shift and Alt on pc; Ctrl, Option, Shift and Cmd on mac. null: none.\n")
            .append("export type Chord = string[];\n")
            .append("export type Cmd = { id: string; title: string; pc: Chord | null; mac: Chord | null };\n")
            .append("export type CmdGroup = { title: string; commands: Cmd[] };\n\n")
            .append("export const commandGroups: CmdGroup[] = [\n");
    for (var group : GROUPS.entrySet()) {
        List<Cmd> cmds = new ArrayList<>();
        for (String id : group.getValue()) {
            Cmd c = byId.get(id);
            if (c == null) continue;
            cmds.add(c);
            grouped.add(id);
        }
        appendGroup(ts, group.getKey(), cmds);
    }
    List<Cmd> other =
            byId.values().stream().filter(c -> !grouped.contains(c.id())).toList();
    for (Cmd c : other) warnings.add("not in any group, listed under Other: " + c.id());
    if (!other.isEmpty()) appendGroup(ts, "Other", other);
    ts.append("];\n");

    // Two commands on one chord: the window's filter runs the first and never reaches the second.
    for (boolean mac : new boolean[] {false, true}) {
        Map<List<String>, List<String>> owners = new LinkedHashMap<>();
        for (Cmd c : byId.values()) {
            List<String> k = mac ? c.mac() : c.pc();
            if (k != null) owners.computeIfAbsent(k, x -> new ArrayList<>()).add(c.id());
        }
        owners.forEach((k, ids) -> {
            if (ids.size() > 1)
                warnings.add((mac ? "macOS" : "pc") + " chord " + String.join("+", k) + " is on " + String.join(", ", ids));
        });
    }

    Files.createDirectories(OUT);
    Files.writeString(OUT.resolve("commands.ts"), ts.toString());
    String version = version(repo, ref);
    Files.writeString(
            OUT.resolve("version.ts"),
            "// AUTO-GENERATED by scripts/GenCommands.java — do not edit by hand.\n"
                    + "// Source: Termina's pom.xml <version> at " + ref + ", or the newest released CHANGELOG.md\n"
                    + "// section while the pom is on a -SNAPSHOT between releases.\n\n"
                    + "export const version = \"" + version + "\";\n");

    System.out.println("commands.ts: " + byId.size() + " commands; version.ts: " + version + " (from " + repo + " at "
            + ref + ")");
    for (String w : warnings) System.err.println("warning: " + w);
}

static void appendGroup(StringBuilder ts, String title, List<Cmd> cmds) {
    if (cmds.isEmpty()) return;
    ts.append("  {\n    title: ").append(js(title)).append(",\n    commands: [\n");
    for (Cmd c : cmds) {
        ts.append("      { id: ")
                .append(js(c.id()))
                .append(", title: ")
                .append(js(c.title()))
                .append(", pc: ")
                .append(chordJs(c.pc()))
                .append(", mac: ")
                .append(chordJs(c.mac()))
                .append(" },\n");
    }
    ts.append("    ],\n  },\n");
}

static String chordJs(List<String> chord) {
    return chord == null ? "null" : "[" + String.join(", ", chord.stream().map(x -> js(x)).toList()) + "]";
}

// --- reading the source at a ref ---

static String show(Path repo, String ref, String path) throws IOException, InterruptedException {
    Process p = new ProcessBuilder("git", "-C", repo.toString(), "show", ref + ":" + path).start();
    String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    String err = new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
    if (p.waitFor() != 0) throw new IllegalStateException("git show " + ref + ":" + path + " failed: " + err.strip());
    return out;
}

/** The newest vX.Y.Z tag, by version rather than by name ("v0.10.0" after "v0.9.0"). */
static String newestTag(Path repo) throws IOException, InterruptedException {
    Process p = new ProcessBuilder("git", "-C", repo.toString(), "tag", "--list", "v*", "--sort=-v:refname").start();
    String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    p.waitFor();
    return out.lines()
            .filter(t -> t.matches("v\\d+\\.\\d+\\.\\d+"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("no release tag in " + repo));
}

/** The top-level arguments of the call whose argument list starts at {@code from}. */
static List<String> arguments(String s, int from) {
    List<String> out = new ArrayList<>();
    int depth = 0;
    boolean inString = false;
    StringBuilder cur = new StringBuilder();
    for (int i = from; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (inString) {
            cur.append(ch);
            if (ch == '\\') cur.append(s.charAt(++i));
            else if (ch == '"') inString = false;
            continue;
        }
        switch (ch) {
            case '"' -> {
                inString = true;
                cur.append(ch);
            }
            case '(', '{', '[' -> {
                depth++;
                cur.append(ch);
            }
            case ')', '}', ']' -> {
                if (depth == 0) {
                    out.add(cur.toString().strip());
                    return out;
                }
                depth--;
                cur.append(ch);
            }
            case ',' -> {
                if (depth == 0) {
                    out.add(cur.toString().strip());
                    cur.setLength(0);
                } else cur.append(ch);
            }
            default -> cur.append(ch);
        }
    }
    throw new IllegalStateException("unbalanced call at " + from);
}

static String label(Properties messages, String key) {
    String text = messages.getProperty(key);
    if (text == null) throw new IllegalStateException("no message for " + key);
    // The one argument a fixed label takes is the application's name (About {0}).
    return text.replace("{0}", "Termina");
}

// --- chords ---

static final Pattern TERNARY = Pattern.compile("^MAC\\s*\\?(.+?):(.+)$", Pattern.DOTALL);
static final Pattern HELPER = Pattern.compile("^MenuAction\\.(appChord|shiftChord)\\(KeyCode\\.(\\w+)\\)$");
static final Pattern COMBINATION =
        Pattern.compile("^new KeyCodeCombination\\(\\s*KeyCode\\.(\\w+)((?:\\s*,\\s*KeyCombination\\.\\w+)*)\\s*\\)$");

/**
 * A chord expression from the menu table, for both platforms. Only the forms the table uses are
 * understood, and anything else stops the script: a chord guessed wrong would be published.
 * macOS modifiers are listed in its own order, ⌃⌥⇧⌘, which is how its menus draw them.
 */
static Pair chord(String expr) {
    String e = expr.replaceAll("\\s+", " ").strip();
    if (e.equals("null")) return Pair.NONE;
    Matcher m = TERNARY.matcher(e);
    if (m.matches()) return new Pair(chord(m.group(2)).pc(), chord(m.group(1)).mac());
    m = HELPER.matcher(e);
    if (m.matches()) {
        String key = m.group(2);
        // MenuAction.appChord: Cmd+key on macOS, Ctrl+Shift+key elsewhere (Ctrl+key is the shell's).
        // MenuAction.shiftChord: Shift on both.
        return m.group(1).equals("appChord")
                ? new Pair(keys(key, "Ctrl", "Shift"), keys(key, "Cmd"))
                : new Pair(keys(key, "Ctrl", "Shift"), keys(key, "Shift", "Cmd"));
    }
    switch (e) {
        // MenuAction.closeWindowChord: Cmd+Shift+W on macOS, Ctrl+Shift+Q (GNOME Terminal's) elsewhere.
        case "MenuAction.closeWindowChord()" -> {
            return new Pair(keys("Q", "Ctrl", "Shift"), keys("W", "Shift", "Cmd"));
        }
        // TerminalWindow.fullScreenChord: F11, or Ctrl+Cmd+F on macOS, which gives F11 to the desktop.
        case "fullScreenChord()" -> {
            return new Pair(List.of("F11"), keys("F", "Ctrl", "Cmd"));
        }
        // Emacs's M-x; Alt is Option on a Mac.
        case "MenuAction.DEFAULT_PALETTE_CHORD" -> {
            return new Pair(keys("X", "Alt"), keys("X", "Option"));
        }
        default -> {}
    }
    m = COMBINATION.matcher(e);
    if (m.matches()) {
        String key = m.group(1);
        String mods = m.group(2);
        boolean shortcut = mods.contains("SHORTCUT_DOWN");
        boolean control = mods.contains("CONTROL_DOWN");
        boolean alt = mods.contains("ALT_DOWN");
        boolean shift = mods.contains("SHIFT_DOWN");
        boolean meta = mods.contains("META_DOWN");
        List<String> pc = new ArrayList<>();
        if (shortcut || control) pc.add("Ctrl");
        if (alt) pc.add("Alt");
        if (shift) pc.add("Shift");
        pc.add(keyName(key));
        List<String> mac = new ArrayList<>();
        if (control) mac.add("Ctrl");
        if (alt) mac.add("Option");
        if (shift) mac.add("Shift");
        if (shortcut || meta) mac.add("Cmd");
        mac.add(keyName(key));
        // META_DOWN alone is a Mac-only chord (the Windows key is the desktop's elsewhere).
        return new Pair(meta && !shortcut ? null : pc, mac);
    }
    throw new IllegalStateException("cannot read the chord in: " + expr);
}

static List<String> keys(String code, String... mods) {
    List<String> k = new ArrayList<>(List.of(mods));
    k.add(keyName(code));
    return k;
}

static String keyName(String code) {
    return switch (code) {
        case "COMMA" -> ",";
        case "EQUALS" -> "=";
        case "MINUS" -> "-";
        case "SLASH" -> "/";
        case "OPEN_BRACKET" -> "[";
        case "CLOSE_BRACKET" -> "]";
        case "UP" -> "Up";
        case "DOWN" -> "Down";
        case "LEFT" -> "Left";
        case "RIGHT" -> "Right";
        case "INSERT" -> "Insert";
        case "HOME" -> "Home";
        case "END" -> "End";
        case "ENTER" -> "Enter";
        default -> code.startsWith("DIGIT") ? code.substring(5) : code;
    };
}

static String unquote(String expr) {
    return expr.length() >= 2 && expr.startsWith("\"") && expr.endsWith("\"")
            ? expr.substring(1, expr.length() - 1)
            : null;
}

static String version(Path repo, String ref) throws IOException, InterruptedException {
    String pom = show(repo, ref, "pom.xml");
    // The project's own <version> is the first one, ahead of the dependency list.
    Matcher m = Pattern.compile("<version>([^<]+)</version>").matcher(pom);
    String v = m.find() ? m.group(1).strip() : "";
    if (!v.isEmpty() && !v.endsWith("-SNAPSHOT")) return v;
    Matcher released = Pattern.compile("(?m)^## (\\d+\\.\\d+\\.\\d+)\\b")
            .matcher(show(repo, ref, "CHANGELOG.md"));
    if (released.find()) return released.group(1);
    throw new IllegalStateException("no released version in pom.xml or CHANGELOG.md at " + ref);
}

static String js(String s) {
    return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
}
