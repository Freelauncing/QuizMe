package com.quiz.quizme.student.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.R
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.StudentTestModel
import com.quiz.quizme.student.statistics.StatisticsAdapter
import com.quiz.quizme.student.statistics.StatisticsController


class StatisticsFragment : Fragment() {

    private lateinit var myView: View

    private lateinit var controller: StatisticsController

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myView = inflater.inflate(R.layout.fragment_statistics, container, false)

        // getting the recyclerview by its id
        recyclerView = myView.findViewById(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inflate the layout for this fragment
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = DatabaseHelper.getAllStudentData()

        controller = StatisticsController(list,this)

        controller.showAllPreviousReports()

    }

    fun showInAdapter(list: List<StudentTestModel>){
        Log.v("CHECK",list.toString())
        // This will pass the ArrayList to our Adapter
        val adapter = StatisticsAdapter(list)
        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }
}



