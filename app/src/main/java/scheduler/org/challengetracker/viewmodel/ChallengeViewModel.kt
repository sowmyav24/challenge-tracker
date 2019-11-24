package scheduler.org.challengetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.entity.ChallengeDetails
import scheduler.org.challengetracker.repository.ChallengeDetailsRepository
import scheduler.org.challengetracker.repository.ChallengeRepository

class ChallengeViewModel(application: Application) : AndroidViewModel(application) {
    private var challengeRepository: ChallengeRepository = ChallengeRepository(application)

    private var challengeDetailsRepository: ChallengeDetailsRepository =
        ChallengeDetailsRepository(application)

    var challenges: LiveData<List<Challenge>> = challengeRepository.getAllChallenges()

    fun getOrderedChallenges(
        challengesFromDB: List<Challenge>?
    ): MutableList<Challenge> {
        val challenges = mutableListOf<Challenge>()
        val selectedChallenge = challengesFromDB?.find { it.isSelected }
        selectedChallenge?.let {
            challenges.add(0, selectedChallenge)
        }
        val otherChallenges = challengesFromDB?.filter { !it.isSelected }
        otherChallenges?.let {
            challenges.addAll(otherChallenges)
        }
        return challenges
    }

    fun challengeDetails(challengeId: Long): LiveData<List<ChallengeDetails>> =
        challengeDetailsRepository.getChallengeDetails(challengeId)

    val text = MutableLiveData<String>().apply {
        value = challenges.value?.find { it.isSelected }?.completedDays.toString()
    }

    fun updateChallenge(challenge: Challenge?) {
        challenge?.let { challengeRepository.updateChallenge(challenge) }
    }

    fun insertChallenge(challenge: Challenge) {
        challengeRepository.insertChallenge(challenge)
    }

    fun insertChallengeDetails(challengeDetails: ChallengeDetails) {
        challengeDetailsRepository.insertChallengeDetails(challengeDetails)
    }

    fun updateChallengeDetails(challengeDetails: ChallengeDetails) {
        challengeDetailsRepository.updateChallengeDetails(challengeDetails)
    }

    fun unSelectAllChallenges() {
        challengeRepository.unSelectAllChallenges()
    }

    fun deleteChallenge(challenge: Challenge) {
        challengeRepository.deleteChallenge(challenge)
    }
}
