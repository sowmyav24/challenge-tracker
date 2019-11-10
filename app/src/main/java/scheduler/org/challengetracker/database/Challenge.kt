package scheduler.org.challengetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Challenge(
    val title: String = "",
    var completedDays: Int = 0,
    val totalDays: Int = 0,
    val isSelected: Boolean = true
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}