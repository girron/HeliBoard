# Octopus Keyboard testing and regression checklist

> Development tracking, **not** a claim that the current branch passed. GitHub Actions build success, unit-test success, and on-device behavior are separate checks.

## Known development concerns

- Prediction availability varies by app and input field, notably Gemini and browser address/search fields.
- Input-field handling changes have previously affected Brave Enter/Search behavior.
- Autocorrection and backspace/undo behavior need regression testing whenever prediction lookup is changed.
- Beta branding, upgrade compatibility, and signing remain under review.

See [GitHub Issues](https://github.com/girron/Octopus-Keyboard/issues) for formal reports. The observations below summarize prior *manual* test sessions rather than certifying a public release.

## Historical manual test snapshots

| Build | Brave Enter | Gemini per-key predictions | Brave split spacebar | `ok` stays `ok` | Backspace/autocorrect crash |
| --- | --- | --- | --- | --- | --- |
| Octopus23 | Pass | Fail | Pass | Pass | Pass |
| Octopus24 | Fail | Pass | Pass | Pass | Fail |
| Octopus25 | Not yet verified | Not yet verified | Not yet verified | Not yet verified | Not yet verified |

Octopus24 also showed autocorrection not occurring in an additional Brave typing check. **Do not infer that a later source change passed until it is tested on-device.**

## Five core device tests

1. **Brave omnibox Enter:** Enter a search/query; pressing the displayed Search/Go key should perform the intended action rather than insert an unexpected newline.
2. **Gemini predictions:** In Gemini's ordinary prompt field, type a word prefix and verify Octopus's predictions appear on the keys.
3. **Brave split spacebar:** Open a Brave address/search field and verify the expected split-spacebar layout.
4. **Keep `ok` unchanged:** Type `ok` followed by a space and ensure it does not incorrectly autocorrect.
5. **Backspace/autocorrect regression:** Trigger an autocorrection, then use Backspace to undo it; verify correct text and no crash.

Also test punctuation after manually typed and predicted words (`.`, `?`, `!`), sentence capitalization, newlines, and prediction recovery after a line break. Repeat in both ordinary editors and browser fields.

## Before approving an APK

- Compare proposed commit(s) against the previous reviewed commit; identify unrelated files and behavior changes.
- Ensure the unit-test workflow runs against the **exact commit SHA** and finishes successfully. A failed workflow or a missing report is not a passing test.
- Verify the APK-build workflow succeeds and identify the APK's branch, version, signing identity, and commit.
- Run the manual device checklist and record results separately from automated tests.
- For a beta release, verify installation and updates without unexpected data loss, and publish clear known issues.

## Report format

Include **build**, **branch**, **commit SHA**, **phone/Android version**, **app and field**, **steps**, **expected**, **actual**, and **crash/log details** if available (redacted).

[← Project homepage](README.md) · [Install guide](INSTALL.md) · [Changelog](CHANGELOG.md)
