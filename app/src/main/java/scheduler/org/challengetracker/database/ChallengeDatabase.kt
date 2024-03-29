package scheduler.org.challengetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import scheduler.org.challengetracker.dao.ChallengeDAO
import scheduler.org.challengetracker.dao.ChallengeDetailsDAO
import scheduler.org.challengetracker.entity.Challenge
import scheduler.org.challengetracker.entity.ChallengeDetails

@Database(entities = [Challenge::class, ChallengeDetails::class], version = 1)
@TypeConverters(Converters::class)
abstract class ChallengeDatabase : RoomDatabase() {

    abstract fun challengeDAO(): ChallengeDAO

    abstract fun challengeDetailsDAO(): ChallengeDetailsDAO

    companion object {
        private var instance: ChallengeDatabase? = null

        fun getInstance(context: Context): ChallengeDatabase {
            if (instance == null) {
                synchronized(ChallengeDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChallengeDatabase::class.java, "challenges_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}
