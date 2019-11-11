package scheduler.org.challengetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import scheduler.org.challengetracker.database.Challenge
import scheduler.org.challengetracker.database.ChallengeRepository

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ChallengeRepository =
        ChallengeRepository(application)

    var challenges: LiveData<List<Challenge>?>? = repository.getAllChallenges()

    val text = MutableLiveData<Int>().apply {
        value = challenges?.value?.find { it.isSelected }?.completedDays ?: 0
    }

    fun updateChallenge(challenge: Challenge?) {
        challenge?.let { repository.updateChallenge(challenge) }
    }

    fun createChallenge(challenge: Challenge) {
        repository.insertChallenge(challenge)
    }

    fun unSelectAllChallenges() {
        repository.unSelectAllChallenges()
    }

    fun deleteChallenge(challenge: Challenge) {
        repository.deleteChallenge(challenge)
    }
}
