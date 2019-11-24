package scheduler.org.challengetracker.ui.challengeDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.ChallengeDetails

class ChallengeDetailsAdapter(
    private val context: Context?,
    private val challengeDetails: List<ChallengeDetails>,
    private val challengeDetailsNotifier: ChallengeDetailsNotifier
) : RecyclerView.Adapter<ChallengeDetailsViewHolder>(), EditChallengeListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeDetailsViewHolder {
        return ChallengeDetailsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_challenge_details,
                parent,
                false
            ),
            this
        )
    }

    override fun onBindViewHolder(holder: ChallengeDetailsViewHolder, position: Int) {
        holder.setData(challengeDetails[position])
    }

    override fun getItemCount(): Int {
        return challengeDetails.size
    }

    override fun updateChallengeDetails(position: Int) {
        challengeDetailsNotifier.updateChallengeDetails(challengeDetails[position])
    }
}

interface EditChallengeListener {
    fun updateChallengeDetails(position: Int)
}
