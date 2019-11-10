package scheduler.org.challengetracker.ui.viewChallenge

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_challenge.view.progress
import kotlinx.android.synthetic.main.view_challenge.view.title
import scheduler.org.challengetracker.database.Challenge

class ViewChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title = view.title
    private val progress = view.progress

    fun setData(challenge: Challenge) {
        title.text = challenge.title
        val progressText = challenge.completedDays.toString() + " / " + challenge.totalDays.toString()
        progress.text = progressText
    }
}