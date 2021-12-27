package com.quiz.quizme.admin

import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.model.ReadQuestionModel
import java.text.SimpleDateFormat
import java.util.*

class AddNewQuestionController(var list:MutableList<ReadQuestionModel>,var addNewQuestionFragment: AddNewQuestionFragment) {

    private var selectedCategory : String = ""

    fun checkAndSave(question: String,Answer1: String,Answer2: String,Answer3: String,Answer4: String, trueAnswer: String?) {
        var check = false
        list.forEach {
            if(it.question.equals(question, ignoreCase = true)){
                check =true
            }
        }
        if(!check){
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val newQuestion = QuestionModel(
                question,
                selectedCategory,
                listOf(
                    Answer1, Answer2, Answer3, Answer4
                ),
                trueAnswer!!,
                currentDate
            )

            val id = DatabaseHelper.insertQuestionsData(newQuestion)
            if (id != null) {
                addNewQuestionFragment.showToast("Question Added Successfully !!!")
                addNewQuestionFragment.resetThings()
                list = DatabaseHelper.getAllQuestionData()
            }


        }else{
            addNewQuestionFragment.showToast("Question Already Exists !!!")
        }
    }

    fun setSelectedCategory(string: String){
        selectedCategory=string
    }
}