package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.generator.FreeFireDatabase
import com.example.data.model.DeviceBrand
import com.example.data.model.PhoneModel
import com.example.data.model.SensiPreset
import com.example.ui.components.CustomDeviceDialog
import com.example.ui.components.SaveSensiDialog
import com.example.ui.theme.AuraBackground
import com.example.ui.theme.AuraBorder
import com.example.ui.theme.AuraCardElevated
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraFlameOrange
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraRed
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurfaceVariant
import com.example.ui.theme.BodyHitYellow
import com.example.ui.theme.DangerDpiColor
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.SafeDpiColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDarkGray
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningDpiColor
import com.example.ui.viewmodel.AnosAuraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensiGeneratorScreen(
    viewModel: AnosAuraViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentConfig by viewModel.currentConfig.collectAsState()
    val selectedBrand by viewModel.selectedBrand.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val selectedPreset by viewModel.selectedPreset.collectAsState()
    val searchQuery by viewModel.searchModelQuery.collectAsState()
    val showCustomDialog by viewModel.showCustomModelDialog.collectAsState()

    var showSaveDialog by remember { mutableStateOf(false) }

    val modelsForSelectedBrand = remember(selectedBrand, searchQuery) {
        val list = FreeFireDatabase.getModelsForBrand(selectedBrand)
        if (searchQuery.isBlank()) {
            list
        } else {
            list.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    if (showCustomDialog) {
        CustomDeviceDialog(
            onDismiss = { viewModel.closeCustomModelDialog() },
            onConfirm = { brand, model ->
                viewModel.createAndApplyUniversalCustomModel(brand, model)
            }
        )
    }

    if (showSaveDialog) {
        SaveSensiDialog(
            defaultTitle = "${selectedModel.name} - ${selectedPreset.badge}",
            onDismiss = { showSaveDialog = false },
            onConfirm = { title, notes ->
                viewModel.saveCurrentConfiguration(title, notes)
                showSaveDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AuraBackground)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Hero Banner Graphic
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, AuraRed.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.anos_aura_hero_banner),
                    contentDescription = "Anos Aura Free Fire Hero Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 8.5f),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Live Device Auto-Detection Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_device_detector"),
                colors = CardDefaults.cardColors(
                    containerColor = AuraSurfaceVariant
                ),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraCyan.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(AuraCyan.copy(alpha = 0.15f))
                                .border(1.dp, AuraCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = "Device icon",
                                tint = AuraCyan,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Mon Téléphone Actuel",
                                    color = AuraGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SafeDpiColor.copy(alpha = 0.2f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "AUTO",
                                        color = SafeDpiColor,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "${viewModel.deviceManufacturer} ${viewModel.deviceModelName}",
                                color = TextWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.detectAndApplyUserDevice() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraRed
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("btn_detect_my_phone")
                    ) {
                        Text(
                            text = "Calibrer",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Brand Picker Carousel
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "1. Choisir la Marque",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${FreeFireDatabase.allBrands.size} marques",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(FreeFireDatabase.allBrands) { brand ->
                        val isSelected = brand == selectedBrand
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) AuraRed else AuraSurface)
                                .border(
                                    1.dp,
                                    if (isSelected) AuraGold else AuraBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.selectBrand(brand) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .testTag("chip_brand_${brand.brandId}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = brand.emoji, fontSize = 15.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = brand.displayName,
                                    color = if (isSelected) Color.White else TextWhite,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Model Picker & Search
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "2. Choisir le Modèle (${selectedBrand.displayName})",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Universal Custom Device Action
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AuraFlameOrange.copy(alpha = 0.2f))
                            .border(1.dp, AuraFlameOrange, RoundedCornerShape(6.dp))
                            .clickable { viewModel.openCustomModelDialog() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("btn_open_custom_device_dialog")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add custom model",
                                tint = AuraGold,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Autre Modèle...",
                                color = AuraGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setModelSearchQuery(it) },
                    placeholder = { Text("Rechercher un modèle (ex: S24, Note 13, GT 20, Hot 40)...", fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextMutedGray,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_phone_model"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraRed,
                        unfocusedBorderColor = AuraBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedPlaceholderColor = TextMutedGray,
                        unfocusedPlaceholderColor = TextDarkGray
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Horizontal scroll of matching models
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(modelsForSelectedBrand) { model ->
                        val isSelected = model.id == selectedModel.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) AuraCardElevated else AuraSurface)
                                .border(
                                    1.2.dp,
                                    if (isSelected) AuraRed else AuraBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.selectModel(model) }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                                .testTag("model_item_${model.id}")
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = model.name,
                                        color = if (isSelected) AuraGold else TextWhite,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isSelected) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = AuraRed,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = model.recommendedHz,
                                    color = TextMutedGray,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Preset Mode Selector (One-Tap / Tournoi / Rush / Sans DPI)
        item {
            Column {
                Text(
                    text = "3. Profil de Tir (Style de Jeu)",
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SensiPreset.entries.forEach { preset ->
                        val isSelected = preset == selectedPreset
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) {
                                        Brush.verticalGradient(
                                            colors = listOf(AuraRed, AuraFlameOrange)
                                        )
                                    } else {
                                        Brush.verticalGradient(
                                            colors = listOf(AuraSurface, AuraSurfaceVariant)
                                        )
                                    }
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) AuraGold else AuraBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.selectPreset(preset) }
                                .padding(vertical = 10.dp, horizontal = 4.dp)
                                .testTag("btn_preset_${preset.name.lowercase()}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = preset.iconEmoji, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = preset.badge,
                                    color = if (isSelected) Color.White else TextWhite,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sensitivity Values Sliders & Gauges
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_sensi_sliders"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎯", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Sensibilités en Jeu Free Fire (0 - 200)",
                                    color = TextWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Échelle Officielle Garena : Min 0 • Max 200 (One-Tap 185-200)",
                                    color = AuraGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.resetCustomOverrides() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset overrides",
                                tint = AuraGold,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Générale
                    SensiSliderRow(
                        label = "Générale (Caméra)",
                        value = currentConfig.general,
                        icon = "👁️",
                        onValueChange = { viewModel.setCustomGeneral(it) }
                    )

                    // Point Rouge
                    SensiSliderRow(
                        label = "Point Rouge (Red Dot)",
                        value = currentConfig.redDot,
                        icon = "🔴",
                        onValueChange = { viewModel.setCustomRedDot(it) }
                    )

                    // Mire 2X
                    SensiSliderRow(
                        label = "Mire 2X",
                        value = currentConfig.scope2x,
                        icon = "🔭",
                        onValueChange = { viewModel.setCustomScope2x(it) }
                    )

                    // Mire 4X
                    SensiSliderRow(
                        label = "Mire 4X",
                        value = currentConfig.scope4x,
                        icon = "🎯",
                        onValueChange = { viewModel.setCustomScope4x(it) }
                    )

                    // Mire Fusil de Précision (Sniper)
                    SensiSliderRow(
                        label = "Sniper (AWM/Kar98)",
                        value = currentConfig.sniper,
                        icon = "🏹",
                        onValueChange = { viewModel.setCustomSniper(it) }
                    )

                    // Regard Libre
                    SensiSliderRow(
                        label = "Regard Libre (360°)",
                        value = currentConfig.freeLook,
                        icon = "🔄",
                        onValueChange = { viewModel.setCustomFreeLook(it) }
                    )
                }
            }
        }

        // Hardware & HUD Settings Card (DPI, Button Size, Pointer Speed)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_hardware_settings"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⚙️", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Paramètres Système & HUD Téléphone",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Fire Button Size with visual preview
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Bouton de Tir Recommandé",
                                color = TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Emplacement : ${currentConfig.fireButtonPosition}",
                                color = TextMutedGray,
                                fontSize = 11.sp
                            )
                        }

                        // Button preview badge
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(AuraRed, AuraFlameOrange.copy(alpha = 0.4f))
                                    )
                                )
                                .border(1.5.dp, AuraGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${currentConfig.fireButtonSize}%",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Slider(
                        value = currentConfig.fireButtonSize.toFloat(),
                        onValueChange = { viewModel.setCustomButtonSize(it.toInt()) },
                        valueRange = 35f..75f,
                        colors = SliderDefaults.colors(
                            thumbColor = AuraRed,
                            activeTrackColor = AuraRed,
                            inactiveTrackColor = AuraBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // DPI Info Box
                    val isApple = selectedModel.brand == DeviceBrand.APPLE
                    val dpiValue = currentConfig.dpi
                    val isSafeDpi = dpiValue <= selectedModel.maxSafeDpi

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(AuraCardElevated)
                            .border(
                                1.dp,
                                if (isApple) AuraCyan.copy(alpha = 0.5f) else if (isSafeDpi) SafeDpiColor.copy(alpha = 0.5f) else WarningDpiColor,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "📐", fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Largeur Minimale (DPI)",
                                        color = TextWhite,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                if (isApple) {
                                    Text(
                                        text = "Non Requis (iOS)",
                                        color = AuraCyan,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    Text(
                                        text = if (dpiValue > 0) "$dpiValue DPI" else "DPI Standard",
                                        color = AuraGold,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isApple) {
                                    "Sur iPhone / iPad, le DPI est automatique. Augmentez la réactivité tactile dans Réglages > Accessibilité > Toucher."
                                } else {
                                    "DPI par défaut : ${selectedModel.defaultDpi} | Limite max sécurisée : ${selectedModel.maxSafeDpi} DPI. Ne dépassez jamais cette limite pour éviter l'écran noir !"
                                },
                                color = TextMutedGray,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Pointer speed & Refresh rate badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AuraSurfaceVariant)
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Vitesse Pointeur",
                                    color = TextMutedGray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = currentConfig.pointerSpeed,
                                    color = TextWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AuraSurfaceVariant)
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Fréquence Écran",
                                    color = TextMutedGray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = selectedModel.recommendedHz,
                                    color = AuraGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pro Advice Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                AuraRed.copy(alpha = 0.2f),
                                AuraFlameOrange.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .border(1.dp, AuraRed.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(text = "💡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Conseil Secret Anos Aura",
                            color = AuraGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = currentConfig.advice,
                            color = TextWhite,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Action Buttons (Copy, Save, WhatsApp)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.copyConfigurationToClipboard(context) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_copy_sensi"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraRed
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Copier la Sensi",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = { showSaveDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_save_sensi"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraGold
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sauvegarder",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // WhatsApp VIP Button for custom consultation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF25D366), Color(0xFF128C7E))
                            )
                        )
                        .clickable { viewModel.openWhatsAppContact(context) }
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .testTag("banner_whatsapp_contact"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "💬", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Contacter Anos sur WhatsApp pour Sensi VIP (+23407071776576)",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
fun SensiSliderRow(
    label: String,
    value: Int,
    icon: String,
    onValueChange: (Int) -> Unit
) {
    val isOneTapRange = value >= 185
    val isFastRange = value in 165..184

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isOneTapRange) {
                    Text(
                        text = "ONE-TAP",
                        color = HeadshotRed,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when {
                                isOneTapRange -> AuraRed.copy(alpha = 0.25f)
                                isFastRange -> AuraFlameOrange.copy(alpha = 0.2f)
                                else -> AuraSurfaceVariant
                            }
                        )
                        .border(
                            1.dp,
                            when {
                                isOneTapRange -> AuraRed
                                isFastRange -> AuraFlameOrange
                                else -> AuraBorder
                            },
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "$value / 200",
                        color = when {
                            isOneTapRange -> HeadshotRed
                            isFastRange -> AuraFlameOrange
                            else -> AuraGold
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..200f,
            colors = SliderDefaults.colors(
                thumbColor = when {
                    isOneTapRange -> HeadshotRed
                    isFastRange -> AuraFlameOrange
                    else -> AuraGold
                },
                activeTrackColor = when {
                    isOneTapRange -> HeadshotRed
                    isFastRange -> AuraFlameOrange
                    else -> AuraGold
                },
                inactiveTrackColor = AuraBorder
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
