package com.quiz.quizme

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.quiz.quizme.databinding.FragmentHomeStudentBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        if(LoginActivity.Role.equals("admin")) {
            Log.v("Kaloo if",LoginActivity.Role)

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_home_admin, container, false)
        }else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_student,container,false)

            binding.playButton.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
            }
            return binding.root
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Id at menu item and Fragment must be same
        // otherwise we have to implement it own ourself like switch()/when() by id
        return NavigationUI.onNavDestinationSelected(item!!,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}