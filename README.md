# Octopus Keyboard 🐙

**Octopus Keyboard brings iOS jailbreak-inspired predictive typing to Android, displaying word suggestions directly on the keys rather than relying on a traditional suggestion bar.**

Octopus Keyboard is a work-in-progress fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard), focused on bringing **per-key word predictions** to a customizable, offline-capable Android keyboard.

**Private by design. Predictive by nature.** Octopus Keyboard runs its dictionary-based predictions locally on your device, with no internet connection required.

> **Project status: pre-release / private beta preparation.** Features and compatibility are still being tested. The Octopus name and icon are being integrated; current development APKs may still display HeliBoard branding.

[Report a bug](https://github.com/girron/Octopus-Keyboard/issues/new) · [View development builds](https://github.com/girron/Octopus-Keyboard/actions/workflows/build-debug-apk.yml) · [Source code](https://github.com/girron/Octopus-Keyboard) · [Upstream HeliBoard](https://github.com/HeliBorg/HeliBoard)

## What makes Octopus different?

Octopus puts dictionary-based **word predictions directly on individual keys**, rather than relying only on a traditional suggestion row. It is an experimental extension of [HeliBoard](https://github.com/HeliBorg/HeliBoard), retaining features such as autocorrection, custom themes and layouts, split keyboard, multilingual typing, emoji search, clipboard history, and settings backups.

Predictions run locally without an internet connection. Availability can still vary between apps and text fields, and compatibility is under active development. For inherited features, see the [HeliBoard wiki](https://github.com/HeliBorg/HeliBoard/wiki) and [layout documentation](layouts.md).

## Inspiration and origins

Octopus Keyboard for Android draws inspiration from two iOS keyboards that have shaped my typing experience.

The original **Octopus Keyboard** jailbreak tweak, developed by Mario Hros (K3A), brought BlackBerry 10-style predictive typing to the iPhone, displaying word suggestions directly on individual keys.

When Octopus stopped receiving updates, I purchased **Crimson Keyboard** upon its release and have continued using it ever since. Both keyboards have been central inspirations for bringing this distinctive predictive typing experience to Android.

Built on the open-source **HeliBoard** project, Octopus Keyboard for Android aims to recreate and refine that experience while retaining the flexibility and customization of a modern Android keyboard.

This is an independent project, unaffiliated with the original Octopus Keyboard, Crimson Keyboard, or HeliBoard developers.

## Getting started

**There is no approved public beta release yet.** Current builds are experimental and intended for development testing. Some may still use HeliBoard branding.

- **[Install a development build](INSTALL.md)** — find an APK, install, enable, and update safely.
- **[Testing checklist and known issues](TESTING.md)** — app compatibility, regression tests, and reporting results.
- **[Development changelog](CHANGELOG.md)** — what changed in recent Octopus builds and what remains unverified.
- **[Report a bug](https://github.com/girron/Octopus-Keyboard/issues/new)** — include version/commit, Android version, affected field, and reproduction steps; redact sensitive typing.

[Development build workflow](https://github.com/girron/Octopus-Keyboard/actions/workflows/build-debug-apk.yml) · [All GitHub Actions](https://github.com/girron/Octopus-Keyboard/actions)

## Development status

| Branch | Purpose |
| --- | --- |
| `main` | Upstream-based mainline and project documentation |
| `octopus-mvp` | Experimental Octopus typing behavior and fixes |
| `feature/octopus-beta-branding` | Experimental app identity, icon, and beta presentation |

Before a public beta, priorities are to verify prediction behavior across apps, guard against Enter/autocorrect/backspace regressions, get automated tests green, confirm beta branding and signing, and validate installation and updates.

**Build from source:** On the appropriate development branch, with the Android SDK/NDK and Java 17 configured, use `./gradlew assembleDebug`. See the [workflow](.github/workflows/build-debug-apk.yml) and [upstream contribution guidance](CONTRIBUTING.md); the latter is inherited from HeliBoard and may describe upstream-specific policies.

## Privacy

Like its HeliBoard base, Octopus currently does not request Android's `INTERNET` permission for the keyboard app. This is not a promise that every app in which you type is private; the receiving app controls what it does with entered text. Do not include private typing content in public bug reports.

## Upstream, credits, and licensing

Octopus Keyboard is an **independent fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard)**, which in turn builds on [OpenBoard](https://github.com/openboard-team/openboard) and the [Android Open Source Project keyboard](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/).

Credit for HeliBoard, its features, existing artwork, and contributors belongs to their respective creators. See [HeliBoard's contributors](https://github.com/HeliBorg/HeliBoard/graphs/contributors) and its [original project README](https://github.com/HeliBorg/HeliBoard#readme). Existing upstream icon credits include Fabian OvrWrt and The Eclectic Dyslexic. Replacement Octopus artwork will be documented separately as it is added.

This fork includes code licensed under the **GNU General Public License v3.0**; see [LICENSE](LICENSE). Related upstream material also has [Apache 2.0](LICENSE-Apache-2.0) and [Creative Commons BY-SA 4.0](LICENSE-CC-BY-SA-4.0) notices. Preserve applicable notices when reusing code or assets.

Octopus Keyboard is not an official HeliBoard release. For HeliBoard's official APKs, documentation, translations, and community, visit the [upstream project](https://github.com/HeliBorg/HeliBoard).
