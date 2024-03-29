package scheduler.org.challengetracker.ui.challengeDetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_challenge_details.view.edit_icon
import kotlinx.android.synthetic.main.view_challenge_details.view.notes
import kotlinx.android.synthetic.main.view_challenge_details.view.notes_date
import scheduler.org.challengetracker.entity.ChallengeDetails
import java.text.SimpleDateFormat
import java.util.*

class ChallengeDetailsViewHolder(
    view: View,
    listener: EditChallengeListener
) : RecyclerView.ViewHolder(view) {

    private val notes = view.notes
    private val date = view.notes_date
    private val edit = view.edit_icon.setOnClickListener {
        listener.updateChallengeDetails(adapterPosition)
    }

    fun setData(challengeDetails: ChallengeDetails) {
        val formattedDate = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.ENGLISH).format(challengeDetails.date)
        notes.text = if (challengeDetails.notes.isNotEmpty()) challengeDetails.notes else "-"
        date.text = formattedDate
    }
}
