package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireCrimson
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.HeadshotYellow
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel
import kotlin.math.roundToInt

enum class SimulatorAssistanceLevel(val title: String, val badge: String, val assistFactor: Float) {
    OFF("Désactivé", "BRUT", 1.0f),
    MINIMAL("Minimal", "FLUIDE", 1.25f),
    BALANCED("Équilibré", "PRO", 1.5f),
    ADVANCED("Avancé", "LASER", 1.8f)
}

@Composable
fun FireButtonSimulatorScreen(
    viewModel: SensiViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentConfig by viewModel.currentConfig.collectAsState()

    var buttonSizePercent by remember { mutableIntStateOf(currentConfig.fireButtonSize) }

    // Advanced visual precision & moving target options
    var isPrecisionModeEnabled by remember { mutableStateOf(true) }
    var isMovingTargetEnabled by remember { mutableStateOf(false) }
    var selectedAssistance by remember { mutableStateOf(SimulatorAssistanceLevel.BALANCED) }

    // Target animation
    val infiniteTransition = rememberInfiniteTransition(label = "targetMotion")
    val animatedTargetX by infiniteTransition.animateFloat(
        initialValue = -90f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "targetX"
    )

    val currentTargetOffsetX = if (isMovingTargetEnabled) animatedTargetX else 0f

    // Drag simulation states
    var dragStartY by remember { mutableFloatStateOf(0f) }
    var dragDeltaY by remember { mutableFloatStateOf(0f) }
    var dragDeltaX by remember { mutableFloatStateOf(0f) }
    var dragStartTime by remember { mutableLongStateOf(0L) }
    var isDragging by remember { mutableStateOf(false) }

    // Hit results
    var lastHitType by remember { mutableStateOf<HitType?>(null) }
    var damageNumber by remember { mutableIntStateOf(0) }
    var feedbackMessage by remember { mutableStateOf("Appuyez sur le bouton de tir et levez rapidement le pouce vers la tête !") }

    // Stats
    var totalShots by remember { mutableIntStateOf(0) }
    var headshotCount by remember { mutableIntStateOf(0) }

    val vibrator = remember {
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    val scrollState = rememberScrollState()
    val themePrimary = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceVariant)
                    .testTag("btn_back_simulator")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = TextPrimary
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Simulateur & Précision Drag",
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Banc de Test & Analyse Tactile en Direct",
                    color = FireGold,
                    fontSize = 11.sp
                )
            }

            IconButton(
                onClick = {
                    totalShots = 0
                    headshotCount = 0
                    lastHitType = null
                    feedbackMessage = "Scores réinitialisés ! Entraînez-vous."
                },
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Stats Banner
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(14.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TIRS TOTAUX", color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    Text("$totalShots", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
                }

                Box(modifier = Modifier.width(1.dp).height(30.dp).background(DarkBorder))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("HEADSHOTS 💥", color = HeadshotRed, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    Text("$headshotCount", color = HeadshotRed, fontSize = 18.sp, fontWeight = FontWeight.Black)
                }

                Box(modifier = Modifier.width(1.dp).height(30.dp).background(DarkBorder))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val rate = if (totalShots > 0) (headshotCount * 100) / totalShots else 0
                    Text("TAUX HEADSHOT", color = FireGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    Text("$rate%", color = FireGold, fontSize = 18.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        // Free Fire Training Canvas (Interactive Area)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C101A)),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .border(2.dp, if (lastHitType == HitType.HEADSHOT) HeadshotRed else DarkBorder, RoundedCornerShape(18.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Background visual tracking grid & distance cues
                if (isPrecisionModeEnabled) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerX = size.width / 2
                        // Center crosshair axis
                        drawLine(
                            color = Color.White.copy(alpha = 0.08f),
                            start = Offset(centerX, 0f),
                            end = Offset(centerX, size.height),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                        // Drag trajectory indicator when dragging
                        if (isDragging) {
                            drawLine(
                                color = themePrimary.copy(alpha = 0.5f),
                                start = Offset(centerX, size.height - 50.dp.toPx()),
                                end = Offset(centerX + dragDeltaX, size.height - 50.dp.toPx() + dragDeltaY),
                                strokeWidth = 3.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                            )
                        }
                    }
                }

                // Target silhouette with Bounding Box
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset { IntOffset(currentTargetOffsetX.roundToInt(), 0) }
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Rectangular tracking bounding box
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.5.dp,
                                if (lastHitType == HitType.HEADSHOT) HeadshotRed
                                else if (isPrecisionModeEnabled) CyberCyan.copy(alpha = 0.6f)
                                else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .background(
                                if (isPrecisionModeEnabled) CyberCyan.copy(alpha = 0.05f) else Color.Transparent
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Head hitbox
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (lastHitType == HitType.HEADSHOT) HeadshotRed
                                        else HeadshotRed.copy(alpha = 0.3f)
                                    )
                                    .border(
                                        2.dp,
                                        if (lastHitType == HitType.HEADSHOT) Color.White else HeadshotRed,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "TÊTE",
                                    color = Color.White,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Body hitbox
                            Box(
                                modifier = Modifier
                                    .width(54.dp)
                                    .height(58.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (lastHitType == HitType.BODY) HeadshotYellow.copy(alpha = 0.6f)
                                        else DarkSurfaceElevated
                                    )
                                    .border(1.dp, DarkBorder, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "CORPS",
                                    color = TextMuted,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (isPrecisionModeEnabled) {
                        Text(
                            text = "[CIBLE : ${if (isMovingTargetEnabled) "MOBILE" else "FIXE"}]",
                            color = CyberCyan,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Dynamic Damage Number Popup
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = lastHitType != null,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    when (lastHitType) {
                                        HitType.HEADSHOT -> HeadshotRed
                                        HitType.BODY -> HeadshotYellow
                                        HitType.OVER_DRAG -> Color(0xFF6B7280)
                                        null -> Color.Transparent
                                    }
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = when (lastHitType) {
                                    HitType.HEADSHOT -> "💥 HEADSHOT $damageNumber !"
                                    HitType.BODY -> "⚠️ CORPS $damageNumber"
                                    HitType.OVER_DRAG -> "❌ TROP HAUT ! ($damageNumber)"
                                    null -> ""
                                },
                                color = if (lastHitType == HitType.BODY) Color.Black else Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                // Drag Up Guide Arrow Animation
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 75.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "▲ LEVEZ LE POUCE (DRAG) ▲",
                        color = FireOrange.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                // Interactive Fire Button
                val buttonPx = (50 + (buttonSizePercent * 0.9f)).dp

                Box(
                    modifier = Modifier
                        .size(buttonPx)
                        .align(Alignment.BottomCenter)
                        .offset { IntOffset(dragDeltaX.roundToInt().coerceIn(-100, 100), dragDeltaY.roundToInt().coerceIn(-180, 0)) }
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    FireOrange,
                                    FireCrimson,
                                    Color(0xFF3E1205)
                                )
                            )
                        )
                        .border(
                            width = 3.dp,
                            color = if (isDragging) FireGold else Color.White.copy(alpha = 0.8f),
                            shape = CircleShape
                        )
                        .pointerInput(buttonSizePercent, selectedAssistance, isMovingTargetEnabled) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    isDragging = true
                                    dragStartY = offset.y
                                    dragDeltaY = 0f
                                    dragDeltaX = 0f
                                    dragStartTime = System.currentTimeMillis()
                                },
                                onDragEnd = {
                                    isDragging = false
                                    val durationMs = (System.currentTimeMillis() - dragStartTime).coerceAtLeast(1)
                                    val swipeDistance = -dragDeltaY * selectedAssistance.assistFactor
                                    totalShots++

                                    // Evaluation logic based on swipe distance, alignment & assistance
                                    val isAlignedWithTarget = if (isMovingTargetEnabled) {
                                        kotlin.math.abs(dragDeltaX - currentTargetOffsetX) < 70f
                                    } else true

                                    if (swipeDistance in 45f..235f && durationMs < 480 && isAlignedWithTarget) {
                                        // Golden Drag Zone -> HEADSHOT
                                        lastHitType = HitType.HEADSHOT
                                        damageNumber = listOf(495, 272, 330, 248).random()
                                        headshotCount++
                                        feedbackMessage = "💥 PARFAIT ! Vitesse de drag impeccable (${durationMs}ms) !"
                                        vibratePhone(vibrator, true)
                                    } else if (swipeDistance >= 235f) {
                                        // Dragged too high
                                        lastHitType = HitType.OVER_DRAG
                                        damageNumber = 0
                                        feedbackMessage = "❌ Le tir a dépassé la tête ! Baissez un peu la sensi."
                                        vibratePhone(vibrator, false)
                                    } else {
                                        // Drag was too weak or off-target -> Body hit
                                        lastHitType = HitType.BODY
                                        damageNumber = listOf(95, 112, 134, 88).random()
                                        feedbackMessage = "⚠️ Tir dans le corps. Levez le pouce plus vite vers la tête !"
                                        vibratePhone(vibrator, false)
                                    }
                                    dragDeltaY = 0f
                                    dragDeltaX = 0f
                                },
                                onDragCancel = {
                                    isDragging = false
                                    dragDeltaY = 0f
                                    dragDeltaX = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    dragDeltaY += dragAmount.y
                                    dragDeltaX += dragAmount.x * 0.7f
                                }
                            )
                        }
                        .testTag("interactive_fire_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(buttonPx * 0.55f)) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.85f),
                            style = Stroke(width = 3.dp.toPx())
                        )
                        drawCircle(
                            color = FireGold,
                            radius = size.minDimension * 0.18f
                        )
                    }
                }
            }
        }

        // Feedback Text Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkSurfaceVariant)
                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = feedbackMessage,
                color = when (lastHitType) {
                    HitType.HEADSHOT -> HeadshotRed
                    HitType.BODY -> FireGold
                    HitType.OVER_DRAG -> TextMuted
                    null -> TextSecondary
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        // Options: Precision Mode & Moving Target
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🎯 Options d'Entraînement Avancé",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                // Precision Mode Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
                        Column {
                            Text("Repères Visuels & Bounding Box", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Encadre la cible et trace la ligne de tir", color = TextSecondary, fontSize = 10.sp)
                        }
                    }
                    Switch(
                        checked = isPrecisionModeEnabled,
                        onCheckedChange = { isPrecisionModeEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = CyberCyan
                        )
                    )
                }

                // Moving Target Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.GpsFixed, contentDescription = null, tint = FireOrange, modifier = Modifier.size(18.dp))
                        Column {
                            Text("Cible Mobile Dynamique", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Simule le déplacement d'un ennemi", color = TextSecondary, fontSize = 10.sp)
                        }
                    }
                    Switch(
                        checked = isMovingTargetEnabled,
                        onCheckedChange = { isMovingTargetEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = FireOrange
                        )
                    )
                }

                // Assistance Level Selector
                Text(
                    text = "Assistance Tactile au Tir (Simulation) :",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SimulatorAssistanceLevel.entries.forEach { level ->
                        val isSelected = selectedAssistance == level
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else DarkSurfaceVariant)
                                .border(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else DarkBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedAssistance = level }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = level.title,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = level.badge,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else TextMuted,
                                    fontSize = 8.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Fire Button Size Adjuster
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ajuster la Taille du Bouton",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FireOrange.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "$buttonSizePercent%",
                            color = FireOrange,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { buttonSizePercent = (buttonSizePercent - 1).coerceIn(25, 80) },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(DarkBorder)
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "-", tint = TextPrimary)
                    }

                    Slider(
                        value = buttonSizePercent.toFloat(),
                        onValueChange = { buttonSizePercent = it.toInt().coerceIn(25, 80) },
                        valueRange = 25f..80f,
                        steps = 54,
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = FireOrange,
                            activeTrackColor = FireOrange,
                            inactiveTrackColor = DarkBorder
                        )
                    )

                    IconButton(
                        onClick = { buttonSizePercent = (buttonSizePercent + 1).coerceIn(25, 80) },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(DarkBorder)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "+", tint = TextPrimary)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("35% (Course longue - OneTap)", color = TextMuted, fontSize = 10.sp)
                    Text("70% (Course courte - Rusher)", color = TextMuted, fontSize = 10.sp)
                }

                Button(
                    onClick = {
                        viewModel.updateFireButtonSize(buttonSizePercent)
                        onBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("btn_apply_button_size"),
                    colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Appliquer cette taille ($buttonSizePercent%) à ma Sensi",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

private enum class HitType {
    HEADSHOT,
    BODY,
    OVER_DRAG
}

private fun vibratePhone(vibrator: Vibrator?, isHeadshot: Boolean) {
    if (vibrator == null || !vibrator.hasVibrator()) return
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timing = if (isHeadshot) longArrayOf(0, 40, 30, 70) else longArrayOf(0, 25)
            val amplitudes = if (isHeadshot) intArrayOf(0, 255, 0, 255) else intArrayOf(0, 100)
            vibrator.vibrate(VibrationEffect.createWaveform(timing, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(if (isHeadshot) 90 else 30)
        }
    } catch (_: Exception) {}
}
