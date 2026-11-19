package com.weapon.simulator.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weapon.simulator.R
import com.weapon.simulator.model.AccentGold
import com.weapon.simulator.model.AccentPurple
import com.weapon.simulator.model.AccentRed
import com.weapon.simulator.model.Destination
import com.weapon.simulator.model.Section
import com.weapon.simulator.model.TextMuted
import com.weapon.simulator.model.ThemeCardPanel
import com.weapon.simulator.model.ThemeCardPanelRaised
import com.weapon.simulator.model.ThemeDarkBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateSection: (Section) -> Unit,
    onNavigateDestination: (Destination) -> Unit
) {
    var showMenuSheet by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val wideLayout = ResponsiveLayoutPolicy.usesWideLayout(maxWidth.value.toInt())
            Column(
                modifier = Modifier
                    .widthIn(max = ResponsiveLayoutPolicy.MaxContentWidthDp.dp)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (wideLayout) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1.15f)) {
                            HomeHeroHeader(
                                onMenuClick = { showMenuSheet = true },
                                onSettingsClick = { onNavigateDestination(Destination.Settings) },
                                compactForWide = true
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            HomeTwoCardsRow(
                                onWeaponsClick = { onNavigateSection(Section.Weapons) },
                                onBarberClick = { onNavigateSection(Section.Barber) }
                            )
                        }
                        HomeQuickAccessRow(
                            onFavoritesClick = { onNavigateDestination(Destination.Favorites) },
                            onMiniGamesClick = { onNavigateDestination(Destination.MiniGames) },
                            onGalleryClick = { onNavigateDestination(Destination.Gallery) },
                            onPremiumClick = { onNavigateDestination(Destination.Premium) },
                            wideLayout = true,
                            modifier = Modifier.weight(0.85f)
                        )
                    }
                    HomeBottomConsole(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp))
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HomeHeroHeader(
                            onMenuClick = { showMenuSheet = true },
                            onSettingsClick = { onNavigateDestination(Destination.Settings) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        HomeTwoCardsRow(
                            onWeaponsClick = { onNavigateSection(Section.Weapons) },
                            onBarberClick = { onNavigateSection(Section.Barber) }
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        HomeQuickAccessRow(
                            onFavoritesClick = { onNavigateDestination(Destination.Favorites) },
                            onMiniGamesClick = { onNavigateDestination(Destination.MiniGames) },
                            onGalleryClick = { onNavigateDestination(Destination.Gallery) },
                            onPremiumClick = { onNavigateDestination(Destination.Premium) }
                        )
                    }
                    HomeBottomConsole(modifier = Modifier.fillMaxWidth().padding(top = 10.dp))
                }
            }

            if (showMenuSheet) {
                val sheetState = rememberModalBottomSheetState()
                ModalBottomSheet(
                    onDismissRequest = { showMenuSheet = false },
                    sheetState = sheetState,
                    containerColor = Color(0xFF0F1524),
                    contentColor = Color.White
                ) {
                    MenuBottomSheetContent(
                        onNavigateSection = {
                            showMenuSheet = false
                            onNavigateSection(it)
                        },
                        onNavigateDestination = {
                            showMenuSheet = false
                            onNavigateDestination(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeHeroHeader(
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    compactForWide: Boolean = false
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val headerModifier = if (compactForWide) {
            Modifier.fillMaxWidth().height((maxWidth / (1152f / 510f)).coerceAtMost(260.dp))
        } else {
            Modifier.fillMaxWidth().aspectRatio(1152f / 510f)
        }
        Box(modifier = headerModifier) {
        Image(
            painter = painterResource(R.drawable.home_hero_header),
            contentDescription = "Weapon & Barber Simulator Header",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Interactive Menu and Settings Buttons matching the positions in header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onMenuClick)
                    .testTag("home_menu_button"),
                contentAlignment = Alignment.Center
            ) {
                // Subtle glowing touch layer
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSettingsClick)
                    .testTag("home_settings_button"),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
        }
    }
}

@Composable
private fun HomeTwoCardsRow(
    onWeaponsClick: () -> Unit,
    onBarberClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Weapons Simulator Card (Red)
        InteractiveHeroCard(
            drawableRes = R.drawable.card_weapons_full,
            contentDescription = "Weapons Simulator",
            accentColor = AccentRed,
            modifier = Modifier.weight(1f),
            testTag = "card_weapons",
            onClick = onWeaponsClick
        )

        // 2. Barber Simulator Card (Purple)
        InteractiveHeroCard(
            drawableRes = R.drawable.card_barber_full,
            contentDescription = "Barber Simulator",
            accentColor = AccentPurple,
            modifier = Modifier.weight(1f),
            testTag = "card_barber",
            onClick = onBarberClick
        )
    }
}

@Composable
private fun InteractiveHeroCard(
    drawableRes: Int,
    contentDescription: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "card_scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(22.dp))
            .border(1.5.dp, accentColor.copy(alpha = if (isPressed) 1f else 0.85f), RoundedCornerShape(22.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        Image(
            painter = painterResource(drawableRes),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun HomeQuickAccessRow(
    onFavoritesClick: () -> Unit,
    onMiniGamesClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onPremiumClick: () -> Unit,
    wideLayout: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (wideLayout) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickAccessTile(
                    label = "Favorites", icon = Icons.Default.Star,
                    iconTint = Color(0xFFA575FF), borderColor = Color(0xFF2E3D60),
                    modifier = Modifier.weight(1f), testTag = "quick_favorites", onClick = onFavoritesClick
                )
                QuickAccessTile(
                    label = "Mini Games", icon = Icons.Default.SportsEsports,
                    iconTint = Color(0xFF8AA5FF), borderColor = Color(0xFF2E3D60),
                    modifier = Modifier.weight(1f), testTag = "quick_minigames", onClick = onMiniGamesClick
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickAccessTile(
                    label = "Gallery", icon = Icons.Default.Collections,
                    iconTint = Color(0xFF55C7FF), borderColor = Color(0xFF2E3D60),
                    modifier = Modifier.weight(1f), testTag = "quick_gallery", onClick = onGalleryClick
                )
                QuickAccessTile(
                    label = "Premium", icon = Icons.Default.WorkspacePremium,
                    iconTint = AccentGold, borderColor = AccentGold, isGlowing = true,
                    modifier = Modifier.weight(1f), testTag = "quick_premium", onClick = onPremiumClick
                )
            }
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuickAccessTile(
                label = "Favorites", icon = Icons.Default.Star,
                iconTint = Color(0xFFA575FF), borderColor = Color(0xFF2E3D60),
                modifier = Modifier.weight(1f), testTag = "quick_favorites", onClick = onFavoritesClick
            )
            QuickAccessTile(
                label = "Mini Games", icon = Icons.Default.SportsEsports,
                iconTint = Color(0xFF8AA5FF), borderColor = Color(0xFF2E3D60),
                modifier = Modifier.weight(1.05f), testTag = "quick_minigames", onClick = onMiniGamesClick
            )
            QuickAccessTile(
                label = "Gallery", icon = Icons.Default.Collections,
                iconTint = Color(0xFF55C7FF), borderColor = Color(0xFF2E3D60),
                modifier = Modifier.weight(1f), testTag = "quick_gallery", onClick = onGalleryClick
            )
            QuickAccessTile(
                label = "Premium", icon = Icons.Default.WorkspacePremium,
                iconTint = AccentGold, borderColor = AccentGold, isGlowing = true,
                modifier = Modifier.weight(1.05f), testTag = "quick_premium", onClick = onPremiumClick
            )
        }
    }
}

@Composable
private fun QuickAccessTile(
    label: String,
    icon: ImageVector,
    iconTint: Color,
    borderColor: Color,
    isGlowing: Boolean = false,
    modifier: Modifier = Modifier,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "btn_scale"
    )

    Column(
        modifier = modifier
            .scale(scale)
            .height(82.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = if (isGlowing) 1.8.dp else 1.2.dp,
                color = if (isPressed) Color.White else borderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                Brush.verticalGradient(
                    listOf(
                        if (isGlowing) Color(0xFF231B10) else ThemeCardPanelRaised,
                        if (isGlowing) Color(0xFF130F08) else ThemeCardPanel
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HomeBottomConsole(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.home_bottom_console),
        contentDescription = null,
        modifier = modifier.height(44.dp),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun MenuBottomSheetContent(
    onNavigateSection: (Section) -> Unit,
    onNavigateDestination: (Destination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(AccentRed)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "WEAPON & BARBER SIMULATOR",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color(0xFF222B42))
        Spacer(modifier = Modifier.height(12.dp))

        // Navigation Items
        DrawerItem("Weapons Simulator (30 Models)", Icons.Default.Shield, AccentRed) {
            onNavigateSection(Section.Weapons)
        }
        DrawerItem("Barber Simulator (5 Tools)", Icons.Default.ContentCut, AccentPurple) {
            onNavigateSection(Section.Barber)
        }
        DrawerItem("Favorites", Icons.Default.Favorite, AccentRed) {
            onNavigateDestination(Destination.Favorites)
        }
        DrawerItem("Mini Games", Icons.Default.SportsEsports, Color(0xFF8AA5FF)) {
            onNavigateDestination(Destination.MiniGames)
        }
        DrawerItem("3D Gallery", Icons.Default.PhotoLibrary, Color(0xFF55C7FF)) {
            onNavigateDestination(Destination.Gallery)
        }
        DrawerItem("Settings & Safe Mode", Icons.Default.Settings, Color.White) {
            onNavigateDestination(Destination.Settings)
        }
        DrawerItem("Premium VIP Edition", Icons.Default.WorkspacePremium, AccentGold) {
            onNavigateDestination(Destination.Premium)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Version 0.1.0 • Offline Ready SFX",
            color = TextMuted,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DrawerItem(
    title: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 11.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}
