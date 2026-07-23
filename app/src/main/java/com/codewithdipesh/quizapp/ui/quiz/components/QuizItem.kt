package com.codewithdipesh.quizapp.ui.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithdipesh.quizapp.data.model.Question


@Composable
fun QuizItem(
    question: Question,
    selectedAnswer: Int?,
    isAnswerRevealed: Boolean,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //question
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.tertiaryContainer,
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = question.question,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier.padding(18.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        //options
        question.options.forEachIndexed { index, option->
            Box(
                modifier = Modifier
                    .customClickable(
                        enabled = !isAnswerRevealed
                    ){
                        onClick(index)
                    }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(13.dp))
                    .background(
                        when {
                            //answered revealed
                            //correct option :- show green
                            isAnswerRevealed && index == question.correctOptionIndex -> MaterialTheme.colorScheme.tertiary
                            //answered revealed
                            //wrong option and its clicked : show Red
                            isAnswerRevealed && index == selectedAnswer -> MaterialTheme.colorScheme.error

                            else -> MaterialTheme.colorScheme.surfaceContainer
                        }
                    ),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}

