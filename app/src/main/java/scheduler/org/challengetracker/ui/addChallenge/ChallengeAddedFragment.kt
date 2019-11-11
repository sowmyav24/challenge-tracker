package scheduler.org.challengetracker.ui.addChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_challenge_added.view.challenge_title
import kotlinx.android.synthetic.main.fragment_challenge_added.view.add_challenge
import kotlinx.android.synthetic.main.fragment_challenge_added.view.home
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.ui.challenge.ChallengeFragment
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel

class ChallengeAddedFragment : Fragment() {

    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengeViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_challenge_added, container, false)
        root.challenge_title.text =
            arguments?.getString("Title")?.plus(" " + getString(R.string.added))
                ?: getString(R.string.challenge_added)
        root.add_challenge.setOnClickListener {
            replaceFragment(AddChallengeFragment())
        }
        root.home.setOnClickListener {
            replaceFragment(ChallengeFragment())
        }
        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}