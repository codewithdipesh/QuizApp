package com.codewithdipesh.quizapp.ui.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithdipesh.quizapp.data.feedback.haptic.HapticManager
import com.codewithdipesh.quizapp.data.feedback.sound.SoundManager
import com.codewithdipesh.quizapp.data.model.Question
import com.codewithdipesh.quizapp.ui.quiz.components.LoadingScreen
import com.codewithdipesh.quizapp.ui.quiz.components.QuizDetails
import com.codewithdipesh.quizapp.ui.quiz.components.QuizItem
import com.codewithdipesh.quizapp.ui.quiz.components.QuizResultScreen
import com.codewithdipesh.quizapp.ui.quiz.components.customClickable
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuizScreen(
    state : QuizState,
    hapticManager: HapticManager,
    soundManager : SoundManager,
    onAnswerClick : (Int, Int) -> Unit,
    onSkip : (Int) -> Unit,
    onPageChanged : (Int) -> Unit,
    onRestartQuiz : () -> Unit
){
    val pagerState = rememberPagerState(
        initialPage = state.currentQuestionIndex,
        pageCount = { state.questions.size }
    )


    //sync pager - viewmodel (swipe)
    LaunchedEffect(pagerState.currentPage) {
        if (!pagerState.isScrollInProgress && pagerState.currentPage != state.currentQuestionIndex) {
            onPageChanged(pagerState.currentPage)
        }
    }

    //viewmodel -> pager (ex  restart )
    LaunchedEffect(state.currentQuestionIndex) {
        if (state.currentQuestionIndex == 0) {
            pagerState.scrollToPage(0)
        } else {
            pagerState.animateScrollToPage(state.currentQuestionIndex)
        }
    }

    // After 2 sec redirect to next ques if answered
    LaunchedEffect(state.currentQuestionIndex, state.revealedQuestions) {
        if (state.currentQuestionIndex in state.revealedQuestions) {

            val answer = state.selectedAnswers[state.currentQuestionIndex]
            val question = state.questions[state.currentQuestionIndex]

            if (answer == question.correctOptionIndex) {
                soundManager.playTap()
                hapticManager.correctHaptic()
            } else {
                soundManager.playWrong()
                hapticManager.wrongHaptic()
            }

            delay(2000.milliseconds)

            onPageChanged(state.currentQuestionIndex + 1)
        }
    }

    Scaffold(
       containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (state.isLoading) {
                LoadingScreen()
            } else if (state.isQuizFinished) {
                QuizResultScreen(
                    score = state.score,
                    totalQuestions = state.questions.size,
                    longestStreak = state.longestStreak,
                    onRestart = onRestartQuiz
                )
            } else {
                //question details
                QuizDetails(
                    totalQuestion = state.questions.size,
                    currentIndex = pagerState.currentPage,
                    streak = state.currentStreak
                )
                Spacer(Modifier.height(20.dp))
                //question
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 24.dp,
                    userScrollEnabled = state.currentQuestionIndex !in state.revealedQuestions,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.weight(1f)
                ) { page ->

                    val question = state.questions[page]

                    QuizItem(
                        question = question,
                        selectedAnswer = state.selectedAnswers[page],
                        isAnswerRevealed = page in state.revealedQuestions,
                        onClick = { answerIndex ->
                            onAnswerClick(page, answerIndex)
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Skip button
                if (state.currentQuestionIndex !in state.revealedQuestions) {
                    TextButton(
                        onClick = {
                            hapticManager.correctHaptic()
                            soundManager.playTap()
                            onSkip(pagerState.currentPage)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Skip Question",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}


