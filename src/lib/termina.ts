// The Termina release version is generated from the app's pom.xml, at the newest release tag, into
// version.ts by scripts/GenCommands.java and committed, so the build never reads the Termina
// repository (it isn't checked out in CI). Re-exported here so pages import everything from one place.
import { version } from "./version";

export { version };

// The user guide is served under its version (DOCS-03, Pauta 6.0.1): /docs/v-0.15.0/…, which is
// the address the app of that version opens. Bare /docs and every unversioned slug redirect here
// (astro.config.mjs), so a link written in a page never carries a version.
export const docsVersion = `v-${version}`;
export const docsBase = `/docs/${docsVersion}`;

export const repo = "https://github.com/adriandeleon/Termina";
export const latestRelease = `${repo}/releases/latest`;
