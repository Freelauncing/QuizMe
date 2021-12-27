package com.quiz.quizme.student

import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.StudentTestModel
import java.util.ArrayList

class GameFinishController(var studentTestModel: StudentTestModel, var gameFinishFragment: GameFinishFragment) {

    var pieEntries: ArrayList<PieEntry>? = null

    fun graphData():PieData{
        pieEntries = arrayListOf()

        pieEntries!!.add(PieEntry(studentTestModel.score.toFloat(),"Correct"))

        val res =  studentTestModel.totalscore.toInt() - studentTestModel.score.toInt()
        pieEntries!!.add(PieEntry( res.toFloat(),"Incorrect"))

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 16f

        return PieData(pieDataSet)
    }


    fun showSaveAndReport(){
        DatabaseHelper.insertStudentTestData(studentTestModel)
        gameFinishFragment.showGraphAndReport(
            studentTestModel.score,
            (studentTestModel.totalscore.toInt() - studentTestModel.score.toInt()).toString(),
            (studentTestModel.score + "/" +studentTestModel.totalscore),
        graphData())
    }
}