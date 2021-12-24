package com.quiz.quizme.data.model

import android.annotation.SuppressLint


@SuppressLint("ParcelCreator")
data class StudentTest(
    val username: String,
    val fullname: String,
    val score: String,
    val totalscore: String,
    val grade: String,
    val date: String,
)
