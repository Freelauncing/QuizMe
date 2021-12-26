package com.quiz.quizme.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.LoginActivity
import com.quiz.quizme.R
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.StudentTestModel


class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // getting the recyclerview by its id
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)


        val list = DatabaseHelper.getAllStudentData()

        Log.v("CHECK",list.toString())

        Log.v("CHECK",LoginActivity.Username+" "+ LoginActivity.Fullname )

        // ArrayList of class ItemsViewModel
        val data = ArrayList<StudentTestModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        list.forEach { item->
            if(item.username.equals(LoginActivity.Username) && item.fullname.equals(LoginActivity.Fullname)){
                data.add(item)
            }
        }

        Log.v("CHECK",data.toString())
        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data.reversed())

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}



class CustomAdapter(private val mList: List<StudentTestModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_record, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.grade_.setText(ItemsViewModel.grade)
        holder.score_.setText(ItemsViewModel.score)
        holder.totalScore_.setText(ItemsViewModel.totalscore)
        holder.date_.setText(ItemsViewModel.date)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val grade_: TextView = itemView.findViewById(R.id.grade)
        val score_: TextView = itemView.findViewById(R.id.score)
        val totalScore_: TextView = itemView.findViewById(R.id.totalScore)
        val date_: TextView = itemView.findViewById(R.id.date)
    }
}