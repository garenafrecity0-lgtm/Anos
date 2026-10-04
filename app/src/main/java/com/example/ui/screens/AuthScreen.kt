package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuthConstants
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireCrimson
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel
import com.example.util.BackgroundMusicManager

@Composable
fun AuthScreen(
    viewModel: SensiViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputKey by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var hasClickedChannel by remember { mutableStateOf(false) }

    val isMusicPlaying by BackgroundMusicManager.isPlaying.collectAsState()
    val isMusicEnabled by BackgroundMusicManager.isMusicEnabled.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "musicPulseAuth")
    val musicScale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "musicIconScaleAuth"
    )

    val scrollState = rememberScrollState()

    fun openWhatsAppChannel() {
        try {
            hasClickedChannel = true
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_CHANNEL_URL))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir le lien de la chaîne WhatsApp.", Toast.LENGTH_LONG).show()
        }
    }

    fun openWhatsAppDirect() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_DIRECT_URL))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir WhatsApp.", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Music Banner & Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkSurfaceVariant)
                .border(1.dp, if (isMusicEnabled) SafeGreen.copy(alpha = 0.4f) else DarkBorder, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isMusicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = null,
                    tint = if (isMusicEnabled) SafeGreen else TextMuted,
                    modifier = Modifier
                        .size(18.dp)
                        .then(if (isMusicEnabled && isMusicPlaying) Modifier.scale(musicScale) else Modifier)
                )

                Text(
                    text = if (isMusicEnabled) "🎵 Indila - Tourner Dans Le Vide" else "🔇 Musique en pause",
                    color = if (isMusicEnabled) TextPrimary else TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }

            IconButton(
                onClick = {
                    BackgroundMusicManager.toggleMusic(context)
                    val status = if (!isMusicEnabled) "🎵 Musique activée : Indila - Tourner Dans Le Vide" else "🔇 Musique en pause"
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = if (isMusicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = "Toggle Music",
                    tint = if (isMusicEnabled) SafeGreen else TextMuted,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // App Logo & Header
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(listOf(FireOrange, FireCrimson))
                )
                .border(2.dp, FireGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Logo Anos Sensi V2",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ANOS SENSI V2",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                letterSpacing = 1.sp
            )
            Text(
                text = "Générateur & Calibration Headshot Free Fire",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }

        // 🚨 WHATSAPP CHANNEL MANDATORY BARRIER
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, SafeGreen.copy(alpha = 0.8f), RoundedCornerShape(18.dp))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SafeGreen.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Chat,
                            contentDescription = null,
                            tint = SafeGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "BARRIÈRE D'ACCÈS OBLIGATOIRE",
                            color = SafeGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Rejoignez la chaîne pour débloquer l'accès",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Text(
                    text = "Pour accéder gratuitement à l'application et recevoir les mises à jour de sensibilités Free Fire, vous devez obligatoirement rejoindre notre chaîne WhatsApp officielle.",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Text(
                    text = "👉 Étape 1 : Cliquez sur le bouton vert ci-dessous pour rejoindre la chaîne.\n👉 Étape 2 : Revenez dans l'application et cliquez sur 'J'ai rejoint la chaîne' pour ouvrir votre accès gratuit immédiat !",
                    color = FireGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp
                )

                // Step 1: Open WhatsApp Channel
                Button(
                    onClick = { openWhatsAppChannel() },
                    colors = ButtonDefaults.buttonColors(containerColor = SafeGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_join_whatsapp_channel")
                ) {
                    Icon(imageVector = Icons.Outlined.Chat, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1. Rejoindre la Chaîne WhatsApp",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // Step 2: Confirm and enter free mode
                Button(
                    onClick = {
                        val success = viewModel.authenticate(AuthConstants.CLIENT_DEFAULT_KEY)
                        if (success) {
                            Toast.makeText(context, "Bienvenue sur Anos Sensi V2 (Mode Gratuit) ! 🔥", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hasClickedChannel) FireOrange else DarkSurfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_confirm_joined_free_access")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = if (hasClickedChannel) Color.White else FireOrange
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "2. J'ai rejoint la chaîne (Entrer en Gratuit)",
                        color = if (hasClickedChannel) Color.White else FireOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // 👑 VIP & ADMIN ACCESS KEY CARD
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, FireGold.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = FireGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Accès VIP & Administrateur",
                        color = FireGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Vous possédez une clé VIP ou Admin ? Entrez-la pour débloquer le Lissage Tactile, le Simulateur One-Tap et tous les modèles mondiaux :",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                OutlinedTextField(
                    value = inputKey,
                    onValueChange = {
                        inputKey = it
                        errorMessage = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_access_key"),
                    placeholder = { Text("Entrez votre clé d'accès (VIP ou Admin)", color = TextMuted, fontSize = 12.sp) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = FireGold
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Afficher/Masquer",
                                tint = TextMuted
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (inputKey.isNotBlank()) {
                            val success = viewModel.authenticate(inputKey)
                            if (!success) {
                                errorMessage = "Clé d'accès incorrecte ou expirée."
                            }
                        }
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FireGold,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = FireGold
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                AnimatedVisibility(visible = errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = HeadshotRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        if (inputKey.isBlank()) {
                            errorMessage = "Veuillez saisir votre clé d'accès."
                            return@Button
                        }
                        val success = viewModel.authenticate(inputKey)
                        if (!success) {
                            errorMessage = "Clé d'accès incorrecte ou expirée."
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FireGold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_login_submit")
                ) {
                    Icon(
                        imageVector = Icons.Default.Login,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Valider la Clé & Débloquer le VIP",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Buy VIP Key direct contact
                OutlinedButton(
                    onClick = { openWhatsAppDirect() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FireGold),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FireGold.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Icon(Icons.Outlined.Chat, contentDescription = null, tint = FireGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Acheter une Clé VIP Permanente sur WhatsApp", color = FireGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
