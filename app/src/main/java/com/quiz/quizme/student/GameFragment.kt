package com.quiz.quizme.student

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.quiz.quizme.R
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.database.SampleData
import com.quiz.quizme.databinding.FragmentGameBinding

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val INCORRECT_BUZZ_PATTERN = longArrayOf(0, 400)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 1000)


class GameFragment : Fragment() {

        enum class BuzzType(val pattern: LongArray) {
            CORRECT(CORRECT_BUZZ_PATTERN),
            GAME_OVER(GAME_OVER_BUZZ_PATTERN),
            INCORRECT(INCORRECT_BUZZ_PATTERN),
        }

        private var vibrate : Boolean = true

        private lateinit var binding : FragmentGameBinding

        // To track Current Question and Answer
        lateinit var currentQuestionModel: QuestionModel
        lateinit var answers: MutableList<String>

        // TO track question index and total questions
        private var questionIndex = 0
        private var score:Int =0

        companion object{
            var questionModels: MutableList<QuestionModel> = SampleData.SAMPLE_QUESTION_MODELS
            var numQuestions = 14
        }
        /**
         * Given a pattern, this method makes sure the device buzzes
         */
        private fun buzz(pattern: LongArray) {
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


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game,container,false)

            // Shuffles the questions and sets the question index to the first question.
            randomizeQuestions()

            // Must to View Everything means Dynamic Data
            // Bind this fragment class to the layout
            binding.game = this

               // binding.fourthAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
            binding.questionRadioGroup.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
                lockAnswers()
                checkAnswer(checkedId)
                vibrate = true
            }

            // Set the onClickListener for the submitButton
            binding.submitButton.setOnClickListener {
                    view:View ->

                vibrate = false

                val checkedId = binding.questionRadioGroup.checkedRadioButtonId

                // Do nothing if nothing is checked (id == -1)
                if (-1 != checkedId) {
                    var answerIndex = 0
                    when (checkedId) {
                        R.id.secondAnswerRadioButton -> answerIndex = 1
                        R.id.thirdAnswerRadioButton -> answerIndex = 2
                        R.id.fourthAnswerRadioButton -> answerIndex = 3
                    }

                    binding.questionRadioGroup.clearCheck()

                    if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
                        score++
                        gotoNextQuestion(view)

                    } else {
                        gotoNextQuestion(view)
                    }
                }
                resetBackground()
                unlockAnswer()
            }

            return binding.root
        }

    private fun unlockAnswer(){
        for (i in 0 until binding.questionRadioGroup.getChildCount()) {
            binding.questionRadioGroup.getChildAt(i).setEnabled(true)
        }
    }

    private fun lockAnswers(){
        for (i in 0 until binding.questionRadioGroup.getChildCount()) {
            binding.questionRadioGroup.getChildAt(i).setEnabled(false)
        }
    }

    private fun checkAnswer(checkedId: Int) {
        var answerIndex = 0
        when (checkedId) {
            R.id.firstAnswerRadioButton -> {
                answerIndex = 0
                if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
                    binding.firstAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                    if(vibrate)
                        buzz(BuzzType.CORRECT.pattern)
                }else{
                    binding.firstAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
                    if(vibrate)
                        buzz(BuzzType.INCORRECT.pattern)
                }
            }
            R.id.secondAnswerRadioButton -> {
                answerIndex = 1
                if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
                    binding.secondAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                    if(vibrate)
                    buzz(BuzzType.CORRECT.pattern)
                }else{
                    binding.secondAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
                    if(vibrate)
                    buzz(BuzzType.INCORRECT.pattern)
                }
            }
            R.id.thirdAnswerRadioButton -> {
                answerIndex = 2
                if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
                    binding.thirdAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                    if(vibrate)
                    buzz(BuzzType.CORRECT.pattern)
                }else{
                    binding.thirdAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
                    if(vibrate)
                    buzz(BuzzType.INCORRECT.pattern)
                }
            }
            R.id.fourthAnswerRadioButton -> {
                answerIndex = 3
                if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
                    binding.fourthAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#B7F8C9")
                    )
                    if(vibrate)
                    buzz(BuzzType.CORRECT.pattern)
                }else{
                    binding.fourthAnswerRadioButton.setBackgroundColor(
                        Color.parseColor("#ff9a9a")
                    )
                    if(vibrate)
                    buzz(BuzzType.INCORRECT.pattern)
                }
            }
        }
        highlightRightAnswer(currentQuestionModel.trueAnswer)
    }

    private fun highlightRightAnswer(answer: String) {
        if(answer.equals(binding.firstAnswerRadioButton.text)) binding.firstAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(binding.secondAnswerRadioButton.text)) binding.secondAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(binding.thirdAnswerRadioButton.text))binding.thirdAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
        if(answer.equals(binding.fourthAnswerRadioButton.text))binding.fourthAnswerRadioButton.setBackgroundColor(Color.parseColor("#B7F8C9"))
    }

    private fun gotoNextQuestion(view: View){
        questionIndex++
        // Advance to the next question
        if (questionIndex < numQuestions) {
            currentQuestionModel = questionModels[questionIndex]
            setQuestion()
            binding.invalidateAll()
        } else {
            buzz(BuzzType.GAME_OVER.pattern)
            // We've won!  Navigate to the gameWonFragment.
            view.findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToGameWonFragment(
                    numQuestions,
                    score
                ),
                NavOptions.Builder().setPopUpTo(R.id.gameFragment,true).build())
        }
    }

    private fun resetBackground(){
        binding.firstAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        binding.secondAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        binding.thirdAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
        binding.fourthAnswerRadioButton.setBackgroundColor(
            Color.parseColor("#00000000")
        )
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questionModels.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestionModel = questionModels[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestionModel.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }

}