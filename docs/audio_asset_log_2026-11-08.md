# سجل الأصول الصوتية

**آخر تدقيق:** 8 نوفمبر 2026. **الملفات الفعالة داخل التطبيق:** 29 ملف MP3؛ فُحصت جميعها بأداة `ffprobe`، ولكل ملف مرجع صريح في `MainActivity.kt`.

## نتيجة مطابقة الأصوات

أزلت من موارد التطبيق الملفات القديمة غير المستخدمة أو التي لم تطابق وصف العنصر: تضمنت مؤثرات الكهرباء القديمة صوت آلة كاتبة كهربائية وخلاط مطبخ وحافلة وسياجاً كهربائياً؛ كما كان ملف المقص القديم لقص الورق. لم تعد هذه الملفات ضمن APK.

- **الأسلحة:** تسجيلان فقط مسميان صراحةً AK-47 وDesert Eagle. بقية نماذج الأسلحة لا تشغّل صوتاً غير مطابق بدلاً منها.
- **أدوات الطاقة الخيالية:** 24 مؤثراً فريداً من فئة Mixkit Electricity، موزعاً حسب طبيعة المؤثر (شحن، طنين، فرقعة، اندفاع، صدمة). هذه مؤثرات تصميمية عامة مناسبة لفئة خيالية، وليست تسجيلات واقعية مخصصة لطراز جهاز بعينه.
- **الصالون:** تسجيل ماكينة قص عام مشترك بين عائلة الماكينات/التريمَر، وتسجيل مقص موصوف بأنه للحلاقة، و«طنين مجفف شعر» مستمر. أدوات الرذاذ والرغوة والمشط والفرشاة وماكينة الحلاقة الكهربائية لا يصدر عنها صوت لعدم توفر تسجيل موثوق مناسب بعد.

| التغطية | عدد ملفات الصوت | كيفية الاستخدام |
|---|---:|---|
| أسلحة | 2 | AK-47 وDesert Eagle فقط؛ العناصر الأخرى صامتة حتى توفر تسجيلات مطابقة |
| أدوات طاقة خيالية | 24 | ملف فريد لكل عنصر، من مكتبة الكهرباء المرخصة |
| صالون | 3 | ملف ماكينة قص مشترك، مقص حلاقة، ومجفف شعر |
| **المجموع** | **29** | الأصول المستخدمة فعلياً فقط في `app/src/main/res/raw/` |

## مؤثرات الطاقة — Mixkit

المصدر والفهرس الرسمي: [Mixkit Electricity sound effects](https://mixkit.co/free-sound-effects/electricity/). الرابط في العمود الأخير يفتح ملف المعاينة الأصلي بمعرّف Mixkit نفسه. أسماء العناصر الصوتية أدناه مأخوذة من صفحة المكتبة الرسمية، لا من تخمين اسم الملف.

| العنصر داخل التطبيق | اسم المؤثر المنشور | المدة الفعلية (ث) | ملف المورد | الملف الأصلي |
|---|---|---:|---|---|
| Thunder Blade | Electric whoosh | 3.16 | `energy_mixkit_2596.mp3` | [Mixkit 2596](https://assets.mixkit.co/active_storage/sfx/2596/2596-preview.mp3) |
| Golden Scepter | Electronics power up | 5.07 | `energy_mixkit_2602.mp3` | [Mixkit 2602](https://assets.mixkit.co/active_storage/sfx/2602/2602-preview.mp3) |
| Void Staff | Small electric glitch | 1.07 | `energy_mixkit_2595.mp3` | [Mixkit 2595](https://assets.mixkit.co/active_storage/sfx/2595/2595-preview.mp3) |
| Ice Scepter | Low electricity humming | 19.23 | `energy_mixkit_2132.mp3` | [Mixkit 2132](https://assets.mixkit.co/active_storage/sfx/2132/2132-preview.mp3) |
| Crimson Mace | Heavy electric shockwave impact | 6.69 | `energy_mixkit_2599.mp3` | [Mixkit 2599](https://assets.mixkit.co/active_storage/sfx/2599/2599-preview.mp3) |
| Lightning Orb | Electricity static power up | 2.85 | `energy_mixkit_2600.mp3` | [Mixkit 2600](https://assets.mixkit.co/active_storage/sfx/2600/2600-preview.mp3) |
| Electric Gauntlet | Static electric crackles | 1.93 | `energy_mixkit_1455.mp3` | [Mixkit 1455](https://assets.mixkit.co/active_storage/sfx/1455/1455-preview.mp3) |
| Storm Spear | Futuristic electricity sparks | 2.85 | `energy_mixkit_2598.mp3` | [Mixkit 2598](https://assets.mixkit.co/active_storage/sfx/2598/2598-preview.mp3) |
| Tesla Rod | Electric charge hum | 2.53 | `energy_mixkit_3201.mp3` | [Mixkit 3201](https://assets.mixkit.co/active_storage/sfx/3201/3201-preview.mp3) |
| Lightning Whip | Screechy electrified item | 2.04 | `energy_mixkit_3213.mp3` | [Mixkit 3213](https://assets.mixkit.co/active_storage/sfx/3213/3213-preview.mp3) |
| Shadow Claw | Electric buzz glitch | 1.07 | `energy_mixkit_2594.mp3` | [Mixkit 2594](https://assets.mixkit.co/active_storage/sfx/2594/2594-preview.mp3) |
| Holy Hammer | Electric welding and sparkles | 7.97 | `energy_mixkit_2603.mp3` | [Mixkit 2603](https://assets.mixkit.co/active_storage/sfx/2603/2603-preview.mp3) |
| Crystal Staff | Static electricity presentation | 4.02 | `energy_mixkit_2592.mp3` | [Mixkit 2592](https://assets.mixkit.co/active_storage/sfx/2592/2592-preview.mp3) |
| Plasma Staff | Electricity reactor buzz | 12.09 | `energy_mixkit_904.mp3` | [Mixkit 904](https://assets.mixkit.co/active_storage/sfx/904/904-preview.mp3) |
| Dragon Staff | Electricity intense hum | 48.54 | `energy_mixkit_2138.mp3` | [Mixkit 2138](https://assets.mixkit.co/active_storage/sfx/2138/2138-preview.mp3) |
| Nature Lightning | Electric static | 2.46 | `energy_mixkit_1453.mp3` | [Mixkit 1453](https://assets.mixkit.co/active_storage/sfx/1453/1453-preview.mp3) |
| Chaos Staff | Futuristic device fail | 3.66 | `energy_mixkit_2939.mp3` | [Mixkit 2939](https://assets.mixkit.co/active_storage/sfx/2939/2939-preview.mp3) |
| Electric Fan | Electricity buzz | 12.38 | `energy_mixkit_910.mp3` | [Mixkit 910](https://assets.mixkit.co/active_storage/sfx/910/910-preview.mp3) |
| Cosmic Staff | Intense electricity humming | 28.84 | `energy_mixkit_2131.mp3` | [Mixkit 2131](https://assets.mixkit.co/active_storage/sfx/2131/2131-preview.mp3) |
| Dual Lightning Blades | Static electric glitch | 1.54 | `energy_mixkit_2597.mp3` | [Mixkit 2597](https://assets.mixkit.co/active_storage/sfx/2597/2597-preview.mp3) |
| Lightning Blaster | Electricity lightning blast | 6.87 | `energy_mixkit_2601.mp3` | [Mixkit 2601](https://assets.mixkit.co/active_storage/sfx/2601/2601-preview.mp3) |
| Energy Vortex | Intense electricity hum | 28.84 | `energy_mixkit_2591.mp3` | [Mixkit 2591](https://assets.mixkit.co/active_storage/sfx/2591/2591-preview.mp3) |
| Lightning Lance | Electricity buzz | 2.95 | `energy_mixkit_2593.mp3` | [Mixkit 2593](https://assets.mixkit.co/active_storage/sfx/2593/2593-preview.mp3) |
| Thunder Tornado | Machine activation short | 2.74 | `energy_mixkit_3180.mp3` | [Mixkit 3180](https://assets.mixkit.co/active_storage/sfx/3180/3180-preview.mp3) |

الأصوات القصيرة لا تتكرر كحلقة مستمرة أثناء الضغط؛ التكرار مستعمل فقط للأصوات المستمرة المناسبة (الطنين/المحرك)، ولمجفف الشعر وعائلة ماكينات القص.

## تسجيلات الأسلحة والصالون

| الاستخدام | التسجيل | المدة الفعلية (ث) | ملف المورد | صفحة الأصل والترخيص |
|---|---|---:|---|---|
| AK-47 | AK-47 Firing | 2.14 | `pixabay_ak47_8760.mp3` | [Pixabay 8760](https://pixabay.com/sound-effects/film-special-effects-ak-47-firing-8760/) |
| Desert Eagle | Desert Eagle Gunshot | 0.89 | `pixabay_desert_eagle_14622.mp3` | [Pixabay 14622](https://pixabay.com/sound-effects/film-special-effects-desert-eagle-gunshot-14622/) |
| عائلة ماكينات القص | Hair Clipper Machine (تسجيل ماكينة عام؛ ليس علامة تجارية محددة) | 21.14 | `pixabay_hair_clipper_63593.mp3` | [Pixabay 63593](https://pixabay.com/sound-effects/film-special-effects-hair-clipper-machine-63593/) |
| المقص | Cut with scissors — Scissors, Barber, Haircut | 6.50 | `pixabay_scissors_barber_411614.mp3` | [Pixabay 411614 — spinopel](https://pixabay.com/sound-effects/film-special-effects-cut-with-scissors-411614/) |
| مجفف الشعر | Hairdryer hum | 27.87 | `barber_mixkit_1894.mp3` | [Mixkit Hair Dryer](https://mixkit.co/free-sound-effects/hair-dryer/) |

## الترخيص والمراجع

- ملفات Mixkit مأخوذة من صفحات المكتبة الرسمية ضمن [Mixkit Free Sound Effects](https://mixkit.co/free-sound-effects/) وتخضع لـ[Mixkit License](https://mixkit.co/license/). احتُفظ بروابط المصادر؛ لا توزّع هذه الملفات منفردة أو تَعِد بملكية حصرية لها.
- ملفات Pixabay مأخوذة من صفحات أصولها الأصلية وتخضع لـ[Pixabay Content License Summary](https://pixabay.com/service/license-summary/). يتيح الملخص الاستخدام المجاني دون إلزام بالإسناد، لكنه يمنع إعادة توزيع المحتوى على أساس مستقل؛ أبقيت الملفات مضمّنة داخل التطبيق لا كحزمة مؤثرات مستقلة.
- استخدمت عناوين الصفحات/الفئات لتحديد طبيعة الصوت، وأجريت فحص سلامة وترميز ومدة لجميع ملفات MP3 الفعالة. المدة الفعلية محسوبة من الملف المحلي باستخدام `ffprobe`.

## Android SDK المستخدم للتحقق

تم تثبيت Android SDK Platform 36 وBuild Tools 36.0.0 وPlatform Tools من مصدر Android الرسمي لاختبار بناء المشروع؛ راجع [Android SDK command-line tools](https://developer.android.com/tools) و[Android SDK Manager](https://developer.android.com/tools/sdkmanager).