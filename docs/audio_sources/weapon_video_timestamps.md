# Weapon video audio extraction

Source: `1000059476.mp4` (720x1280, 30 fps, 61.207 s). The source video contains hard cuts and no background music. Audio clips were extracted as mono MP3 at 44.1 kHz.

| File | Label | Start | End | App image mapping |
|---|---|---:|---:|---|
| `weapon_famas.mp3` | FAMAS | 00.00 | 01.00 | `weapon_16` / existing FAMAS |
| `weapon_aug.mp3` | AUG | 01.00 | 03.00 | `weapon_15` / existing AUG A3 |
| `weapon_p90.mp3` | P90 | 03.00 | 05.00 | `weapon_12` / existing P90 |
| `weapon_mp5.mp3` | MP5 | 05.00 | 07.00 | `weapon_11` / existing MP5 |
| `weapon_uzi.mp3` | UZI | 07.00 | 09.00 | `weapon_10` / existing Uzi |
| `weapon_pp_bizon.mp3` | PP Bizon | 09.00 | 11.00 | pending matching image |
| `weapon_mp7.mp3` | MP7 | 11.00 | 14.00 | pending matching image |
| `weapon_ump.mp3` | UMP | 14.00 | 16.00 | pending matching image |
| `weapon_kriss_vector.mp3` | KRISS Vector | 16.00 | 18.00 | `weapon_26` / existing Kriss Vector |
| `weapon_apc9.mp3` | APC9 | 18.00 | 19.00 | pending matching image |
| `weapon_scorpion_evo.mp3` | Scorpion EVO | 19.00 | 20.00 | pending matching image |
| `weapon_hk33.mp3` | HK33 | 20.00 | 22.00 | pending matching image |
| `weapon_m4a1.mp3` | M4A1 | 22.00 | 24.00 | `weapon_02` / existing M4A1 |
| `weapon_ash_12_7.mp3` | Ash 12.7 | 24.00 | 27.00 | pending matching image |
| `weapon_mcx_virtus.mp3` | MCX Virtus | 27.00 | 29.00 | pending matching image |
| `weapon_fn_scar_17.mp3` | FN SCAR 17 | 29.00 | 30.00 | no exact image; existing SCAR-L is not substituted |
| `weapon_ak47.mp3` | AK47 | 30.00 | 33.00 | `weapon_01` / existing AK-47 |
| `weapon_ctar_21.mp3` | CTAR 21 | 33.00 | 35.00 | pending matching image |
| `weapon_sg_552.mp3` | SG 552 | 35.00 | 38.00 | pending matching image |
| `weapon_ace_32.mp3` | ACE 32 | 38.00 | 40.00 | pending matching image |
| `weapon_an94.mp3` | AN94 | 40.00 | 43.00 | pending matching image |
| `weapon_asval.mp3` | AS VAL | 43.00 | 44.00 | pending matching image |
| `weapon_pkm.mp3` | PKM | 44.00 | 47.00 | pending matching image |
| `weapon_sa80.mp3` | SA80 | 47.00 | 49.00 | pending matching image |
| `weapon_fn_minimi.mp3` | FN Minimi | 49.00 | 52.00 | pending matching image |
| `weapon_mg34.mp3` | MG34 | 52.00 | 55.00 | pending matching image |
| `weapon_mg36.mp3` | MG36 | 55.00 | 57.00 | pending matching image |
| `weapon_m249_saw.mp3` | M249 SAW | 57.00 | 60.00 | pending matching image |

## Integration policy

Only clips with an exact catalog image are assigned to an item. All 28 extracted clips are included in `app/src/main/res/raw/`. Eight exact matches are wired in the app (`FAMAS`, `AUG`, `P90`, `MP5`, `UZI`, `KRISS Vector`, `M4A1`, and `AK-47`); the remaining clips remain available but unassigned until a matching image/model is confirmed, preventing incorrect audio-image pairings.
