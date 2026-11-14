# كتالوج أسلحة الفيديو المعتمد

تم استبدال قسم الأسلحة القديم بالمجموعة الموجودة في لوحة الصور `weapons_23_catalog.png` المرفوعة إلى المستودع، مع استخراج الصوت المقابل لكل اسم من الفيديو `snaptik_7596426591916363039_v3.mp4`.

- الصور المعتمدة: 23 صورة مقصوصة من لوحة المصدر، دون توليد أو تنزيل خارجي.
- الأصوات المعتمدة: 23 مقطعًا مقصوصًا من الفيديو المرفق، دون استخدام أصوات Mixkit أو أي مصدر خارجي لقسم الأسلحة.
- الفئات: 7 مسدسات، 6 بنادق خرطوش، 3 بنادق هجومية، 7 قناصات.
- الصوت السابق يتوقف قبل تشغيل صوت جديد عبر `ItemSoundController`.
- الأسلحة الجديدة تعمل كأصوات طلقة قصيرة عند الضغط، وليست أصواتًا مستمرة.

| # | الاسم | الفئة | الصورة | الصوت | بداية الفيديو | نهاية الفيديو |
|---:|---|---|---|---|---:|---:|
| 1 | Glock | Pistols | `weapon_01.webp` | `weapon_glock.mp3` | 00:00 | 00:01 |
| 2 | M1911 | Pistols | `weapon_02.webp` | `weapon_m1911.mp3` | 00:02 | 00:03 |
| 3 | Sig P320 | Pistols | `weapon_03.webp` | `weapon_sig_p320.mp3` | 00:03 | 00:05 |
| 4 | Desert Eagle | Pistols | `weapon_04.webp` | `weapon_desert_eagle.mp3` | 00:05 | 00:07 |
| 5 | Beretta M9 | Pistols | `weapon_05.webp` | `weapon_beretta_m9.mp3` | 00:07 | 00:08 |
| 6 | Makarov | Pistols | `weapon_06.webp` | `weapon_makarov.mp3` | 00:09 | 00:10 |
| 7 | S&W Model 19 | Pistols | `weapon_07.webp` | `weapon_sw_model_19.mp3` | 00:11 | 00:12 |
| 8 | Browning Citori 725 | Shotguns | `weapon_08.webp` | `weapon_browning_citori_725.mp3` | 00:13 | 00:15 |
| 9 | Remington 870 | Shotguns | `weapon_09.webp` | `weapon_remington_870.mp3` | 00:15 | 00:17 |
| 10 | M26 MASS | Shotguns | `weapon_10.webp` | `weapon_m26_mass.mp3` | 00:17 | 00:19 |
| 11 | DP-12 | Shotguns | `weapon_11.webp` | `weapon_dp12.mp3` | 00:20 | 00:22 |
| 12 | Origin 12 | Shotguns | `weapon_12.webp` | `weapon_origin_12.mp3` | 00:23 | 00:25 |
| 13 | AA-12 | Shotguns | `weapon_13.webp` | `weapon_aa12.mp3` | 00:25 | 00:28 |
| 14 | Mk 14 EBR | Assault Rifles | `weapon_14.webp` | `weapon_mk14_ebr.mp3` | 00:28 | 00:31 |
| 15 | SKS | Assault Rifles | `weapon_15.webp` | `weapon_sks.mp3` | 00:31 | 00:33 |
| 16 | FN FAL | Assault Rifles | `weapon_16.webp` | `weapon_fn_fal.mp3` | 00:33 | 00:35 |
| 17 | Dragunov | Snipers | `weapon_17.webp` | `weapon_dragunov.mp3` | 00:35 | 00:37 |
| 18 | Barrett | Snipers | `weapon_18.webp` | `weapon_barrett.mp3` | 00:37 | 00:39 |
| 19 | Marlin Model 336 | Snipers | `weapon_19.webp` | `weapon_marlin_model_336.mp3` | 00:39 | 00:42 |
| 20 | Kar 98k | Snipers | `weapon_20.webp` | `weapon_kar_98k.mp3` | 00:42 | 00:46 |
| 21 | R700 | Snipers | `weapon_21.webp` | `weapon_r700.mp3` | 00:46 | 00:50 |
| 22 | HDR | Snipers | `weapon_22.webp` | `weapon_hdr.mp3` | 00:50 | 00:55 |
| 23 | AX50 | Snipers | `weapon_23.webp` | `weapon_ax50.mp3` | 00:56 | 01:01 |

## التنظيف

تم حذف موارد الصور الفردية القديمة للأسلحة من `app/src/main/assets/images/weapons/` و`app/src/main/res/drawable/`، وحذف موارد أصوات الأسلحة القديمة من `app/src/main/res/raw/`. بقي مورد `weapons_mixkit_1681.mp3` فقط لأنه مستخدم من قسم الطاقة كصوت `Lightning Blaster`، وليس صوتًا لقسم الأسلحة.
