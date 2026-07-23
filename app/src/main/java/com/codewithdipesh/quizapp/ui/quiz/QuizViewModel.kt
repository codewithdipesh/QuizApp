package com.codewithdipesh.quizapp.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithdipesh.quizapp.data.model.Question
import com.codewithdipesh.quizapp.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val questions = repository.loadQuestions()
                _state.update {
                    it.copy(
                        questions = questions,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
                Log.d("Quiz","${e.message}")
            }
        }
    }

    fun onAnswerSelected(answerIndex: Int) {

        val currentIndex = state.value.currentQuestionIndex
        if (currentIndex in state.value.revealedQuestions) return

        val question = state.value.questions[currentIndex]
        val isCorrect = question.correctOptionIndex == answerIndex

        _state.update {

            val newStreak = if (isCorrect) it.currentStreak + 1 else 0

            it.copy(
                selectedAnswers = it.selectedAnswers + (currentIndex to answerIndex),
                revealedQuestions = it.revealedQuestions + currentIndex,
                skippedQuestions = it.skippedQuestions - currentIndex,
                score = if (isCorrect) it.score + 10 else it.score,
                currentStreak = newStreak,
                longestStreak = maxOf(it.longestStreak, newStreak)
            )
        }
    }

    fun goToQuestion(index: Int) {
        if (index in state.value.questions.indices) {
            _state.update {
                it.copy(currentQuestionIndex = index)
            }
        } else {
            _state.update { it.copy(isQuizFinished = true) }
        }
    }

    fun skipQuestion() {
        val currentQuestionIndex = _state.value.currentQuestionIndex
        _state.update {
            it.copy(
                skippedQuestions = it.skippedQuestions.plus(currentQuestionIndex),
                selectedAnswers = _state.value.selectedAnswers.plus(currentQuestionIndex  to -1 ),
                currentStreak = 0
            )
        }
    }
}