package com.quiz.quizme

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.TextView


class LoginActivity : AppCompatActivity() {

    companion object {
        var Role: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val username = findViewById<EditText>(R.id.editTextTextPersonName)

        val signIn = findViewById<Button>(R.id.button)
        signIn.setOnClickListener {
           Log.v("Kaloo",username.text.toString())

            val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
            if(username.text.toString().equals("a")) {
                myIntent.putExtra("Role", "admin")
                Role = "admin"
            }else{
                myIntent.putExtra("Role", "student")
                Role = "student"
            }
            this@LoginActivity.startActivity(myIntent)
        }

        val textView7 = findViewById<TextView>(R.id.textView7)
        textView7.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            this@LoginActivity.startActivity(myIntent)
        }

    }
}