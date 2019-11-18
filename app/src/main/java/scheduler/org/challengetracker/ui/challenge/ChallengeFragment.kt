package scheduler.org.challengetracker.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_challenge.view.count
import kotlinx.android.synthetic.main.fragment_challenge.view.filled_exposed_dropdown
import scheduler.org.challengetracker.R
import androidx.appcompat.app.AppCompatActivity
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.ui.addChallenge.AddChallengeFragment
import scheduler.org.challengetracker.viewmodel.ChallengeViewModel
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import scheduler.org.challengetracker.entity.ChallengeDetails
import scheduler.org.challengetracker.entity.isCompleted


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

        challengeViewModel.challenges.observe(this, Observer {
            val title: String
            if (it.isEmpty()) {
                title = getString(R.string.start_challenging)
                challengeViewModel.text.value = getString(R.string.start_now)
            } else {
                challenge = it?.find { selected -> selected.isSelected }
                onChallengeSelected(root, it)
                if (challenge?.isCompleted() == true) {
                    completeChallenge(textView)
                } else {
                    challengeViewModel.text.value = challenge?.completedDays.toString()
                }
                title = challenge?.title ?: ""
            }
            (activity as AppCompatActivity).supportActionBar?.title = title
        })
        return root
    }

    private fun onChallengeSelected(
        root: View,
        it: List<Challenge>
    ) {
        val adapter = ArrayAdapter(
            context,
            R.layout.dropdown_menu_popup_item,
            it.map { c -> c.title }
        )
        val editTextFilledExposedDropdown = root.filled_exposed_dropdown
        editTextFilledExposedDropdown.setAdapter(adapter)
        editTextFilledExposedDropdown.setText(challenge?.title, false)
        editTextFilledExposedDropdown.setOnItemClickListener { _, _, position, _ ->
            challenge = it[position]
            challengeViewModel.unSelectAllChallenges()
            challenge?.isSelected = true
            challengeViewModel.updateChallenge(challenge)
        }
    }

    private fun completeChallenge(textView: TextView) {
        challengeViewModel.text.value = getString(R.string.congratulations)
        textView.background = resources.getDrawable(R.drawable.ic_circle_gradient)
        textView.isEnabled = false
    }

    private fun setCounterValue(textView: TextView) =
        challengeViewModel.text.observe(this, Observer {
            textView.text = it.toString()
        })

    private fun onCounterIncrement(textView: TextView) = textView.setOnClickListener {
        challenge?.let {
            if (it.hasNotes) {
                captureNotes(it.id)
            }
            it.completedDays = challenge?.completedDays?.plus(1) ?: 0
            challengeViewModel.updateChallenge(challenge)
            challengeViewModel.text.value = challenge?.completedDays.toString()
        } ?: replaceFragment(AddChallengeFragment())
    }

    private fun captureNotes(challengeId: Long): ChallengeDetails {
        val challengeDetails = ChallengeDetails()
        val alert = AlertDialog.Builder(context!!)
        alert.setMessage(R.string.notes_heading)
        val input = EditText(context!!)
        alert.setView(input)
        alert.setPositiveButton(R.string.ok) { _, _ ->
            challengeDetails.notes = input.text.toString()
            challengeViewModel.insertChallengeDetails(challengeDetails)
        }
        challengeDetails.challengeId = challengeId
        alert.show()
        return challengeDetails
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, fragment)
            ?.addToBackStack(null)?.commit()
    }
}