package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.generator.PythonLessonsBeginner
import com.example.data.generator.PythonLessonsExpert
import com.example.data.generator.PythonLessonsIntermediate1
import com.example.data.generator.PythonLessonsIntermediate2
import com.example.data.generator.PythonLessonsOop
import com.example.data.generator.PythonPlaygroundData
import com.example.data.model.PythonCheatSheetItem
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.PythonPlaygroundSnippet
import com.example.data.model.QuizQuestion
import com.example.data.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PythonCourseRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("anos_py_progress_prefs", Context.MODE_PRIVATE)

    val allLessons: List<PythonLesson> = (
        PythonLessonsBeginner.lessons +
        PythonLessonsIntermediate1.lessons +
        PythonLessonsIntermediate2.lessons +
        PythonLessonsOop.lessons +
        PythonLessonsExpert.lessons
    ).sortedBy { it.number }

    val playgroundSnippets: List<PythonPlaygroundSnippet> = PythonPlaygroundData.snippets
    val cheatSheetItems: List<PythonCheatSheetItem> = PythonPlaygroundData.cheatSheetItems

    private val _userProgress = MutableStateFlow(loadUserProgress())
    val userProgress: StateFlow<UserProgress> = _userProgress.asStateFlow()

    private fun loadUserProgress(): UserProgress {
        val completed = prefs.getStringSet("completed_lessons", emptySet()) ?: emptySet()
        val bookmarks = prefs.getStringSet("bookmarked_lessons", emptySet()) ?: emptySet()
        val totalXp = prefs.getInt("total_xp", 0)
        val streak = prefs.getInt("streak_days", 1)
        val globalQuizPassed = prefs.getInt("global_quiz_passed", 0)

        val scores = mutableMapOf<String, Int>()
        allLessons.forEach { lesson ->
            val score = prefs.getInt("quiz_score_${lesson.id}", -1)
            if (score >= 0) {
                scores[lesson.id] = score
            }
        }

        return UserProgress(
            completedLessonIds = completed,
            bookmarkedLessonIds = bookmarks,
            quizScores = scores,
            totalXp = totalXp,
            streakDays = streak,
            globalQuizPassedCount = globalQuizPassed
        )
    }

    fun getLessonById(id: String): PythonLesson? {
        return allLessons.find { it.id == id }
    }

    fun getLessonsByLevel(level: PythonLevel?): List<PythonLesson> {
        return if (level == null) allLessons else allLessons.filter { it.level == level }
    }

    fun markLessonCompleted(lessonId: String) {
        val current = _userProgress.value
        if (current.completedLessonIds.contains(lessonId)) return

        val lesson = getLessonById(lessonId)
        val gainedXp = lesson?.xpReward ?: 50
        val newCompleted = current.completedLessonIds + lessonId
        val newXp = current.totalXp + gainedXp

        prefs.edit()
            .putStringSet("completed_lessons", newCompleted)
            .putInt("total_xp", newXp)
            .apply()

        _userProgress.value = current.copy(
            completedLessonIds = newCompleted,
            totalXp = newXp
        )
    }

    fun toggleBookmark(lessonId: String): Boolean {
        val current = _userProgress.value
        val isBookmarked = current.bookmarkedLessonIds.contains(lessonId)
        val newBookmarks = if (isBookmarked) {
            current.bookmarkedLessonIds - lessonId
        } else {
            current.bookmarkedLessonIds + lessonId
        }

        prefs.edit()
            .putStringSet("bookmarked_lessons", newBookmarks)
            .apply()

        _userProgress.value = current.copy(
            bookmarkedLessonIds = newBookmarks
        )
        return !isBookmarked
    }

    fun recordQuizScore(lessonId: String, scorePercentage: Int, bonusXp: Int) {
        val current = _userProgress.value
        val newScores = current.quizScores.toMutableMap()
        newScores[lessonId] = scorePercentage
        val newXp = current.totalXp + bonusXp

        prefs.edit()
            .putInt("quiz_score_$lessonId", scorePercentage)
            .putInt("total_xp", newXp)
            .apply()

        _userProgress.value = current.copy(
            quizScores = newScores,
            totalXp = newXp
        )
    }

    fun recordGlobalQuizPassed(scorePercentage: Int, earnedXp: Int) {
        val current = _userProgress.value
        val newPassed = current.globalQuizPassedCount + 1
        val newXp = current.totalXp + earnedXp

        prefs.edit()
            .putInt("global_quiz_passed", newPassed)
            .putInt("total_xp", newXp)
            .apply()

        _userProgress.value = current.copy(
            globalQuizPassedCount = newPassed,
            totalXp = newXp
        )
    }

    fun getAllQuizQuestions(): List<QuizQuestion> {
        return allLessons.flatMap { it.quiz }
    }

    fun getRandomQuizChallenge(count: Int = 10): List<QuizQuestion> {
        val all = getAllQuizQuestions()
        return all.shuffled().take(count)
    }

    fun resetProgress() {
        prefs.edit().clear().apply()
        _userProgress.value = UserProgress()
    }
}
