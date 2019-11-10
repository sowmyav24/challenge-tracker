package scheduler.org.challengetracker.ui.viewChallenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.database.ChallengeRepository

class ViewChallengesViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ChallengeRepository =
        ChallengeRepository(application)
    var challenges: LiveData<List<Challenge>?>? = repository.getAllChallenges()

    fun deleteChallenge(challenge: Challenge) {
        repository.deleteChallenge(challenge)
    }}