/// Generator for src/content/docs/pauta.md, the user guide's Pauta conformance page.
/// A JDK 25 compact source file (JEP 512): run with
///
///   java scripts/GenPauta.java [/path/to/Termina] [--ref=v0.15.0]
///
/// from the website repo root. It reads the Termina app's pauta.json (the rules of its Pauta
/// edition it does not meet, each with a reason and a link) and the pauta.version of its pom.xml,
/// and writes the page, so the page cannot list a different set from the app (pauta-project.dev,
/// Conformance: PAUTA-13, PAUTA-14). The output is committed, like commands.ts: the site build
/// needs no access to the Termina repo (it isn't checked out in CI).
///
/// The checkout is the argument, else $TERMINA_REPO, else ../Termina, as for GenCommands.java,
/// and the two files are read at a git ref, not from the working tree: the newest vX.Y.Z tag by
/// default, or --ref=<tag|branch|HEAD>. A release before 0.15.0 has no pauta.json to read.
/// Ported from editora-website's GenPauta.java, which reads the working tree instead.
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

static final String PAUTA_JSON = "src/main/resources/com/termina/pauta.json";

/// One rule the app does not meet as written, as pauta.json records it.
record Entry(String rule, String level, String url, String kind, String reason, String link) {}

/// The kinds in the order the app's Pauta window lists them: what is still owed first.
static final List<String> KINDS = List.of("pending", "deviation", "not-applicable");

// ---- a JSON reader just big enough for pauta.json (objects, arrays, strings, literals) ----

static final class Json {
    private final String s;
    private int i;

    Json(String s) {
        this.s = s;
    }

    Object value() {
        ws();
        char c = s.charAt(i);
        if (c == '{') return object();
        if (c == '[') return array();
        if (c == '"') return string();
        int start = i;
        while (i < s.length() && ",}] \t\r\n".indexOf(s.charAt(i)) < 0) i++;
        String literal = s.substring(start, i);
        return switch (literal) {
            case "true" -> Boolean.TRUE;
            case "false" -> Boolean.FALSE;
            case "null" -> null;
            default -> Double.valueOf(literal);
        };
    }

    private Map<String, Object> object() {
        Map<String, Object> map = new LinkedHashMap<>();
        i++; // {
        ws();
        if (s.charAt(i) == '}') {
            i++;
            return map;
        }
        while (true) {
            ws();
            String key = string();
            ws();
            expect(':');
            map.put(key, value());
            ws();
            if (s.charAt(i) == ',') {
                i++;
                continue;
            }
            expect('}');
            return map;
        }
    }

    private List<Object> array() {
        List<Object> list = new ArrayList<>();
        i++; // [
        ws();
        if (s.charAt(i) == ']') {
            i++;
            return list;
        }
        while (true) {
            list.add(value());
            ws();
            if (s.charAt(i) == ',') {
                i++;
                continue;
            }
            expect(']');
            return list;
        }
    }

    private String string() {
        expect('"');
        StringBuilder out = new StringBuilder();
        while (s.charAt(i) != '"') {
            char c = s.charAt(i++);
            if (c != '\\') {
                out.append(c);
                continue;
            }
            char e = s.charAt(i++);
            switch (e) {
                case 'n' -> out.append('\n');
                case 't' -> out.append('\t');
                case 'r' -> out.append('\r');
                case 'b' -> out.append('\b');
                case 'f' -> out.append('\f');
                case 'u' -> {
                    out.append((char) Integer.parseInt(s.substring(i, i + 4), 16));
                    i += 4;
                }
                default -> out.append(e); // \" \\ \/
            }
        }
        i++; // closing quote
        return out.toString();
    }

    private void ws() {
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
    }

    private void expect(char c) {
        if (s.charAt(i) != c) {
            throw new IllegalStateException("pauta.json: expected '" + c + "' at offset " + i);
        }
        i++;
    }
}

@SuppressWarnings("unchecked")
List<Entry> readExceptions(String json) {
    Map<String, Object> root = (Map<String, Object>) new Json(json).value();
    List<Entry> entries = new ArrayList<>();
    for (Object o : (List<Object>) root.getOrDefault("exceptions", List.of())) {
        Map<String, Object> e = (Map<String, Object>) o;
        Entry entry = new Entry(
            text(e, "rule"), text(e, "level"), text(e, "url"), text(e, "kind"), text(e, "reason"), (String) e.get("link"));
        if (!KINDS.contains(entry.kind())) {
            // app-only is a template repository's alone; anything else is a typo. Either way the
            // page has no heading for it, so say so instead of leaving the rule out.
            throw new IllegalStateException(entry.rule() + ": this page has no section for the kind \"" + entry.kind() + "\"");
        }
        entries.add(entry);
    }
    return entries;
}

String text(Map<String, Object> e, String field) {
    Object v = e.get(field);
    if (!(v instanceof String s) || s.isBlank()) {
        throw new IllegalStateException("pauta.json: an exception has no " + field + ": " + e);
    }
    return s;
}

// ---- the conformance, as the app's own helper computes it (PautaConformance.of) ----

/// Partial when any MUST is pending, Core when any SHOULD is pending, Complete otherwise.
String conformance(List<Entry> exceptions) {
    boolean should = false;
    for (Entry e : exceptions) {
        if (!e.kind().equals("pending")) continue;
        if (e.level().equals("MUST")) return "Partial";
        if (e.level().equals("SHOULD")) should = true;
    }
    return should ? "Core" : "Complete";
}

long pending(List<Entry> exceptions, String level) {
    return exceptions.stream().filter(e -> e.kind().equals("pending") && e.level().equals(level)).count();
}

// ---- the page ----

String cell(String s) {
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("|", "\\|");
}

/// "#212" for an issue, "Decision 0007" for a decision record, else "Details".
String linkText(String link) {
    Matcher issue = Pattern.compile("/issues/(\\d+)$").matcher(link);
    if (issue.find()) return "#" + issue.group(1);
    Matcher decision = Pattern.compile("/docs/decisions/(\\d+)-").matcher(link);
    if (decision.find()) return "Decision " + decision.group(1);
    return "Details";
}

String plural(long n, String one, String many) {
    return n == 1 ? "1 " + one : n + " " + many;
}

String kindTitle(String kind) {
    return switch (kind) {
        case "pending" -> "Pending";
        case "deviation" -> "Deviations";
        default -> "Not applicable";
    };
}

String kindIntro(String kind) {
    return switch (kind) {
        case "pending" -> "Rules Termina does not meet yet. Each one links the issue that tracks it.";
        case "deviation" ->
            "Recommendations Termina follows differently on purpose. Each one links the decision record that says why.";
        default -> "Rules that read as applying to every app but whose subject does not exist in Termina.";
    };
}

String page(String edition, List<Entry> exceptions) {
    String conformance = conformance(exceptions);
    String base = "https://pauta-project.dev/" + edition + "/";
    long musts = pending(exceptions, "MUST");
    long shoulds = pending(exceptions, "SHOULD");
    String opening = conformance.equals("Partial") ? "is working toward" : "is built to";
    String meaning = switch (conformance) {
        case "Partial" ->
            "Termina does not meet every MUST of this edition yet: " + (musts == 1 ? "1 is" : musts + " are") + " pending.";
        case "Core" ->
            "Termina meets every MUST of this edition; " + (shoulds == 1 ? "1 SHOULD is" : shoulds + " SHOULDs are")
                + " pending.";
        default -> "Termina meets every MUST and SHOULD of this edition.";
    };

    StringBuilder md = new StringBuilder();
    md.append("---\n");
    md.append("title: Pauta conformance\n");
    md.append("description: The edition of Pauta, the guidelines Termina's family of apps follows, that Termina ")
        .append(conformance.equals("Partial") ? "is working toward" : "is built to")
        .append(", how fully it conforms, and every rule it does not meet as written.\n");
    md.append("category: Help\n");
    md.append("order: 13\n"); // after Troubleshooting and the FAQ, the last of Help
    md.append("---\n\n");
    md.append("<!-- AUTO-GENERATED by scripts/GenPauta.java from Termina's pauta.json and pom.xml. Do not edit by hand. -->\n\n");

    md.append("[Pauta](https://pauta-project.dev/) is the set of guidelines Termina and its sibling apps follow, so they\n")
        .append("look and work the same on Windows, macOS and Linux. Each rule has an ID and a level: a **MUST** is\n")
        .append("required, a **SHOULD** is expected unless there is a stated reason.\n\n");
    md.append("Termina ").append(opening).append(" **[Pauta ").append(edition).append("](").append(base).append(")**.\n\n");
    md.append("**Pauta conformance: ").append(conformance).append(".** ").append(meaning).append("\n\n");
    md.append("| Conformance | Means |\n|---|---|\n");
    md.append("| Complete | Every MUST and every SHOULD is met, does not apply, or is a recorded deviation. |\n");
    md.append("| Core | Every MUST is met; one or more SHOULDs are still pending. |\n");
    md.append("| Partial | One or more MUSTs are still pending. The app says *Working toward*, not *Built to*. |\n\n");
    md.append("The conformance is worked out from the list below, never typed in, so it cannot claim more than the list\n")
        .append("shows. The same list is in the app: **Help → Pauta Conformance** opens it, and **About** names the edition\n")
        .append("and the conformance.\n");

    if (exceptions.isEmpty()) {
        md.append("\n## Exceptions\n\nNone: Termina meets every MUST and SHOULD of Pauta ").append(edition).append(".\n");
        return md.toString();
    }

    Comparator<Entry> order = Comparator.<Entry>comparingInt(e -> e.level().equals("MUST") ? 0 : e.level().equals("SHOULD") ? 1 : 2)
        .thenComparing(Entry::rule);
    for (String kind : KINDS) {
        List<Entry> group = exceptions.stream().filter(e -> e.kind().equals(kind)).sorted(order).toList();
        if (group.isEmpty()) continue;
        long m = group.stream().filter(e -> e.level().equals("MUST")).count();
        md.append("\n## ").append(kindTitle(kind)).append("\n\n");
        md.append(kindIntro(kind)).append(' ');
        md.append(plural(group.size(), "rule", "rules")).append(": ").append(m).append(" MUST, ")
            .append(group.size() - m).append(" SHOULD.\n\n");
        boolean linked = group.stream().anyMatch(e -> e.link() != null);
        md.append(linked ? "| Rule | Level | What is different | Link |\n|---|---|---|---|\n" : "| Rule | Level | What is different |\n|---|---|---|\n");
        for (Entry e : group) {
            // The ID in monospace, as in the app's Pauta window. An HTML link rather than a Markdown one,
            // for the nowrap: the narrow first column otherwise breaks every ID at its hyphen.
            md.append("| <a href=\"").append(e.url()).append("\" style=\"white-space: nowrap\"><code>").append(e.rule())
                .append("</code></a> | ").append(e.level()).append(" | ")
                .append(cell(e.reason()));
            if (linked) {
                md.append(" | ");
                if (e.link() != null) md.append('[').append(linkText(e.link())).append("](").append(e.link()).append(')');
            }
            md.append(" |\n");
        }
    }
    return md.toString();
}

// ---- reading the app's files at a git ref, as GenCommands.java does ----

static String show(Path repo, String ref, String path) throws IOException, InterruptedException {
    Process p = new ProcessBuilder("git", "-C", repo.toString(), "show", ref + ":" + path).start();
    String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    String err = new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
    if (p.waitFor() != 0) throw new IllegalStateException("git show " + ref + ":" + path + " failed: " + err.strip());
    return out;
}

static String newestTag(Path repo) throws IOException, InterruptedException {
    Process p = new ProcessBuilder("git", "-C", repo.toString(), "tag", "--list", "v*", "--sort=-v:refname").start();
    String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    p.waitFor();
    for (String tag : out.lines().toList()) {
        if (tag.matches("v\\d+\\.\\d+\\.\\d+")) return tag;
    }
    throw new IllegalStateException("no vX.Y.Z tag in " + repo + "; pass --ref=");
}

void main(String[] args) throws IOException, InterruptedException {
    String ref = null;
    String path = null;
    for (String a : args) {
        if (a.startsWith("--ref=")) ref = a.substring("--ref=".length());
        else path = a;
    }
    String env = System.getenv("TERMINA_REPO");
    Path repo = Path.of(path != null ? path : env != null && !env.isBlank() ? env : "../Termina")
        .toAbsolutePath()
        .normalize();
    if (!Files.isDirectory(repo.resolve(".git")) && !Files.isRegularFile(repo.resolve(".git"))) {
        throw new IllegalStateException("No Termina checkout at " + repo + " (pass its path, or set TERMINA_REPO)");
    }
    if (ref == null) ref = newestTag(repo);

    Matcher m = Pattern.compile("<pauta\\.version>([^<]+)</pauta\\.version>").matcher(show(repo, ref, "pom.xml"));
    if (!m.find()) throw new IllegalStateException("pom.xml at " + ref + " defines no pauta.version");
    String edition = m.group(1).strip();

    List<Entry> exceptions = readExceptions(show(repo, ref, PAUTA_JSON));
    for (Entry e : exceptions) {
        // Every rule links its own edition's page: an entry at another address is a stale copy.
        if (!e.url().startsWith("https://pauta-project.dev/" + edition + "/")) {
            throw new IllegalStateException(e.rule() + " links " + e.url() + ", not Pauta " + edition);
        }
    }

    Path out = Path.of("src/content/docs/pauta.md");
    if (!Files.isDirectory(out.getParent())) {
        throw new IllegalStateException("run it from the website's root: no " + out.getParent() + " here");
    }
    Files.writeString(out, page(edition, exceptions), StandardCharsets.UTF_8);
    System.out.println("Wrote " + out + ": Pauta " + edition + ", " + conformance(exceptions) + ", "
        + exceptions.size() + " exceptions (from " + repo + " at " + ref + ")");
}
