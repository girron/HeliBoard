# Octopus Keyboard changelog

This is a development history, **not** a list of publicly released beta versions. A change appearing in source does not mean it passed automated or device tests.

## Unreleased — Octopus25 development

- Iterating on Brave Enter/Search action handling after an Octopus24 regression.
- Adjusting prediction-only lookup behavior to avoid unintended autocorrection or learning; adding regression tests.
- Unit-test workflow remains a release gate. Runs [37831598638](https://github.com/girron/Octopus-Keyboard/actions/runs/37831598638) and [37832465620](https://github.com/girron/Octopus-Keyboard/actions/runs/37832465620) failed; the latter ran tests but encountered a common Robolectric SDK initialization exception. These failures **do not establish a passing test suite**.
- No public Octopus25 beta has been verified.

## Octopus24 — development testing

- Expanded prediction lookup for general text editors, including the Gemini field.
- Manual testing: Gemini predictions passed; Brave Enter and the backspace/autocorrect regression failed; Brave split spacebar and preserving `ok` passed.

## Octopus23 — development testing

- Manual testing: four of five checks passed—Brave Enter, Brave split spacebar, preserving `ok`, and the backspace/autocorrect crash regression.
- Gemini per-key predictions remained unavailable.

## Documentation

- Added the project origin story crediting the original iOS Octopus Keyboard jailbreak tweak and Crimson Keyboard.
- Updated the homepage introduction and highlighted locally processed, offline dictionary predictions.

For ongoing verification use [TESTING.md](TESTING.md). For upstream HeliBoard history, see the [upstream repository](https://github.com/HeliBorg/HeliBoard).

[← Project homepage](README.md)
