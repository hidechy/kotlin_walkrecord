package work.toyohide.walkrecord

import androidx.lifecycle.LiveData

class WalkRecordRepository(private val walkRecordDao: WalkRecordDao) {
    fun addRecord(walkRecords: WalkRecords) {
        walkRecordDao.insert(walkRecords)
    }

    val readAllRecords: LiveData<List<WalkRecords>> = walkRecordDao.readAllRecords()

}
