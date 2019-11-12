package scheduler.org.challengetracker.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Challenge::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("challengeId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class ChallengeDetails(
    var challengeId: Long = 0,
    var notes: String = "",
    var date: Date = Date()
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
