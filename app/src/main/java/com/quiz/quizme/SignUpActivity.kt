package com.quiz.quizme

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.LoginUserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var btnSignUp:Button
    private lateinit var progressBar2: ProgressBar
    private lateinit var username:EditText
    private lateinit var fullname:EditText
    private lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        DatabaseHelper.initDatabaseInstance(this)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        fullname = findViewById(R.id.tbFullName)
        username = findViewById(R.id.tbUsername)
        password = findViewById(R.id.tbPassword)
        btnSignUp = findViewById(R.id.btnSignUp)

        progressBar2 = findViewById(R.id.progressBar2)

        fullname.setText("")
        username.setText("")
        password.setText("")

        onThings()

        btnSignUp.setOnClickListener {
            if(fullname.text.isNullOrEmpty() || username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this,"Some Fields are Missing", Toast.LENGTH_SHORT).show()
            }else {
                offThings()
                if(createAccount(fullname.text.toString().trim(), username.text.toString().trim(), password.text.toString().trim())) {
                    val myIntent = Intent(this@SignUpActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "student")
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@SignUpActivity.startActivity(myIntent)
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                    Toast.makeText(this,"Welcome Account Created!",Toast.LENGTH_SHORT).show()
                }else{
                    onThings()
                    Toast.makeText(this,"Username Already registered!",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val textView7 = findViewById<TextView>(R.id.textView7)
        textView7.setOnClickListener {
            val myIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            this@SignUpActivity.startActivity(myIntent)
        }
    }

    private fun createAccount(fullname: String, username: String, password: String): Boolean {
        val list =  DatabaseHelper.getAllLoginUserData()
        list.forEach { item->
            if(item.username.equals(username)) {
                return false
            }
        }
        val loginUser = LoginUserModel(username,fullname,password)
        if(null != (DatabaseHelper.insertLoginUserData(loginUser))){
            LoginActivity.Fullname = fullname
            LoginActivity.Username = username
            return true
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }

    fun onThings(){
        btnSignUp.isEnabled = true
        btnSignUp.isClickable = true
        progressBar2.visibility = View.GONE
    }

    fun offThings(){
        btnSignUp.isEnabled = false
        btnSignUp.isClickable = false
        progressBar2.visibility = View.VISIBLE
    }

}