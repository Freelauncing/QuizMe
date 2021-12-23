package com.quiz.quizme.student

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.quiz.quizme.R
import com.quiz.quizme.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private lateinit var binding : FragmentTitleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title,container,false)

        setHasOptionsMenu(true)
        // 1 way
//        binding.playButton.setOnClickListener { view:View ->
//            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)
//        }
        // 2 way
//        binding.playButton.setOnClickListener { view:View ->
//            // kotlin extension function to reduce code structure
//            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//        }
        // 3 way
        binding.playButton.setOnClickListener {
            // this is used when we donot have to pass safe args
//           ( Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment) )
            // otherwise this below
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }
        return binding.root
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