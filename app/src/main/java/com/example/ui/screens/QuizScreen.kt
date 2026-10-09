package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CodeBlockView
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.IdeBackground
import com.example.ui.theme.IdeBorder
import com.example.ui.theme.IdeCardElevated
import com.example.ui.theme.IdeCodeBg
import com.example.ui.theme.IdeSurface
import com.example.ui.theme.IdeSurfaceVariant
import com.example.ui.theme.PythonBlue
import com.example.ui.theme.PythonGold
import com.example.ui.theme.SuccessEmerald
import com.example.ui.theme.TextMutedGray
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PythonAppViewModel

@Composable
fun QuizScreen(
    viewModel: PythonAppViewModel,
    modifier: Modifier = Modifier
) {
    val isQuizActive by viewModel.isQuizActive.collectAsState()
    val isQuizFinished by viewModel.isQuizFinished.collectAsState()
    val quizTitle by viewModel.quizTitle.collectAsState()
    val questions by viewModel.quizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswerIndex.collectAsState()
    val isSubmitted by viewModel.isAnswerSubmitted.collectAsState()
    val correctCount by viewModel.quizCorrectCount.collectAsState()
    val progress by viewModel.userProgress.collectAsState()

    BackHandler(enabled = isQuizActive) {
        viewModel.exitQuiz()
    }

    if (!isQuizActive) {
        // Idle State: Quiz Hub
        QuizHubView(
            viewModel = viewModel,
            globalPassedCount = progress.globalQuizPassedCount,
            modifier = modifier
        )
    } else if (isQuizFinished) {
        // Results State
        QuizResultView(
            total = questions.size,
            correct = correctCount,
            quizTitle = quizTitle,
            onRetry = {
                if (quizTitle.contains("Défi")) {
                    viewModel.startGlobalChallengeQuiz()
                } else {
                    viewModel.exitQuiz()
                }
            },
            onExit = { viewModel.exitQuiz() },
            modifier = modifier
        )
    } else {
        // Running Quiz State
        val currentQuestion = questions.getOrNull(currentIndex)
        if (currentQuestion != null) {
            val progressRatio = (currentIndex + 1).toFloat() / questions.size

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(IdeBackground)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Progress and exit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { viewModel.exitQuiz() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Quitter le quiz",
                            tint = TextMutedGray,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Text(
                        text = "Question ${currentIndex + 1} / ${questions.size}",
                        color = PythonGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IdeSurfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Score : $correctCount",
                            color = SuccessEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { progressRatio },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = PythonGold,
                    trackColor = IdeSurfaceVariant
                )

                // Question Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = IdeSurface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, IdeBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = currentQuestion.question,
                            color = TextWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.sp
                        )

                        // Code snippet inside question if present
                        currentQuestion.codeSnippet?.let { snippet ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(IdeCodeBg)
                                    .border(1.dp, IdeBorder, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = snippet.trim(),
                                    color = PythonGold,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }

                // Options List
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedAnswer == optIndex
                        val isCorrectOption = optIndex == currentQuestion.correctIndex

                        val backgroundColor = when {
                            isSubmitted && isCorrectOption -> SuccessEmerald.copy(alpha = 0.2f)
                            isSubmitted && isSelected && !isCorrectOption -> ErrorCrimson.copy(alpha = 0.2f)
                            isSelected -> PythonBlue.copy(alpha = 0.25f)
                            else -> IdeSurface
                        }

                        val borderColor = when {
                            isSubmitted && isCorrectOption -> SuccessEmerald
                            isSubmitted && isSelected && !isCorrectOption -> ErrorCrimson
                            isSelected -> PythonGold
                            else -> IdeBorder
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = backgroundColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isSubmitted) {
                                    viewModel.selectQuizAnswer(optIndex)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isSubmitted && isCorrectOption -> SuccessEmerald
                                                isSubmitted && isSelected && !isCorrectOption -> ErrorCrimson
                                                isSelected -> PythonGold
                                                else -> IdeSurfaceVariant
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSubmitted && isCorrectOption) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                    } else if (isSubmitted && isSelected && !isCorrectOption) {
                                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    } else {
                                        Text(
                                            text = listOf("A", "B", "C", "D").getOrElse(optIndex) { "?" },
                                            color = if (isSelected) Color.Black else TextWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }

                                Text(
                                    text = optionText,
                                    color = TextWhite,
                                    fontSize = 13.5.sp,
                                    lineHeight = 18.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // Explanation Card when submitted
                AnimatedVisibility(visible = isSubmitted) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = IdeSurfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, PythonGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = PythonGold, modifier = Modifier.size(18.dp))
                                Text("Explication Pédagogique :", color = PythonGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Text(
                                text = currentQuestion.explanation,
                                color = TextWhite.copy(alpha = 0.9f),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Action Button (Valider or Suivant)
                if (!isSubmitted) {
                    Button(
                        onClick = { viewModel.submitQuizAnswer() },
                        enabled = selectedAnswer != null,
                        colors = ButtonDefaults.buttonColors(containerColor = PythonGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_validate_answer")
                    ) {
                        Text(
                            text = "Valider ma réponse",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessEmerald),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_next_question")
                    ) {
                        Text(
                            text = if (currentIndex + 1 < questions.size) "Question Suivante ❯" else "Voir mes Résultats 🏆",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Composable
fun QuizHubView(
    viewModel: PythonAppViewModel,
    globalPassedCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IdeBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        // Big Banner for Global Challenge
        Card(
            colors = CardDefaults.cardColors(containerColor = IdeSurface),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, PythonGold.copy(alpha = 0.7f), RoundedCornerShape(18.dp))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(PythonGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = PythonGold, modifier = Modifier.size(24.dp))
                    }

                    Column {
                        Text(
                            text = "Grand Défi Python 🏆",
                            color = TextWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "10 questions aléatoires tous niveaux",
                            color = TextMutedGray,
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = "Mesurez votre niveau réel ! Ce test mélange des questions de syntaxe de base, de structures de données, de POO et de fonctionnalités avancées avec explications détaillées.",
                    color = TextWhite.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Button(
                    onClick = { viewModel.startGlobalChallengeQuiz() },
                    colors = ButtonDefaults.buttonColors(containerColor = PythonGold),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp).testTag("btn_start_global_challenge")
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Démarrer le Défi (10 Questions)",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Section Title: Lesson Quizzes
        Text(
            text = "Quizz par Leçon (${viewModel.allLessons.size})",
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        // List of all lessons with quiz buttons
        viewModel.allLessons.forEach { lesson ->
            val isUnlocked = viewModel.isLessonUnlocked(lesson)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isUnlocked) IdeSurface else IdeSurface.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isUnlocked) IdeBorder else IdeBorder.copy(alpha = 0.4f),
                        RoundedCornerShape(14.dp)
                    )
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
                            text = "Cours #${lesson.number} • ${lesson.title}",
                            color = if (isUnlocked) TextWhite else TextWhite.copy(alpha = 0.5f),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isUnlocked) "${lesson.quiz.size} questions disponibles" else "🔒 Débloquez le Cours #${lesson.number} d'abord",
                            color = if (isUnlocked) TextMutedGray else Color(0xFFFFB74D),
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = { viewModel.startLessonQuiz(lesson) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isUnlocked) PythonBlue else IdeSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = if (isUnlocked) "Quiz" else "🔒 Bloqué",
                            color = if (isUnlocked) Color.White else TextMutedGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Composable
fun QuizResultView(
    total: Int,
    correct: Int,
    quizTitle: String,
    onRetry: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percentage = (correct.toFloat() / total * 100).toInt()
    val isSuccess = percentage >= 70

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IdeBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(if (isSuccess) SuccessEmerald.copy(alpha = 0.2f) else ErrorCrimson.copy(alpha = 0.2f))
                .border(2.dp, if (isSuccess) SuccessEmerald else ErrorCrimson, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isSuccess) "🏆" else "💪",
                fontSize = 42.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isSuccess) "Félicitations ! Excellent travail !" else "Continuez vos efforts !",
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = quizTitle,
            color = PythonGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = IdeSurface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, IdeBorder, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$percentage%",
                    color = if (isSuccess) SuccessEmerald else PythonGold,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "$correct réponses correctes sur $total questions",
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Gain d'expérience : +${correct * 25} XP ⚡",
                    color = PythonGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = PythonGold),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retenter le Quiz", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onExit,
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, IdeBorder),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Retour à la liste des cours", color = TextWhite)
        }
    }
}
