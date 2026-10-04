package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuthConstants
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkObsidian
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.FireGold
import com.example.ui.theme.FireOrange
import com.example.ui.theme.HeadshotRed
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SensiViewModel

@Composable
fun VipLockScreen(
    featureTitle: String,
    featureDescription: String,
    viewModel: SensiViewModel,
    onBackToFree: () -> Unit
) {
    val context = LocalContext.current
    var vipKeyInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }

    fun openWhatsAppChannel() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_CHANNEL_URL))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir le lien.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsAppDirect() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AuthConstants.WHATSAPP_DIRECT_URL))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir WhatsApp.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkObsidian)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Golden Glowing VIP Lock Icon
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            FireGold.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .border(2.dp, FireGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = FireGold,
                modifier = Modifier.size(36.dp)
            )
        }

        Text(
            text = "FONCTIONNALITÉ VIP & ADMIN",
            color = FireGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.5.sp
        )

        Text(
            text = featureTitle,
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = featureDescription,
            color = TextSecondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // VIP Perks Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = FireGold, modifier = Modifier.size(18.dp))
                    Text("Avantages du Mode VIP Permanent :", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                VipPerkItem("⚡ Moteur de Lissage Tactile & Anti-Jitter (Hz)")
                VipPerkItem("🎯 Simulateur Bouton de Tir & Cibles Mobiles")
                VipPerkItem("🌐 Déblocage de tous les modèles mondiaux (Samsung, iPhone, Xiaomi, Tecno...)")
                VipPerkItem("🔥 Profils One-Tap Spéciaux & Sauvegarde illimitée")
            }
        }

        // Enter VIP Key Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, FireGold.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🔑 Entrez votre Clé d'Accès VIP / Admin :",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = vipKeyInput,
                    onValueChange = {
                        vipKeyInput = it
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth().testTag("input_vip_key_gate"),
                    placeholder = { Text("Ex: VIP-XXXX ou Clé Admin", color = TextMuted, fontSize = 12.sp) },
                    singleLine = true,
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
                        if (vipKeyInput.isBlank()) {
                            errorMessage = "Veuillez entrer votre clé d'accès VIP."
                            return@Button
                        }
                        val success = viewModel.authenticate(vipKeyInput.trim())
                        if (success && viewModel.userRole.value.isVipOrAdmin) {
                            isSuccess = true
                            errorMessage = null
                            Toast.makeText(context, "Mode VIP Activé avec succès ! ⭐", Toast.LENGTH_SHORT).show()
                        } else {
                            errorMessage = "Clé VIP invalide. Contactez l'administrateur."
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_unlock_vip_gate"),
                    colors = ButtonDefaults.buttonColors(containerColor = FireGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Key, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Débloquer le Module VIP", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // Action Buttons: WhatsApp Channel & Back
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { openWhatsAppDirect() },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SafeGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Outlined.Chat, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Obtenir une Clé VIP sur WhatsApp", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            OutlinedButton(
                onClick = { openWhatsAppChannel() },
                modifier = Modifier.fillMaxWidth().height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                border = androidx.compose.foundation.BorderStroke(1.dp, SafeGreen)
            ) {
                Text("📢 Rejoindre la Chaîne WhatsApp", color = SafeGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Button(
                onClick = onBackToFree,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("⬅️ Retourner au Générateur Gratuit", color = TextSecondary, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun VipPerkItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(Icons.Default.Check, contentDescription = null, tint = SafeGreen, modifier = Modifier.size(15.dp))
        Text(text = text, color = TextSecondary, fontSize = 11.sp)
    }
}
