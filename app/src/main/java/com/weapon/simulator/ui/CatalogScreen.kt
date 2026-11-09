package com.weapon.simulator.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weapon.simulator.audio.ItemSoundController
import com.weapon.simulator.model.AccentGold
import com.weapon.simulator.model.AccentRed
import com.weapon.simulator.model.Section
import com.weapon.simulator.model.tint
import com.weapon.simulator.model.SimItem
import com.weapon.simulator.model.TextMuted
import com.weapon.simulator.model.ThemeCardPanel
import com.weapon.simulator.model.ThemeCardPanelRaised
import com.weapon.simulator.model.ThemeDarkBg

@Composable
fun CatalogScreen(
    section: Section?,
    items: List<SimItem>,
    favorites: Set<String>,
    soundController: ItemSoundController,
    onToggleFavorite: (String) -> Unit,
    onPerformHaptic: () -> Unit,
    onBack: () -> Unit
) {
    var selectedCategory by remember(section) { mutableStateOf("All") }
    var activeItemName by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        onDispose { soundController.stop() }
    }

    val title = section?.title ?: "Favorites"
    val subtitle = section?.subtitle ?: "SAVED ITEMS"
    val accent = section?.tint ?: AccentGold

    val categories = when (section) {
        Section.Weapons -> listOf("All", "Pistols", "Assault Rifles", "SMG", "Shotguns", "Snipers", "Special")
        Section.Energy -> listOf("All", "Laser", "Taser", "Electric", "Fantasy")
        Section.Barber -> listOf("All", "Clippers", "Scissors", "Shaving", "Salon")
        null -> listOf("All")
    }

    val filteredItems = if (selectedCategory == "All") {
        items
    } else {
        items.filter { it.category == selectedCategory }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeDarkBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(ThemeCardPanel)
                        .border(1.dp, accent.copy(alpha = 0.5f), CircleShape)
                        .clickable(onClick = onBack)
                        .testTag("catalog_back_button"),
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

                Column(modifier = Modifier.weight(1f)) {
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

                Text(
                    text = "${filteredItems.size.toString().padStart(2, '0')}",
                    color = accent,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }

            // Categories Filter Chips Row
            if (categories.size > 1) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    items(categories.size) { index ->
                        val cat = categories[index]
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accent.copy(alpha = 0.2f),
                                selectedLabelColor = Color.White,
                                labelColor = TextMuted
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedCategory == cat,
                                borderColor = ThemeCardPanelRaised,
                                selectedBorderColor = accent
                            )
                        )
                    }
                }
            }

            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = accent,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (section == null) "No favorites saved yet" else "No items found",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (section == null) "Tap the heart icon on any weapon or tool to save it here." else "Try selecting another category.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 140.dp),
                    contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredItems, key = { it.name }) { item ->
                        CatalogItemCard(
                            item = item,
                            accent = item.section.tint,
                            isFavorite = item.name in favorites,
                            isActive = activeItemName == item.name,
                            onToggleFavorite = { onToggleFavorite(item.name) },
                            onPress = {
                                activeItemName = item.name
                                onPerformHaptic()
                                soundController.play(item)
                            },
                            onRelease = {
                                soundController.stop()
                                activeItemName = null
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CatalogItemCard(
    item: SimItem,
    accent: Color,
    isFavorite: Boolean,
    isActive: Boolean,
    onToggleFavorite: () -> Unit,
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    val cardBgColor by animateColorAsState(
        targetValue = if (isActive) accent.copy(alpha = 0.25f) else ThemeCardPanel,
        label = "item_card_bg"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isActive) 1.5.dp else 1.dp,
                color = accent.copy(alpha = if (isActive) 0.95f else 0.55f),
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Visual Preview Box with Hold-to-play gesture
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(
                                accent.copy(alpha = if (isActive) 0.38f else 0.18f),
                                Color(0xFF090E18),
                                Color(0xFF0B101B)
                            )
                        )
                    )
                    .pointerInput(item.name) {
                        detectTapGestures(onPress = {
                            onPress()
                            try {
                                awaitRelease()
                            } finally {
                                onRelease()
                            }
                        })
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(item.drawableRes),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    contentScale = ContentScale.Fit
                )

                // Category Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = item.category.uppercase(),
                        color = accent,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                // Play icon pill in bottom center while active
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 6.dp)
                            .clip(CircleShape)
                            .background(accent)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "PLAYING",
                            color = Color.Black,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Name and Favorite Heart Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite ${item.name}",
                        tint = if (isFavorite) AccentRed else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom action hint row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "HOLD TO FIRE",
                    color = accent,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.18f))
                        .border(1.dp, accent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
