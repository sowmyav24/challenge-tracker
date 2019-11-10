package scheduler.org.challengetracker.ui.viewChallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.database.Challenge

class ViewChallengeAdapter(private val context: Context?, val challenges: List<Challenge>) :
    RecyclerView.Adapter<ViewChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewChallengeViewHolder {
        return ViewChallengeViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_challenge,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewChallengeViewHolder, position: Int) {
        holder.setData(challenges[position])
    }

    override fun getItemCount(): Int {
        return challenges.size
    }
}
