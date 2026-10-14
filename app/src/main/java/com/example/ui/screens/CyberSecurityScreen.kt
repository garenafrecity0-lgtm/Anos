package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CyberLesson
import com.example.data.model.CyberLevel
import com.example.ui.theme.IdeBackground
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PythonAppViewModel

val CyberNeonBlue = Color(0xFF00E5FF)
val CyberPurple = Color(0xFFA855F7)
val CyberRed = Color(0xFFFF5252)

@Composable
fun CyberSecurityScreen(
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    val lessons by viewModel.filteredCyberLessons.collectAsState()
    val progress by viewModel.userProgress.collectAsState()
    val selectedLevel by viewModel.selectedCyberLevel.collectAsState()
    val searchQuery by viewModel.cyberSearchQuery.collectAsState()
    val context = LocalContext.current

    val totalLessons = viewModel.allCyberLessons.size
    val completedCyberCount = progress.completedCyberCount(viewModel.allCyberLessons)
    val progressPercent = if (totalLessons > 0) completedCyberCount.toFloat() / totalLessons else 0f

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IdeBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            // Overall Cyber Progress Hero Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = IdeSurface),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Brush.linearGradient(listOf(CyberNeonBlue, CyberPurple)),
                        RoundedCornerShape(18.dp)
                    )
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
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = CyberNeonBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Cybersécurité de A à Z",
                                    color = TextWhite,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Text(
                                text = "Hacking éthique, scans réseau, failles web & défense",
                                color = CyberNeonBlue,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(CyberNeonBlue.copy(alpha = 0.15f))
                                .border(1.dp, CyberNeonBlue.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "$completedCyberCount / $totalLessons",
                                color = CyberNeonBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { progressPercent },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = CyberNeonBlue,
                        trackColor = IdeSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${(progressPercent * 100).toInt()}% du cursus Cyber maîtrisé",
                            color = TextMutedGray,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Progression pas à pas 🔓",
                            color = PythonGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setCyberSearchQuery(it) },
                placeholder = {
                    Text(
                        "Rechercher un concept (SQLi, Nmap, XSS, OSI...)",
                        color = TextMutedGray,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Recherche",
                        tint = CyberNeonBlue
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setCyberSearchQuery("") }) {
                            Text("✕", color = TextMutedGray, fontSize = 14.sp)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberNeonBlue,
                    unfocusedBorderColor = IdeBorder,
                    focusedContainerColor = IdeSurface,
                    unfocusedContainerColor = IdeSurface,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("cyber_search_field")
            )
        }

        // Level Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CyberFilterChipItem(
                    label = "Tous les Niveaux ($totalLessons)",
                    isSelected = selectedLevel == null,
                    onClick = { viewModel.filterByCyberLevel(null) }
                )

                CyberLevel.entries.forEach { level ->
                    val isSelected = selectedLevel == level
                    CyberFilterChipItem(
                        label = "${level.iconEmoji} ${level.badge}",
                        isSelected = isSelected,
                        onClick = {
                            viewModel.filterByCyberLevel(if (isSelected) null else level)
                        }
                    )
                }
            }
        }

        // List of Cyber Lessons
        items(lessons, key = { it.id }) { lesson ->
            val isUnlocked = viewModel.isCyberLessonUnlocked(lesson)
            val isCompleted = progress.completedLessonIds.contains(lesson.id)
            val isBookmarked = progress.bookmarkedLessonIds.contains(lesson.id)
            val quizScore = progress.quizScores[lesson.id]

            CyberLessonCardItem(
                lesson = lesson,
                isUnlocked = isUnlocked,
                isCompleted = isCompleted,
                isBookmarked = isBookmarked,
                quizScore = quizScore,
                onLessonClick = { viewModel.openCyberLesson(lesson) },
                onBookmarkClick = { viewModel.toggleBookmark(lesson.id) }
            )
        }

        // WhatsApp Help Card at bottom
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = IdeSurface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF25D366).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Une question sur la Cybersécurité ?",
                            color = TextWhite,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Discutez en direct sur WhatsApp (+234 07071776576)",
                            color = Color(0xFF25D366),
                            fontSize = 11.5.sp
                        )
                    }

                    Button(
                        onClick = { viewModel.openWhatsAppContact(context) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("btn_cyber_whatsapp_contact")
                    ) {
                        Text("Contacter", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CyberFilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) CyberNeonBlue else IdeSurfaceVariant)
            .border(1.dp, if (isSelected) CyberNeonBlue else IdeBorder, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.Black else TextWhite,
            fontSize = 11.5.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun CyberLessonCardItem(
    lesson: CyberLesson,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    quizScore: Int?,
    onLessonClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    val containerColor = if (isUnlocked) IdeSurface else IdeSurface.copy(alpha = 0.5f)
    val borderColor = when {
        isCompleted -> SuccessEmerald.copy(alpha = 0.6f)
        isUnlocked -> CyberNeonBlue.copy(alpha = 0.4f)
        else -> IdeBorder.copy(alpha = 0.3f)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onLessonClick)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCompleted -> SuccessEmerald.copy(alpha = 0.2f)
                                    isUnlocked -> CyberNeonBlue.copy(alpha = 0.2f)
                                    else -> IdeSurfaceVariant
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isCompleted -> {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Complété", tint = SuccessEmerald, modifier = Modifier.size(18.dp))
                            }
                            isUnlocked -> {
                                Text(
                                    text = "${lesson.number}",
                                    color = CyberNeonBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                            else -> {
                                Icon(Icons.Default.Lock, contentDescription = "Verrouillé", tint = TextMutedGray, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    if (isUnlocked) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(lesson.level.primaryColor.copy(alpha = 0.2f))
                                .border(1.dp, lesson.level.primaryColor.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${lesson.level.iconEmoji} ${lesson.level.badge}",
                                color = lesson.level.primaryColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(IdeSurfaceVariant)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🔒 VERROUILLÉ",
                                color = TextMutedGray,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isUnlocked) {
                        IconButton(
                            onClick = onBookmarkClick,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Favori",
                                tint = if (isBookmarked) PythonGold else TextMutedGray,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Ouvrir",
                            tint = TextMutedGray,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Verrouillé",
                            tint = TextMutedGray.copy(alpha = 0.6f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = lesson.title,
                color = if (isUnlocked) TextWhite else TextWhite.copy(alpha = 0.5f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )

            Text(
                text = if (isUnlocked) lesson.subtitle else "Terminez le Cours Cyber #${lesson.number - 1} pour débloquer ce chapitre.",
                color = if (isUnlocked) TextMutedGray else Color(0xFFFFB74D),
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            if (isUnlocked) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "⏱️ ${lesson.durationMinutes} min",
                            color = TextMutedGray,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "⚡ +${lesson.xpReward} XP",
                            color = CyberNeonBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    quizScore?.let { score ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SuccessEmerald.copy(alpha = 0.2f))
                                .border(1.dp, SuccessEmerald.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Quiz : $score%",
                                color = SuccessEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
