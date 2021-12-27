package com.quiz.quizme.student

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.quiz.quizme.R
import com.quiz.quizme.data.database.SampleData
import com.quiz.quizme.data.model.QuestionModel


class GameFragment : Fragment() {

    private lateinit var myView: View

    private lateinit var controller: GameController

    private lateinit var radioGroup: RadioGroup

    private lateinit var questionText:TextView

    private lateinit var firstAnswerRadioButton:RadioButton
    private lateinit var secondAnswerRadioButton:RadioButton
    private lateinit var thirdAnswerRadioButton:RadioButton
    private lateinit var fourthAnswerRadioButton:RadioButton

    companion object{
        var questionModels: MutableList<QuestionModel> = SampleData.SAMPLE_QUESTION_MODELS
        var numQuestions = 14
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_game,container,false)

        radioGroup = myView.findViewById(R.id.questionRadioGroup)

        firstAnswerRadioButton = myView.findViewById(R.id.firstAnswerRadioButton)
        secondAnswerRadioButton= myView.findViewById(R.id.secondAnswerRadioButton)
        thirdAnswerRadioButton= myView.findViewById(R.id.thirdAnswerRadioButton)
        fourthAnswerRadioButton= myView.findViewById(R.id.fourthAnswerRadioButton)

        questionText = myView.findViewById(R.id.questionText)

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = GameController(questionModels,this)

        // Shuffles the questions and sets the question index to the first question.
        controller.randomizeQuestions()

        // binding.fourthAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        radioGroup.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            lockAnswers()
            checkAnswer(checkedId)
            controller.vibrate = true
        }

        // Set the onClickListener for the submitButton
        myView.findViewById<Button>(R.id.submitButton).setOnClickListener {
                view:View ->

            controller.vibrate = false

            val checkedId = radioGroup.checkedRadioButtonId

            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                radioGroup.clearCheck()

                controller.makeDecision(answerIndex)

            }
            resetBackground()
            unlockAnswer()
        }

    }

    private fun checkAnswer(checkedId: Int) {
        when (checkedId) {
            R.id.firstAnswerRadioButton -> {
                if (controller.checkFirst())
                    firstAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                else
                    firstAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
            }
            R.id.secondAnswerRadioButton -> {
                if(controller.checkSecond())
                    secondAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                else
                    secondAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
            }
            R.id.thirdAnswerRadioButton -> {
                if(controller.checkThird())
                    thirdAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                else
                    thirdAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
            }
            R.id.fourthAnswerRadioButton -> {
                if(controller.checkFourth())
                    fourthAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                else
                    fourthAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
            }
        }
        controller.highlightRightAnswer()
    }

    fun highlightRightAnswer(answer: String) {
        if(answer.equals(firstAnswerRadioButton.text))
            firstAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(secondAnswerRadioButton.text))
            secondAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(thirdAnswerRadioButton.text))
            thirdAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(fourthAnswerRadioButton.text))
            fourthAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
    }



    private fun unlockAnswer(){
        for (i in 0 until radioGroup.getChildCount()) {
            radioGroup.getChildAt(i).setEnabled(true)
        }
    }

    private fun lockAnswers(){
        for (i in 0 until radioGroup.getChildCount()) {
            radioGroup.getChildAt(i).setEnabled(false)
        }
    }

    private fun resetBackground(){
        firstAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        secondAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        thirdAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        fourthAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
    }

    fun setToolBarTitle(questionIndex:Int){
        (activity as AppCompatActivity).supportActionBar?.title = getString(
            R.string.title_android_trivia_question, questionIndex,
            numQuestions
        )
    }
    /**
     * Given a pattern, this method makes sure the device buzzes
     */
    fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    fun invalidate() {
        myView.invalidate()
    }

    fun moveToFinish(score:Int) {
        Log.v("CKK","Before score="+score + " question="+numQuestions)
        // We've won!  Navigate to the gameWonFragment.
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameWonFragment(
                numQuestions, score
            ), NavOptions.Builder().setPopUpTo(R.id.gameFragment,true).build())
    }

    fun setQuestion(question: String, s: String, s1: String, s2: String, s3: String) {
        questionText.setText("Question : "+question)
        firstAnswerRadioButton.setText(s)
        secondAnswerRadioButton.setText(s1)
        thirdAnswerRadioButton.setText(s2)
        fourthAnswerRadioButton.setText(s3)
    }
}