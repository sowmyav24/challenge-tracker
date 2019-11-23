package scheduler.org.challengetracker.ui.addChallenge

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_add_challenge.view.add_challenge
import kotlinx.android.synthetic.main.fragment_add_challenge.view.days
import kotlinx.android.synthetic.main.fragment_add_challenge.view.notes_needed
import kotlinx.android.synthetic.main.fragment_add_challenge.view.title
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.Challenge
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
            val methodManager: InputMethodManager =
                activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            methodManager.hideSoftInputFromWindow(activity!!.currentFocus.windowToken, 0)
            val isValid = validateField(listOf(root.title, root.days))
            if (isValid) {
                addChallenge(root.title, root.days, root)
            }
        }
        return root
    }

    private fun addChallenge(
        title: TextInputEditText,
        totalDays: TextInputEditText,
        root: View
    ) {
        challengeViewModel.unSelectAllChallenges()
        challengeViewModel.insertChallenge(
            Challenge(
                title.text.toString(),
                0,
                totalDays.text.toString().toInt(),
                true,
                root.notes_needed.isChecked
            )
        )
        replaceFragment(ChallengeAddedFragment(), root)
    }

    private fun validateField(fields: List<TextInputEditText>): Boolean {
        var isValid = true
        fields.forEach {
            if (it.text.isNullOrEmpty()) {
                isValid = false
                it.error = getString(R.string.required)
            }
        }
        return isValid
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
