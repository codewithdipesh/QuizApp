package com.codewithdipesh.quizapp.di

import android.content.Context
import com.codewithdipesh.quizapp.data.feedback.haptic.HapticManager
import com.codewithdipesh.quizapp.data.feedback.haptic.HapticManagerImpl
import com.codewithdipesh.quizapp.data.feedback.sound.SoundManager
import com.codewithdipesh.quizapp.data.feedback.sound.SoundManagerImpl
import com.codewithdipesh.quizapp.data.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideQuizRepository(client: HttpClient): QuizRepository {
        return QuizRepository(client)
    }

    @Provides
    @Singleton
    fun provideSoundManager(
        @ApplicationContext context: Context
    ): SoundManager = SoundManagerImpl(context)

    @Provides
    @Singleton
    fun provideHapticManager(
        @ApplicationContext context: Context
    ): HapticManager = HapticManagerImpl(context)
}
