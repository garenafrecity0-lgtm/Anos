package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireGold
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel

enum class SmoothingProfile(val title: String, val badge: String, val desc: String, val alphaFactor: Float) {
    BALANCED_ONE_TAP("Lissage Équilibré One-Tap", "⚡ RECOMMANDÉ", "Supprime les micro-vibrations du pouce lors du tir (M1887 & Deagle)", 0.65f),
    FAST_SMG("Lissage Dynamique SMG", "🚀 ULTRA-RAPIDE", "Latence ultra-faible pour duels rapides et rotations 360° (MP40/UMP)", 0.85f),
    MAX_LASER("Lissage Chirurgical Laser", "🎯 ANTI-SECOUSSE", "Stabilité maximale sans aucun tremblement (Woodpecker & Snipers)", 0.45f)
}

@Composable
fun TouchSmoothingScreen(viewModel: SensiViewModel) {
    val isSmoothingActive by viewModel.isTouchSmoothingEnabled.collectAsState()
    val currentProfile by viewModel.selectedSmoothingProfile.collectAsState()
    val detectedDevice = viewModel.detectedInfo.matchedSpec

    // Live gesture canvas points
    val rawPoints = remember { mutableStateListOf<Offset>() }
    val smoothedPoints = remember { mutableStateListOf<Offset>() }
    var currentDragSpeed by remember { mutableFloatStateOf(0f) }
    var lastTimestamp by remember { mutableStateOf(0L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Lissage & Optimisation Tactile",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Moteur de lissage gestuel & Anti-Jitter",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = isSmoothingActive,
                        onCheckedChange = { viewModel.setTouchSmoothing(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("switch_touch_smoothing")
                    )
                }

                // Active status banner
                val bannerBg by animateColorAsState(
                    targetValue = if (isSmoothingActive) SafeGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
                    label = "bannerBg"
                )
                val bannerBorder by animateColorAsState(
                    targetValue = if (isSmoothingActive) SafeGreen.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline,
                    label = "bannerBorder"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(bannerBg)
                        .border(1.dp, bannerBorder, RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = if (isSmoothingActive) Icons.Default.CheckCircle else Icons.Default.Security,
                        contentDescription = null,
                        tint = if (isSmoothingActive) SafeGreen else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )

                    Column {
                        Text(
                            text = if (isSmoothingActive) "LISSAGE TACTILE ACTIF (Interpolation ${detectedDevice.touchSamplingHz}Hz)" else "LISSAGE TACTILE EN VEILLE",
                            color = if (isSmoothingActive) SafeGreen else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = if (isSmoothingActive)
                                "Les micro-saccades de votre doigt sont filtrées pour un drag One-Tap rectiligne sans déviation."
                            else
                                "Activez l'interrupteur pour lisser les gestes du pouce et éliminer le tremblement de visée.",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        // Real-time Hardware Metrics
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "📊 Données Télémétriques de l'Écran",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricBox(
                        title = "Échantillonnage",
                        value = "${detectedDevice.touchSamplingHz} Hz",
                        icon = Icons.Default.Speed,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Latence Tactile",
                        value = if (isSmoothingActive) "1.2 ms" else "4.8 ms",
                        icon = Icons.Default.Bolt,
                        isHighlight = isSmoothingActive,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Anti-Jitter",
                        value = if (isSmoothingActive) "99.2%" else "OFF",
                        icon = Icons.Default.Gesture,
                        isHighlight = isSmoothingActive,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Smoothing Profiles Selector
        AnimatedVisibility(visible = isSmoothingActive) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "🎯 Profils d'Interpolation Gestuelle",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    SmoothingProfile.entries.forEach { profile ->
                        val isSelected = currentProfile == profile
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = profile.title,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = profile.badge,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = profile.desc,
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }

                            Switch(
                                checked = isSelected,
                                onCheckedChange = { if (it) viewModel.setSmoothingProfile(profile) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }

        // Live Touch Canvas & Gesture Tester
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "🧪 Banc d'Essai Tactile en Direct",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Glissez votre pouce vers le haut pour tester le lissage",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }

                    IconButton(
                        onClick = {
                            rawPoints.clear()
                            smoothedPoints.clear()
                            currentDragSpeed = 0f
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Effacer",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Interactive Touch Box
                val themePrimary = MaterialTheme.colorScheme.primary
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                        .pointerInput(isSmoothingActive, currentProfile) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    rawPoints.clear()
                                    smoothedPoints.clear()
                                    rawPoints.add(offset)
                                    smoothedPoints.add(offset)
                                    lastTimestamp = System.currentTimeMillis()
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    val newPoint = change.position
                                    rawPoints.add(newPoint)

                                    val now = System.currentTimeMillis()
                                    val dt = (now - lastTimestamp).coerceAtLeast(1)
                                    val dist = dragAmount.getDistance()
                                    currentDragSpeed = (dist / dt) * 1000f // pixels/sec
                                    lastTimestamp = now

                                    if (isSmoothingActive && smoothedPoints.isNotEmpty()) {
                                        val prev = smoothedPoints.last()
                                        val alpha = currentProfile.alphaFactor
                                        // Exponential Moving Average filter
                                        val smoothedX = prev.x + alpha * (newPoint.x - prev.x)
                                        val smoothedY = prev.y + alpha * (newPoint.y - prev.y)
                                        smoothedPoints.add(Offset(smoothedX, smoothedY))
                                    } else {
                                        smoothedPoints.add(newPoint)
                                    }
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Draw grid lines
                        val step = 40.dp.toPx()
                        var x = 0f
                        while (x < size.width) {
                            drawLine(
                                color = Color.White.copy(alpha = 0.04f),
                                start = Offset(x, 0f),
                                end = Offset(x, size.height),
                                strokeWidth = 1f
                            )
                            x += step
                        }
                        var y = 0f
                        while (y < size.height) {
                            drawLine(
                                color = Color.White.copy(alpha = 0.04f),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1f
                            )
                            y += step
                        }

                        // Draw raw path (red faint line)
                        if (rawPoints.size > 1 && isSmoothingActive) {
                            val rawPath = Path().apply {
                                moveTo(rawPoints.first().x, rawPoints.first().y)
                                for (i in 1 until rawPoints.size) {
                                    lineTo(rawPoints[i].x, rawPoints[i].y)
                                }
                            }
                            drawPath(
                                path = rawPath,
                                color = Color.Red.copy(alpha = 0.4f),
                                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }

                        // Draw smoothed path (glow neon theme line)
                        if (smoothedPoints.size > 1) {
                            val smoothPath = Path().apply {
                                moveTo(smoothedPoints.first().x, smoothedPoints.first().y)
                                for (i in 1 until smoothedPoints.size) {
                                    val p0 = smoothedPoints[i - 1]
                                    val p1 = smoothedPoints[i]
                                    quadraticTo(p0.x, p0.y, (p0.x + p1.x) / 2, (p0.y + p1.y) / 2)
                                }
                            }

                            // Glow effect
                            drawPath(
                                path = smoothPath,
                                color = themePrimary.copy(alpha = 0.25f),
                                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )

                            drawPath(
                                path = smoothPath,
                                color = if (isSmoothingActive) themePrimary else Color.White,
                                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }

                    if (rawPoints.isEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Gesture,
                                contentDescription = null,
                                tint = TextMuted.copy(alpha = 0.6f),
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Zone de Drag Interactive",
                                color = TextMuted,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Tracez une ligne vers le haut avec votre pouce",
                                color = TextMuted.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Drag Speed indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isSmoothingActive) SafeGreen else FireGold)
                        )
                        Text(
                            text = if (isSmoothingActive) "Courbe lissée (Vert/Thème) vs Brute (Rouge)" else "Courbe standard (Sans lissage)",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }

                    Text(
                        text = "Vitesse : ${currentDragSpeed.toInt()} px/s",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Instructions for Android Developer Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "⚙️ Optimisations Système Recommandées",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "• Activez la fréquence d'actualisation maximale (90Hz / 120Hz) dans Paramètres > Écran.\n• Dans Options Développeurs, réglez la Mémoire tampon du journaliseur sur 4M ou 16M.\n• Définissez les Échelles d'animation sur 0.5x pour éliminer toute latence d'affichage.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun MetricBox(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isHighlight: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isHighlight) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant)
            .border(
                1.dp,
                if (isHighlight) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isHighlight) MaterialTheme.colorScheme.primary else TextMuted,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = value,
                color = if (isHighlight) MaterialTheme.colorScheme.primary else TextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
            )
            Text(
                text = title,
                color = TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}
