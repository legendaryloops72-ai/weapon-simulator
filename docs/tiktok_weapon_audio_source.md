# TikTok weapon-audio source

Source supplied by the project owner:

- https://vt.tiktok.com/ZSpUnuucc/

The link resolves to a TikTok video by `@fazbeargaming92` titled **MW2019 weapon sounds — part 2**. The browser view reports a duration of approximately **01:01**; the visible frame at inspection was labeled **FAMAS**. The post is a compilation where the weapon name appears on screen while its sound plays, rather than separate downloadable audio files.

TikTok allowed playback in the browser but returned HTTP 403 when the CDN file was requested from the build environment. Therefore the compilation has not been split or assigned by guesswork. To create exact per-weapon files, upload the original MP4 (or an exported audio file) to the project; then split at the visible label boundaries, normalize each clip, and map each clip only to its matching weapon image in `Models.kt`.

The current app keeps the existing verified one-to-one weapon map in `app/src/main/java/com/weapon/simulator/audio/ItemSoundController.kt`. It does not rename a generic compilation or reuse one weapon recording for unrelated weapons.
