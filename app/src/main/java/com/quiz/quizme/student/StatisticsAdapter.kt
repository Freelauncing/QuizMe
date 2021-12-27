package com.quiz.quizme.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.R
import com.quiz.quizme.data.model.StudentTestModel

class StatisticsAdapter(private val mList: List<StudentTestModel>) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

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