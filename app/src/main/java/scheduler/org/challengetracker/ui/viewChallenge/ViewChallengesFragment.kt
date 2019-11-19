package scheduler.org.challengetracker.ui.viewChallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_view_challenge.view.challenge_list
import kotlinx.android.synthetic.main.fragment_view_challenge.view.no_challenges
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.ui.addChallenge.EditChallengeFragment
import scheduler.org.challengetracker.ui.challengeDetails.ChallengeDetailsFragment
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel

class ViewChallengesFragment : Fragment(), ViewChallengeNotifier {

    private lateinit var challengesViewModel: ChallengeViewModel
    private lateinit var adapter: ViewChallengeAdapter

    override fun deleteChallenge(challenge: Challenge, position: Int) {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.delete)
            .setMessage(R.string.confirmation_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                challengesViewModel.deleteChallenge(challenge)
                adapter.deleteSuccess(position)
            }.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun editChallenge(challenge: Challenge) {
        val bundle = Bundle()
        bundle.putParcelable("challenge", challenge)
        replaceFragment(EditChallengeFragment(), bundle)
    }

    override fun updateChallenge(challenge: Challenge) {
        challengesViewModel.updateChallenge(challenge)
    }

    override fun onItemSelected(challenge: Challenge) {
        val bundle = Bundle()
        bundle.putParcelable("challenge", challenge)
        replaceFragment(ChallengeDetailsFragment(), bundle)
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
        challengesViewModel.challenges.observe(this, Observer {
            val orderedChallenges = challengesViewModel.getOrderedChallenges(it)
            toggleVisibility(root.no_challenges, root.challenge_list, orderedChallenges.isEmpty())
            adapter = ViewChallengeAdapter(
                activity,
                orderedChallenges as MutableList<Challenge>?
                    ?: mutableListOf(),
                this
            )
            recyclerView.adapter = adapter
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragmentManager?.let {
            fragment.arguments = bundle
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun toggleVisibility(
        noNotes: View,
        recyclerView: RecyclerView,
        isEmpty: Boolean
    ) {
        noNotes.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}

interface ViewChallengeNotifier {
    fun deleteChallenge(challenge: Challenge, position: Int)

    fun editChallenge(challenge: Challenge)

    fun updateChallenge(challenge: Challenge)

    fun onItemSelected(challenge: Challenge)
}
