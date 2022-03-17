package work.toyohide.walkrecord

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface WalkRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(walkRecords: WalkRecords)

    @Query("select * from walk_records order by year, month, day")
    fun readAllRecords(): LiveData<List<WalkRecords>>

    @Query("select * from walk_records where year=:year and month=:month order by year,month,day")
    fun readMonthRecords(year:String, month:String): Flow<List<WalkRecords>>

}
