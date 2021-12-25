package com.quiz.quizme

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.LoginUser

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        QuizContract.DatabaseHelper.initDatabaseInstance(this)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val fullname = findViewById<EditText>(R.id.tbFullName)
        val username = findViewById<EditText>(R.id.tbUsername)
        val password = findViewById<EditText>(R.id.tbPassword)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            if(fullname.text.isNullOrEmpty() || username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this,"Fields Missing", Toast.LENGTH_SHORT).show()
            }else {
                if(createAccount(fullname.text.toString(),username.text.toString(),password.text.toString())) {
                    val myIntent = Intent(this@SignUpActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "student")
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@SignUpActivity.startActivity(myIntent)
                    Toast.makeText(this,"Account Created!",Toast.LENGTH_SHORT).show()
                }else{
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
        val list =  QuizContract.DatabaseHelper.getAllLoginUserData()
        list.forEach { item->
            if(item.username.equals(username)) {
                return false
            }
        }
        val loginUser = LoginUser(username,fullname,password)
        if(null != (QuizContract.DatabaseHelper.insertLoginUserData(loginUser))){
            return true
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        QuizContract.DatabaseHelper.closeDatabase()
    }

}