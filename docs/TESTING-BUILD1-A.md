# Build 1 Checkpoint A verification

Original version-code-1 verification record. Theme update validation is recorded
in THEME-UPDATE-A.md.

Status: ready for Kat's device testing; not approved or merged.

Completed in the build environment:
- Clean Kotlin/Gradle debug APK compilation.
- Two JUnit foundation tests: both passed, zero failures.
- Android lint: zero errors; one retained launcher-shape warning for the exact approved artwork.
- Device-test APK compiled, with repository persistence and navigation tests included.
- APK is nonempty and has a valid Android debug signature.
- Original README and original artwork are unchanged from main at 02052db.
- Main remains unchanged; source is saved on build1/checkpoint-a.

Runtime limitation:
The software-only Android 15 emulator did not complete boot in a reasonable
verification window. This environment has no /dev/kvm hardware acceleration.
No passing emulator launch, UI navigation, persistence, phone/tablet layout,
or crash-free runtime result is claimed. The compiled instrumentation tests
remain available to run on a working emulator or connected device.

Device acceptance is still required for launch, create/edit profile, switching,
force-close/reopen persistence, companions, settings, Home/Back navigation,
portrait phone/tablet sizing, rotation, and larger text. See the checklist in
BUILD1-CHECKPOINT-A.md.

Known limitations:
- Original approved homepage and separate dragon artwork are missing.
- Home uses exact approved logo plus a clearly identified functional themed layout.
- Companion selection uses explicitly temporary text, without new art.
- Small landscape windows and accessibility text sizes can scroll to keep controls reachable.
- Profile/long information screens intentionally scroll as content grows.
- No lessons, assessment, prerequisite system, or mastery implementation yet.
- Local-only data; uninstalling removes saved profiles.
