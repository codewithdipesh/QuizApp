package com.codewithdipesh.quizapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = Color.White,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = Purple,
    secondary = Purple,
    tertiary = Success,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    surfaceContainer = SurfaceVariantLight,
    surfaceContainerHigh = SurfaceHighLight,
    surfaceContainerHighest = SurfaceLight,
    outline = BorderLight,
    error = Error,
    errorContainer = ErrorContainer,
    onError = Color.White,
    tertiaryContainer = QuestionBorderLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PurpleDark,
    onPrimary = Color.White,
    primaryContainer = PurpleContainerDark,
    onPrimaryContainer = Color.White,
    secondary = PurpleDark,
    tertiary = Success,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    surfaceContainer = SurfaceVariantDark,
    surfaceContainerHigh = SurfaceHighDark,
    surfaceContainerHighest = SurfaceDark,
    outline = BorderDark,
    error = Error,
    errorContainer = Color(0xFF572525),
    onError = Color.White,
    tertiaryContainer = QuestionBorderDark
)

@Composable
fun QuizAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}