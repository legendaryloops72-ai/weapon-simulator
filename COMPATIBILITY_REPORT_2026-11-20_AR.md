# تقرير إصلاح توافق اتجاه الشاشة — Weapon Simulator

**تاريخ الفحص:** 20 نوفمبر 2026

**الحزمة المبنية:** `com.simulator.weaponbarber` — `versionCode=3` — `versionName=0.1.0`

## الخلاصة والسبب

كان النشاط الرئيسي يعلن في `AndroidManifest.xml` عن `android:screenOrientation="portrait"`. هذا يفرض اتجاهًا عموديًا ثابتًا، ويمكن أن يجعل Android/Google Play يستنتج أن `android.hardware.screen.portrait` ميزة مطلوبة، ما يقيّد ظهور التطبيق على بعض تكوينات الأجهزة. لم يكن هناك تصريح صريح مستقل بميزة `android.hardware.screen.portrait`، ولم يُعثر على قفل اتجاه برمجي آخر.

أُزيل قيد الاتجاه من النشاط الرئيسي، ولم تُضف تصريحات دعم للأجهزة اللوحية أو Android XR. أصبح النظام قادرًا على تدوير النشاط، مع تحديث الواجهات ذات الافتراضات الأشد تأثرًا بالعرض والارتفاع.

## الملفات التي عُدّلت

| الملف | التعديل |
|---|---|
| `app/src/main/AndroidManifest.xml` | إزالة `screenOrientation="portrait"` فقط؛ إبقاء الأذونات والميزات الحالية كما هي. |
| `app/build.gradle.kts` | مواءمة `applicationId` مع الحزمة المطلوبة `com.simulator.weaponbarber` وتعيين `versionCode=3` بطلب المستخدم؛ إبقاء `namespace=com.weapon.simulator` و`versionName=0.1.0` وإعدادات التوقيع دون تغيير. |
| `app/src/main/java/com/weapon/simulator/MainActivity.kt` | تكييف مساحة صورة شاشة البداية كي لا تستهلك ارتفاعًا زائدًا في الوضع الأفقي. |
| `app/src/main/java/com/weapon/simulator/ui/HomeScreen.kt` | إعادة ترتيب الصفحة الرئيسية إلى لوحتين في النوافذ بعرض 600dp فأكثر، وتحديد أقصى عرض وتقليل ارتفاع البانر؛ يبقى تركيب الهاتف الأصلي كما هو. |
| `app/src/main/java/com/weapon/simulator/ui/CatalogScreen.kt` | تحديد عرض المحتوى وتكييف الحد الأدنى لحجم خلايا الشبكة في العرض الواسع. |
| `app/src/main/java/com/weapon/simulator/ui/UtilityScreens.kt` | جعل لعبة Mini Games قابلة لإعادة الترتيب أفقيًا في النوافذ الواسعة والتمرير رأسيًا في المساحات القصيرة، وتحويل مرشحات المعرض إلى صف قابل للتمرير، وتوسيط/تحديد عرض الإعدادات وPremium. |
| `app/src/main/java/com/weapon/simulator/ui/ResponsiveLayoutPolicy.kt` | سياسة مشتركة ومختبرة لعتبة العرض وأحجام الخلايا. |
| `app/src/test/java/com/weapon/simulator/ui/ResponsiveLayoutPolicyTest.kt` | اختبارات عتبة 600dp والحفاظ على سياسة الهاتف وأحجام الخلايا الواسعة. |

لم تُحذف صور أو أصوات أو أقسام، ولم تتغير إعدادات JKS/التوقيع داخل Gradle. أُبقيت `namespace` و`versionName` كما هما. تم تغيير `applicationId` إلى اسم الحزمة المطلوب، وتغيير رقم الإصدار إلى 3 بناءً على أحدث تعليمات المستخدم.

## فحص Manifest وميزات الجهاز

فُحص Manifest المدمج داخل AAB، وكانت النتيجة:

| العنصر | النتيجة |
|---|---|
| اسم الحزمة | `com.simulator.weaponbarber` |
| `versionCode` | `3` |
| `versionName` | `0.1.0` |
| `android:screenOrientation` / `requestedOrientation` | غير موجودين في التطبيق |
| `android.hardware.screen.portrait` | غير معلنة كميزة مطلوبة |
| `android.hardware.camera` | موجودة مع `required=false` |
| `android.hardware.camera.flash` | موجودة مع `required=false` |

لم تُضف ميزة أو متطلب جهاز جديد. إزالة القفل تسمح بتغيير الاتجاه، لكنها لا تُعد ادعاءً بأن جميع الأجهزة اللوحية أو Android XR قد خضعت للاختبار.

## التخطيطات والاختبارات

حافظت تغييرات الهاتف على المسار الضيق، وأضافت سياسة إعادة ترتيب ابتداءً من 600dp. تغطي اختبارات الوحدة عتبة الانتقال بين التخطيط الضيق والواسع وأحجام خلايا الكتالوج والمعرض. نتيجة `:app:testDebugUnitTest`: **5 اختبارات، 0 إخفاق، 0 أخطاء، 0 متخطاة**.

نجح `:app:bundleRelease` في Gradle. كما اجتاز الملف اختبار سلامة ZIP وتحقق `bundletool validate`. كانت حزمة Gradle الخام غير موقعة؛ لذلك وُقعت النسخة النهائية بالمفتاح المرفق دون تغيير إعدادات توقيع Gradle. تحقق `jarsigner` من التوقيع، وتطابقت بصمة SHA-256 للشهادة مع AAB السابق: `90:D4:E7:88:DA:7B:C5:85:40:06:96:D6:CD:11:82:A9:D8:1A:98:24:FB:99:B5:4E:25:EB:17:FE:AF:5A:64:9D`.

**قيد التحقق البصري:** لم يتوفر Android Emulator أو جهاز فعلي في بيئة التنفيذ (`/dev/kvm` غير متاح ولم يكن المحاكي مثبتًا)، لذلك لم تُجرَ اختبارات عرض فعلية/لقطات شاشة على هاتف أو جهاز لوحي. اختبارات العرض هنا هي اختبارات سياسة المقاسات وتجميع التطبيق؛ يوصى بفحص يدوي نهائي على هاتف عمودي وأفقي وعلى جهاز لوحي قبل إعلان توافق واسع. ولم يظهر تقرير Play Console كملف مستقل في مساحة العمل؛ استند تحليل السبب إلى وصف الرسالة وفحص Manifest المدمج.

## المخرجات

- AAB موقّع: `weapon-simulator-v3.aab`.
- مصدر المشروع يتضمن التعديلات والاختبارات أعلاه، مع إبقاء الأصول وإعدادات التوقيع دون تغيير.
