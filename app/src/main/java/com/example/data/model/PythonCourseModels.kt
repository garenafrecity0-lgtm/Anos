package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class PythonLevel(
    val title: String,
    val badge: String,
    val description: String,
    val iconEmoji: String,
    val primaryColor: Color,
    val order: Int
) {
    DEBUTANT(
        title = "Niveau 1 : Débutant Absolu",
        badge = "DÉBUTANT",
        description = "Bases, variables, types primitifs, entrées/sorties et opérateurs fondamentaux.",
        iconEmoji = "🌱",
        primaryColor = Color(0xFF387EB8),
        order = 1
    ),
    INTERMEDIAIRE_1(
        title = "Niveau 2 : Logique & Structures",
        badge = "STRUCTURES",
        description = "Conditions, boucles for/while, listes, tuples, dictionnaires et compréhensions.",
        iconEmoji = "⚡",
        primaryColor = Color(0xFFFFD438),
        order = 2
    ),
    INTERMEDIAIRE_2(
        title = "Niveau 3 : Fonctions & Modularité",
        badge = "FONCTIONS",
        description = "Fonctions, *args/**kwargs, lambdas, gestion des exceptions et manipulation de fichiers.",
        iconEmoji = "🧩",
        primaryColor = Color(0xFF00E5FF),
        order = 3
    ),
    AVANCE(
        title = "Niveau 4 : POO & Architecture",
        badge = "POO AVANCÉE",
        description = "Classes, méthodes dunder, héritage, polymorphisme, encapsulation et dataclasses.",
        iconEmoji = "🏛️",
        primaryColor = Color(0xFFA855F7),
        order = 4
    ),
    EXPERT(
        title = "Niveau 5 : Python Expert",
        badge = "EXPERT",
        description = "Décorateurs, générateurs, context managers, programmation asynchrone et typage statique.",
        iconEmoji = "🔥",
        primaryColor = Color(0xFFFF5252),
        order = 5
    )
}

data class LessonSection(
    val title: String,
    val content: String,
    val codeSnippet: String? = null,
    val expectedOutput: String? = null,
    val tip: String? = null,
    val warning: String? = null
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class PythonLesson(
    val id: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val level: PythonLevel,
    val durationMinutes: Int,
    val xpReward: Int,
    val summary: String,
    val sections: List<LessonSection>,
    val quiz: List<QuizQuestion>
)

data class PythonCheatSheetItem(
    val id: String,
    val category: String,
    val title: String,
    val code: String,
    val description: String
)

data class PythonPlaygroundSnippet(
    val id: String,
    val title: String,
    val level: PythonLevel,
    val description: String,
    val code: String,
    val simulatedOutput: String
)

data class UserProgress(
    val completedLessonIds: Set<String> = emptySet(),
    val bookmarkedLessonIds: Set<String> = emptySet(),
    val quizScores: Map<String, Int> = emptyMap(), // lessonId -> percentage
    val totalXp: Int = 0,
    val streakDays: Int = 1,
    val globalQuizPassedCount: Int = 0
) {
    val completedCount: Int get() = completedLessonIds.size
    
    val rankTitle: String get() = when {
        totalXp >= 1500 -> "Python Guru 🧙‍♂️"
        totalXp >= 1000 -> "Python Architecte 💎"
        totalXp >= 600 -> "Développeur Senior 🚀"
        totalXp >= 300 -> "Développeur Confirmé ⚡"
        totalXp >= 100 -> "Apprenti Python 🌱"
        else -> "Débutant Curieux 🔍"
    }

    val rankLevel: Int get() = when {
        totalXp >= 1500 -> 5
        totalXp >= 1000 -> 4
        totalXp >= 600 -> 3
        totalXp >= 300 -> 2
        totalXp >= 100 -> 1
        else -> 0
    }

    fun isLessonUnlocked(lessonNumber: Int, allLessons: List<PythonLesson>): Boolean {
        if (lessonNumber == 1) return true
        val previousLesson = allLessons.find { it.number == lessonNumber - 1 }
        return previousLesson != null && completedLessonIds.contains(previousLesson.id)
    }
}
