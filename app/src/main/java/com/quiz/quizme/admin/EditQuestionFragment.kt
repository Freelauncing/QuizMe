package com.quiz.quizme.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import com.quiz.quizme.R
import com.quiz.quizme.data.database.DatabaseHelper

class EditQuestionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var controller: EditQuestionController

    private lateinit var myView: View

    private val args: EditQuestionFragmentArgs by navArgs()

    private var questionId:Int = 0

    private lateinit  var selectedCategory:String
    private lateinit  var Question:String
    private lateinit  var Answer1:String
    private lateinit  var Answer2:String
    private lateinit  var Answer3:String
    private lateinit  var Answer4:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_edit_question, container, false)

        questionId = args.questionID

        Log.v("CHKK",questionId.toString())

        myView.findViewById<Button>(R.id.addQuestionButton).setOnClickListener {
            Update()
        }

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = myView.findViewById(R.id.spinner)

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

        controller = EditQuestionController(list,this)

        controller.findQuestionAndShow(questionId)


    }

    fun Update() {
        Question = myView.findViewById<EditText>(R.id.newQuestion).text.toString()
        Answer1 = myView.findViewById<EditText>(R.id.Answer1).text.toString()
        Answer2 = myView.findViewById<EditText>(R.id.Answer2).text.toString()
        Answer3 = myView.findViewById<EditText>(R.id.Answer3).text.toString()
        Answer4 = myView.findViewById<EditText>(R.id.Answer4).text.toString()

        val radioGroup = myView.findViewById<RadioGroup>(R.id.questionRadioGroup)

        if(Question.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Question is Empty", Toast.LENGTH_SHORT).show()
        }
        else if(Answer1.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer1 is Empty", Toast.LENGTH_SHORT).show()
        }
        else if(Answer2.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer2 is Empty", Toast.LENGTH_SHORT).show()
        }
        else if(Answer3.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer3 is Empty", Toast.LENGTH_SHORT).show()
        }
        else if(Answer4.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer4 is Empty", Toast.LENGTH_SHORT).show()
        }
        else if(selectedCategory.equals("Select a Category")){
            Toast.makeText(requireContext(),"Category Not Selected", Toast.LENGTH_SHORT).show()
        }else {

            controller.updateRecord(
                questionId,
                Question.trim(),
                Answer1.trim(),
                Answer2.trim(),
                Answer3.trim(),
                Answer4.trim(),
                getTrueAnswer(radioGroup.checkedRadioButtonId)
            )

        }
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent!!.getItemAtPosition(position).toString()
        controller.setSelectedCategory(parent!!.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun showQuestion(
        question: String,
        s1: String,
        s2: String,
        s3: String,
        s4: String,
        trueAnswer: String,
        category: String
    ) {
        myView.findViewById<EditText>(R.id.newQuestion).setText(question)
        myView.findViewById<EditText>(R.id.Answer1).setText(s1)
        myView.findViewById<EditText>(R.id.Answer2).setText(s2)
        myView.findViewById<EditText>(R.id.Answer3).setText(s3)
        myView.findViewById<EditText>(R.id.Answer4).setText(s4)

        if(trueAnswer.equals(s1)){
            myView.findViewById<RadioButton>(R.id.firstAnswerRadioButton).setChecked(true)
        }
        else if(trueAnswer.equals(s2)){
            myView.findViewById<RadioButton>(R.id.secondAnswerRadioButton).setChecked(true)
        }
        else if(trueAnswer.equals(s3)){
            myView.findViewById<RadioButton>(R.id.thirdAnswerRadioButton).setChecked(true)
        }
        else if(trueAnswer.equals(s4)){
            myView.findViewById<RadioButton>(R.id.fourthAnswerRadioButton).setChecked(true)
        }

        val spinner = myView.findViewById<Spinner>(R.id.spinner)
        for (i in 0 until spinner.getCount()) {
            if (spinner.getItemAtPosition(i).equals(category)) {
                spinner.setSelection(i)
                break
            }
        }

        selectedCategory = category
        controller.setSelectedCategory(category)
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}