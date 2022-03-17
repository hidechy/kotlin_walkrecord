package work.toyohide.walkrecord

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "walk_records")
data class WalkRecords(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val date: String,

    val year: String,
    val month: String,
    val day: String,

    val step: String,
    val distance: String
)
