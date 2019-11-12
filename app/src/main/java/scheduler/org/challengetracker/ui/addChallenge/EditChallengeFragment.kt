package scheduler.org.challengetracker.ui.addChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.completed
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.title
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.total
import kotlinx.android.synthetic.main.fragment_edit_challenge.view.update
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel
import scheduler.org.challengetracker.ui.viewChallenge.ViewChallengesFragment

class EditChallengeFragment : Fragment() {

    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengeViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit_challenge, container, false)
        val challenge = arguments?.getParcelable("challenge") as Challenge
        root.title.setText(challenge.title)
        root.completed.setText(challenge.completedDays.toString())
        root.total.setText(challenge.totalDays.toString())
        root.update.setOnClickListener {
            val isValid = validateFields(listOf(root.title, root.completed, root.total))
            if (isValid) {
                challengeViewModel.updateChallenge(buildChallengeToBeUpdated(challenge.id, root))
                replaceFragment(ViewChallengesFragment())
            }
        }
        return root
    }

    private fun buildChallengeToBeUpdated(challengeId: Long, root: View): Challenge {
        val challengeToBeUpdated = Challenge(
            root.title.text.toString(),
            root.completed.text.toString().toInt(),
            root.total.text.toString().toInt(),
            true
        )
        challengeToBeUpdated.id = challengeId
        return challengeToBeUpdated
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun validateFields(fields: List<TextInputEditText>): Boolean {
        var isValid = true
        fields.forEach {
            if(it.text.isNullOrEmpty()) {
                isValid = false
                it.error = getString(R.string.required)
            }
        }
        return isValid
    }
}
