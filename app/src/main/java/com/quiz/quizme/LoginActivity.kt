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
import android.widget.Toast
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.LoginUser
import com.quiz.quizme.data.model.SampleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class LoginActivity : AppCompatActivity() {

    companion object {
        var Role: String = ""
        var Fullname:String=""
        var Username:String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        val username = findViewById<EditText>(R.id.editTextTextPersonName)
        val password = findViewById<EditText>(R.id.edtTextPassword)

        val signIn = findViewById<Button>(R.id.button)
        signIn.setOnClickListener {
           Log.v("Kaloo",username.text.toString())

            if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this,"Credentials Missing",Toast.LENGTH_SHORT).show()
            }else {
                if(username.text.toString().equals(SampleData.SAMPLE_ADMIN)
                    && password.text.toString().equals(SampleData.SAMPLE_ADMIN_PASSWORD)){
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "admin")
                    Role = "admin"
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@LoginActivity.startActivity(myIntent)
                    findViewById<EditText>(R.id.editTextTextPersonName).setText("")
                    findViewById<EditText>(R.id.edtTextPassword).setText("")
                }
                else if(checkCredentials(username.text.toString(),password.text.toString())) {
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    myIntent.putExtra("Role", "student")
                    Role = "student"
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@LoginActivity.startActivity(myIntent)
                    Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()
                    findViewById<EditText>(R.id.editTextTextPersonName).setText("")
                    findViewById<EditText>(R.id.edtTextPassword).setText("")
                }else{
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
        val list =  QuizContract.DatabaseHelper.getAllLoginUserData()
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
        QuizContract.DatabaseHelper.closeDatabase()
    }

}