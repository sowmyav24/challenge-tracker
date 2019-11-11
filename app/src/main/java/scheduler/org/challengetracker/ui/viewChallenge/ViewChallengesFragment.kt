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
import scheduler.org.challengetracker.ui.addChallenge.EditChallengeFragment
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel

class ViewChallengesFragment : Fragment(), ViewChallengeNotifier {

    private lateinit var challengesViewModel: ChallengeViewModel

    override fun deleteChallenge(challenge: Challenge) {
        challengesViewModel.deleteChallenge(challenge)
    }

    override fun editChallenge(challenge: Challenge) {
        replaceFragment(EditChallengeFragment(), challenge)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengesViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_view_challenge, container, false)
        val recyclerView = root.challenge_list
        challengesViewModel.challenges?.observe(this, Observer {
            recyclerView.adapter =
                ViewChallengeAdapter(
                    activity, challengesViewModel.challenges?.value as MutableList<Challenge>?
                        ?: mutableListOf(), this
                )
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun replaceFragment(fragment: Fragment, challenge: Challenge) {
        fragmentManager?.let {
            val bundle = Bundle()
            bundle.putParcelable("challenge", challenge)
            fragment.arguments = bundle
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

interface ViewChallengeNotifier {
    fun deleteChallenge(challenge: Challenge)

    fun editChallenge(challenge: Challenge)
}
