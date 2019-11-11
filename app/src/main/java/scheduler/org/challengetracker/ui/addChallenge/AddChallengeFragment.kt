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
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel


class AddChallengeFragment : Fragment() {

    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        challengeViewModel =
            ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_challenge, container, false)
        root.add_challenge.setOnClickListener {
            challengeViewModel.unSelectAllChallenges()
            challengeViewModel.createChallenge(
                Challenge(
                    root.title.text.toString(),
                    0,
                    root.days.text.toString().toInt(),
                    true
                )
            )
            replaceFragment(ChallengeAddedFragment(), root)
        }
        return root
    }

    private fun replaceFragment(fragment: Fragment, root: View) {
        fragmentManager?.let {
            val bundle = Bundle()
            bundle.putString("Title", root.title.text.toString())
            val transaction = it.beginTransaction()
            fragment.arguments = bundle
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}