package work.toyohide.walkrecord

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WalkRecords::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun WalkRecordDao(): WalkRecordDao

    companion object {

        @Volatile
        private var INSTANCE: work.toyohide.walkrecord.Database? = null

        fun getDatabase(context: Context): work.toyohide.walkrecord.Database {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, work.toyohide.walkrecord.Database::class.java, "walk_records"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}
