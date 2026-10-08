# Installing Octopus Keyboard (development builds)

> **Pre-release:** There is no approved public beta APK yet. These instructions are for development testers. Builds may be unstable or still display HeliBoard branding.

## 1. Find a build

1. Open [GitHub Actions](https://github.com/girron/Octopus-Keyboard/actions/workflows/build-debug-apk.yml).
2. Choose a **successful** build run for the intended branch. The functional development branch is `octopus-mvp`; the separate branding experiment is `feature/octopus-beta-branding`.
3. Open the run's **Artifacts** section and download the debug APK artifact ZIP. You may need to sign into GitHub.
4. Extract the ZIP to locate the `.apk` file. A green *Test build* workflow is not necessarily an APK download; use the **Build debug APK** workflow for APK artifacts.

If there is no suitable successful run, do **not** assume the latest source has been approved for testing. Maintainers with workflow permission can use **Run workflow** to select a branch; GitHub may restrict who can do this.

## 2. Install and enable

1. Transfer the extracted APK to your Android device and open it.
2. Android may ask you to allow installs from the app used to open the APK; authorize only if you trust the exact file and source.
3. Install, then open the keyboard's settings. In Android's keyboard settings, enable the installed keyboard and select it as your current input method.
4. Check which keyboard name appears: development builds can still be labeled **HeliBoard**.
5. Use [TESTING.md](TESTING.md) for a focused functional check.

**Updating:** An APK can update an installed app only if its application/package ID and signing certificate are compatible. Debug builds can differ by branch or signing configuration; an "App not installed" error is not proof of a keyboard logic bug. Back up keyboard settings before experimenting. Do not uninstall an existing keyboard simply to resolve a signature mismatch without considering its stored data.

## Troubleshooting and reporting

Record the branch, build version, commit SHA or workflow run URL, Android version, affected app/input field, expected result, and actual result. [Report an issue](https://github.com/girron/Octopus-Keyboard/issues/new) after checking [existing issues](https://github.com/girron/Octopus-Keyboard/issues).

Never attach passwords, sensitive typed text, or private messages to a public report.

[← Project homepage](README.md) · [Testing checklist](TESTING.md)
