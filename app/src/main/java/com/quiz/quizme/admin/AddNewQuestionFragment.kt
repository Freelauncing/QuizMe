package com.quiz.quizme.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.quiz.quizme.R
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.Question
import java.text.SimpleDateFormat
import java.util.*


class AddNewQuestionFragment : Fragment() , AdapterView.OnItemSelectedListener{

    private lateinit  var selectedCategory:String
    private lateinit  var Question:String
    private lateinit  var Answer1:String
    private lateinit  var Answer2:String
    private lateinit  var Answer3:String
    private lateinit  var Answer4:String
    private lateinit  var correctAnswer:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val view = inflater.inflate(R.layout.fragment_add_new_question, container, false)

        view.findViewById<Button>(R.id.addQuestionButton).setOnClickListener {
            submit(view)
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun submit(view: View?) {
        Question = view!!.findViewById<EditText>(R.id.newQuestion).text.toString()
        Answer1 = view.findViewById<EditText>(R.id.Answer1).text.toString()
        Answer2 = view.findViewById<EditText>(R.id.Answer2).text.toString()
        Answer3 = view.findViewById<EditText>(R.id.Answer3).text.toString()
        Answer4 = view.findViewById<EditText>(R.id.Answer4).text.toString()

        val radioGroup = view.findViewById<RadioGroup>(R.id.questionRadioGroup)

        if(Question.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Question is Empty",Toast.LENGTH_SHORT).show()
        }
        else if(Answer1.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer1 is Empty",Toast.LENGTH_SHORT).show()
        }
        else if(Answer2.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer2 is Empty",Toast.LENGTH_SHORT).show()
        }
        else if(Answer3.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer3 is Empty",Toast.LENGTH_SHORT).show()
        }
        else if(Answer4.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Answer4 is Empty",Toast.LENGTH_SHORT).show()
        }
        else if(selectedCategory.equals("Select a Category")){
            Toast.makeText(requireContext(),"Category Not Selected",Toast.LENGTH_SHORT).show()
        }else {
            var check =false
            val list = QuizContract.DatabaseHelper.getAllQuestionData()
            list.forEach {
                if(it.question.equals(Question)){
                    check =true
                }
            }
            if(!check){
                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())

                val trueAnswer = getTrueAnswer(radioGroup.checkedRadioButtonId)
                val question = Question(
                    Question,
                    selectedCategory,
                    listOf(
                        Answer1, Answer2, Answer3, Answer4
                    ),
                    trueAnswer!!,
                    currentDate
                )

                val id = QuizContract.DatabaseHelper.insertQuestionsData(question)
                if (id != null) {
                    Toast.makeText(requireContext(), "Question Added Successfully !!!", Toast.LENGTH_LONG).show()
                    resetThings(view)
                }
                Log.v(
                    "DATOO",
                    Question + " " + Answer1 + " " + Answer2 + " " + Answer3 + " " + Answer4 + " " + selectedCategory
                )
            }else{
                Toast.makeText(requireContext(),"Question Already Exists !!!",Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun resetThings(view: View) {
        view.findViewById<EditText>(R.id.newQuestion).setText("")
        view.findViewById<EditText>(R.id.Answer1).setText("")
        view.findViewById<EditText>(R.id.Answer2).setText("")
        view.findViewById<EditText>(R.id.Answer3).setText("")
        view.findViewById<EditText>(R.id.Answer4).setText("")
        view.findViewById<Spinner>(R.id.spinner).setSelection(0)
        view.findViewById<RadioButton>(R.id.firstAnswerRadioButton).setChecked(true)
    }

    private fun getTrueAnswer(checkedId: Int): String? {
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
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.question_category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}