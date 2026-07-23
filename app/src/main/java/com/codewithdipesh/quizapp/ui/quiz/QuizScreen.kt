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
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.codewithdipesh.quizapp.data.model.Question
import com.codewithdipesh.quizapp.ui.quiz.components.LoadingScreen
import com.codewithdipesh.quizapp.ui.quiz.components.QuizDetails
import com.codewithdipesh.quizapp.ui.quiz.components.QuizItem
import com.codewithdipesh.quizapp.ui.quiz.components.customClickable
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuizScreen(
   state : QuizState,
   onAnswerClick : (Int) -> Unit,
   onSkip : () -> Unit,
   onPageChanged : (Int) -> Unit,
   onRestartQuiz : () -> Unit
){
    val pagerState = rememberPagerState(
        initialPage = state.currentQuestionIndex,
        pageCount = { state.questions.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentQuestionIndex) {
            onPageChanged(pagerState.currentPage)
        }
    }
    //after 2 sec redirect to next ques
    LaunchedEffect(state.currentQuestionIndex, state.revealedQuestions) {
        if (state.currentQuestionIndex in state.revealedQuestions) {
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
            if(state.isLoading){
                LoadingScreen()
            }else{
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) { page ->

                    val question = state.questions[page]

                    QuizItem(
                        question = question,
                        selectedAnswer = state.selectedAnswers[page],
                        isAnswerRevealed = page in state.revealedQuestions,
                        onClick = onAnswerClick
                    )
                }

            }
        }
    }
}


