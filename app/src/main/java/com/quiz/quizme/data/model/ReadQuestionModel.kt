package com.quiz.quizme.data.model

data class ReadQuestionModel (
    val id:Int,
    val question: String,
    val category: String,
    val answers: List<String>,
    val trueAnswer:String,
    val date: String,
)