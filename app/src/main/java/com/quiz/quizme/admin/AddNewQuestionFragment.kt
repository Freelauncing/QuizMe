package com.quiz.quizme.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.quiz.quizme.R
import com.quiz.quizme.data.database.DatabaseHelper


class AddNewQuestionFragment : Fragment() , AdapterView.OnItemSelectedListener{

    private lateinit  var selectedCategory:String
    private lateinit  var Question:String
    private lateinit  var Answer1:String
    private lateinit  var Answer2:String
    private lateinit  var Answer3:String
    private lateinit  var Answer4:String

    private lateinit var controller: AddNewQuestionController

    private lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myView = inflater.inflate(R.layout.fragment_add_new_question, container, false)

        myView.findViewById<Button>(R.id.addQuestionButton).setOnClickListener {
            submit()
        }

        return myView
    }

    private fun submit() {
        Question = myView.findViewById<EditText>(R.id.newQuestion).text.toString()
        Answer1 = myView.findViewById<EditText>(R.id.Answer1).text.toString()
        Answer2 = myView.findViewById<EditText>(R.id.Answer2).text.toString()
        Answer3 = myView.findViewById<EditText>(R.id.Answer3).text.toString()
        Answer4 = myView.findViewById<EditText>(R.id.Answer4).text.toString()

        val radioGroup = myView.findViewById<RadioGroup>(R.id.questionRadioGroup)

        if(Question.isNullOrEmpty()){
            showToast("Question is Empty")
        }
        else if(Answer1.isNullOrEmpty()){
            showToast("Answer1 is Empty")
        }
        else if(Answer2.isNullOrEmpty()){
            showToast("Answer2 is Empty")
        }
        else if(Answer3.isNullOrEmpty()){
            showToast("Answer3 is Empty")
        }
        else if(Answer4.isNullOrEmpty()){
            showToast("Answer4 is Empty")
        }
        else if(selectedCategory.equals("Select a Category")){
            showToast("Category Not Selected")
        }else {
           controller.checkAndSave(
                   Question.trim(),
                   Answer1.trim(),
                   Answer2.trim(),
                   Answer3.trim(),
                   Answer4.trim(),
                   getTrueAnswer(radioGroup.checkedRadioButtonId))
        }
    }

    fun resetThings() {
        myView.findViewById<EditText>(R.id.newQuestion).setText("")
        myView.findViewById<EditText>(R.id.Answer1).setText("")
        myView.findViewById<EditText>(R.id.Answer2).setText("")
        myView.findViewById<EditText>(R.id.Answer3).setText("")
        myView.findViewById<EditText>(R.id.Answer4).setText("")
        myView.findViewById<Spinner>(R.id.spinner).setSelection(0)
        myView.findViewById<RadioButton>(R.id.firstAnswerRadioButton).setChecked(true)
    }

    fun getTrueAnswer(checkedId: Int): String? {
        when (checkedId) {
            R.id.firstAnswerRadioButton-> return Answer1
            R.id.secondAnswerRadioButton -> return Answer2
            R.id.thirdAnswerRadioButton -> return Answer3
            R.id.fourthAnswerRadioButton -> return Answer4
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.question_category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        val list = DatabaseHelper.getAllQuestionData()

        controller = AddNewQuestionController(list,this)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent!!.getItemAtPosition(position).toString()
        controller.setSelectedCategory(parent!!.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}