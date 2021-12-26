package com.quiz.quizme.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.quiz.quizme.data.model.LoginUserModel
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.model.ReadQuestionModel
import com.quiz.quizme.data.model.StudentTestModel

class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx,
    QuizContract.DATABASE_NAME, null,
    QuizContract.DATABASE_VERSION
) {

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

        fun insertStudentTestData(studentTestModel: StudentTestModel): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(QuizContract.ROW_USERNAME, studentTestModel.username)
            values.put(QuizContract.ROW_FULLNAME, studentTestModel.fullname)
            values.put(QuizContract.ROW_SCORE, studentTestModel.score)
            values.put(QuizContract.ROW_TOTALSCORE, studentTestModel.totalscore)
            values.put(QuizContract.ROW_GRADE, studentTestModel.grade)
            values.put(QuizContract.ROW_DATE, studentTestModel.date)
            return database.insert(QuizContract.DATABASE_TABLE_STUDENT, null, values)
        }

        fun insertLoginUserData(loginUserModel: LoginUserModel): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(QuizContract.ROW_USERNAME, loginUserModel.username)
            values.put(QuizContract.ROW_FULLNAME, loginUserModel.fullname)
            values.put(QuizContract.ROW_PASSWORD, loginUserModel.password)
            return database.insert(QuizContract.DATABASE_TABLE_LOGINUSER, null, values)
        }

        fun insertQuestionsData(questionModel: QuestionModel): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(QuizContract.ROW_QUESTION, questionModel.question)
            values.put(QuizContract.ROW_QUESTIONCATEGORY, questionModel.category)
            values.put(QuizContract.ROW_ANSWER1, questionModel.answers.get(0))
            values.put(QuizContract.ROW_ANSWER2, questionModel.answers.get(1))
            values.put(QuizContract.ROW_ANSWER3, questionModel.answers.get(2))
            values.put(QuizContract.ROW_ANSWER4, questionModel.answers.get(3))
            values.put(QuizContract.ROW_CORRENTANSWER, questionModel.trueAnswer)
            values.put(QuizContract.ROW_DATE, questionModel.date)
            return database.insert(QuizContract.DATABASE_TABLE_QUESTION, null, values)
        }

        fun getAllStudentData(): MutableList<StudentTestModel> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<StudentTestModel> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${QuizContract.DATABASE_TABLE_STUDENT}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        val student = StudentTestModel(
                            cur.getString(1),
                            cur.getString(2),
                            cur.getString(3),
                            cur.getString(4),
                            cur.getString(5),
                            cur.getString(6)
                        )
                        data.add(student)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun getAllLoginUserData(): MutableList<LoginUserModel> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<LoginUserModel> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${QuizContract.DATABASE_TABLE_LOGINUSER}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        val loginUser = LoginUserModel(
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

        fun getAllQuestionData(): MutableList<ReadQuestionModel> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<ReadQuestionModel> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${QuizContract.DATABASE_TABLE_QUESTION}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val question = ReadQuestionModel(
                            cur.getInt(0),
                            cur.getString(1),
                            cur.getString(2),
                            listOf<String>(cur.getString(3),
                                cur.getString(4),
                                cur.getString(5),
                                cur.getString(6)),
                            cur.getString(7),
                            cur.getString(8)
                        )
                        data.add(question)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun updateQuestionData(questionModel: QuestionModel, id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(QuizContract.ROW_QUESTION, questionModel.question)
            values.put(QuizContract.ROW_QUESTIONCATEGORY, questionModel.category)
            values.put(QuizContract.ROW_ANSWER1, questionModel.answers.get(0))
            values.put(QuizContract.ROW_ANSWER2, questionModel.answers.get(1))
            values.put(QuizContract.ROW_ANSWER3, questionModel.answers.get(2))
            values.put(QuizContract.ROW_ANSWER4, questionModel.answers.get(3))
            values.put(QuizContract.ROW_CORRENTANSWER, questionModel.trueAnswer)
            values.put(QuizContract.ROW_DATE, questionModel.date)
            return database.update(QuizContract.DATABASE_TABLE_QUESTION, values, "${QuizContract.ROW_ID} = ${id}", null)
        }

        fun deleteQuestionData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            return database.delete(QuizContract.DATABASE_TABLE_QUESTION, "${QuizContract.ROW_ID} = $id", null)
        }

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(QuizContract.QUERY_CREATE_STUDENT)
        p0?.execSQL(QuizContract.QUERY_CREATE_LOGINUSER)
        p0?.execSQL(QuizContract.QUERY_CREATE_QUESTION)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(QuizContract.QUERY_UPGRADE_STUDENT)
        p0?.execSQL(QuizContract.QUERY_UPGRADE_LOGINUSER)
        p0?.execSQL(QuizContract.QUERY_UPGRADE_QUESTION)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}