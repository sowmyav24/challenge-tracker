package scheduler.org.challengetracker.ui.addChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.completed
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.title
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.total
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.update
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.ui.viewChallenge.ViewChallengesFragment

class EditChallengeFragment : Fragment() {

    private lateinit var editChallengeViewModel: EditChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editChallengeViewModel =
            ViewModelProviders.of(this).get(EditChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit_challenge, container, false)
        val challengeId = arguments?.getLong("id")
        root.update.setOnClickListener {
            val challenge = buildChallengeToBeUpdated(challengeId, root)
            challenge?.let {
                editChallengeViewModel.updateChallenge(it)
            }
            replaceFragment(ViewChallengesFragment())
        }
        return root
    }

    private fun buildChallengeToBeUpdated(challengeId: Long?, root: View) : Challenge? {
        return challengeId?.let {
            val challengeToBeUpdated = Challenge(
                root.title.text.toString(),
                root.completed.text.toString().toInt(),
                root.total.text.toString().toInt(),
                true
            )
            challengeToBeUpdated.id = it
            challengeToBeUpdated
        }
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