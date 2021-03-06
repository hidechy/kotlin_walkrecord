package work.toyohide.walkrecord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import androidx.lifecycle.asLiveData

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val walkRecordRepository: WalkRecordRepository

    val readAllRecords: LiveData<List<WalkRecords>>

    init {
        val WalkRecordDao = Database.getDatabase(application).WalkRecordDao()
        walkRecordRepository = WalkRecordRepository(WalkRecordDao)

        readAllRecords = walkRecordRepository.readAllRecords

    }

    fun addRecord(walkRecords: WalkRecords) {
        viewModelScope.launch(Dispatchers.IO) {
            walkRecordRepository.addRecord(walkRecords)
        }
    }

    fun readMonthRecords(year:String, month:String): LiveData<List<WalkRecords>> {
        return walkRecordRepository.readMonthRecords(year, month).asLiveData()
    }

    fun updateData(walkRecords: WalkRecords){
        viewModelScope.launch(Dispatchers.IO){
            walkRecordRepository.updateData(walkRecords)
        }
    }

    fun deleteData(walkRecords: WalkRecords){
        viewModelScope.launch(Dispatchers.IO){
            walkRecordRepository.deleteData(walkRecords)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            walkRecordRepository.deleteAll()
        }
    }

}
