package scheduler.org.challengetracker.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ChallengeDetailsRepository(application: Application) {
    private var challengeDetailsDAO: ChallengeDetailsDAO

    init {
        val database: ChallengeDatabase =
            ChallengeDatabase.getInstance(
                application.applicationContext
            )
        challengeDetailsDAO = database.challengeDetailsDAO()
    }

    fun getChallengeDetails(challengeId: Long): LiveData<List<ChallengeDetails>> {
        return challengeDetailsDAO.getChallengeDetails(challengeId)
    }

    fun insertChallengeDetails(challengeDetails: ChallengeDetails) {
        InsertAsyncTask(challengeDetailsDAO).execute(challengeDetails)
    }
}

private class InsertAsyncTask(val challengeDetailsDAO: ChallengeDetailsDAO) :
    AsyncTask<ChallengeDetails, Unit, Unit>() {

    override fun doInBackground(vararg challengeDetails: ChallengeDetails?) {
        challengeDetailsDAO.insertChallengeDetails(challengeDetails[0]!!)
    }
}

