package com.quiz.quizme

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.Question
import com.quiz.quizme.data.model.ReadQuestion
import com.quiz.quizme.data.model.SampleData
import com.quiz.quizme.databinding.FragmentHomeStudentBinding
import com.quiz.quizme.student.GameFragment
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(),MyCustomAdapter.RefreshPlease {

    private lateinit var binding : FragmentHomeStudentBinding

    private lateinit var myView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        if(LoginActivity.Role.equals("admin")) {
            Log.v("Kaloo if",LoginActivity.Role)

            myView = inflater.inflate(R.layout.fragment_home_admin, container, false)
            // Inflate the layout for this fragment
            return myView
        }else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_student,container,false)

            binding.playButton.setOnClickListener {
                if(getQuestionsFromDatabase()) {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
                }else{
                    Toast.makeText(requireContext(), "No Question Added yet !!!", Toast.LENGTH_LONG).show()
                }
            }
            return binding.root
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Id at menu item and Fragment must be same
        // otherwise we have to implement it own ourself like switch()/when() by id
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    fun getQuestionsFromDatabase() : Boolean{
        var category1  = SampleData.SAMPLE_CATEGORIES.get(0).categoryName
        var category1_list = ArrayList<Question>()
        var category2  = SampleData.SAMPLE_CATEGORIES.get(1).categoryName
        var category2_list = ArrayList<Question>()
        var category3  = SampleData.SAMPLE_CATEGORIES.get(2).categoryName
        var category3_list = ArrayList<Question>()
        var category4  = SampleData.SAMPLE_CATEGORIES.get(3).categoryName
        var category4_list = ArrayList<Question>()
        var category5  = SampleData.SAMPLE_CATEGORIES.get(4).categoryName
        var category5_list = ArrayList<Question>()
        var category6  = SampleData.SAMPLE_CATEGORIES.get(5).categoryName
        var category6_list = ArrayList<Question>()
        var category7  = SampleData.SAMPLE_CATEGORIES.get(6).categoryName
        var category7_list = ArrayList<Question>()

        val list = QuizContract.DatabaseHelper.getAllQuestionData()

        list.forEach { item->
            if(item.category.equals(category1)){
                category1_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category2)){
                category2_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category3)){
                category3_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category4)){
                category4_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category5)){
                category5_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category6)){
                category6_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
            else if(item.category.equals(category7)){
                category7_list.add(Question(item.question,item.category,item.answers,item.trueAnswer,item.date))
            }
        }

        var final_list = ArrayList<Question>()

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
            GameFragment.questions = final_list
            GameFragment.numQuestions = final_list.size
            return true
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(LoginActivity.Role.equals("admin")){
           refreshQuestions()
        }
    }

    fun refreshQuestions() {
        // getting the recyclerview by its id
        val recyclerview = myView.findViewById<RecyclerView>(R.id.recyclerview_questions)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)


        val list = QuizContract.DatabaseHelper.getAllQuestionData()

        Log.v("CHECK",list.toString())

        Log.v("CHECK",LoginActivity.Username+" "+ LoginActivity.Fullname )

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ReadQuestion>()

        // This loop will create 20 Views containing
        // the image with the count of view
        list.forEach { item->
            data.add(item)
        }

        Log.v("CHECK",data.toString())
        // This will pass the ArrayList to our Adapter
        val adapter = MyCustomAdapter(data,requireContext(),this.layoutInflater,this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }

    override fun onRefresh() {
        refreshQuestions()
    }


}


class MyCustomAdapter(private val mList: List<ReadQuestion>,private val context: Context,private val layoutInflater: LayoutInflater, private val refreshPlease: RefreshPlease) : RecyclerView.Adapter<MyCustomAdapter.ViewHolder>() {


    interface RefreshPlease{
        fun onRefresh()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomAdapter.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_question, parent, false)

        return MyCustomAdapter.ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: MyCustomAdapter.ViewHolder, position: Int) {

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

    private fun showImageChoiceDialogue(id:Int,context: Context,layoutInflater: LayoutInflater){
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
            QuizContract.DatabaseHelper.deleteQuestionData(id)
            alertDialog.dismiss()
            refreshPlease.onRefresh()
        }

        alertDialog.show()
    }
}