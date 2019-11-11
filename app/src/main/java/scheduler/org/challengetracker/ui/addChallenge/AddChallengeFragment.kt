package scheduler.org.challengetracker.ui.addChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_add_challenge.view.add_challenge
import kotlinx.android.synthetic.main.fragment_add_challenge.view.days
import kotlinx.android.synthetic.main.fragment_add_challenge.view.title
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.database.Challenge


class AddChallengeFragment : Fragment() {

    private lateinit var addChallengeViewModel: AddChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addChallengeViewModel =
            ViewModelProviders.of(this).get(AddChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_challenge, container, false)
        root.add_challenge.setOnClickListener {
            addChallengeViewModel.unSelectAllChallenges()
            addChallengeViewModel.createChallenge(
                Challenge(
                    root.title.text.toString(),
                    0,
                    root.days.text.toString().toInt(),
                    true
                )
            )
            fragmentManager?.let {
                val bundle = Bundle()
                bundle.putString("Title", root.title.text.toString())
                val transaction = it.beginTransaction()
                val challengeAddedFragment = ChallengeAddedFragment()
                challengeAddedFragment.arguments = bundle
                transaction.replace(R.id.nav_host_fragment, challengeAddedFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return root
    }
}