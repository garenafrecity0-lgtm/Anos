package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PythonLesson
import com.example.ui.components.CodeBlockView
import com.example.ui.components.LevelBadge
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
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.PythonAppViewModel

@Composable
fun LessonDetailScreen(
    lesson: PythonLesson,
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.closeLesson()
    }

    val progress by viewModel.userProgress.collectAsState()
    val isCompleted = progress.completedLessonIds.contains(lesson.id)
    val isBookmarked = progress.bookmarkedLessonIds.contains(lesson.id)
    val quizScore = progress.quizScores[lesson.id]

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IdeBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Navigation header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { viewModel.closeLesson() },
                    modifier = Modifier.size(36.dp).testTag("btn_back_to_courses")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour aux cours",
                        tint = PythonGold,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = "Cours #${lesson.number}",
                    color = PythonGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                LevelBadge(level = lesson.level)

                IconButton(
                    onClick = { viewModel.toggleBookmark(lesson.id) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Favori",
                        tint = if (isBookmarked) PythonGold else TextMutedGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Title & Description Header
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = lesson.title,
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 28.sp
            )

            Text(
                text = lesson.subtitle,
                color = PythonBlue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "⏱️ ${lesson.durationMinutes} minutes de lecture",
                    color = TextMutedGray,
                    fontSize = 11.5.sp
                )
                Text(
                    text = "⚡ +${lesson.xpReward} XP",
                    color = PythonGold,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Summary Card
        Card(
            colors = CardDefaults.cardColors(containerColor = IdeSurface),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, PythonBlue.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "🎯 Objectif Pédagogique",
                    color = PythonGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lesson.summary,
                    color = TextWhite.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }

        // Sections
        lesson.sections.forEachIndexed { index, section ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = section.title,
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = section.content,
                    color = TextWhite.copy(alpha = 0.88f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                // Code Snippet
                section.codeSnippet?.let { snippet ->
                    CodeBlockView(
                        code = snippet,
                        title = "exemple_${lesson.number}_${index + 1}.py",
                        expectedOutput = section.expectedOutput
                    )
                }

                // Pro Tip Box
                section.tip?.let { tipText ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(IdeSurfaceVariant)
                            .border(1.dp, WarningAmber.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = WarningAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "Astuce de Développeur :",
                                    color = WarningAmber,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = tipText,
                                    color = TextWhite.copy(alpha = 0.9f),
                                    fontSize = 11.5.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                // Common Warning Box
                section.warning?.let { warnText ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ErrorCrimson.copy(alpha = 0.1f))
                            .border(1.dp, ErrorCrimson.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = ErrorCrimson,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "Erreur Fréquente à Éviter :",
                                    color = ErrorCrimson,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = warnText,
                                    color = TextWhite.copy(alpha = 0.9f),
                                    fontSize = 11.5.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quiz Call to action Card
        Card(
            colors = CardDefaults.cardColors(containerColor = IdeSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, PythonGold.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        tint = PythonGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Validez vos compétences avec le Quiz !",
                        color = PythonGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Testez immédiatement votre compréhension des concepts abordés dans cette leçon avec des questions à choix multiples et explications détaillées.",
                    color = TextMutedGray,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Button(
                    onClick = { viewModel.startLessonQuiz(lesson) },
                    colors = ButtonDefaults.buttonColors(containerColor = PythonGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp).testTag("btn_start_lesson_quiz")
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Lancer le Quiz de la leçon (${lesson.quiz.size} questions)",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Mark as completed button
        if (!isCompleted) {
            Button(
                onClick = { viewModel.markCurrentLessonCompleted() },
                colors = ButtonDefaults.buttonColors(containerColor = SuccessEmerald),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp).testTag("btn_mark_completed")
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Valider ce cours & Débloquer la suite (+${lesson.xpReward} XP) 🔓",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SuccessEmerald.copy(alpha = 0.15f))
                        .border(1.dp, SuccessEmerald.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Ce cours est validé ! Le chapitre suivant est débloqué. 🎉",
                            color = SuccessEmerald,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                val nextLesson = viewModel.allLessons.find { it.number == lesson.number + 1 }
                if (nextLesson != null) {
                    Button(
                        onClick = { viewModel.openLesson(nextLesson) },
                        colors = ButtonDefaults.buttonColors(containerColor = PythonGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(46.dp)
                    ) {
                        Text(
                            text = "Passer au Cours #${nextLesson.number} ❯",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}
