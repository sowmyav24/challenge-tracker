package scheduler.org.challengetracker.ui.viewChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_view_challenge.view.challenge_list
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.database.Challenge

class ViewChallengesFragment : Fragment(), ViewChallengeNotifier {

    private lateinit var viewChallengesViewModel: ViewChallengesViewModel

    override fun deleteChallenge(challenge: Challenge) {
        viewChallengesViewModel.deleteChallenge(challenge)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewChallengesViewModel =
            ViewModelProviders.of(this).get(ViewChallengesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_view_challenge, container, false)
        val recyclerView = root.challenge_list
        viewChallengesViewModel.challenges?.observe(this, Observer {
            recyclerView.adapter =
                ViewChallengeAdapter(activity, viewChallengesViewModel.challenges?.value as MutableList<Challenge>?
                    ?: mutableListOf(), this)
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }
}

interface ViewChallengeNotifier {
    fun deleteChallenge(challenge: Challenge)
}