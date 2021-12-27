package com.quiz.quizme.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.HomeFragmentDirections
import com.quiz.quizme.R
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.ReadQuestionModel

class HomeAdapter(private val mList: List<ReadQuestionModel>, private val context: Context, private val layoutInflater: LayoutInflater, private val refreshPlease: RefreshPlease) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    interface RefreshPlease{
        fun onRefresh()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_question, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.questionText_.setText(ItemsViewModel.question)
        holder.answerText_.setText(ItemsViewModel.trueAnswer)
        holder.categoryText_.setText(ItemsViewModel.category)
        holder.editQuestion_.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditQuestionFragment(
                ItemsViewModel.id
            )
            it.findNavController().navigate(action)
        }
        holder.deleteQuestion_.setOnClickListener {
            showImageChoiceDialogue(ItemsViewModel.id,context,layoutInflater)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val questionText_: TextView = itemView.findViewById(R.id.questionText)
        val answerText_: TextView = itemView.findViewById(R.id.answerText)
        val categoryText_: TextView = itemView.findViewById(R.id.categoryText)
        val editQuestion_: Button = itemView.findViewById(R.id.editQuestion)
        val deleteQuestion_: Button = itemView.findViewById(R.id.deleteQuestion)
    }

    private fun showImageChoiceDialogue(id:Int, context: Context, layoutInflater: LayoutInflater){
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_delete_dialogue, null)
        dialogBuilder.setView(dialogView)

        val btn_no = dialogView.findViewById<Button>(R.id.btn_no)
        val btn_yes = dialogView.findViewById<Button>(R.id.btn_yes)
        val txt_dialog_title = dialogView.findViewById<TextView>(R.id.txt_dialog_content)
        val alertDialog = dialogBuilder.create()
        txt_dialog_title.setText("Are you sure you want to permanantly delete the Question ?")

        btn_no.setOnClickListener {
            alertDialog.dismiss()
        }

        btn_yes.setOnClickListener {
            DatabaseHelper.deleteQuestionData(id)
            alertDialog.dismiss()
            refreshPlease.onRefresh()
        }

        alertDialog.show()
    }
}