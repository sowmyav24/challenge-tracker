package scheduler.org.challengetracker.ui.addChallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.database.ChallengeRepository

class EditChallengeViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ChallengeRepository =
        ChallengeRepository(application)

    fun updateChallenge(challenge: Challenge) {
        repository.updateChallenge(challenge)
    }
}