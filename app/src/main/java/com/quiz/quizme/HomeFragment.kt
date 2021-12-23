package com.quiz.quizme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("Kaloo check",LoginActivity.Role)
        if(LoginActivity.Role.equals("admin")) {
            Log.v("Kaloo if",LoginActivity.Role)
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_home, container, false)
        }else {
            Log.v("Kaloo else",LoginActivity.Role)
            return inflater.inflate(R.layout.fragment_title, container, false)
        }
    }

}