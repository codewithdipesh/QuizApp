package com.codewithdipesh.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codewithdipesh.quizapp.data.feedback.haptic.HapticManager
import com.codewithdipesh.quizapp.data.feedback.sound.SoundManager
import com.codewithdipesh.quizapp.ui.quiz.QuizScreen
import com.codewithdipesh.quizapp.ui.quiz.QuizViewModel
import com.codewithdipesh.quizapp.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var hapticManager: HapticManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val quizViewModel = viewModel<QuizViewModel>()

            val state by quizViewModel.state.collectAsStateWithLifecycle()
            QuizAppTheme {
                QuizScreen(
                    state = state,
                    onAnswerClick = quizViewModel::onAnswerSelected,
                    onSkip = quizViewModel::skipQuestion,
                    onPageChanged = quizViewModel::goToQuestion,
                    onRestartQuiz = quizViewModel::restartQuiz,
                    soundManager = soundManager,
                    hapticManager = hapticManager
                )
            }
        }
    }
}
