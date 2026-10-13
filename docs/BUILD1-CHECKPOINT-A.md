# EndraMath Build 1 — Checkpoint A

Creator, owner, and project lead: Kat. The repository account is hosting only.

Base: main at 02052db1f306d0ad2638187e3c1dff17d3d33fe7.
Development branch: build1/checkpoint-a. Not approved or merged to main.
The original README and approved artwork remain unchanged.

## Scope

Native Kotlin Android app, Java 17, Gradle 8.9, Android Gradle Plugin 8.7.3.
Android 8.0 (API 26) or newer. No network permission, account, or payment code.

- Exact approved logo on homepage and launcher.
- Home, information, settings, profiles, academy list, and two academy detail screens.
- Empty first install; create/edit name, age, grade; select independent learner profiles.
- App-private versioned local profiles, selected learner, per-learner companion, larger-text preference.
- Age and grade shown only in the profile editor, never used for ability judgments.
- Optional free Solaris/Aurora choices with clearly labeled temporary text presentation.
- No-dragon default; no academic gates based on companions, age, grade, or payments.
- Typed academy, skill, learning-stage, and mastery data contracts for later implementation.
- Unreadable stored profiles are preserved rather than overwritten.

## Intentionally incomplete

Lessons, practice, quizzes, placement, prerequisite enforcement, mastery tracking,
whiteboard, advanced graphing, animation, growth, premium cosmetics, cloud sync,
teacher tools, subscriptions, and complete curriculum are not implemented.
The two academy spaces contain truthful development descriptions, not sample lessons.
The remaining ten academies are noninteractive future-content text.

Only the approved logo was present in the repository. The themed functional
homepage is awaiting the original approved homepage artwork. No new mascot design
has been generated or substituted. Individual Solaris/Aurora artwork is also pending.

## Local data

SharedPreferences stores a versioned JSON profile envelope and preference values.
Writes complete before navigation, IDs are stable, and profile edits preserve
companion choices. No personal information is preloaded. Uninstalling removes
local data. Cloud/backup and progress implementations belong to later checkpoints.

## Build

Install Android SDK platform 35 and build-tools 35.0.0, and use Java 17.
Set ANDROID_HOME or create an untracked local.properties with sdk.dir.
Run ./gradlew assembleDebug testDebugUnitTest lintDebug assembleDebugAndroidTest.
The installable debug APK is app/build/outputs/apk/debug/app-debug.apk.
Optional emulator storage check: ./gradlew connectedDebugAndroidTest.

## Kat's device acceptance checklist

1. Install and open on a phone and tablet; verify exact logo and visible home buttons.
2. Create two test learners; edit name, age, and grade; switch between them.
3. Force close and reopen; confirm profiles, selected learner, and settings remain.
4. Choose Solaris, Aurora, and No dragon separately; verify choices stay per learner.
5. Open both academies, Settings, and How EndraMath Works; check Home and Android Back.
6. Rotate and test larger text; report clipped text, duplicates, unusual scrolling, or crashes.
7. Confirm the functional layout for this checkpoint; original homepage art is still pending.

Profile lists, forms, settings, and informational screens can scroll as content grows.
Home uses a compact logo on short screens and a capped content width on tablets.
Accessibility settings or very short landscape windows may require home scrolling
so controls remain reachable instead of clipped.

Approval requires Kat's explicit acceptance after installing and testing.

## Debug install continuity

The included config/endramath-debug.keystore is a public development-only key,
with standard Android debug passwords. Local and CI debug builds reuse this key
so later debug checkpoints can update this install while preserving local data.
It must never be used for a production or Play Store release.
