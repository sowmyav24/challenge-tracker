package scheduler.org.challengetracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy.REPLACE
import scheduler.org.challengetracker.entity.Challenge

@Dao
interface ChallengeDAO {

    @Query("SELECT * FROM Challenge")
    fun getAllChallenges(): LiveData<List<Challenge>>

    @Insert(onConflict = REPLACE)
    fun insertChallenge(challenge: Challenge): Long

    @Update
    fun updateChallenge(challenge: Challenge)

    @Query("UPDATE Challenge SET isSelected = :isSelected")
    fun unSelectAllChallenges(isSelected: Boolean)

    @Delete
    fun deleteChallenge(challenge: Challenge)
}
