package com.quiz.quizme.data.database

class QuizContract{
    companion object {

        val DATABASE_NAME = "db_QUIZZES.db"
        val DATABASE_VERSION = 1


        val DATABASE_TABLE_STUDENT = "TBL_STUDENTTEST"
        val DATABASE_TABLE_LOGINUSER = "TBL_LOGINUSER"
        val DATABASE_TABLE_QUESTION = "TBL_QUESTION"


        val ROW_ID = "_id"
        val ROW_PASSWORD = "password"
        val ROW_USERNAME = "username"
        val ROW_FULLNAME = "fullname"
        val ROW_SCORE = "score"
        val ROW_TOTALSCORE = "totalscore"
        val ROW_GRADE = "grade"
        val ROW_DATE = "date"
        val ROW_QUESTION = "question"
        val ROW_QUESTIONCATEGORY = "questioncategory"
        val ROW_ANSWER1 = "answer1"
        val ROW_ANSWER2 = "answer2"
        val ROW_ANSWER3 = "answer3"
        val ROW_ANSWER4 = "answer4"
        val ROW_CORRENTANSWER = "correctanswer"


        val QUERY_CREATE_STUDENT = "CREATE TABLE IF NOT EXISTS " +
                "$DATABASE_TABLE_STUDENT " +
                "($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ROW_USERNAME TEXT , $ROW_FULLNAME TEXT, " +
                "$ROW_SCORE TEXT , $ROW_TOTALSCORE TEXT , $ROW_GRADE TEXT , $ROW_DATE TEXT)"
        val QUERY_UPGRADE_STUDENT = "DROP TABLE IF EXISTS $DATABASE_TABLE_STUDENT"


        val QUERY_CREATE_LOGINUSER = "CREATE TABLE IF NOT EXISTS " +
                "$DATABASE_TABLE_LOGINUSER " +
                "($ROW_USERNAME TEXT PRIMARY KEY , " +
                "$ROW_FULLNAME TEXT, " +
                "$ROW_PASSWORD TEXT )"
        val QUERY_UPGRADE_LOGINUSER = "DROP TABLE IF EXISTS $DATABASE_TABLE_LOGINUSER"


        val QUERY_CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS " +
                "$DATABASE_TABLE_QUESTION " +
                "($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ROW_QUESTION TEXT , $ROW_QUESTIONCATEGORY TEXT, " +
                "$ROW_ANSWER1 TEXT , $ROW_ANSWER2 TEXT, " +
                "$ROW_ANSWER3 TEXT , $ROW_ANSWER4 TEXT, " +
                "$ROW_CORRENTANSWER TEXT , $ROW_DATE TEXT)"
        val QUERY_UPGRADE_QUESTION = "DROP TABLE IF EXISTS $DATABASE_TABLE_QUESTION"
    }
    

}