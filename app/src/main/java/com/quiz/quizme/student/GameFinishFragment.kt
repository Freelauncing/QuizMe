package com.quiz.quizme.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.quiz.quizme.LoginActivity
import com.quiz.quizme.R
import com.quiz.quizme.data.model.StudentTestModel
import com.quiz.quizme.databinding.FragmentGameFinishBinding
import java.text.SimpleDateFormat
import java.util.*


class GameFinishFragment : Fragment() {

    val currentDate = SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(Date())

    private val args: GameFinishFragmentArgs by navArgs()

    private lateinit var myView: View

    private lateinit var controller: GameFinishController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myView = inflater.inflate(R.layout.fragment_game_finish, container, false)

        setHasOptionsMenu(true)

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myView.findViewById<Button>(R.id.nextMatchButton).setOnClickListener {
            findNavController().navigate(GameFinishFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        Log.v("CKK",args.numCorrect.toString())
        Log.v("CKK",args.numQuestions.toString())
        var studentTestModel = StudentTestModel(
            LoginActivity.Username,
            LoginActivity.Fullname,
            args.numCorrect.toString(),
            args.numQuestions.toString(),
            args.numCorrect.toString()+" G ",
            currentDate)

        controller = GameFinishController(studentTestModel,this)

        controller.showSaveAndReport()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu,menu)
        if(null == getShareIntent().resolveActivity(requireActivity()!!.packageManager)){
            menu.findItem(R.id.share)?.setVisible(false)
        }
    }

    private fun getShareIntent() : Intent {
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

    fun showGraphAndReport(score: String, incorrect: String, total: String,pieData: PieData) {
        myView.findViewById<TextView>(R.id.correct).setText(score)
        myView.findViewById<TextView>(R.id.wrong).setText(incorrect)
        myView.findViewById<TextView>(R.id.total).setText(total)

        showGraphDetails(pieData)
    }


    fun showGraphDetails(pieData: PieData) {

        var pieChart = myView.findViewById<PieChart>(R.id.pieChart)

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