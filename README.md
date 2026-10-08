# Octopus Keyboard 🐙

**An experimental Android keyboard with word predictions on the keys.**

Octopus Keyboard is a work-in-progress fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard), focused on bringing **per-key word predictions** to a customizable, offline-capable Android keyboard.

> **Project status: pre-release / private beta preparation.** Features and compatibility are still being tested. The Octopus name and icon are being integrated; current development APKs may still display HeliBoard branding.

[Report a bug](https://github.com/girron/Octopus-Keyboard/issues/new) · [View development builds](https://github.com/girron/Octopus-Keyboard/actions/workflows/build-debug-apk.yml) · [Source code](https://github.com/girron/Octopus-Keyboard) · [Upstream HeliBoard](https://github.com/HeliBorg/HeliBoard)

## What makes Octopus different?

Octopus experiments with **showing word predictions above individual keyboard keys**, instead of relying solely on the usual suggestions row. The goal is to make suggestions accessible while keeping familiar typing behavior.

The project builds on HeliBoard's existing keyboard capabilities, including:

- Dictionary-based suggestions, spell checking, and autocorrection
- Custom themes, colors, and keyboard layouts
- Split keyboard, one-handed mode, and multilingual layouts
- Emoji search, clipboard history, and settings backups
- Local operation without the Android `INTERNET` permission

**Experimental behavior:** prediction availability can depend on the app and its text input field. Compatibility, including with Gemini, remains under active testing. A successful build does not guarantee that every prediction issue has been fixed.

For details about features inherited from HeliBoard, see the [upstream wiki and FAQ](https://github.com/HeliBorg/HeliBoard/wiki) and the [layout documentation](layouts.md).

## Inspiration and origins

Octopus Keyboard for Android draws inspiration from two iOS keyboards that have shaped my typing experience.

The original **Octopus Keyboard** jailbreak tweak, developed by Mario Hros (K3A), brought BlackBerry 10-style predictive typing to the iPhone, displaying word suggestions directly on individual keys.

When Octopus stopped receiving updates, I purchased **Crimson Keyboard** upon its release and have continued using it ever since. Both keyboards have been central inspirations for bringing this distinctive predictive typing experience to Android.

Built on the open-source **HeliBoard** project, Octopus Keyboard for Android aims to recreate and refine that experience while retaining the flexibility and customization of a modern Android keyboard.

This is an independent project, unaffiliated with the original Octopus Keyboard, Crimson Keyboard, or HeliBoard developers.

## Try a development build

There is **no general beta release yet**. Until a reviewed beta APK is published, builds are primarily for development and testing.

1. Open the [Build debug APK workflow](https://github.com/girron/Octopus-Keyboard/actions/workflows/build-debug-apk.yml).
2. If you have permission, select **Run workflow** and choose the branch you want to test.
3. After a successful run, download the APK artifact from the workflow run.

Development builds can change without notice. The APK may still be named **HeliBoard**, and installation over an existing debug build can depend on matching its signing certificate and package ID. Back up your settings before experimenting.

## Beta testing and bug reports

Feedback is welcome, especially for predictions, autocorrect, punctuation spacing, input field compatibility, layout behavior, and crashes.

[Open an Octopus Keyboard issue](https://github.com/girron/Octopus-Keyboard/issues/new) and include:

- **Build/version** and, if possible, the commit SHA
- **Device** and **Android version**
- **App and field** where the problem occurred (for example, a browser address bar)
- **Steps to reproduce**, expected behavior, and actual behavior
- A screenshot or crash log **only if it does not expose passwords, private messages, or other sensitive typed text**

Check [existing reports](https://github.com/girron/Octopus-Keyboard/issues) first to avoid duplicates.

## Project development

| Branch | Purpose |
| --- | --- |
| `main` | Upstream HeliBoard base |
| `octopus-mvp` | Octopus behavior and bug-fix development |
| `feature/octopus-beta-branding` | App identity, icon, and beta presentation |

Current beta-preparation goals:

- [ ] Apply Octopus Keyboard branding and the selected A1 launcher icon
- [ ] Update the visible app name in Settings and Android's keyboard selector
- [ ] Verify Gemini predictions without regressing previously tested fixes
- [ ] Test installation, signing, upgrades, and everyday typing
- [ ] Publish a clearly versioned beta for invited testers

The checklist describes **planned verification**, not completed or released features.

## Building from source

This is an Android/Gradle project. With an appropriate Android SDK, NDK, and Java 17 environment, a debug build can be attempted using:

```bash
./gradlew assembleDebug
```

The [GitHub Actions workflow](.github/workflows/build-debug-apk.yml) documents the CI build configuration. For upstream technical details and contribution guidance, see [CONTRIBUTING.md](CONTRIBUTING.md).

## Privacy

Like its HeliBoard base, Octopus currently does not request Android's `INTERNET` permission for the keyboard app. This is not a promise that every app in which you type is private; the receiving app controls what it does with entered text. Do not include private typing content in public bug reports.

## Upstream, credits, and licensing

Octopus Keyboard is an **independent fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard)**, which in turn builds on [OpenBoard](https://github.com/openboard-team/openboard) and the [Android Open Source Project keyboard](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/).

Credit for HeliBoard, its features, existing artwork, and contributors belongs to their respective creators. See [HeliBoard's contributors](https://github.com/HeliBorg/HeliBoard/graphs/contributors) and its [original project README](https://github.com/HeliBorg/HeliBoard#readme). Existing upstream icon credits include Fabian OvrWrt and The Eclectic Dyslexic. Replacement Octopus artwork will be documented separately as it is added.

This fork includes code licensed under the **GNU General Public License v3.0**; see [LICENSE](LICENSE). Related upstream material also has [Apache 2.0](LICENSE-Apache-2.0) and [Creative Commons BY-SA 4.0](LICENSE-CC-BY-SA-4.0) notices. Preserve applicable notices when reusing code or assets.

Octopus Keyboard is not an official HeliBoard release. For HeliBoard's official APKs, documentation, translations, and community, visit the [upstream project](https://github.com/HeliBorg/HeliBoard).
