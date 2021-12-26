package com.quiz.quizme.student

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.quiz.quizme.LoginActivity
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.StudentTestModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class GameFinishViewModel(application: Application):AndroidViewModel(application) {

    lateinit var studentTestModel:StudentTestModel

    val currentDate = SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(Date())

    var pieEntries: ArrayList<PieEntry>? = null

    private val _showGraphData = MutableLiveData<PieData>()
    val showGraphData: LiveData<PieData> = _showGraphData

    private val _score = MutableLiveData<String>()
    val score: LiveData<String> = _score

    private val _incorrectScore = MutableLiveData<String>()
    val incorrectScore: LiveData<String> = _incorrectScore

    private val _totalScore = MutableLiveData<String>()
    val totalScore: LiveData<String> = _totalScore

    fun start(numCorrect: Int, numQuestions: Int) {
      studentTestModel = StudentTestModel(
            LoginActivity.Username,
            LoginActivity.Fullname,
          numCorrect.toString(),
          numQuestions.toString(),
          numCorrect.toString()+" G ",
          currentDate)

        showReport()

        showGraph()

        insertReportToDatabase()
    }

    fun insertReportToDatabase(){
        viewModelScope.launch {
            DatabaseHelper.insertStudentTestData(studentTestModel)
        }
    }

    fun showGraph(){
        pieEntries = arrayListOf()

        pieEntries!!.add(PieEntry(studentTestModel.score.toFloat(),"Correct"))

        val res =  studentTestModel.totalscore.toInt() - studentTestModel.score.toInt()
        pieEntries!!.add(PieEntry( res.toFloat(),"Incorrect"))

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)

        _showGraphData.value = pieData

    }

    fun showReport() {
        Log.v("CHKK",studentTestModel.toString())
        _score.value = studentTestModel.score
        _incorrectScore.value = (studentTestModel.totalscore.toInt() - studentTestModel.score.toInt()).toString()
        _totalScore.value = (studentTestModel.score + "/" +studentTestModel.totalscore)
    }
}