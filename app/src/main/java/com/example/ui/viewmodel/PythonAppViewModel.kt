package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CyberLesson
import com.example.data.model.CyberLevel
import com.example.data.model.PythonCheatSheetItem
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.PythonPlaygroundSnippet
import com.example.data.model.QuizQuestion
import com.example.data.model.UserProgress
import com.example.data.repository.PythonCourseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PythonAppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PythonCourseRepository(application)

    val userProgress: StateFlow<UserProgress> = repository.userProgress
    val allLessons: List<PythonLesson> = repository.allLessons
    val allCyberLessons: List<CyberLesson> = repository.allCyberLessons
    val playgroundSnippets: List<PythonPlaygroundSnippet> = repository.playgroundSnippets
    val cheatSheetItems: List<PythonCheatSheetItem> = repository.cheatSheetItems

    // Filter & Search Python
    private val _selectedLevel = MutableStateFlow<PythonLevel?>(null)
    val selectedLevel: StateFlow<PythonLevel?> = _selectedLevel.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredLessons: StateFlow<List<PythonLesson>> = combine(
        _selectedLevel,
        _searchQuery
    ) { level, query ->
        allLessons.filter { lesson ->
            val matchesLevel = level == null || lesson.level == level
            val matchesQuery = query.isBlank() ||
                lesson.title.contains(query, ignoreCase = true) ||
                lesson.subtitle.contains(query, ignoreCase = true) ||
                lesson.summary.contains(query, ignoreCase = true) ||
                lesson.sections.any { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }
            matchesLevel && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allLessons)

    // Current Lesson Detail
    private val _selectedLesson = MutableStateFlow<PythonLesson?>(null)
    val selectedLesson: StateFlow<PythonLesson?> = _selectedLesson.asStateFlow()

    // Cyber Security Screen State
    private val _selectedCyberLevel = MutableStateFlow<CyberLevel?>(null)
    val selectedCyberLevel: StateFlow<CyberLevel?> = _selectedCyberLevel.asStateFlow()

    private val _cyberSearchQuery = MutableStateFlow("")
    val cyberSearchQuery: StateFlow<String> = _cyberSearchQuery.asStateFlow()

    val filteredCyberLessons: StateFlow<List<CyberLesson>> = combine(
        _selectedCyberLevel,
        _cyberSearchQuery
    ) { level, query ->
        allCyberLessons.filter { lesson ->
            val matchesLevel = level == null || lesson.level == level
            val matchesQuery = query.isBlank() ||
                lesson.title.contains(query, ignoreCase = true) ||
                lesson.subtitle.contains(query, ignoreCase = true) ||
                lesson.summary.contains(query, ignoreCase = true) ||
                lesson.sections.any { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }
            matchesLevel && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allCyberLessons)

    private val _selectedCyberLesson = MutableStateFlow<CyberLesson?>(null)
    val selectedCyberLesson: StateFlow<CyberLesson?> = _selectedCyberLesson.asStateFlow()

    // Quiz Session State
    private val _isQuizActive = MutableStateFlow(false)
    val isQuizActive: StateFlow<Boolean> = _isQuizActive.asStateFlow()

    private val _quizTitle = MutableStateFlow("")
    val quizTitle: StateFlow<String> = _quizTitle.asStateFlow()

    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _quizCorrectCount = MutableStateFlow(0)
    val quizCorrectCount: StateFlow<Int> = _quizCorrectCount.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    private val _activeQuizLessonId = MutableStateFlow<String?>(null)

    // Playground Interactive Code Simulator
    private val _currentPlaygroundSnippet = MutableStateFlow<PythonPlaygroundSnippet>(playgroundSnippets.first())
    val currentPlaygroundSnippet: StateFlow<PythonPlaygroundSnippet> = _currentPlaygroundSnippet.asStateFlow()

    private val _playgroundCode = MutableStateFlow(playgroundSnippets.first().code)
    val playgroundCode: StateFlow<String> = _playgroundCode.asStateFlow()

    private val _playgroundOutput = MutableStateFlow("Sortie console prête. Appuyez sur Exécuter ▶️")
    val playgroundOutput: StateFlow<String> = _playgroundOutput.asStateFlow()

    private val _isPlaygroundRunning = MutableStateFlow(false)
    val isPlaygroundRunning: StateFlow<Boolean> = _isPlaygroundRunning.asStateFlow()

    // Toast feedback message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun filterByLevel(level: PythonLevel?) {
        _selectedLevel.value = level
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun isLessonUnlocked(lesson: PythonLesson): Boolean {
        return userProgress.value.isLessonUnlocked(lesson.number, allLessons)
    }

    fun openLesson(lesson: PythonLesson) {
        if (!isLessonUnlocked(lesson)) {
            val prevNumber = lesson.number - 1
            _toastMessage.value = "🔒 Chapitre verrouillé ! Terminez d'abord le Cours #$prevNumber pour débloquer la suite."
            return
        }
        _selectedLesson.value = lesson
    }

    fun closeLesson() {
        _selectedLesson.value = null
    }

    fun markCurrentLessonCompleted() {
        val lesson = _selectedLesson.value ?: return
        repository.markLessonCompleted(lesson.id)
        val nextNumber = lesson.number + 1
        val nextLesson = allLessons.find { it.number == nextNumber }
        if (nextLesson != null) {
            _toastMessage.value = "🎉 Cours #${lesson.number} validé ! Le Cours #$nextNumber est désormais débloqué ! 🔓"
        } else {
            _toastMessage.value = "🏆 Félicitations ! Vous avez complété tous les cours de Anos py !"
        }
    }

    // Cyber Security Methods
    fun filterByCyberLevel(level: CyberLevel?) {
        _selectedCyberLevel.value = level
    }

    fun setCyberSearchQuery(query: String) {
        _cyberSearchQuery.value = query
    }

    fun isCyberLessonUnlocked(lesson: CyberLesson): Boolean {
        return userProgress.value.isCyberLessonUnlocked(lesson.number, allCyberLessons)
    }

    fun openCyberLesson(lesson: CyberLesson) {
        if (!isCyberLessonUnlocked(lesson)) {
            val prevNumber = lesson.number - 1
            _toastMessage.value = "🔒 Chapitre verrouillé ! Terminez d'abord le Cours #$prevNumber pour débloquer la suite."
            return
        }
        _selectedCyberLesson.value = lesson
    }

    fun closeCyberLesson() {
        _selectedCyberLesson.value = null
    }

    fun markCurrentCyberLessonCompleted() {
        val lesson = _selectedCyberLesson.value ?: return
        repository.markLessonCompleted(lesson.id)
        val nextNumber = lesson.number + 1
        val nextLesson = allCyberLessons.find { it.number == nextNumber }
        if (nextLesson != null) {
            _toastMessage.value = "🎉 Cours Cyber #${lesson.number} validé ! Le Cours #$nextNumber est désormais débloqué ! 🔓"
        } else {
            _toastMessage.value = "🛡️ Félicitations ! Vous avez complété tous les cours de Cybersécurité !"
        }
    }

    fun toggleBookmark(lessonId: String) {
        val isNowBookmarked = repository.toggleBookmark(lessonId)
        _toastMessage.value = if (isNowBookmarked) "⭐ Leçon ajoutée aux favoris !" else "Leçon retirée des favoris"
    }

    // Quiz Launchers
    fun startLessonQuiz(lesson: PythonLesson) {
        if (!isLessonUnlocked(lesson)) {
            _toastMessage.value = "🔒 Chapitre verrouillé ! Complétez le cours précédent d'abord."
            return
        }
        if (lesson.quiz.isEmpty()) {
            _toastMessage.value = "Aucun quiz disponible pour ce cours."
            return
        }
        _activeQuizLessonId.value = lesson.id
        _quizTitle.value = "Quiz : ${lesson.title}"
        _quizQuestions.value = lesson.quiz
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
        _quizCorrectCount.value = 0
        _isQuizFinished.value = false
        _isQuizActive.value = true
    }

    fun startCyberQuiz(lesson: CyberLesson) {
        if (!isCyberLessonUnlocked(lesson)) {
            _toastMessage.value = "🔒 Chapitre verrouillé ! Complétez le cours précédent d'abord."
            return
        }
        if (lesson.quiz.isEmpty()) {
            _toastMessage.value = "Aucun quiz disponible pour ce cours."
            return
        }
        _activeQuizLessonId.value = lesson.id
        _quizTitle.value = "Quiz Cyber : ${lesson.title}"
        _quizQuestions.value = lesson.quiz
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
        _quizCorrectCount.value = 0
        _isQuizFinished.value = false
        _isQuizActive.value = true
    }

    fun startGlobalChallengeQuiz() {
        val questions = repository.getRandomQuizChallenge(10)
        _activeQuizLessonId.value = null
        _quizTitle.value = "Grand Défi Python & Cybersécurité (10 Questions)"
        _quizQuestions.value = questions
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
        _quizCorrectCount.value = 0
        _isQuizFinished.value = false
        _isQuizActive.value = true
    }

    fun selectQuizAnswer(index: Int) {
        if (_isAnswerSubmitted.value) return
        _selectedAnswerIndex.value = index
    }

    fun submitQuizAnswer() {
        val selected = _selectedAnswerIndex.value ?: return
        val questions = _quizQuestions.value
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex >= questions.size) return

        val question = questions[currentIndex]
        val isCorrect = selected == question.correctIndex
        if (isCorrect) {
            _quizCorrectCount.value += 1
        }
        _isAnswerSubmitted.value = true
    }

    fun nextQuizQuestion() {
        val currentIndex = _currentQuestionIndex.value
        val total = _quizQuestions.value.size
        if (currentIndex + 1 < total) {
            _currentQuestionIndex.value = currentIndex + 1
            _selectedAnswerIndex.value = null
            _isAnswerSubmitted.value = false
        } else {
            // Quiz completed!
            _isQuizFinished.value = true
            val correct = _quizCorrectCount.value
            val percentage = (correct.toFloat() / total * 100).toInt()
            val earnedXp = correct * 25

            val lessonId = _activeQuizLessonId.value
            if (lessonId != null) {
                repository.recordQuizScore(lessonId, percentage, earnedXp)
                repository.markLessonCompleted(lessonId)
            } else {
                repository.recordGlobalQuizPassed(percentage, earnedXp)
            }
            _toastMessage.value = "🏆 Quiz terminé : $correct/$total ($percentage%) ! +$earnedXp XP"
        }
    }

    val activeQuizLessonId: StateFlow<String?> = _activeQuizLessonId.asStateFlow()

    fun exitQuiz() {
        _isQuizActive.value = false
        _isQuizFinished.value = false
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
    }

    fun retryCurrentQuiz() {
        val lessonId = _activeQuizLessonId.value
        if (lessonId == null) {
            startGlobalChallengeQuiz()
        } else {
            val pyLesson = allLessons.find { it.id == lessonId }
            val cyLesson = allCyberLessons.find { it.id == lessonId }
            if (pyLesson != null) {
                _quizTitle.value = "Quiz : ${pyLesson.title}"
                _quizQuestions.value = pyLesson.quiz
                _currentQuestionIndex.value = 0
                _selectedAnswerIndex.value = null
                _isAnswerSubmitted.value = false
                _quizCorrectCount.value = 0
                _isQuizFinished.value = false
                _isQuizActive.value = true
            } else if (cyLesson != null) {
                _quizTitle.value = "Quiz Cyber : ${cyLesson.title}"
                _quizQuestions.value = cyLesson.quiz
                _currentQuestionIndex.value = 0
                _selectedAnswerIndex.value = null
                _isAnswerSubmitted.value = false
                _quizCorrectCount.value = 0
                _isQuizFinished.value = false
                _isQuizActive.value = true
            } else {
                startGlobalChallengeQuiz()
            }
        }
    }

    // Playground Actions
    fun setPlaygroundCode(code: String) {
        _playgroundCode.value = code
    }

    fun loadPlaygroundSnippet(snippet: PythonPlaygroundSnippet) {
        _currentPlaygroundSnippet.value = snippet
        _playgroundCode.value = snippet.code
        _playgroundOutput.value = "Code chargé : ${snippet.title}\nAppuyez sur Exécuter ▶️ pour lancer la simulation."
    }

    fun runPlaygroundCode() {
        viewModelScope.launch {
            _isPlaygroundRunning.value = true
            _playgroundOutput.value = "⚙️ [Python 3.12] Exécution du script en cours..."
            delay(500)
            
            val currentSnippet = _currentPlaygroundSnippet.value
            val code = _playgroundCode.value.trim()
            
            val output = if (code.contains("print(") || code.contains("def ")) {
                val lines = code.lines()
                val printOutputs = mutableListOf<String>()
                for (line in lines) {
                    val trimmed = line.trim()
                    if (trimmed.startsWith("print(") && trimmed.endsWith(")")) {
                        val inside = trimmed.removePrefix("print(").removeSuffix(")").trim()
                        val unquoted = inside
                            .removeSurrounding("\"")
                            .removeSurrounding("'")
                            .replace("\\n", "\n")
                        printOutputs.add(unquoted)
                    }
                }
                if (printOutputs.isNotEmpty() && code != currentSnippet.code.trim()) {
                    printOutputs.joinToString("\n") + "\n\n>>> Process finished with exit code 0"
                } else {
                    currentSnippet.simulatedOutput + "\n\n>>> Process finished with exit code 0"
                }
            } else {
                currentSnippet.simulatedOutput + "\n\n>>> Process finished with exit code 0"
            }

            _playgroundOutput.value = output
            _isPlaygroundRunning.value = false
            _toastMessage.value = "▶️ Script Python exécuté avec succès !"
        }
    }

    // WhatsApp Contact Action (+23407071776576)
    fun openWhatsAppContact(context: Context) {
        val phone = "2347071776576" // Nigeria format for +23407071776576
        val message = "Bonjour Anos ! J'utilise votre application Anos py (Python & Cybersécurité) et je souhaite vous contacter."
        val url = "https://wa.me/$phone?text=${Uri.encode(message)}"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val apiIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(message)}")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(apiIntent)
            } catch (e2: Exception) {
                Toast.makeText(context, "Contact WhatsApp : +234 07071776576", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun resetProgress() {
        repository.resetProgress()
        _toastMessage.value = "Progression réinitialisée à zéro."
    }
}
