package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AuraBorder
import com.example.ui.theme.AuraCardElevated
import com.example.ui.theme.AuraFlameOrange
import com.example.ui.theme.AuraGold
import com.example.ui.theme.AuraRed
import com.example.ui.theme.AuraSurface
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite

@Composable
fun CustomDeviceDialog(
    onDismiss: () -> Unit,
    onConfirm: (brand: String, model: String) -> Unit
) {
    var brandInput by remember { mutableStateOf("") }
    var modelInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(AuraSurface)
                .border(1.5.dp, AuraRed.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🌍", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Calculateur Universel",
                            color = TextWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Pour tout smartphone au monde",
                            color = AuraGold,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Entrez la marque et le modèle exact de votre téléphone. L'algorithme mathématique Anos Aura calibrera automatiquement la sensibilité idéale (taille écran, tactile & DPI).",
                    color = TextMutedGray,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = brandInput,
                    onValueChange = { brandInput = it },
                    label = { Text("Marque (ex: Infinix, Xiaomi, Wiko, Nubia...)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_brand"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraRed,
                        unfocusedBorderColor = AuraBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = AuraGold,
                        unfocusedLabelColor = TextMutedGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = modelInput,
                    onValueChange = { modelInput = it },
                    label = { Text("Modèle exact (ex: Hot 40 Pro, Note 12...)") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_model"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraRed,
                        unfocusedBorderColor = AuraBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = AuraGold,
                        unfocusedLabelColor = TextMutedGray
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextMutedGray
                        ),
                        modifier = Modifier.testTag("btn_cancel_custom_device")
                    ) {
                        Text("Annuler")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            onConfirm(brandInput, modelInput)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraRed
                        ),
                        modifier = Modifier.testTag("btn_confirm_custom_device")
                    ) {
                        Text(
                            text = "⚡ Calculer la Sensi",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SaveSensiDialog(
    defaultTitle: String,
    onDismiss: () -> Unit,
    onConfirm: (title: String, notes: String) -> Unit
) {
    var titleInput by remember { mutableStateOf(defaultTitle) }
    var notesInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(AuraSurface)
                .border(1.5.dp, AuraGold.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sauvegarder la Sensi",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Nom de la configuration") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraGold,
                        unfocusedBorderColor = AuraBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = AuraGold,
                        unfocusedLabelColor = TextMutedGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = notesInput,
                    onValueChange = { notesInput = it },
                    label = { Text("Notes (ex: Pour tournoi CS avec M1887)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AuraGold,
                        unfocusedBorderColor = AuraBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = AuraGold,
                        unfocusedLabelColor = TextMutedGray
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextMutedGray
                        )
                    ) {
                        Text("Annuler")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            onConfirm(titleInput, notesInput)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AuraGold
                        )
                    ) {
                        Text(
                            text = "Sauvegarder",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
