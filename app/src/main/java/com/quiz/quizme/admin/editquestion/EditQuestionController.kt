package com.quiz.quizme.admin.editquestion

import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.model.ReadQuestionModel
import java.text.SimpleDateFormat
import java.util.*

class EditQuestionController(var list:MutableList<ReadQuestionModel>, var editQuestionFragment: EditQuestionFragment)  {

    private var selectedCategory : String = ""

    fun findQuestionAndShow(questionId: Int) {
        list.forEach {
            if(it.id == questionId){
                editQuestionFragment.showQuestion(
                    it.question,it.answers[0],it.answers[1],it.answers[2],it.answers[3],it.trueAnswer,it.category)
            }
        }
    }

    fun setSelectedCategory(string: String){
        selectedCategory=string
    }

    fun updateRecord(
        questionId: Int,
        question: String,
        Answer1: String,
        Answer2: String,
        Answer3: String,
        Answer4: String,
        trueAnswer: String?
    ) {

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

        val id = DatabaseHelper.updateQuestionData(newQuestion,questionId)
        if (id != null) {
            editQuestionFragment.showToast("Question Updated Successfully !!!")
            list = DatabaseHelper.getAllQuestionData()
        }
    }
}