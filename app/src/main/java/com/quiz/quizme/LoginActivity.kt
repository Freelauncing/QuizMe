package com.quiz.quizme

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.database.SampleData


class LoginActivity : AppCompatActivity() {

    private lateinit var submitBtn:Button
    private lateinit var progressBar: ProgressBar
    private lateinit var username:EditText
    private lateinit var password:EditText

    companion object {
        var Role: String = ""
        var Fullname:String=""
        var Username:String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        DatabaseHelper.initDatabaseInstance(this)

        submitBtn = findViewById(R.id.button)
        username = findViewById(R.id.editTextTextPersonName)
        password = findViewById(R.id.edtTextPassword)

        progressBar = findViewById(R.id.progressBar)

        onThings()

        username.setText("")
        password.setText("")

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        checkAndStoreQuestion()

        submitBtn.setOnClickListener {

            if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this,"Credentials Missing",Toast.LENGTH_SHORT).show()
            }else {
                offThings()

                if(username.text.toString().trim().equals(SampleData.SAMPLE_ADMIN)
                    && password.text.toString().equals(SampleData.SAMPLE_ADMIN_PASSWORD)){
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "admin")
                    Role = "admin"
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@LoginActivity.startActivity(myIntent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)

                }
                else if(checkCredentials(username.text.toString().trim(),password.text.toString().trim())) {
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "student")
                    Role = "student"
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@LoginActivity.startActivity(myIntent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                    Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()

                }else{
                    onThings()
                    Toast.makeText(this,"Account Not Found!",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val textView7 = findViewById<TextView>(R.id.textView7)
        textView7.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            Role = "student"
            this@LoginActivity.startActivity(myIntent)
        }

    }

    private fun checkCredentials(username:String,password:String): Boolean {
        val list =  DatabaseHelper.getAllLoginUserData()
        list.forEach { item->
            if(item.username.equals(username) && item.password.equals(password)){
                Fullname = item.fullname
                Username = item.username
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }

    fun onThings(){
        submitBtn.isEnabled = true
        submitBtn.isClickable = true
        progressBar.visibility = View.GONE
    }

    fun offThings(){
        submitBtn.isEnabled = false
        submitBtn.isClickable = false
        progressBar.visibility = View.VISIBLE
    }

    fun checkAndStoreQuestion(){
        try {
            if (!checkQuestions()) {
                storeQuestions()
                val list = SampleData.SAMPLE_QUESTION_MODELS
                list.forEach {
                    DatabaseHelper.insertQuestionsData(it)
                }
            }
        }catch (e:Exception){

        }
    }

    fun storeQuestions(){
        val sharedPreference =  getSharedPreferences("QUIZ_QUIZ_ME_QUESTION", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("Question",true)
        editor.commit()
    }

    fun checkQuestions(): Boolean{
        val sharedPreference =  getSharedPreferences("QUIZ_QUIZ_ME_QUESTION",Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("Question",false)
    }
}