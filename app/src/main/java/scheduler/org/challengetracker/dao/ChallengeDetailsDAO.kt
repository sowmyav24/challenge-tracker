package scheduler.org.challengetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import scheduler.org.challengetracker.entity.ChallengeDetails

@Dao
interface ChallengeDetailsDAO {

    @Query("SELECT * FROM ChallengeDetails where challengeId = :challengeId")
    fun getChallengeDetails(challengeId: Long): LiveData<List<ChallengeDetails>>

    @Insert(onConflict = REPLACE)
    fun insertChallengeDetails(challengeDetails: ChallengeDetails): Long

}
