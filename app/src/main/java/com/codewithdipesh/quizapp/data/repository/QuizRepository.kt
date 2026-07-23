package com.codewithdipesh.quizapp.data.repository

import com.codewithdipesh.quizapp.data.model.Question
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.json.Json

class QuizRepository(
    private val client: HttpClient
) {
    private val JSON_URL = "https://gist.githubusercontent.com/dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw"

    suspend fun loadQuestions(): List<Question> {

        val json = client.get(JSON_URL).body<String>()

        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.decodeFromString<List<Question>>(json)
    }
}