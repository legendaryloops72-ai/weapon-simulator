package com.weapon.simulator.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weapon.simulator.audio.ItemSoundController
import com.weapon.simulator.model.AccentCyan
import com.weapon.simulator.model.AccentGold
import com.weapon.simulator.model.AccentPurple
import com.weapon.simulator.model.AccentRed
import com.weapon.simulator.model.Section
import com.weapon.simulator.model.tint
import com.weapon.simulator.model.SimItem
import com.weapon.simulator.model.TextMuted
import com.weapon.simulator.model.ThemeCardPanel
import com.weapon.simulator.model.ThemeCardPanelRaised
import com.weapon.simulator.model.ThemeDarkBg
import kotlin.random.Random

@Composable
fun SettingsScreen(
    safeMode: Boolean,
    onSafeModeChange: (Boolean) -> Unit,
    screenFlash: Boolean,
    onScreenFlashChange: (Boolean) -> Unit,
    vibration: Boolean,
    onVibrationChange: (Boolean) -> Unit,
    flashStrength: Float,
    onFlashStrengthChange: (Float) -> Unit,
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            UtilityTopBar(title = "Settings", subtitle = "SIMULATOR PREFERENCES", accent = AccentCyan, onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            // Effects Panel
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ThemeCardPanel),
                modifier = Modifier.fillMaxWidth().border(1.dp, ThemeCardPanelRaised, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "VISUAL & AUDIO EFFECTS",
                        color = AccentCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    SettingToggleItem(
                        title = "Screen Flash",
                        description = "Displays rapid weapon discharge illumination",
                        icon = Icons.Default.FlashOn,
                        checked = screenFlash,
                        onCheckedChange = onScreenFlashChange
                    )

                    HorizontalDivider(color = Color(0xFF1E2638), modifier = Modifier.padding(vertical = 10.dp))

                    SettingToggleItem(
                        title = "Haptic Vibration",
                        description = "Tactile vibration pulse when holding weapons or tools",
                        icon = Icons.Default.Vibration,
                        checked = vibration,
                        onCheckedChange = onVibrationChange
                    )

                    HorizontalDivider(color = Color(0xFF1E2638), modifier = Modifier.padding(vertical = 10.dp))

                    SettingToggleItem(
                        title = "Safe Effects Mode",
                        description = "Reduces flashing intensity for visual comfort",
                        icon = Icons.Default.Bolt,
                        checked = safeMode,
                        onCheckedChange = onSafeModeChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "FLASH INTENSITY (${(flashStrength * 100).toInt()}%)",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = flashStrength,
                        onValueChange = onFlashStrengthChange,
                        colors = SliderDefaults.colors(
                            thumbColor = AccentCyan,
                            activeTrackColor = AccentCyan,
                            inactiveTrackColor = Color(0xFF1E2638)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Localization & About Panel
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ThemeCardPanel),
                modifier = Modifier.fillMaxWidth().border(1.dp, ThemeCardPanelRaised, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "APP INFORMATION",
                        color = AccentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Language: English / العربية",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Offline SoundPool audio engine loaded with 50 high-fidelity sound effects.",
                        color = TextMuted,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingToggleItem(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f).padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF192236)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(description, color = TextMuted, fontSize = 11.sp, lineHeight = 14.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AccentCyan,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = Color(0xFF1E2638)
            )
        )
    }
}

@Composable
fun MiniGamesScreen(
    soundController: ItemSoundController,
    onPerformHaptic: () -> Unit,
    onBack: () -> Unit
) {
    var score by remember { mutableIntStateOf(0) }
    var streak by remember { mutableIntStateOf(0) }
    var targetIndex by remember { mutableIntStateOf(Random.nextInt(0, 9)) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            UtilityTopBar(title = "Mini Games", subtitle = "TACTICAL REFLEX TARGETS", accent = Color(0xFF8AA5FF), onBack = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            // Score Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(ThemeCardPanel)
                    .border(1.dp, Color(0xFF263352), RoundedCornerShape(18.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TARGET SCORE", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("$score", color = AccentGold, fontSize = 28.sp, fontWeight = FontWeight.Black)
                }
                Box(modifier = Modifier.width(1.dp).height(36.dp).background(Color(0xFF263352)))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("HIT STREAK", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("$streak", color = AccentCyan, fontSize = 28.sp, fontWeight = FontWeight.Black)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "TAP THE ACTIVE GLOWING TARGET TO FIRE",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))

            // 3x3 Target Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (col in 0..2) {
                            val index = row * 3 + col
                            val isActive = index == targetIndex
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(
                                        if (isActive) Brush.radialGradient(listOf(AccentRed.copy(alpha = 0.4f), ThemeCardPanel))
                                        else Brush.radialGradient(listOf(ThemeCardPanelRaised, ThemeCardPanel))
                                    )
                                    .border(
                                        width = if (isActive) 2.dp else 1.dp,
                                        color = if (isActive) AccentRed else Color(0xFF1E2840),
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .clickable {
                                        if (isActive) {
                                            score += 10
                                            streak += 1
                                            onPerformHaptic()
                                            soundController.playRandomWeapon()
                                            targetIndex = Random.nextInt(0, 9)
                                        } else {
                                            streak = 0
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (isActive) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = "Active Target",
                                        tint = AccentRed,
                                        modifier = Modifier.size(36.dp)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF2A3654))
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    score = 0
                    streak = 0
                    targetIndex = Random.nextInt(0, 9)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ThemeCardPanelRaised),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reset Game", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GalleryScreen(
    items: List<SimItem>,
    soundController: ItemSoundController,
    onPerformHaptic: () -> Unit,
    onBack: () -> Unit
) {
    var selectedSection by remember { mutableStateOf<Section?>(null) }
    val displayedItems = if (selectedSection == null) items else items.filter { it.section == selectedSection }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            UtilityTopBar(
                title = "3D Gallery",
                subtitle = "ALL 81 WEAPON & TOOL MODELS",
                accent = Color(0xFF55C7FF),
                onBack = onBack,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Section Filter Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedSection == null,
                    onClick = { selectedSection = null },
                    label = { Text("All (81)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentCyan.copy(alpha = 0.2f),
                        selectedLabelColor = Color.White
                    )
                )
                Section.entries.forEach { sec ->
                    FilterChip(
                        selected = selectedSection == sec,
                        onClick = { selectedSection = sec },
                        label = { Text("${sec.title} (${items.count { it.section == sec }})", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = sec.tint.copy(alpha = 0.2f),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 110.dp),
                contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayedItems, key = { it.name }) { item ->
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, item.section.tint.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .background(ThemeCardPanel)
                            .clickable {
                                onPerformHaptic()
                                soundController.play(item)
                            }
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(item.section.tint.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(item.drawableRes),
                                contentDescription = item.name,
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.name,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = item.category,
                            color = item.section.tint,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumScreen(onBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            UtilityTopBar(title = "Premium", subtitle = "VIP SIMULATOR EXPERIENCE", accent = AccentGold, onBack = onBack)

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E170A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, AccentGold, RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(AccentGold.copy(alpha = 0.2f))
                            .border(1.5.dp, AccentGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("VIP MEMBERSHIP", color = AccentGold, fontSize = 22.sp, fontWeight = FontWeight.Black)
                    Text("Unlimited high-tech simulation effects", color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(18.dp))
                    HorizontalDivider(color = Color(0xFF332714))
                    Spacer(modifier = Modifier.height(16.dp))

                    PremiumFeatureRow("Unlock All 81 Weapon & Tool Skins")
                    PremiumFeatureRow("Exclusive Ultra-Bass Gunfire Sounds")
                    PremiumFeatureRow("Continuous High-Speed Clipper FX")
                    PremiumFeatureRow("Infinite Reflex Mini Game Modes")
                    PremiumFeatureRow("Zero Ads & Complete Offline Access")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = AccentGold),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("All Features Unlocked", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumFeatureRow(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Check, contentDescription = null, tint = AccentGold, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun UtilityTopBar(
    title: String,
    subtitle: String,
    accent: Color,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(ThemeCardPanel)
                .border(1.dp, accent.copy(alpha = 0.5f), CircleShape)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title.uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
            Text(
                text = subtitle,
                color = accent,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
    }
}
