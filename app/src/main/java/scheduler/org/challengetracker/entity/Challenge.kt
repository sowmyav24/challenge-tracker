package scheduler.org.challengetracker.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Challenge(
    val title: String = "",
    var completedDays: Int = 0,
    val totalDays: Int = 0,
    var isSelected: Boolean = true,
    val hasNotes: Boolean = false,
    val createdDate: Date = Date()
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}

fun Challenge.isCompleted() : Boolean {
    return this.completedDays == this.totalDays
}
