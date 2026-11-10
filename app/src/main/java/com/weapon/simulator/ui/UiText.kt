package com.weapon.simulator.ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text as MaterialText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

val LocalAppLanguage = compositionLocalOf { "ar" }

private val arabicLabels = mapOf(
    "home" to "الرئيسية", "weapons" to "الأسلحة", "energy tools" to "أدوات الطاقة",
    "barber tools" to "أدوات الحلاقة", "favorites" to "المفضلة", "mini games" to "ألعاب مصغرة",
    "gallery" to "المعرض", "3d gallery" to "معرض ثلاثي الأبعاد", "settings" to "الإعدادات",
    "premium" to "النسخة المميزة", "menu" to "القائمة", "back" to "رجوع",
    "weapons simulator" to "محاكي الأسلحة", "barber simulator" to "محاكي الحلاقة",
    "all" to "الكل", "pistols" to "المسدسات", "assault rifles" to "البنادق الهجومية",
    "smg" to "الرشاشات الخفيفة", "shotguns" to "البنادق الخرطوشية", "snipers" to "بنادق القنص",
    "special" to "أسلحة خاصة", "lmg" to "الرشاشات المتوسطة", "laser" to "الليزر",
    "taser" to "الصعق الكهربائي", "electric" to "كهربائي", "fantasy" to "خيالي",
    "clippers" to "ماكينات الحلاقة", "scissors" to "المقصات", "shaving" to "الحلاقة", "salon" to "أدوات الصالون",
    "saved items" to "العناصر المحفوظة", "no favorites saved yet" to "لا توجد عناصر مفضلة بعد",
    "no items found" to "لم يتم العثور على عناصر",
    "tap the heart icon on any weapon or tool to save it here." to "اضغط على رمز القلب لأي سلاح أو أداة لحفظه هنا.",
    "try selecting another category." to "جرّب اختيار تصنيف آخر.",
    "settings & safe mode" to "الإعدادات والوضع الآمن", "premium vip edition" to "إصدار VIP المميز",
    "visual & audio effects" to "المؤثرات المرئية والصوتية", "screen flash" to "وميض الشاشة",
    "displays rapid weapon discharge illumination" to "يعرض إضاءة متزامنة مع المؤثرات",
    "haptic vibration" to "الاهتزاز اللمسي", "tactile vibration pulse when holding weapons or tools" to "اهتزاز عند الضغط على الأسلحة أو الأدوات",
    "safe effects mode" to "وضع المؤثرات الآمن", "reduces flashing intensity for visual comfort" to "يقلل شدة الوميض لراحة العين",
    "app information" to "معلومات التطبيق", "language: english / العربية" to "اللغة",
    "use the en / عربي button in the home header to switch language." to "استخدم زر EN / عربي في أعلى الشاشة لتبديل اللغة.",
    "47 realistic weapons" to "47 سلاحاً متنوعاً",
    "24 electric effects" to "24 مؤثراً كهربائياً",
    "27 professional tools" to "27 أداة احترافية",
    "offline soundpool audio engine loaded with 50 high-fidelity sound effects." to "محرك صوت يعمل دون اتصال بالإنترنت.",
    "flash intensity" to "شدة الوميض", "simulator preferences" to "تفضيلات المحاكي",
    "target score" to "النقاط", "hit streak" to "الإصابات المتتالية",
    "tap the active glowing target to fire" to "اضغط على الهدف المضيء لإصابته",
    "reset game" to "إعادة اللعبة", "tactical reflex targets" to "أهداف اختبار سرعة الاستجابة",
    "vip simulator experience" to "تجربة المحاكي المميزة", "vip membership" to "عضوية VIP",
    "simulator preferences" to "تفضيلات المحاكي", "visual & audio effects" to "المؤثرات المرئية والصوتية",
    "app information" to "معلومات التطبيق", "tactical reflex targets" to "أهداف اختبار سرعة الاستجابة",
    "target score" to "النقاط", "hit streak" to "الإصابات المتتالية",
    "tap the active glowing target to fire" to "اضغط على الهدف المضيء لإصابته",
    "active target" to "الهدف النشط", "reset game" to "إعادة اللعبة", "playing" to "قيد التشغيل",
    "hold to fire" to "اضغط باستمرار للتشغيل", "unlimited high-tech simulation effects" to "مؤثرات محاكاة متقدمة بلا حدود",
    "exclusive ultra-bass gunfire sounds" to "مؤثرات إطلاق نار حصرية",
    "continuous high-speed clipper fx" to "مؤثرات ماكينات حلاقة مستمرة",
    "infinite reflex mini game modes" to "أنماط ألعاب سرعة استجابة متعددة",
    "zero ads & complete offline access" to "دون إعلانات مع إمكانية العمل دون إنترنت",
    "unlimited high-tech simulation effects" to "مؤثرات محاكاة متقدمة بلا حدود",
    "all features unlocked" to "جميع المزايا متاحة",
    "unlock all 81 weapon & tool skins" to "فتح جميع أشكال الأسلحة والأدوات",
    "unlock all 98 weapon & tool skins" to "فتح جميع أشكال الأسلحة والأدوات الـ98",
    "exclusive ultra-bass gunfire sounds" to "مؤثرات إطلاق نار حصرية",
    "continuous high-speed clipper fx" to "مؤثرات ماكينات حلاقة مستمرة",
    "infinite reflex mini game modes" to "أنماط ألعاب سرعة استجابة متعددة",
    "zero ads & complete offline access" to "دون إعلانات مع إمكانية العمل دون إنترنت",
    "weapon & barber simulator" to "محاكي الأسلحة والحلاقة",
    "weapons simulator (30 models)" to "محاكي الأسلحة (47 سلاحاً)",
    "energy tools (24 effects)" to "أدوات الطاقة (24 مؤثراً)",
    "barber simulator (27 tools)" to "محاكي الحلاقة (27 أداة)",
    "3d gallery" to "معرض ثلاثي الأبعاد", "version 0.1.0 • offline ready sfx" to "الإصدار 0.1.0 • مؤثرات صوتية دون إنترنت",
    "ak-47" to "AK-47", "m4a1" to "M4A1", "desert eagle" to "نسر الصحراء",
    "awm sniper" to "قناصة AWM", "shotgun" to "بندقية خرطوش", "minigun" to "ميني غان",
    "glock 19" to "غلوك 19", "beretta 92" to "بيريتا 92", "revolver" to "مسدس دوار",
    "uzi" to "عوزي", "mp5" to "MP5", "p90" to "P90", "vector smg" to "فيكتور",
    "scar-l" to "SCAR-L", "aug a3" to "AUG A3", "famas" to "فاماس", "g36" to "G36",
    "m16" to "M16", "tactical pistol" to "مسدس تكتيكي", "usp tactical" to "USP تكتيكي",
    "spas-12" to "SPAS-12", "pump shotgun" to "خرطوش بمضخة", "barrett m82" to "باريت M82",
    "dragunov svd" to "دراغونوف SVD", "thompson" to "تومبسون", "kriss vector" to "كريس فيكتور",
    "laser gun" to "مسدس ليزر", "plasma rifle" to "بندقية بلازما", "energy cannon" to "مدفع طاقة",
    "neon rifle" to "بندقية نيون",
    "thunder blade" to "نصل الرعد", "golden scepter" to "صولجان ذهبي", "void staff" to "عصا الفراغ",
    "ice scepter" to "صولجان الجليد", "crimson mace" to "هراوة قرمزية", "lightning orb" to "كرة البرق",
    "electric gauntlet" to "قفاز كهربائي", "storm spear" to "رمح العاصفة", "tesla rod" to "عصا تسلا",
    "lightning whip" to "سوط البرق", "shadow claw" to "مخلب الظل", "holy hammer" to "مطرقة مقدسة",
    "crystal staff" to "عصا الكريستال", "plasma staff" to "عصا البلازما", "dragon staff" to "عصا التنين",
    "nature lightning" to "برق الطبيعة", "chaos staff" to "عصا الفوضى", "electric fan" to "مروحة كهربائية",
    "cosmic staff" to "عصا كونية", "dual lightning blades" to "نصلان صاعقان", "lightning blaster" to "قاذف البرق",
    "energy vortex" to "دوامة الطاقة", "lightning lance" to "رمح البرق", "thunder tornado" to "إعصار الرعد",
    "hair clipper" to "ماكينة حلاقة الشعر", "professional clipper" to "ماكينة احترافية",
    "classic clipper" to "ماكينة كلاسيكية", "beard trimmer" to "ماكينة تهذيب اللحية",
    "hair trimmer" to "ماكينة تشذيب الشعر", "mini trimmer" to "ماكينة صغيرة",
    "vintage clipper" to "ماكينة كلاسيكية قديمة", "power clipper" to "ماكينة قوية",
    "metal clipper" to "ماكينة معدنية", "premium clipper" to "ماكينة فاخرة",
    "cordless clipper" to "ماكينة لاسلكية", "barber pro x" to "باربر برو X",
    "titanium clipper" to "ماكينة تيتانيوم", "gold master" to "غولد ماستر",
    "chrome master" to "كروم ماستر", "carbon fiber clipper" to "ماكينة ألياف الكربون",
    "neon clipper" to "ماكينة نيون", "cyber trimmer" to "ماكينة سايبر",
    "turbo clipper" to "ماكينة توربو", "ultimate clipper" to "ماكينة ألتيميت",
    "electric shaver" to "آلة حلاقة كهربائية", "hair dryer" to "مجفف شعر",
    "spray bottle" to "زجاجة رش", "shaving foam" to "رغوة حلاقة", "comb" to "مشط", "barber brush" to "فرشاة حلاقة",
    "tactical fixed-stock rifle" to "بندقية تكتيكية بمخزون ثابت",
    "m4-style assault rifle" to "بندقية هجومية بنمط M4", "tactical bullpup rifle" to "بندقية بولببب تكتيكية",
    "top-optic assault rifle" to "بندقية هجومية بمنظار علوي", "green scoped bullpup rifle" to "بندقية بولببب خضراء بمنظار",
    "wood ak rifle" to "بندقية AK بمخزون خشبي", "scoped bullpup rifle" to "بندقية بولببب بمنظار",
    "folding-stock assault rifle" to "بندقية هجومية بمخزون قابل للطي", "classic assault rifle" to "بندقية هجومية كلاسيكية",
    "compact assault rifle" to "بندقية هجومية قصيرة", "long-barrel automatic rifle" to "بندقية آلية بسبطانة طويلة",
    "bipod light machine gun" to "رشاش خفيف بحامل ثنائي", "scoped assault rifle" to "بندقية هجومية بمنظار",
    "bipod automatic rifle" to "بندقية آلية بحامل ثنائي", "wood rifle with ported barrel" to "بندقية خشبية بسبطانة مثقبة",
    "wood-stock light machine gun" to "رشاش خفيف بمخزون خشبي", "folding-stock machine gun" to "رشاش آلي بمخزون قابل للطي"
)

fun localizedUiText(value: String, language: String): String {
    if (language != "ar") return value
    val key = value.trim().replace(Regex("\\s+"), " ").lowercase()
    arabicLabels[key]?.let { return it }
    Regex("^(\\d+) realistic weapons$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return it.groupValues[1] + " سلاحاً متنوعاً"
    }
    Regex("^(\\d+) electric effects$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return it.groupValues[1] + " مؤثراً كهربائياً"
    }
    Regex("^(\\d+) professional tools$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return it.groupValues[1] + " أداة احترافية"
    }
    Regex("^(weapons|energy tools|barber tools) \\((\\d+)\\)$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        val label = when (it.groupValues[1].lowercase()) {
            "weapons" -> "الأسلحة"
            "energy tools" -> "أدوات الطاقة"
            else -> "أدوات الحلاقة"
        }
        return label + " (" + it.groupValues[2] + ")"
    }
    Regex("^favorite (.+)$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return "المفضلة: " + localizedUiText(it.groupValues[1], language)
    }
    Regex("^flash intensity \\((\\d+)%\\)$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return "شدة الوميض (" + it.groupValues[1] + "%)"
    }
    Regex("^all \\((\\d+)\\)$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return "الكل (" + it.groupValues[1] + ")"
    }
    Regex("^all (\\d+) weapon & tool models$", RegexOption.IGNORE_CASE).matchEntire(value.trim())?.let {
        return "جميع الأسلحة والأدوات (" + it.groupValues[1] + ")"
    }
    return value
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    MaterialText(
        text = localizedUiText(text, LocalAppLanguage.current),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}
