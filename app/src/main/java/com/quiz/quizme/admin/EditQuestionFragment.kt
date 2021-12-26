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
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.Question
import com.quiz.quizme.data.model.ReadQuestion
import java.text.SimpleDateFormat
import java.util.*

class EditQuestionFragment : Fragment(), AdapterView.OnItemSelectedListener {


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
        val view =  inflater.inflate(R.layout.fragment_edit_question, container, false)

        questionId = args.questionID

        Log.v("CHKK",questionId.toString())

        view.findViewById<Button>(R.id.addQuestionButton).setOnClickListener {
            Update(view)
        }

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = QuizContract.DatabaseHelper.getAllQuestionData()

        Log.v("CHKK",list.toString())

        list.forEach {
            if(it.id == questionId){
                view.findViewById<EditText>(R.id.newQuestion).setText(it.question)
                view.findViewById<EditText>(R.id.Answer1).setText(it.answers.get(0))
                view.findViewById<EditText>(R.id.Answer2).setText(it.answers.get(1))
                view.findViewById<EditText>(R.id.Answer3).setText(it.answers.get(2))
                view.findViewById<EditText>(R.id.Answer4).setText(it.answers.get(3))

                if(it.trueAnswer.equals(it.answers.get(0))){
                    view.findViewById<RadioButton>(R.id.firstAnswerRadioButton).setChecked(true)
                }
                else if(it.trueAnswer.equals(it.answers.get(1))){
                    view.findViewById<RadioButton>(R.id.secondAnswerRadioButton).setChecked(true)
                }
                else if(it.trueAnswer.equals(it.answers.get(2))){
                    view.findViewById<RadioButton>(R.id.thirdAnswerRadioButton).setChecked(true)
                }
                else if(it.trueAnswer.equals(it.answers.get(3))){
                    view.findViewById<RadioButton>(R.id.fourthAnswerRadioButton).setChecked(true)
                }

                val spinner = view.findViewById<Spinner>(R.id.spinner)
                for (i in 0 until spinner.getCount()) {
                    if (spinner.getItemAtPosition(i).equals(it.category)) {
                        spinner.setSelection(i)
                        break
                    }
                }

                selectedCategory = it.category
            }
        }


    }

    private fun Update(view: View?) {
        Question = view!!.findViewById<EditText>(R.id.newQuestion).text.toString()
        Answer1 = view.findViewById<EditText>(R.id.Answer1).text.toString()
        Answer2 = view.findViewById<EditText>(R.id.Answer2).text.toString()
        Answer3 = view.findViewById<EditText>(R.id.Answer3).text.toString()
        Answer4 = view.findViewById<EditText>(R.id.Answer4).text.toString()

        val radioGroup = view.findViewById<RadioGroup>(R.id.questionRadioGroup)

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

            val id = QuizContract.DatabaseHelper.updateQuestionData(question,questionId)
            if (id != null) {
                Toast.makeText(requireContext(), "Question Updated Successfully !!!", Toast.LENGTH_SHORT).show()
            }
            Log.v(
                "DATOO",
                Question + " " + Answer1 + " " + Answer2 + " " + Answer3 + " " + Answer4 + " " + selectedCategory
            )
        }
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}