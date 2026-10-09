package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.IdeBackground
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PythonAppViewModel

@Composable
fun ProfileScreen(
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    val progress by viewModel.userProgress.collectAsState()
    val allLessons = viewModel.allLessons
    val bookmarkedLessons = allLessons.filter { progress.bookmarkedLessonIds.contains(it.id) }
    var showResetDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IdeBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))

            // User Hero Profile Card
            Card(
                colors = CardDefaults.cardColors(containerColor = IdeSurface),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PythonGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(PythonBlue.copy(alpha = 0.25f))
                            .border(2.dp, PythonGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = PythonGold, modifier = Modifier.size(40.dp))
                    }

                    Text(
                        text = progress.rankTitle,
                        color = PythonGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Niveau de Maîtrise : ${progress.rankLevel} / 5",
                        color = TextMutedGray,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Stats Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // XP Card
                StatCard(
                    title = "Points XP",
                    value = "${progress.totalXp} ⚡",
                    color = PythonGold,
                    modifier = Modifier.weight(1f)
                )

                // Completed Card
                StatCard(
                    title = "Cours Finis",
                    value = "${progress.completedCount} / ${allLessons.size}",
                    color = SuccessEmerald,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Streak Card
                StatCard(
                    title = "Série d'Étude",
                    value = "${progress.streakDays} jours 🔥",
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )

                // Quizz Passed
                StatCard(
                    title = "Défis Réussis",
                    value = "${progress.globalQuizPassedCount} 🏆",
                    color = PythonBlue,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Badges & Trophies Section
        item {
            Text(
                text = "Trophées & Badges Débloqués",
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BadgeItem(
                    title = "Premier Script",
                    desc = "Complétez votre toute première leçon",
                    isUnlocked = progress.completedCount >= 1,
                    icon = "🌱"
                )
                BadgeItem(
                    title = "Maître des Boucles",
                    desc = "Complétez 5 leçons de programmation",
                    isUnlocked = progress.completedCount >= 5,
                    icon = "⚡"
                )
                BadgeItem(
                    title = "Architecte Objet",
                    desc = "Complétez 15 leçons (Bases + POO)",
                    isUnlocked = progress.completedCount >= 15,
                    icon = "🏛️"
                )
                BadgeItem(
                    title = "Python Guru Absolu",
                    desc = "Terminez la totalité des 24 leçons du cursus",
                    isUnlocked = progress.completedCount >= allLessons.size,
                    icon = "🧙‍♂️"
                )
            }
        }

        // Bookmarks Section
        item {
            Text(
                text = "Cours en Favoris (${bookmarkedLessons.size})",
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (bookmarkedLessons.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = IdeSurface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, IdeBorder, RoundedCornerShape(12.dp))
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Aucun cours enregistré en favori. Cliquez sur l'étoile/marque-page d'une leçon pour la retrouver ici !",
                            color = TextMutedGray,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        } else {
            items(bookmarkedLessons, key = { it.id }) { lesson ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = IdeSurface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, IdeBorder, RoundedCornerShape(12.dp))
                        .clickable { viewModel.openLesson(lesson) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Cours #${lesson.number} • ${lesson.title}",
                                color = TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = lesson.subtitle,
                                color = TextMutedGray,
                                fontSize = 11.sp
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMutedGray, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // Reset progress option
        item {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { showResetDialog = true },
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorCrimson.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorCrimson),
                modifier = Modifier.fillMaxWidth().height(44.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = ErrorCrimson, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Réinitialiser ma progression", color = ErrorCrimson, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            containerColor = IdeSurface,
            title = {
                Text("Réinitialiser la progression ?", color = TextWhite, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Cette action remettra à zéro vos points d'XP, vos leçons validées et vos scores aux quiz. Voulez-vous continuer ?",
                    color = TextMutedGray,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetProgress()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorCrimson)
                ) {
                    Text("Oui, Réinitialiser", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Annuler", color = TextWhite)
                }
            }
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = IdeSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.border(1.dp, IdeBorder, RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, color = TextMutedGray, fontSize = 11.sp)
            Text(text = value, color = color, fontSize = 17.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun BadgeItem(
    title: String,
    desc: String,
    isUnlocked: Boolean,
    icon: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) IdeSurface else IdeSurface.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isUnlocked) PythonGold.copy(alpha = 0.6f) else IdeBorder.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                modifier = Modifier.size(32.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = if (isUnlocked) TextWhite else TextMutedGray,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = desc,
                    color = TextMutedGray,
                    fontSize = 11.sp
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isUnlocked) SuccessEmerald.copy(alpha = 0.2f)
                        else IdeSurfaceVariant
                    )
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = if (isUnlocked) "Débloqué" else "Verrouillé",
                    color = if (isUnlocked) SuccessEmerald else TextMutedGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
