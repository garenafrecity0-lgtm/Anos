package com.example.ui.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraBackground
import com.example.ui.theme.AuraBorder
import com.example.ui.theme.AuraCardElevated
import com.example.ui.theme.AuraCyan
import com.example.ui.theme.AuraFlameOrange
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraRed
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.AuraSurfaceVariant
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDarkGray
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.AnosAuraViewModel

@Composable
fun HudCalibrationScreen(
    viewModel: AnosAuraViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentConfig by viewModel.currentConfig.collectAsState()

    var interactiveButtonSize by remember { mutableFloatStateOf(currentConfig.fireButtonSize.toFloat()) }
    var interactiveBottomPercent by remember { mutableFloatStateOf(16f) }
    var interactiveRightPercent by remember { mutableFloatStateOf(16f) }

    var selectedThumbSize by remember { mutableStateOf("Moyen") } // Fin, Moyen, Large
    var selectedScreenDiagonal by remember { mutableStateOf("6.6\" (Standard)") } // 6.1", 6.6", 6.8"+

    val calculatedOptimalButtonSize = remember(selectedThumbSize, selectedScreenDiagonal) {
        val base = when (selectedThumbSize) {
            "Fin" -> 42
            "Moyen" -> 46
            else -> 52
        }
        val modifier = when (selectedScreenDiagonal) {
            "6.1\" (Compact)" -> +4
            "6.6\" (Standard)" -> 0
            else -> -3
        }
        (base + modifier).coerceIn(38, 58)
    }

    // Calcul de la zone de drag vers le haut restante
    val dragDistancePercentage = (100f - interactiveBottomPercent - (interactiveButtonSize / 2f)).coerceIn(10f, 95f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AuraBackground)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))

            // En-tête HUD Réel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_hud_header"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(listOf(AuraFlameOrange, AuraRed))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RadioButtonChecked,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Calibration HUD & Bouton Réel",
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Calculez la taille et l'emplacement exact de votre bouton de tir pour 100% de One-Tap.",
                            color = TextMutedGray,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Télémétrie Matérielle Réelle Détectée
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_real_hardware_telemetry"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = AuraCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Matériel Détecté en Temps Réel",
                                color = TextWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SuccessGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACTIF",
                                color = SuccessGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Téléphone
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AuraSurfaceVariant)
                                .border(1.dp, AuraBorder, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Modèle Système",
                                    color = TextMutedGray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "${Build.MANUFACTURER.uppercase()} ${Build.MODEL}",
                                    color = TextWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                        }

                        // Résolution & Densité
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AuraSurfaceVariant)
                                .border(1.dp, AuraBorder, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Densité Écran",
                                    color = TextMutedGray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "${configuration.densityDpi} DPI (${configuration.screenWidthDp}x${configuration.screenHeightDp} dp)",
                                    color = AuraGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        // Simulateur Visuel du HUD & Surface de Drag
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_hud_visualizer"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "📐 Aperçu Visuel de l'Écran de Jeu",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Plus le bouton est bas, plus la distance de drag vertical vers la tête est grande.",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Cadre de l'écran virtuel en paysage
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF090A10))
                            .border(1.5.dp, AuraBorder, RoundedCornerShape(12.dp))
                    ) {
                        // Lignes guides HUD
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            // Indicateur Ennemi / Tête en haut au milieu
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(HeadshotRed.copy(alpha = 0.2f))
                                    .border(1.dp, HeadshotRed.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "🎯 Zone Cible (Tête Rouge)",
                                    color = HeadshotRed,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Flèche ou Trajectoire de Drag vers le haut
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = (interactiveRightPercent * 1.5f).dp)
                                    .offset(y = (-20).dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "▲ FLICK ONE-TAP",
                                    color = AuraGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(48.dp)
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(HeadshotRed, AuraFlameOrange, Color.Transparent)
                                            )
                                        )
                                )
                            }

                            // Bouton de tir virtuel placé selon les pourcentages réels
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(
                                        x = (-interactiveRightPercent).dp,
                                        y = (-interactiveBottomPercent).dp
                                    )
                                    .size((interactiveButtonSize * 0.9f).dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(AuraRed, AuraFlameOrange.copy(alpha = 0.8f))
                                        )
                                    )
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "🔴",
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "${interactiveButtonSize.toInt()}%",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }

                        // Badge de course de drag
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(alpha = 0.7f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Course de tir disponible : ${dragDistancePercentage.toInt()}% de l'écran",
                                color = if (dragDistancePercentage >= 70f) SuccessGreen else AuraGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Sliders de réglage du bouton
                    Text(
                        text = "Ajuster la Taille du Bouton : ${interactiveButtonSize.toInt()}%",
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Slider(
                        value = interactiveButtonSize,
                        onValueChange = { interactiveButtonSize = it },
                        valueRange = 35f..75f,
                        colors = SliderDefaults.colors(
                            thumbColor = AuraRed,
                            activeTrackColor = AuraFlameOrange,
                            inactiveTrackColor = AuraBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "35% (Compact / Expert)", color = TextMutedGray, fontSize = 10.sp)
                        Text(text = "50% (Recommandé)", color = AuraGold, fontSize = 10.sp)
                        Text(text = "75% (Grand / Doigts larges)", color = TextMutedGray, fontSize = 10.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.setCustomButtonSize(interactiveButtonSize.toInt())
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AuraRed),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Appliquer ${interactiveButtonSize.toInt()}% au Générateur",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Calculateur Morphologique Doigt / Écran
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_finger_calculator"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.TouchApp,
                            contentDescription = null,
                            tint = AuraGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Calculateur Morphologique de Bouton",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Calculez la taille mathématiquement parfaite selon la morphologie de votre pouce.",
                        color = TextMutedGray,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "1. Épaisseur de votre pouce :",
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Fin", "Moyen", "Large").forEach { size ->
                            FilterChip(
                                selected = selectedThumbSize == size,
                                onClick = { selectedThumbSize = size },
                                label = { Text(size, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AuraRed,
                                    selectedLabelColor = Color.White,
                                    containerColor = AuraSurfaceVariant,
                                    labelColor = TextMutedGray
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "2. Taille de l'écran du téléphone :",
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("6.1\" (Compact)", "6.6\" (Standard)", "6.8\"+ (Grand)").forEach { diag ->
                            FilterChip(
                                selected = selectedScreenDiagonal == diag,
                                onClick = { selectedScreenDiagonal = diag },
                                label = { Text(diag, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AuraFlameOrange,
                                    selectedLabelColor = Color.White,
                                    containerColor = AuraSurfaceVariant,
                                    labelColor = TextMutedGray
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Résultat calculé
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(AuraCardElevated)
                            .border(1.dp, AuraBorder, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Taille Optimale Recommandée",
                                    color = TextMutedGray,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "$calculatedOptimalButtonSize%",
                                    color = HeadshotRed,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "Emplacement idéal : 15% à 18% du bord droit et bas",
                                    color = AuraGold,
                                    fontSize = 11.sp
                                )
                            }

                            Button(
                                onClick = {
                                    interactiveButtonSize = calculatedOptimalButtonSize.toFloat()
                                    viewModel.setCustomButtonSize(calculatedOptimalButtonSize)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AuraGold),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Appliquer",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Paramètres Réels Téléphone (Vitesse Pointeur & Accessibilité)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_android_system_settings"),
                colors = CardDefaults.cardColors(containerColor = AuraSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AuraBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = AuraCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Réglages Système Android Essentiels",
                            color = TextWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val settingsList = listOf(
                        Triple("Vitesse du Pointeur", "Régler au maximum (100%) dans Système > Langues et saisie.", "⚡"),
                        Triple("Délai avant pression", "Régler sur 'Court (0.5 seconde)' dans Accessibilité.", "⏱️"),
                        Triple("Fréquence d'actualisation", "Sélectionner 120Hz ou 90Hz dans Paramètres > Affichage.", "🖥️"),
                        Triple("Sensibilité tactile", "Activer 'Sensibilité accrue' (utile avec verre trempé).", "🧤")
                    )

                    settingsList.forEach { (title, desc, emoji) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = emoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = title,
                                    color = TextWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = desc,
                                    color = TextMutedGray,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
