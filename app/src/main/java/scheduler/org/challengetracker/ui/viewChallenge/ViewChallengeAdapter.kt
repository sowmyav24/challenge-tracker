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
        val challengeToBeRemoved = challenges.removeAt(adapterPosition)
        viewChallengeNotifier.deleteChallenge(challengeToBeRemoved)
        notifyDataSetChanged()
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
        val isSelectedIcon =
            if (position == 0) R.drawable.ic_home_black_selected else R.drawable.ic_home_black
        holder.setData(challenges[position], isSelectedIcon)
    }

    override fun getItemCount(): Int {
        return challenges.size
    }
}

interface ViewChallengeListener {
    fun deleteChallenge(adapterPosition: Int)

    fun editChallenge(adapterPosition: Int)

    fun onItemSelected(adapterPosition: Int)
}
