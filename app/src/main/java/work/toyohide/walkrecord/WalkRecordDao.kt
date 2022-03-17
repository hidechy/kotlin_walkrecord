package work.toyohide.walkrecord

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WalkRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(walkRecords: WalkRecords)

    @Query("select * from walk_records order by year, month, day")
    fun readAllRecords(): LiveData<List<WalkRecords>>

}
