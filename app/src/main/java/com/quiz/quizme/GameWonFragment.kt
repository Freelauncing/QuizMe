package com.quiz.quizme

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.quiz.quizme.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false)

        var args = GameWonFragmentArgs.fromBundle(requireArguments())

        Toast.makeText(context,"NumCorrect = ${args.numCorrect} , Numquestions = ${args.numQuestions}",
            Toast.LENGTH_SHORT).show()

        binding.nextMatchButton.setOnClickListener{
//            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())

        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu,menu)
        if(null == getShareIntent().resolveActivity(requireActivity()!!.packageManager)){
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    private fun getShareIntent() : Intent {
        var args = GameWonFragmentArgs.fromBundle(requireArguments())
        //
        // Both Ways work
        //
        // 1
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent
//            .setType("text/plain")
//            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text,args.numCorrect,args.numQuestions))
//        return shareIntent
        //
        //2
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text,args.numCorrect,args.numQuestions))
            .setType("text/plain")
            .intent

    }

    private fun shareSuccess(){
//        if(null != getShareIntent().resolveActivity(requireActivity()!!.packageManager)){
//            startActivity(getShareIntent())
//        }
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}