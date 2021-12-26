package com.quiz.quizme.student

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.PieData
import com.quiz.quizme.R
import com.quiz.quizme.databinding.FragmentGameFinishBinding


class GameFinishFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentGameFinishBinding

    private val args: GameFinishFragmentArgs by navArgs()

    private val viewModel by viewModels<GameFinishViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_game_finish, container, false)

        viewDataBinding = FragmentGameFinishBinding.bind(root).apply {
            this.viewmodel = viewmodel
        }

        setHasOptionsMenu(true)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.numCorrect,args.numQuestions)

        viewDataBinding.nextMatchButton.setOnClickListener{
            it.findNavController().navigate(GameFinishFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        viewModel.showGraphData.observe(this, {
            showGraphDetails(it)
        })
        viewModel.score.observe(this, {
            viewDataBinding.correct.text = it
        })
        viewModel.incorrectScore.observe(this, {
            viewDataBinding.wrong.text = it
        })
        viewModel.totalScore.observe(this, {
            viewDataBinding.total.text = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu,menu)
        if(null == getShareIntent().resolveActivity(requireActivity()!!.packageManager)){
            menu.findItem(R.id.share)?.setVisible(false)
        }
    }

    private fun getShareIntent() : Intent {
        var args = GameFinishFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text,args.numCorrect,args.numQuestions))
            .setType("text/plain")
            .intent

    }

    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showGraphDetails(pieData: PieData) {

        var pieChart = viewDataBinding.pieChart

        pieChart.data = pieData

        val legend = pieChart.legend
        legend.textSize = 13f
        legend.setDrawInside(false)
        legend.textColor = resources.getColor(R.color.colorPrimaryDark)
        legend.isWordWrapEnabled = true

        pieChart.animateXY(1000,1000);
        pieChart.invalidate();
    }

}