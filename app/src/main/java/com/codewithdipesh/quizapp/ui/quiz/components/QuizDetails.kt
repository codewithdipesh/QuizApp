package com.codewithdipesh.quizapp.ui.quiz.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codewithdipesh.quizapp.R


@Composable
fun QuizDetails(
    modifier: Modifier = Modifier,
    totalQuestion : Int,
    currentIndex : Int,
    streak : Int
) {
    val progress by animateFloatAsState(
        targetValue = ((currentIndex + 1).toFloat() / totalQuestion.toFloat()),
        animationSpec = tween(400)
    )

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.streak_animation)
    )

    val scale = remember { Animatable(1f) }

    LaunchedEffect(streak){
        if(streak > 3){
            scale.animateTo(1.6f,tween(80))
            scale.animateTo(
                1f,
                spring(dampingRatio = 0.65f, stiffness = Spring.StiffnessLow)
            )
        }
    }
    Column {
        //question status and streak
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            //question status
            Column {
                Text(
                    text = "Question",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Row {
                    Text(
                        text = "${currentIndex + 1}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                    Text(
                        text = "/$totalQuestion",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

            }
            //streak
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = streak > 2,
                    enter = scaleIn(initialScale = 0.6f) + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(50.dp)
                    )
                }
                if(streak > 3){
                    Text(
                        text = "X${streak - 2}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.graphicsLayer{
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // progressbar
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20))
                    .background(MaterialTheme.colorScheme.primary.copy(0.2f))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20))
                    .fillMaxWidth(progress)
                    .background(MaterialTheme.colorScheme.primary)
            )

        }

    }
}