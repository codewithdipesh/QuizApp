package com.codewithdipesh.quizapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id : Int,
    val question : String,
    val options : List<String>,
    val correctOptionIndex:Int
)
