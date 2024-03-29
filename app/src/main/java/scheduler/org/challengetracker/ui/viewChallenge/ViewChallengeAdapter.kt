package scheduler.org.challengetracker.ui.viewChallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import scheduler.org.challengetracker.R
import scheduler.org.challengetracker.entity.Challenge

class ViewChallengeAdapter(
    private val context: Context?,
    private val challenges: MutableList<Challenge>,
    private val viewChallengeNotifier: ViewChallengeNotifier
) :
    RecyclerView.Adapter<ViewChallengeViewHolder>(), ViewChallengeListener {

    override fun deleteChallenge(adapterPosition: Int) {
        viewChallengeNotifier.deleteChallenge(challenges[adapterPosition], adapterPosition)
    }

    override fun editChallenge(adapterPosition: Int) {
        viewChallengeNotifier.editChallenge(challenges[adapterPosition])
        notifyDataSetChanged()
    }

    override fun onItemSelected(adapterPosition: Int) {
        viewChallengeNotifier.onItemSelected(challenges[adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewChallengeViewHolder {
        return ViewChallengeViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_challenge,
                parent,
                false
            ), this
        )
    }

    override fun onBindViewHolder(holder: ViewChallengeViewHolder, position: Int) {
        holder.setData(challenges[position])
    }

    override fun getItemCount(): Int {
        return challenges.size
    }

    fun deleteSuccess(adapterPosition: Int) {
        val isSelectedChallenge = challenges[adapterPosition].isSelected
        challenges.removeAt(adapterPosition)
        if(isSelectedChallenge && challenges.size > 0) {
            challenges[0].isSelected = true
            viewChallengeNotifier.updateChallenge(challenges[0])
        }
        notifyDataSetChanged()
    }
}

interface ViewChallengeListener {
    fun deleteChallenge(adapterPosition: Int)

    fun editChallenge(adapterPosition: Int)

    fun onItemSelected(adapterPosition: Int)
}
