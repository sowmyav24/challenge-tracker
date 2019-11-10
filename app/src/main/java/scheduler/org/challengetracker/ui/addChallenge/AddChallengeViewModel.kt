package scheduler.org.challengetracker.ui.addChallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.database.ChallengeRepository

class AddChallengeViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ChallengeRepository =
        ChallengeRepository(application)

    fun createChallenge(challenge: Challenge) {
        repository.insertChallenge(challenge)
    }

    fun unSelectAllChallenges() {
        repository.unSelectAllChallenges()
    }
}