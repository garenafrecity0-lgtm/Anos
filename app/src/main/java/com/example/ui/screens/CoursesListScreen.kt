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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.ui.components.LevelBadge
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeCardElevated
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PythonAppViewModel

@Composable
fun CoursesListScreen(
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    val lessons by viewModel.filteredLessons.collectAsState()
    val progress by viewModel.userProgress.collectAsState()
    val selectedLevel by viewModel.selectedLevel.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val totalLessons = viewModel.allLessons.size
    val completedCount = progress.completedCount
    val progressPercent = if (totalLessons > 0) completedCount.toFloat() / totalLessons else 0f

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(com.example.ui.theme.IdeBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            // Overall Progress Hero Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = IdeSurface),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PythonBlue.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
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
                            Text(
                                text = "Progression Linéaire",
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Chaque chapitre débloque le suivant !",
                                color = PythonGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(PythonGold.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${(progressPercent * 100).toInt()}%",
                                color = PythonGold,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { progressPercent },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PythonGold,
                        trackColor = IdeSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedCount sur $totalLessons cours validés",
                            color = SuccessEmerald,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${progress.totalXp} XP accumulés",
                            color = PythonBlue,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_lessons"),
                placeholder = { Text("Rechercher un concept (ex: boucle, decorateur, dict...)", color = TextMutedGray, fontSize = 12.sp) },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = PythonGold, modifier = Modifier.size(20.dp))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PythonGold,
                    unfocusedBorderColor = IdeBorder,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    cursorColor = PythonGold
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Level Filter Chips Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // "Tous" Chip
                FilterChipItem(
                    label = "Tous les niveaux (${viewModel.allLessons.size})",
                    isSelected = selectedLevel == null,
                    onClick = { viewModel.filterByLevel(null) }
                )

                PythonLevel.entries.forEach { level ->
                    val isSelected = selectedLevel == level
                    FilterChipItem(
                        label = "${level.iconEmoji} ${level.badge}",
                        isSelected = isSelected,
                        onClick = { viewModel.filterByLevel(level) }
                    )
                }
            }
        }

        // Section Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cursus Python (${lessons.size} Chapitres)",
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                if (selectedLevel != null || searchQuery.isNotBlank()) {
                    Text(
                        text = "Filtre actif",
                        color = PythonGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // List of Lessons with Lock / Unlock state
        items(lessons, key = { it.id }) { lesson ->
            val isUnlocked = viewModel.isLessonUnlocked(lesson)
            val isCompleted = progress.completedLessonIds.contains(lesson.id)
            val isBookmarked = progress.bookmarkedLessonIds.contains(lesson.id)
            val quizScore = progress.quizScores[lesson.id]

            LessonCardItem(
                lesson = lesson,
                isUnlocked = isUnlocked,
                isCompleted = isCompleted,
                isBookmarked = isBookmarked,
                quizScore = quizScore,
                onLessonClick = { viewModel.openLesson(lesson) },
                onBookmarkClick = { viewModel.toggleBookmark(lesson.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) PythonGold else IdeSurfaceVariant)
            .border(1.dp, if (isSelected) PythonGold else IdeBorder, RoundedCornerShape(10.dp))
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
fun LessonCardItem(
    lesson: PythonLesson,
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
        isUnlocked -> IdeBorder
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
            // Header Row: Status Badge (Number / Check / Lock), Level Badge, Action
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
                                    isUnlocked -> PythonBlue.copy(alpha = 0.2f)
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
                                    color = PythonBlue,
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
                        LevelBadge(level = lesson.level)
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

            // Title & Subtitle
            Text(
                text = lesson.title,
                color = if (isUnlocked) TextWhite else TextWhite.copy(alpha = 0.5f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )

            Text(
                text = if (isUnlocked) lesson.subtitle else "Terminez le Cours #${lesson.number - 1} pour débloquer ce chapitre.",
                color = if (isUnlocked) TextMutedGray else Color(0xFFFFB74D),
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            // Footer info
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
                            color = PythonGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    quizScore?.let { score ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (score >= 80) SuccessEmerald.copy(alpha = 0.2f)
                                    else PythonGold.copy(alpha = 0.2f)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Quiz : $score%",
                                color = if (score >= 80) SuccessEmerald else PythonGold,
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
