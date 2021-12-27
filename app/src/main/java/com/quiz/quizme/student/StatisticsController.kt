package com.quiz.quizme.student

import com.quiz.quizme.LoginActivity
import com.quiz.quizme.data.model.StudentTestModel

class StatisticsController(var list:MutableList<StudentTestModel> , var statisticsFragment: StatisticsFragment) {

    // ArrayList of class ItemsViewModel
    val data = ArrayList<StudentTestModel>()


    fun showAllPreviousReports() {
        list.forEach { item->
            if(item.username.equals(LoginActivity.Username) && item.fullname.equals(LoginActivity.Fullname)){
                data.add(item)
            }
        }

        statisticsFragment.showInAdapter(data.reversed())
    }

}