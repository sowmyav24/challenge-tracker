package scheduler.org.challengetracker.ui.challengeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_challenge_details.view.challenge_details
import kotlinx.android.synthetic.main.fragment_challenge_details.view.no_notes
import kotlinx.android.synthetic.main.fragment_challenge_details.view.progress
import kotlinx.android.synthetic.main.fragment_challenge_details.view.title
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel

class ChallengeDetailsFragment : Fragment() {

    private lateinit var challengesViewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengesViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_challenge_details, container, false)
        val challenge = arguments?.getParcelable("challenge") as Challenge
        root.title.text = challenge.title
        val progressText =
            challenge.completedDays.toString() + " / " + challenge.totalDays.toString()
        root.progress.text = progressText
        val recyclerView = root.challenge_details
        challengesViewModel.challengeDetails(challenge.id).observe(this, Observer {
            toggleVisibility(root.no_notes, recyclerView, it.isEmpty())
            recyclerView.adapter =
                ChallengeDetailsAdapter(
                    activity,
                    it ?: mutableListOf()
                )
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun toggleVisibility(
        noNotes: View,
        recyclerView: RecyclerView,
        isEmpty: Boolean
    ) {
        noNotes.visibility = if(isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if(isEmpty) View.GONE else View.VISIBLE
    }
}
