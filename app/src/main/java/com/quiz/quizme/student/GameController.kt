package com.quiz.quizme.student

import com.quiz.quizme.data.model.QuestionModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val INCORRECT_BUZZ_PATTERN = longArrayOf(0, 400)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 1000)


class GameController(var list:MutableList<QuestionModel>, var gameFragment: GameFragment) {


    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        INCORRECT(INCORRECT_BUZZ_PATTERN),
    }

    var vibrate : Boolean = true

    // To track Current Question and Answer
    lateinit var currentQuestionModel: QuestionModel
    lateinit var answers: MutableList<String>

    // TO track question index and total questions
    private var questionIndex = 0
    private var score:Int =0

    // randomize the questions and set the first question
    fun randomizeQuestions() {
        list.shuffle()
        questionIndex = 0
        setQuestion()
    }


    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestionModel = list[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestionModel.answers.toMutableList()
        // and shuffle them
        answers.shuffle()

        gameFragment.setToolBarTitle(questionIndex+1)

        gameFragment.setQuestion(currentQuestionModel.question
            ,answers[0]
            ,answers[1]
            ,answers[2]
            ,answers[3])
    }

    fun makeDecision(answerIndex:Int) {
        if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
            score++
            gotoNextQuestion()

        } else {
            gotoNextQuestion()
        }
    }

    fun gotoNextQuestion(){
        questionIndex++
        // Advance to the next question
        if (questionIndex < GameFragment.numQuestions) {
            currentQuestionModel = list[questionIndex]
            setQuestion()
            gameFragment.invalidate()

        } else {
            gameFragment.buzz(BuzzType.GAME_OVER.pattern)
            gameFragment.moveToFinish(score)
        }
    }

    fun checkFirst() : Boolean{
        var answerIndex = 0
        if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
            if(vibrate)
                gameFragment.buzz(BuzzType.CORRECT.pattern)
            return true
        }else{
            if(vibrate)
                gameFragment.buzz(BuzzType.INCORRECT.pattern)
            return false
        }
    }

    fun checkSecond(): Boolean {
        var answerIndex = 1
        if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
            if(vibrate)
                gameFragment.buzz(BuzzType.CORRECT.pattern)
            return true
        }else{
            if(vibrate)
                gameFragment.buzz(BuzzType.INCORRECT.pattern)
            return false
        }
    }

    fun checkThird(): Boolean {
        var answerIndex = 2
        if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
            if(vibrate)
                gameFragment.buzz(BuzzType.CORRECT.pattern)
            return true
        }else{
            if(vibrate)
                gameFragment.buzz(BuzzType.INCORRECT.pattern)
            return false
        }
    }

    fun checkFourth(): Boolean {
        var answerIndex = 3
        if (answers[answerIndex] == currentQuestionModel.trueAnswer) {
            if(vibrate)
                gameFragment.buzz(BuzzType.CORRECT.pattern)
            return true
        }else{
            if(vibrate)
                gameFragment.buzz(BuzzType.INCORRECT.pattern)
            return false
        }
    }

    fun highlightRightAnswer() {
        gameFragment.highlightRightAnswer(currentQuestionModel.trueAnswer)
    }
}