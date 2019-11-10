package scheduler.org.challengetracker.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Challenge::class], version = 1)
abstract class ChallengeDatabase : RoomDatabase() {

    abstract fun challengeDAO(): ChallengeDAO

    companion object {
        private var instance: ChallengeDatabase? = null

        fun getInstance(context: Context): ChallengeDatabase {
            if (instance == null) {
                synchronized(ChallengeDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChallengeDatabase::class.java, "challenges_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(
                    instance
                )
                    .execute()
            }
        }

    }

    class PopulateDbAsyncTask(db: ChallengeDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val challengeDAO = db?.challengeDAO()

        override fun doInBackground(vararg p0: Unit?) {
            challengeDAO?.insertChallenge(
                Challenge(
                    "Challenge",
                    0,
                    30,
                    true
                )
            )
        }
    }

}