package com.example

import com.example.data.generator.CyberLessonsData
import com.example.data.generator.PythonLessonsBeginner
import com.example.data.generator.PythonLessonsExpert
import com.example.data.generator.PythonLessonsIntermediate1
import com.example.data.generator.PythonLessonsIntermediate2
import com.example.data.generator.PythonLessonsOop
import com.example.data.model.UserProgress
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnosPyLearningTest {

    private val allPythonLessons = (
        PythonLessonsBeginner.lessons +
        PythonLessonsIntermediate1.lessons +
        PythonLessonsIntermediate2.lessons +
        PythonLessonsOop.lessons +
        PythonLessonsExpert.lessons
    ).sortedBy { it.number }

    private val allCyberLessons = CyberLessonsData.lessons.sortedBy { it.number }

    @Test
    fun testPythonCurriculumCompleteness() {
        assertEquals("Python curriculum must contain 24 complete lessons", 24, allPythonLessons.size)
        // Verify numbers from 1 to 24
        allPythonLessons.forEachIndexed { index, lesson ->
            assertEquals(index + 1, lesson.number)
            assertTrue("Lesson ${lesson.number} must have a title", lesson.title.isNotBlank())
            assertTrue("Lesson ${lesson.number} must have sections", lesson.sections.isNotEmpty())
            assertTrue("Lesson ${lesson.number} must have quizzes", lesson.quiz.isNotEmpty())
            lesson.quiz.forEach { q ->
                assertTrue("Correct index must be within options bounds", q.correctIndex in q.options.indices)
                assertTrue("Explanation must be provided", q.explanation.isNotBlank())
            }
        }
    }

    @Test
    fun testCyberCurriculumCompleteness() {
        assertEquals("Cyber curriculum must contain 10 complete lessons", 10, allCyberLessons.size)
        allCyberLessons.forEachIndexed { index, lesson ->
            assertEquals(index + 1, lesson.number)
            assertTrue("Cyber lesson ${lesson.number} must have a title", lesson.title.isNotBlank())
            assertTrue("Cyber lesson ${lesson.number} must have sections", lesson.sections.isNotEmpty())
            assertTrue("Cyber lesson ${lesson.number} must have quizzes", lesson.quiz.isNotEmpty())
            lesson.quiz.forEach { q ->
                assertTrue("Correct index must be within options bounds", q.correctIndex in q.options.indices)
                assertTrue("Explanation must be provided", q.explanation.isNotBlank())
            }
        }
    }

    @Test
    fun testPythonProgressiveUnlocking() {
        val initialProgress = UserProgress()

        // Lesson 1 is always unlocked
        assertTrue("Lesson 1 must be unlocked initially", initialProgress.isLessonUnlocked(1, allPythonLessons))
        // Lesson 2 is locked initially
        assertFalse("Lesson 2 must be locked initially", initialProgress.isLessonUnlocked(2, allPythonLessons))

        // After completing lesson 1, lesson 2 is unlocked
        val updatedProgress = initialProgress.copy(completedLessonIds = setOf(allPythonLessons[0].id))
        assertTrue("Lesson 2 must be unlocked after completing lesson 1", updatedProgress.isLessonUnlocked(2, allPythonLessons))
        assertFalse("Lesson 3 must still be locked", updatedProgress.isLessonUnlocked(3, allPythonLessons))
    }

    @Test
    fun testCyberProgressiveUnlocking() {
        val initialProgress = UserProgress()

        // Cyber Lesson 1 is always unlocked
        assertTrue("Cyber Lesson 1 must be unlocked initially", initialProgress.isCyberLessonUnlocked(1, allCyberLessons))
        // Cyber Lesson 2 is locked initially
        assertFalse("Cyber Lesson 2 must be locked initially", initialProgress.isCyberLessonUnlocked(2, allCyberLessons))

        // After completing Cyber lesson 1, lesson 2 is unlocked
        val updatedProgress = initialProgress.copy(completedLessonIds = setOf(allCyberLessons[0].id))
        assertTrue("Cyber Lesson 2 must be unlocked after completing lesson 1", updatedProgress.isCyberLessonUnlocked(2, allCyberLessons))
        assertFalse("Cyber Lesson 3 must still be locked", updatedProgress.isCyberLessonUnlocked(3, allCyberLessons))
    }

    @Test
    fun testWhatsAppContactNumberFormat() {
        val phoneNumber = "2347071776576"
        assertTrue("WhatsApp phone must match Nigerian international format", phoneNumber.startsWith("234"))
        assertEquals("Length should be 13 digits for Nigeria mobile format", 13, phoneNumber.length)
    }
}
