# SOUND_ASSETS_MANIFEST.md

## الحالة الحالية

تمت إضافة **50 ملفاً صوتياً فعلياً** إلى `app/src/main/res/raw/` بصيغة MP3. تم تنزيلها من صفحات Mixkit الرسمية وفحص قابلية تشغيلها بـ `ffprobe`. هذه الملفات أصول خام داخل المشروع؛ لم يتم بعد ربط كل ملف بعنصر داخل `MainActivity.kt` لأن طبقة AudioManager/MediaPlayer لم تُنفذ بعد.

### الترخيص

ملفات Mixkit المستخدمة تحمل **Mixkit Free License** وفق صفحة الترخيص الرسمية. الصفحة العامة للمكتبة تذكر أنها مجانية للاستخدام الشخصي والتجاري ودون إلزام بالإسناد، مع منع توزيع الملف منفرداً كما هو. يجب الاحتفاظ برابط المصدر وعدم إعادة بيع الملفات كأصول مستقلة.

- الترخيص: [Mixkit License](https://mixkit.co/license/)
- سياسة مكتبة المؤثرات: [Mixkit Free Sound Effects](https://mixkit.co/free-sound-effects/)
- لم يتم استخدام أي ملف من موقع مجهول أو مقرصن.
- لم أعتبر تسجيلات الفيديوهات المرفقة مرخّصة تلقائياً؛ التسجيل الجديد يحتوي صوت تطبيق وبيئة محيطة، لذلك بقي مرجعاً ولم يُدمج كأصل مرخّص.

## الملخص

| الفئة | الملفات المضافة | ملاحظة الملاءمة |
|---|---:|---|
| أسلحة / Gun SFX | 23 | مؤثرات أسلحة عامة؛ لا تُنسب إلى طراز محدد دون اختبار سمعي مستقل |
| كهرباء / Energy SFX | 24 | مؤثرات كهرباء/طاقة عامة؛ مناسبة كطبقة أولية وليست تسجيلات جهاز محدد |
| حلاقة وصالون / Barber SFX | 3 | مؤثر مقص واحد ومؤثران لمجفف الشعر؛ لا يوجد صوت ماكينة حلاقة مرخّص مضاف حتى الآن |
| **الإجمالي** | **50** | **أصول خام مضافة، والربط البرمجي لم يكتمل** |

## الملفات الفعلية

### أسلحة / Gun SFX

| الملف | المدة (ثانية) | الحجم | SHA-256 | صفحة المصدر | رابط الملف الأصلي |
|---|---:|---:|---|---|---|
| `weapons_mixkit_1659.mp3` | 0.418 | 14,719 بايت | `28b17f06eb1d360895416ec8aad3a8a2e50ba5d6f1cbeaaffde65958d6bdcc2a` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1659](https://assets.mixkit.co/active_storage/sfx/1659/1659-preview.mp3) |
| `weapons_mixkit_1660.mp3` | 0.967 | 27,058 بايت | `40618308e3743a92defe1dcf2d4850874a10fd42588148d4d6c98fb8d35816a8` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1660](https://assets.mixkit.co/active_storage/sfx/1660/1660-preview.mp3) |
| `weapons_mixkit_1662.mp3` | 1.907 | 25,893 بايت | `6f0840f974b1a227237af7c3fc590bbf2af7326597654bafb2cdf7afb473a0fe` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1662](https://assets.mixkit.co/active_storage/sfx/1662/1662-preview.mp3) |
| `weapons_mixkit_1664.mp3` | 0.209 | 8,560 بايت | `f87eef755cdcbd21c4a418ae27ff48eff6700cdeb1d367c893a99f876d24b2db` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1664](https://assets.mixkit.co/active_storage/sfx/1664/1664-preview.mp3) |
| `weapons_mixkit_1665.mp3` | 0.392 | 13,937 بايت | `d35c2fd7a77f539a588e24058ce5f71ea7ff719bf2ab097cbf280f18c21deb48` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1665](https://assets.mixkit.co/active_storage/sfx/1665/1665-preview.mp3) |
| `weapons_mixkit_1666.mp3` | 0.914 | 27,662 بايت | `f07f874380ce64d788b8b7e3cdaf0903365c52cead7b653cdcfefecf366109bc` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1666](https://assets.mixkit.co/active_storage/sfx/1666/1666-preview.mp3) |
| `weapons_mixkit_1668.mp3` | 0.575 | 20,095 بايت | `86aae8631c2693ce6065d36e1a1c31bbe7b15fd0684dffa4194457027ec7c3af` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1668](https://assets.mixkit.co/active_storage/sfx/1668/1668-preview.mp3) |
| `weapons_mixkit_1670.mp3` | 0.967 | 29,389 بايت | `fb27075fdbe1c7cc6d6d33eeef70b929fa67688892ec62edc7e17b7952f2635d` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1670](https://assets.mixkit.co/active_storage/sfx/1670/1670-preview.mp3) |
| `weapons_mixkit_1674.mp3` | 0.209 | 7,724 بايت | `baff34e4bac81159141b920156eb76546b6467998c990a86c3731bc1abd88b9c` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1674](https://assets.mixkit.co/active_storage/sfx/1674/1674-preview.mp3) |
| `weapons_mixkit_1678.mp3` | 1.228 | 40,920 بايت | `f9eaaaf981ad86dcc9805a91e9e505498997c19e55270eeecb38cf8ccf0129ec` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1678](https://assets.mixkit.co/active_storage/sfx/1678/1678-preview.mp3) |
| `weapons_mixkit_1679.mp3` | 2.482 | 78,503 بايت | `804109b15a81f6fdf516571d10139761650005ef3edabdb514844fd3e615e5f9` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1679](https://assets.mixkit.co/active_storage/sfx/1679/1679-preview.mp3) |
| `weapons_mixkit_1680.mp3` | 5.616 | 174,853 بايت | `e0bebcd167c5c886d4096b8adbf2de27987650b53e8ea735fd946b4d306f255b` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1680](https://assets.mixkit.co/active_storage/sfx/1680/1680-preview.mp3) |
| `weapons_mixkit_1681.mp3` | 2.638 | 66,013 بايت | `557a91ccfca2b73f4cb21c0b846e1f0accc3f682828ddff2cf73b5791f9d5fa7` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1681](https://assets.mixkit.co/active_storage/sfx/1681/1681-preview.mp3) |
| `weapons_mixkit_1693.mp3` | 2.978 | 95,305 بايت | `c34617ce11b48229bae76a955c6bbf34c92ddfa6246accf9f1f666dab166a4b7` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 1693](https://assets.mixkit.co/active_storage/sfx/1693/1693-preview.mp3) |
| `weapons_mixkit_223.mp3` | 0.888 | 25,993 بايت | `5ae9d1f4d6ff2b30050af6ff465ba1c762ed07ce1b3aa6484b5738f292902bde` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 223](https://assets.mixkit.co/active_storage/sfx/223/223-preview.mp3) |
| `weapons_mixkit_2777.mp3` | 8.542 | 269,752 بايت | `523d01312df00bad5fcec0c67d06bb0e48dd5147b9bb8033bcfa7dde3320ce46` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2777](https://assets.mixkit.co/active_storage/sfx/2777/2777-preview.mp3) |
| `weapons_mixkit_2778.mp3` | 2.168 | 67,023 بايت | `838381eeafd5e6eee1acfa6f463d5a4b82f398538019300a0ba67c482fccc0c9` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2778](https://assets.mixkit.co/active_storage/sfx/2778/2778-preview.mp3) |
| `weapons_mixkit_2779.mp3` | 2.064 | 66,033 بايت | `b94998f9faf2892a4ee77b5273d8f2343eb4dd3375f793756a00e01da6a6d2b3` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2779](https://assets.mixkit.co/active_storage/sfx/2779/2779-preview.mp3) |
| `weapons_mixkit_2780.mp3` | 4.153 | 127,052 بايت | `7ab2087df41e53d9111438b07fe917cae901b055cfc4bb0af8aaa41a2735548c` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2780](https://assets.mixkit.co/active_storage/sfx/2780/2780-preview.mp3) |
| `weapons_mixkit_2782.mp3` | 4.571 | 148,658 بايت | `5eadacabb07da83ba1bceb0f33ea040628e8e7bada3135ddf44cdafc1fb75bc5` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2782](https://assets.mixkit.co/active_storage/sfx/2782/2782-preview.mp3) |
| `weapons_mixkit_2783.mp3` | 2.638 | 82,573 بايت | `934e24ba1ae254766395226f4cf41cc28985641e907165df866634f0a5b10f22` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2783](https://assets.mixkit.co/active_storage/sfx/2783/2783-preview.mp3) |
| `weapons_mixkit_2784.mp3` | 4.258 | 130,187 بايت | `f5dbfe7e93567cefc8731c8917bfdd247b57d5fde26d00a4ec40adf6995c4df0` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2784](https://assets.mixkit.co/active_storage/sfx/2784/2784-preview.mp3) |
| `weapons_mixkit_2804.mp3` | 7.993 | 249,071 بايت | `490940745ce36fac0fa4f46805b570909eb047bc205abb0d52a2f6cb4f0759f8` | [Mixkit](https://mixkit.co/free-sound-effects/gun/) | [asset 2804](https://assets.mixkit.co/active_storage/sfx/2804/2804-preview.mp3) |

### كهرباء / Energy SFX

| الملف | المدة (ثانية) | الحجم | SHA-256 | صفحة المصدر | رابط الملف الأصلي |
|---|---:|---:|---|---|---|
| `energy_mixkit_1054.mp3` | 9.979 | 319,759 بايت | `050c4cb7c7ab8fa4b6f65d3088c5b301a01ae3ae44df94c8a46fea7f35431036` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1054](https://assets.mixkit.co/active_storage/sfx/1054/1054-preview.mp3) |
| `energy_mixkit_1377.mp3` | 7.184 | 95,206 بايت | `cf38f29bdf7d9a01ac86af7e38103dcf1927ef4e2ef4b6f8514304308d0c0f29` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1377](https://assets.mixkit.co/active_storage/sfx/1377/1377-preview.mp3) |
| `energy_mixkit_1453.mp3` | 2.456 | 95,421 بايت | `6731a3e50be7863753ec705f86836862e66554c6cb7aa2c0e4f270eb5aa3d728` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1453](https://assets.mixkit.co/active_storage/sfx/1453/1453-preview.mp3) |
| `energy_mixkit_1455.mp3` | 1.933 | 70,939 بايت | `0e997a2a0dce967a3353981e45546807d6d31adbadd235468af05ee2b21707c2` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1455](https://assets.mixkit.co/active_storage/sfx/1455/1455-preview.mp3) |
| `energy_mixkit_1808.mp3` | 1.411 | 40,923 بايت | `f953a8a87ba86569803cca50a5967cfbfd7e54227358fa9340c012196c891d42` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1808](https://assets.mixkit.co/active_storage/sfx/1808/1808-preview.mp3) |
| `energy_mixkit_1811.mp3` | 3.683 | 112,315 بايت | `81394e48478acfc579a72cf332895f602f8a218de54e81fc3697b21acbed4b73` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1811](https://assets.mixkit.co/active_storage/sfx/1811/1811-preview.mp3) |
| `energy_mixkit_1814.mp3` | 10.710 | 331,420 بايت | `e2081a60cc3f93a7d4077927f91412060432dbcf4887992e99253150b70f0e30` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 1814](https://assets.mixkit.co/active_storage/sfx/1814/1814-preview.mp3) |
| `energy_mixkit_2340.mp3` | 1.123 | 24,524 بايت | `c66f2beb9032ef1eba1c289ea3f214fbcaadbd333fe4947b24f090569fe5c080` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2340](https://assets.mixkit.co/active_storage/sfx/2340/2340-preview.mp3) |
| `energy_mixkit_2365.mp3` | 1.149 | 37,423 بايت | `77e9ba9ef0ef8a9091d30ecec778461770d20eeed57521de50911c6651a43f24` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2365](https://assets.mixkit.co/active_storage/sfx/2365/2365-preview.mp3) |
| `energy_mixkit_2594.mp3` | 1.071 | 34,866 بايت | `f31663a613dfc484f09d25ba9e4b6b8518cbda32de13c83279d36b117047a43a` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2594](https://assets.mixkit.co/active_storage/sfx/2594/2594-preview.mp3) |
| `energy_mixkit_2595.mp3` | 1.071 | 36,538 بايت | `8a649434e49485135236a8d5f337674a01cb3d32ea89fcb14bca113747b2c514` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2595](https://assets.mixkit.co/active_storage/sfx/2595/2595-preview.mp3) |
| `energy_mixkit_2596.mp3` | 3.161 | 72,010 بايت | `fceafcb7c5ca92a9a3844d58ada70d061863376a22a185816f13b72aaf3bf212` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2596](https://assets.mixkit.co/active_storage/sfx/2596/2596-preview.mp3) |
| `energy_mixkit_2597.mp3` | 1.541 | 52,196 بايت | `5476474f138eabb144203615ca0d8d2e46cf2bc16e15989e2acee2ca875772c0` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2597](https://assets.mixkit.co/active_storage/sfx/2597/2597-preview.mp3) |
| `energy_mixkit_2599.mp3` | 6.687 | 215,151 بايت | `db01ea375897b47166292daa4ab4c9798fa2ff2087c397db62de5ae262286abc` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2599](https://assets.mixkit.co/active_storage/sfx/2599/2599-preview.mp3) |
| `energy_mixkit_2603.mp3` | 7.967 | 288,236 بايت | `74d39f511f0d155e692061c7468f0335e9ed226516fc542fa7c544d30c6e379c` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2603](https://assets.mixkit.co/active_storage/sfx/2603/2603-preview.mp3) |
| `energy_mixkit_2614.mp3` | 1.489 | 46,712 بايت | `0bd5526345991b0036b9b0abc1fc88f3dc69c41570d0fb379b9da020c0f2619f` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2614](https://assets.mixkit.co/active_storage/sfx/2614/2614-preview.mp3) |
| `energy_mixkit_2646.mp3` | 8.882 | 271,620 بايت | `5c3f23935996845d0db2a1929e2f71c5859930fb34120c31655dfd6417499e40` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2646](https://assets.mixkit.co/active_storage/sfx/2646/2646-preview.mp3) |
| `energy_mixkit_2961.mp3` | 1.933 | 63,729 بايت | `306160a8a52b545c4d15b627147441e0781e73e4bdeecd34d3733e51db2afa26` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2961](https://assets.mixkit.co/active_storage/sfx/2961/2961-preview.mp3) |
| `energy_mixkit_2966.mp3` | 1.149 | 41,237 بايت | `8f13a4a161219909b9c454eee6d0fb27640d489217ed7786541cabe4920c776a` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2966](https://assets.mixkit.co/active_storage/sfx/2966/2966-preview.mp3) |
| `energy_mixkit_2967.mp3` | 1.149 | 38,994 بايت | `ebad5cdb5611f017ae5dfa96c6d1737036b2b7db5b438587b1daffab2013c51d` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2967](https://assets.mixkit.co/active_storage/sfx/2967/2967-preview.mp3) |
| `energy_mixkit_2968.mp3` | 1.149 | 40,819 بايت | `b85ce24b3180016211b23a08b2b5aff53941d3f627fbdae1b16bf5c4b70ed3ab` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2968](https://assets.mixkit.co/active_storage/sfx/2968/2968-preview.mp3) |
| `energy_mixkit_2969.mp3` | 5.642 | 211,354 بايت | `b96e581639fb3bd67b956f8ecb1a76488d9c09f8682a709952672102dc1be091` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 2969](https://assets.mixkit.co/active_storage/sfx/2969/2969-preview.mp3) |
| `energy_mixkit_3032.mp3` | 9.927 | 309,719 بايت | `be72b8c80a1019b9f0cec0be2301cbd368064cc993e9fd05b0cd6f0164605c94` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 3032](https://assets.mixkit.co/active_storage/sfx/3032/3032-preview.mp3) |
| `energy_mixkit_3201.mp3` | 2.534 | 43,252 بايت | `efd1369b0944558ed32eb2204fc3dc311385f2f6b9acefdd04ac84e6beecc23a` | [Mixkit](https://mixkit.co/free-sound-effects/electric/) | [asset 3201](https://assets.mixkit.co/active_storage/sfx/3201/3201-preview.mp3) |

### حلاقة وصالون / Barber SFX

| الملف | المدة (ثانية) | الحجم | SHA-256 | صفحة المصدر | رابط الملف الأصلي |
|---|---:|---:|---|---|---|
| `barber_mixkit_1894.mp3` | 27.873 | 867,239 بايت | `3ea0aec92d4e1f24c8cf2f68f5267016d435a0705e9546a75c9ecc203d7aa161` | [Mixkit](https://mixkit.co/free-sound-effects/hair-dryer/) | [asset 1894](https://assets.mixkit.co/active_storage/sfx/1894/1894-preview.mp3) |
| `barber_mixkit_1895.mp3` | 4.362 | 134,304 بايت | `f0ababd7631cb23f8606520d14c66d171311ebe9bb0bfa6cd77fb99fe648365d` | [Mixkit](https://mixkit.co/free-sound-effects/hair-dryer/) | [asset 1895](https://assets.mixkit.co/active_storage/sfx/1895/1895-preview.mp3) |
| `barber_mixkit_2378.mp3` | 2.978 | 60,324 بايت | `8a9dc5b73277d6ccf0f9cb19c48458ef9af0658fec9ca7083c2b094da1d39138` | [Mixkit](https://mixkit.co/free-sound-effects/scissors/) | [asset 2378](https://assets.mixkit.co/active_storage/sfx/2378/2378-preview.mp3) |

## حدود الاستخدام داخل التطبيق

- الملفات موجودة فعلياً داخل APK resources عبر `app/src/main/res/raw/`.
- لم أكرر الملف نفسه بين هذه الأصول الخمسين.
- لا أزعم أن مؤثر Mixkit العام هو التسجيل الحقيقي المطابق لنموذج AK-47 أو M4A1 أو أي جهاز بعينه؛ هذا يحتاج اختياراً واختباراً لكل عنصر وربطاً برمجياً منفصلاً.
- الأصوات المطلوبة التي ما زالت بلا أصل مطابق موثق تشمل أصوات النماذج الفردية المتبقية، أصوات ماكينة الحلاقة الكهربائية، ماكينة القص، الرذاذ، الرغوة، المشط والفرشاة.
