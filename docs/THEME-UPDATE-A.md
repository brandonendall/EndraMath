# Checkpoint A theme update — version code 2

Kat supplied artwork/approved-homepage-reference.png as the homepage reference.
That original file is preserved unchanged. The prior approved launcher logo and
README are unchanged.

The academy_scene.png background is an AI-assisted edit of that reference, with
the demo portrait/name/level, baked navigation cards, central sign, and mission
panel removed so Android can supply real controls. The supplied dragon and
castle/waterfall/book setting remain the visual reference; the derived background
and its Android layout await Kat's visual approval.

Homepage: selected learner, create/continue, Practice/Lessons academy routes,
Quests/Challenges with truthful future-content dialogs, settings and information.
Dark bronze surfaces, gold borders/type and the academy background replace the
cream prototype panels across the screens. No lessons or rewards were added.
Age and grade remain private editor inputs. No invented level or achievements.

Version code 2 uses the existing app ID and preserved debug signing key. Install
as an update over the first APK; do not uninstall, so local learner profiles remain.
Storage formats and profile logic are unchanged. Kat has confirmed profile
persistence on her tablet in version 1; version 2 requires device retesting.

Original reference is landscape. The background shows the entire scene across
the width in portrait and fades into bronze below; two-column live cards adapt
without forcing landscape. Enlarged text or short landscape windows may scroll.

Checks passed: clean APK compile, both foundation unit tests, and lint with zero
errors. The sole lint warning is the retained approved launcher image shape.
APK signature verifies; the device-test APK also compiles. The legacy
instrumentation navigation labels are updated for the new learner control.
Runtime phone/tablet checks and visual acceptance are pending Kat's testing.

## Background preparation prompt

Built-in imagegen edit of the supplied reference: preserve the exact friendly
orange dragon face, horns, pose, scales and orange/cream palette, and retain the
castle, waterfalls, ivy, stone arches, books and orange/gold/bronze lighting.
Remove the girl/Welcome Alex/Level 12/progress badge, top-right menu icons and
labels, central EndraMath sign and ribbon, four baked navigation panels and
Today's Mission panel. Reveal the existing academy environment behind them.
No text, new characters, generic replacement dragon, new emblem or logo. Keep
landscape 3:2 composition. Native Android supplies live labels and controls.

Derived asset: app/src/main/res/drawable-nodpi/academy_scene.png.
