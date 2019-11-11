package scheduler.org.challengetracker.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_challenge.view.count
import scheduler.org.challengetracker.R
import androidx.appcompat.app.AppCompatActivity
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel


class ChallengeFragment : Fragment() {

    private lateinit var challengeViewModel: ChallengeViewModel
    private var challenge: Challenge? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengeViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_challenge, container, false)
        val textView: TextView = root.count
        onCounterIncrement(textView)
        setCounterValue(textView)
        challengeViewModel.challenges?.observe(this, Observer {
            challenge = it?.last()
            (activity as AppCompatActivity).supportActionBar?.title = challenge?.title ?: ""
            challengeViewModel.text.value = challenge?.completedDays ?: 0
        })
        return root
    }

    private fun setCounterValue(textView: TextView) =
        challengeViewModel.text.observe(this, Observer {
            textView.text = it.toString()
        })

    private fun onCounterIncrement(textView: TextView) = textView.setOnClickListener {
        challenge?.completedDays = challenge?.completedDays?.plus(1) ?: 0
        challengeViewModel.updateChallenge(challenge)
        challengeViewModel.text.value = challenge?.completedDays
    }
}