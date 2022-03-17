package work.toyohide.walkrecord

import androidx.lifecycle.LiveData

import kotlinx.coroutines.flow.Flow

class WalkRecordRepository(private val walkRecordDao: WalkRecordDao) {
    fun addRecord(walkRecords: WalkRecords) {
        walkRecordDao.insert(walkRecords)
    }

    val readAllRecords: LiveData<List<WalkRecords>> = walkRecordDao.readAllRecords()

    fun readMonthRecords(year:String, month:String): Flow<List<WalkRecords>> {
        return walkRecordDao.readMonthRecords(year, month)
    }

}
