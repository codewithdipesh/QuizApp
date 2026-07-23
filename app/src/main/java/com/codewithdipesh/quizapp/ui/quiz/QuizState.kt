package com.codewithdipesh.quizapp.ui.quiz

import com.codewithdipesh.quizapp.data.model.Question

data class QuizState (
    val isLoading: Boolean = true,
    val questions: List<Question> = emptyList(),
    val error: String? = null,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val revealedQuestions: Set<Int> = emptySet(),
    val skippedQuestions: List<Int> = emptyList(),
    val score: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val isQuizFinished: Boolean = false
)