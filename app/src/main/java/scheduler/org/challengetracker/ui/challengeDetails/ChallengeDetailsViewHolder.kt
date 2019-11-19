package scheduler.org.challengetracker.ui.challengeDetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_challenge_details.view.notes
import kotlinx.android.synthetic.main.view_challenge_details.view.notes_date
import scheduler.org.challengetracker.entity.ChallengeDetails
import java.text.SimpleDateFormat

class ChallengeDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val notes = view.notes
    private val date = view.notes_date

    fun setData(challengeDetails: ChallengeDetails) {
        val formattedDate = SimpleDateFormat("MMM dd, yyyy").format(challengeDetails.date)
        notes.text = if (challengeDetails.notes.isNotEmpty()) challengeDetails.notes else "-"
        date.text = formattedDate
    }
}
