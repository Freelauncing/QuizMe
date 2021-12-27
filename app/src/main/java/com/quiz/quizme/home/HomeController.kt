package com.quiz.quizme.home

import android.util.Log
import com.quiz.quizme.HomeFragment
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.database.SampleData
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.model.ReadQuestionModel
import com.quiz.quizme.student.game.GameFragment
import java.util.*
import kotlin.collections.ArrayList

class HomeController(var list: MutableList<ReadQuestionModel>,var homeFragment: HomeFragment) {

    fun getQuestionsFromDatabase() : Boolean{
        var category1  = SampleData.SAMPLE_CATEGORY_MODELS.get(0).categoryName
        var category1_list = ArrayList<QuestionModel>()
        var category2  = SampleData.SAMPLE_CATEGORY_MODELS.get(1).categoryName
        var category2_list = ArrayList<QuestionModel>()
        var category3  = SampleData.SAMPLE_CATEGORY_MODELS.get(2).categoryName
        var category3_list = ArrayList<QuestionModel>()
        var category4  = SampleData.SAMPLE_CATEGORY_MODELS.get(3).categoryName
        var category4_list = ArrayList<QuestionModel>()
        var category5  = SampleData.SAMPLE_CATEGORY_MODELS.get(4).categoryName
        var category5_list = ArrayList<QuestionModel>()
        var category6  = SampleData.SAMPLE_CATEGORY_MODELS.get(5).categoryName
        var category6_list = ArrayList<QuestionModel>()
        var category7  = SampleData.SAMPLE_CATEGORY_MODELS.get(6).categoryName
        var category7_list = ArrayList<QuestionModel>()

        val list = DatabaseHelper.getAllQuestionData()

        list.forEach { item->
            if(item.category.equals(category1)){
                category1_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category2)){
                category2_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category3)){
                category3_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category4)){
                category4_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category5)){
                category5_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category6)){
                category6_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category7)){
                category7_list.add(QuestionModel(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
        }

        var final_list = ArrayList<QuestionModel>()

        if(!category1_list.isNullOrEmpty()){
            Collections.shuffle(category1_list)
            if(category1_list.size > 1){
                final_list.add(category1_list.get(0))
                final_list.add(category1_list.get(1))
            }else{
                final_list.add(category1_list.get(0))
            }
        }
        if(!category2_list.isNullOrEmpty()) {
            Collections.shuffle(category2_list)
            if(category2_list.size > 1){
                final_list.add(category2_list.get(0))
                final_list.add(category2_list.get(1))
            }else{
                final_list.add(category2_list.get(0))
            }
        }
        if(!category3_list.isNullOrEmpty()) {
            Collections.shuffle(category3_list)
            if(category3_list.size > 1){
                final_list.add(category3_list.get(0))
                final_list.add(category3_list.get(1))
            }else{
                final_list.add(category3_list.get(0))
            }
        }
        if(!category4_list.isNullOrEmpty()) {
            Collections.shuffle(category4_list)
            if(category4_list.size > 1){
                final_list.add(category4_list.get(0))
                final_list.add(category4_list.get(1))
            }else{
                final_list.add(category4_list.get(0))
            }
        }
        if(!category5_list.isNullOrEmpty()) {
            Collections.shuffle(category5_list)
            if(category5_list.size > 1){
                final_list.add(category5_list.get(0))
                final_list.add(category5_list.get(1))
            }else{
                final_list.add(category5_list.get(0))
            }
        }
        if(!category6_list.isNullOrEmpty()) {
            Collections.shuffle(category6_list)
            if(category6_list.size > 1){
                final_list.add(category6_list.get(0))
                final_list.add(category6_list.get(1))
            }else{
                final_list.add(category6_list.get(0))
            }
        }
        if(!category7_list.isNullOrEmpty()) {
            Collections.shuffle(category7_list)
            if(category7_list.size > 1){
                final_list.add(category7_list.get(0))
                final_list.add(category7_list.get(1))
            }else{
                final_list.add(category7_list.get(0))
            }
        }
        Log.v("FINALE list",final_list.toString())
        Log.v("FINALE size",final_list.size.toString())

        if(final_list.size>0) {
            GameFragment.questionModels = final_list
            GameFragment.numQuestions = final_list.size
            return true
        }
        return false
    }
}