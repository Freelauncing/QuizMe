package com.quiz.quizme.data.model


class SampleData {
    companion object{
        val SAMPLE_QUESTIONS: MutableList<Question> = mutableListOf(
            Question(
                question = "Choose the number that goes in the blank.\n"+"2,000 + _____ + 30 + 9 = 2,739",
                category = "Number – number and place value",
                answers = listOf("30", "700", "900", "2"),
                trueAnswer = "700",
                date = "25-12-2021"
            ),
            Question(
                question = "What is the value of four in the number?\n" + "890,465",
                category = "Number – number and place value",
                answers = listOf("40", "4000", "400", "40"),
                trueAnswer = "400",
                date = "25-12-2021"
            ),
            Question(
                question = "Dan raised 127 sheep. Mason raised 141 sheep. How many sheep did they raise in all?",
                category = "Number – addition and subtraction",
                answers = listOf("14", "265", "268", "269"),
                trueAnswer = "268",
                date = "25-12-2021"
            ),
            Question(
                question = "Jack has 734 baseball cards. Benny bought 341 of Jack's baseball cards. How many baseball cards does Jack have now?",
                category = "Number – addition and subtraction",
                answers = listOf("391", "392", "393", "394"),
                trueAnswer = "393",
                date = "25-12-2021"
            ),
            Question(
                question = "What is 2 multiplied by 18?",
                category = "Number – multiplication and division",
                answers = listOf("18", "27", "36", "24"),
                trueAnswer = "36",
                date = "25-12-2021"
            ),
            Question(
                question = "What is the result of 18 / 2 = ",
                category = "Number – multiplication and division",
                answers = listOf("2", "4", "9", "10"),
                trueAnswer = "9",
                date = "25-12-2021"
            ),
            Question(
                question = "In a fraction, the number on the bottom is the _____",
                category = "Number – fractions",
                answers = listOf("denominator", "numerators", "fraction", "propotion"),
                trueAnswer = "denominator",
                date = "25-12-2021"
            ),
            Question(
                question = "Tick the greatest one ",
                category = "Number – fractions",
                answers = listOf("6/12", "8/12", "3/12", "9/12"),
                trueAnswer = "9/12",
                date = "25-12-2021"
            ),
            Question(
                question = "The most appropriate unit to measure distance between two cities is ?",
                category = "Measurement",
                answers = listOf("meter", "kilometer", "centimeter", "picometer"),
                trueAnswer = "kilometer",
                date = "25-12-2021"
            ),
            Question(
                question = "How many centimetres are there in one metre?",
                category = "Measurement",
                answers = listOf("1000", "10", "100", "1"),
                trueAnswer = "100",
                date = "25-12-2021"
            ),
            Question(
                question = "How many corners does a circle have?",
                category = "Geometry – properties of shapes",
                answers = listOf("12", "2", "1", "0"),
                trueAnswer = "0",
                date = "25-12-2021"
            ),
            Question(
                question = "Which of the following polygons has 4 sides?",
                category = "Geometry – properties of shapes",
                answers = listOf("triangle", "parallelogram", "hexagon", "pentagon"),
                trueAnswer = "parallelogram",
                date = "25-12-2021"
            ),
            Question(
                question = "Opposite sides of _______ are equal",
                category = "Geometry – position and direction",
                answers = listOf("triangle", "circle", "rectangle", "square"),
                trueAnswer = "rectangle",
                date = "25-12-2021"
            ),
            Question(
                question = "A line segment from centre to the circle is called ",
                category = "Geometry – position and direction",
                answers = listOf("radius", "diameter", "chord", "distance"),
                trueAnswer = "radius",
                date = "25-12-2021"
            ),
        )
        val SAMPLE_CATEGORIES : MutableList<QuestionCategory> = mutableListOf(
            QuestionCategory(categoryName = "Number – number and place value"),
            QuestionCategory(categoryName = "Number – addition and subtraction"),
            QuestionCategory(categoryName = "Number – multiplication and division"),
            QuestionCategory(categoryName = "Number – fractions"),
            QuestionCategory(categoryName = "Measurement"),
            QuestionCategory(categoryName = "Geometry – properties of shapes"),
            QuestionCategory(categoryName = "Geometry – position and direction"),
        )
    }
}