package com.quiz.quizme.student

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.quiz.quizme.LoginActivity
import com.quiz.quizme.R
import com.quiz.quizme.data.database.QuizContract
import com.quiz.quizme.data.model.StudentTest
import com.quiz.quizme.databinding.FragmentGameWonBinding
import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.data.PieEntry

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.components.Legend
import kotlin.collections.ArrayList


class GameWonFragment : Fragment() {

    var pieChart: PieChart? = null
    var pieEntries: ArrayList<PieEntry>? = null

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

        binding.correct.text = args.numCorrect.toString()
        binding.wrong.text = (args.numQuestions - args.numCorrect).toString()
        binding.total.text = args.numCorrect.toString() + "/" + args.numQuestions.toString()

        pieChart = binding.pieChart

        val correct = args.numCorrect
        val incorrect = args.numQuestions - args.numCorrect

        pieEntries = arrayListOf()
        pieEntries!!.add(PieEntry(correct.toFloat(),"Correct"))
        pieEntries!!.add(PieEntry(incorrect.toFloat(),"Incorrect"))

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.valueTextSize = 16f

        val pieData = PieData(pieDataSet)
        pieChart!!.data = pieData

        val legend = pieChart!!.legend
        legend.textSize = 13f
        legend.setDrawInside(false)
        legend.textColor = resources.getColor(R.color.colorPrimaryDark)
        legend.isWordWrapEnabled = true

        pieChart!!.animateXY(1000,1000);
        pieChart!!.invalidate();

        binding.nextMatchButton.setOnClickListener{
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        QuizContract.DatabaseHelper.insertStudentTestData(
            StudentTest(
            LoginActivity.Username,LoginActivity.Fullname,args.numCorrect.toString(),args.numQuestions.toString(),args.numCorrect.toString()+" G ",currentDate))
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