#!/usr/bin/env bash
#
# Retakes the user guide's screenshots from the real window, at 2x, light and dark
# (DOCS-01, DOCS-18, Pauta 6.0.1).
#
#   scripts/screenshots.sh            # every picture
#   scripts/screenshots.sh panes tabs # only those
#
# Each picture is Termina's own window, drawn by its capture hook on JavaFX's headless platform:
# no window opens on the desktop and nothing of the desktop is in it. Nothing of your shell's
# set-up is either. The shell is bash with a start-up file written here, in a folder made here,
# with a home folder of its own and no history file, so the same Termina gives the same pictures
# on any machine.
#
# It needs a Termina checkout, built: TERMINA_REPO, else ../Termina. TERMINA_DEV_RUN names the
# launcher, one that takes -D options as Termina's scripts/dev-run.sh does (the default). Capture
# from a build of the release the site describes. Needs bash, and eza for the file-type icons;
# without eza the listings are plain `ls -l`.
#
# A picture is public/screenshots/docs/<name>.png in the light theme and <name>.dark.png in the
# dark one; a docs page shows it with ![alt](/screenshots/docs/<name>.png "caption") on a line of
# its own, and src/lib/rehype-doc-figures.mjs does the rest.
set -euo pipefail

cd "$(dirname "$0")/.."
site="$PWD"
repo="$(cd "${TERMINA_REPO:-../Termina}" && pwd)"
run="${TERMINA_DEV_RUN:-$repo/scripts/dev-run.sh}"
out="$site/public/screenshots/docs"
# A folder of a fixed name, since its path is in two of the pictures (the shell Settings and the
# Doctor name). Emptied first and removed after.
work="${TMPDIR:-/tmp}/termina-guide"
rm -rf "$work"
trap 'rm -rf "$work"' EXIT
mkdir -p "$out" "$work/home"

if command -v eza > /dev/null; then
  list='eza -l --icons=always --color=always --group-directories-first --no-user --no-time'
else
  list='ls -l --color=always --group-directories-first'
fi

# A folder to list: the same names, sizes and kinds every time.
project="$work/home/projects/atlas"
mkdir -p "$project"/{src,docs,tests,build}
printf '%s\n' '# Atlas' '' 'A small map tile server.' > "$project/README.md"
printf '%s\n' 'build:' '	cargo build --release' > "$project/Makefile"
printf '%s\n' '[package]' 'name = "atlas"' 'version = "0.4.2"' > "$project/Cargo.toml"
printf 'fn main() {\n    println!("atlas");\n}\n' > "$project/src/main.rs"
printf '%s\n' 'tiles served: 18204' 'warning: cache is 91% full' 'error: tile 14/8192/5461 missing' \
  'tiles served: 18377' 'warning: slow upstream (2.1 s)' 'tiles served: 18512' > "$project/build/server.log"
printf '%s\n' 'https://example.com/atlas/docs' 'see docs/tiles.md' > "$project/docs/links.txt"
printf '# Tiles\n' > "$project/docs/tiles.md"
chmod +x "$project/Makefile"

# What every shell starts with: a short prompt, the project folder, a clear screen, no history.
{
  printf '%s\n' 'unset HISTFILE'
  printf '%s\n' "PS1='\\[\\e[1;34m\\]\\W\\[\\e[0m\\] \\\$ '"
  printf 'cd %q\n' "$project"
  printf '%s\n' "alias ll='$list'" 'clear'
} > "$work/rc"
{
  printf '%s\n' '#!/bin/sh'
  printf 'exec bash --rcfile %q -i\n' "$work/rc"
} > "$work/shell"
chmod +x "$work/shell"

wanted=("$@")

# shot <name> <capture options…>: the picture in both themes.
shot() {
  local name="$1"
  shift
  if [ "${#wanted[@]}" -gt 0 ] && [[ ! " ${wanted[*]} " == *" $name "* ]]; then return; fi
  local theme file config
  for theme in editora-light editora-dark; do
    file="$out/$name.png"
    [ "$theme" = editora-dark ] && file="$out/$name.dark.png"
    config="$work/config-$name-$theme"
    mkdir -p "$config"
    printf '{\n  "schemaVersion" : 3,\n  "settings" : {\n    "terminal.shell" : "%s",\n    "theme" : "%s",\n    "updates.check" : "false"\n  }\n}\n' \
      "$work/shell" "$theme" > "$config/settings.json"
    rm -f "$file"
    env -u DISPLAY -u WAYLAND_DISPLAY HOME="$work/home" TERMINA_CONFIG_DIR="$config" \
      "$run" -Dglass.platform=Headless -Dprism.order=sw \
      -Dtermina.capture="$file" -Dtermina.captureScale=2 "$@" > "$work/log" 2>&1 || true
    [ -s "$file" ] || {
      echo "screenshots.sh: no picture for $name in $theme; the run said:" >&2
      tail -20 "$work/log" >&2
      exit 1
    }
    echo "wrote ${file#"$site"/}"
  done
}

# The window: two tabs, a listing with file-type icons and colour.
shot window -Dtermina.captureTabs=1 "-Dtermina.captureCommand=ll" -Dtermina.captureAfterCommandMs=1500

# Tabs: four, one of them named.
shot tabs -Dtermina.captureTabs=3 -Dtermina.captureRenameTab=server \
  "-Dtermina.captureCommand=ll" -Dtermina.captureAfterCommandMs=1500

# Panes: one tab split in three.
shot panes -Dtermina.capturePanes=right,down "-Dtermina.captureCommand=ll src docs" \
  -Dtermina.captureAfterCommandMs=1500

# The find bar, with its matches marked in the text.
shot search "-Dtermina.captureCommand=cat build/server.log" -Dtermina.captureAfterCommandMs=1500 \
  -Dtermina.captureSearch=warning

# The Command Palette, narrowed by a query.
shot palette "-Dtermina.captureCommand=ll" -Dtermina.captureAfterCommandMs=1500 -Dtermina.capturePalette=tab

# The Message Log, over the terminal.
shot message-log "-Dtermina.captureCommand=ll" -Dtermina.captureAfterCommandMs=1500 \
  -Dtermina.captureMessageLogFilter=

# Settings: its first page, the Keyboard page, the Profiles page and the Sync page.
shot settings -Dtermina.captureSettings=true
shot settings-keyboard "-Dtermina.captureSettings=new tab"
shot settings-profiles -Dtermina.captureSettings=profile
# "interval" and not "repository" or "sync": those match a command on the Keyboard page, which
# comes first, and the search opens the first page with a match.
shot settings-sync -Dtermina.captureSettings=interval

# The windows beside the terminal.
shot shortcuts -Dtermina.captureShortcuts=true
shot doctor -Dtermina.captureDoctor=true -Dtermina.captureAfterCommandMs=12000
shot pauta -Dtermina.capturePauta=true
