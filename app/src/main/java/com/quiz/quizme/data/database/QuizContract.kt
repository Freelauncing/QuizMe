package com.quiz.quizme.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.quiz.quizme.data.model.LoginUser
import com.quiz.quizme.data.model.Question
import com.quiz.quizme.data.model.StudentTest


class QuizContract{
    companion object {

        val DATABASE_NAME = "db_QUIZZ.db"
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
    
    class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            private lateinit var INSTANCE: DatabaseHelper
            private lateinit var database: SQLiteDatabase
            private var databaseOpen: Boolean = false

            fun closeDatabase() {
                if (database.isOpen && databaseOpen) {
                    database.close()
                    databaseOpen = false

                    Log.i("Database" , "Database close")
                }
            }

            fun initDatabaseInstance(ctx: Context): DatabaseHelper {
                INSTANCE = DatabaseHelper(ctx)
                return INSTANCE
            }

            fun insertStudentTestData(studentTest: StudentTest): Long {

                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val values = ContentValues()
                values.put(ROW_USERNAME, studentTest.username)
                values.put(ROW_FULLNAME, studentTest.fullname)
                values.put(ROW_SCORE, studentTest.score)
                values.put(ROW_TOTALSCORE, studentTest.totalscore)
                values.put(ROW_GRADE, studentTest.grade)
                values.put(ROW_DATE, studentTest.date)
                return database.insert(DATABASE_TABLE_STUDENT, null, values)
            }

            fun insertLoginUserData(loginUser: LoginUser): Long {

                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val values = ContentValues()
                values.put(ROW_USERNAME, loginUser.username)
                values.put(ROW_FULLNAME, loginUser.fullname)
                values.put(ROW_PASSWORD, loginUser.password)
                return database.insert(DATABASE_TABLE_STUDENT, null, values)
            }

            fun insertQuestionsData(question: Question): Long {

                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val values = ContentValues()
                values.put(ROW_QUESTION, question.question)
                values.put(ROW_QUESTIONCATEGORY, question.category)
                values.put(ROW_ANSWER1, question.answers.get(0))
                values.put(ROW_ANSWER2, question.answers.get(1))
                values.put(ROW_ANSWER3, question.answers.get(2))
                values.put(ROW_ANSWER4, question.answers.get(3))
                values.put(ROW_CORRENTANSWER, question.trueAnswer)
                values.put(ROW_DATE, question.date)
                return database.insert(DATABASE_TABLE_STUDENT, null, values)
            }

            fun getAllStudentData(): MutableList<StudentTest> {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val data: MutableList<StudentTest> = ArrayList()
                val cursor = database.rawQuery("SELECT * FROM ${DATABASE_TABLE_STUDENT}", null)
                cursor.use { cur ->
                    if (cursor.moveToFirst()) {
                        do {
                            val student = StudentTest(
                                cur.getString(0),
                                cur.getString(1),
                                cur.getString(2),
                                cur.getString(3),
                                cur.getString(4),
                                cur.getString(5)
                            )
                            data.add(student)

                        } while (cursor.moveToNext())
                    }
                }
                return data
            }

            fun getAllLoginUserData(): MutableList<LoginUser> {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val data: MutableList<LoginUser> = ArrayList()
                val cursor = database.rawQuery("SELECT * FROM ${DATABASE_TABLE_LOGINUSER}", null)
                cursor.use { cur ->
                    if (cursor.moveToFirst()) {
                        do {
                            val loginUser = LoginUser(
                                cur.getString(0),
                                cur.getString(1),
                                cur.getString(2)
                            )
                            data.add(loginUser)

                        } while (cursor.moveToNext())
                    }
                }
                return data
            }

            fun getAllQuestionData(): MutableList<Question> {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database" , "Database Open")
                }

                val data: MutableList<Question> = ArrayList()
                val cursor = database.rawQuery("SELECT * FROM ${DATABASE_TABLE_QUESTION}", null)
                cursor.use { cur ->
                    if (cursor.moveToFirst()) {
                        do {

                            val question = Question(
                                cur.getString(0),
                                cur.getString(1),
                                listOf<String>(cur.getString(2),
                                    cur.getString(3),
                                    cur.getString(4),
                                    cur.getString(5)),
                                cur.getString(6),
                                cur.getString(7)
                            )
                            data.add(question)

                        } while (cursor.moveToNext())
                    }
                }
                return data
            }
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0?.execSQL(QUERY_CREATE_STUDENT)
            p0?.execSQL(QUERY_CREATE_LOGINUSER)
            p0?.execSQL(QUERY_CREATE_QUESTION)
            Log.i("DATABASE", "DATABASE CREATED")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL(QUERY_UPGRADE_STUDENT)
            p0?.execSQL(QUERY_UPGRADE_LOGINUSER)
            p0?.execSQL(QUERY_UPGRADE_QUESTION)
            Log.i("DATABASE", "DATABASE UPDATED")
        }

    }
}