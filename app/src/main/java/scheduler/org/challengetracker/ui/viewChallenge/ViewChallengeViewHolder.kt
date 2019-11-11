package scheduler.org.challengetracker.ui.viewChallenge

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_challenge.view.delete_icon
import kotlinx.android.synthetic.main.view_challenge.view.edit_icon
import kotlinx.android.synthetic.main.view_challenge.view.progress
import kotlinx.android.synthetic.main.view_challenge.view.title
import scheduler.org.challengetracker.database.Challenge

class ViewChallengeViewHolder(view: View, listener: ViewChallengeListener) : RecyclerView.ViewHolder(view) {
    private val title = view.title
    private val progress = view.progress
    private val delete = view.delete_icon.setOnClickListener {
        listener.deleteChallenge(adapterPosition)
    }
    private val edit  = view.edit_icon.setOnClickListener {
        listener.editChallenge(adapterPosition)
    }
    fun setData(challenge: Challenge) {
        title.text = challenge.id.toString()
        title.text = challenge.title
        val progressText = challenge.completedDays.toString() + " / " + challenge.totalDays.toString()
        progress.text = progressText
    }
}
