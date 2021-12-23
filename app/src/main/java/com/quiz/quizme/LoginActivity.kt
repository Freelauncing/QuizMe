package com.quiz.quizme

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import com.quiz.quizme.student.StudentMainActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()!!.hide();

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val signIn = findViewById<Button>(R.id.button)
        signIn.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, StudentMainActivity::class.java)
            this@LoginActivity.startActivity(myIntent)
        }

        val textView7 = findViewById<TextView>(R.id.textView7)
        textView7.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            this@LoginActivity.startActivity(myIntent)
        }

    }
}