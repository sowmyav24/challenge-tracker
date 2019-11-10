package scheduler.org.challengetracker.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ChallengeRepository(application: Application) {
    private var challengeDAO: ChallengeDAO

    init {
        val database: ChallengeDatabase =
            ChallengeDatabase.getInstance(
                application.applicationContext
            )
        challengeDAO = database.challengeDAO()
    }

    fun getAllChallenges(): LiveData<List<Challenge>?> {
        return challengeDAO.getAllChallenges()
    }

    fun insertChallenge(challenge: Challenge) {
        InsertAsyncTask(challengeDAO).execute(challenge)
    }

    fun updateChallenge(challenge: Challenge) {
        UpdateAsyncTask(challengeDAO).execute(challenge)
    }

    fun unSelectAllChallenges() {
        UpdateAllAsyncTask(challengeDAO).execute()
    }

    fun deleteChallenge(challenge: Challenge) {
        DeleteAsyncTask(challengeDAO).execute(challenge)
    }

    private class InsertAsyncTask(val challengeDAO: ChallengeDAO) :
        AsyncTask<Challenge, Unit, Unit>() {

        override fun doInBackground(vararg challenge: Challenge?) {
            challengeDAO.insertChallenge(challenge[0]!!)
        }
    }

    private class UpdateAllAsyncTask(val challengeDAO: ChallengeDAO) :
        AsyncTask<Challenge, Unit, Unit>() {
        override fun doInBackground(vararg p0: Challenge?) {
            challengeDAO.unSelectAllChallenges(false)
        }
    }

    private class UpdateAsyncTask(val challengeDAO: ChallengeDAO) :
        AsyncTask<Challenge, Unit, Unit>() {
        override fun doInBackground(vararg p0: Challenge?) {
            challengeDAO.updateChallenge(p0[0]!!)
        }
    }

    private class DeleteAsyncTask(val challengeDAO: ChallengeDAO) :
        AsyncTask<Challenge, Unit, Unit>() {
        override fun doInBackground(vararg p0: Challenge?) {
            challengeDAO.deleteChallenge(p0[0]!!)
        }
    }
}