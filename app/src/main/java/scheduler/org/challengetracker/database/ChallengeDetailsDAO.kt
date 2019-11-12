package scheduler.org.challengetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ChallengeDetailsDAO {

    @Query("SELECT * FROM ChallengeDetails where challengeId = :challengeId")
    fun getChallengeDetails(challengeId: Long): LiveData<List<ChallengeDetails>>

    @Insert(onConflict = REPLACE)
    fun insertChallengeDetails(challengeDetails: ChallengeDetails): Long

}
