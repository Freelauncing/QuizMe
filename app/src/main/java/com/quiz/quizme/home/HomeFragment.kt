package com.quiz.quizme

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quiz.quizme.data.database.DatabaseHelper
import com.quiz.quizme.data.model.QuestionModel
import com.quiz.quizme.data.model.ReadQuestionModel
import com.quiz.quizme.data.database.SampleData
import com.quiz.quizme.databinding.FragmentHomeStudentBinding
import com.quiz.quizme.home.HomeAdapter
import com.quiz.quizme.home.HomeController
import com.quiz.quizme.student.game.GameFragment
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), HomeAdapter.RefreshPlease {

    private lateinit var controller: HomeController

    private lateinit var myView_admin : View
    private lateinit var myView_student : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        if(LoginActivity.Role.equals("admin")) {

            myView_admin = inflater.inflate(R.layout.fragment_home_admin, container, false)

            return myView_admin
        }else {

            myView_student = inflater.inflate( R.layout.fragment_home_student,container,false)

            return myView_student
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = DatabaseHelper.getAllQuestionData()

        controller = HomeController(list,this)

        if(LoginActivity.Role.equals("admin")){

           refreshQuestions()

        }else {

            myView_student.findViewById<Button>(R.id.playButton).setOnClickListener {
                if (controller.getQuestionsFromDatabase()) {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
                } else {
                    Toast.makeText(requireContext(), "No Question Added yet !!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    fun refreshQuestions() {
        // getting the recyclerview by its id
        val recyclerview = myView_admin.findViewById<RecyclerView>(R.id.recyclerview_questions)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)


        val list = controller.list

        Log.v("CHECK",list.toString())

        Log.v("CHECK",LoginActivity.Username+" "+ LoginActivity.Fullname )

        // This will pass the ArrayList to our Adapter
        val adapter = HomeAdapter(list.reversed(),requireContext(),this.layoutInflater,this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }

    override fun onRefresh() {
        refreshQuestions()
    }


}


