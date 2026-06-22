package com.example.apparthithecturebusschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BusSchedule::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busScheduleDao(): BusScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bus_schedule_database"
                )
                .addCallback(AppDatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.busScheduleDao())
                    }
                }
            }

            suspend fun populateDatabase(busScheduleDao: BusScheduleDao) {
                val schedules = listOf(
                    BusSchedule(stopName = "Terminal Pulo Gebang", arrivalTime = 28800), // 08:00
                    BusSchedule(stopName = "Terminal Pulo Gebang", arrivalTime = 36000), // 10:00
                    BusSchedule(stopName = "Terminal Purabaya", arrivalTime = 32400), // 09:00
                    BusSchedule(stopName = "Terminal Purabaya", arrivalTime = 39600), // 11:00
                    BusSchedule(stopName = "Terminal Tirtonadi", arrivalTime = 34200), // 09:30
                    BusSchedule(stopName = "Terminal Tirtonadi", arrivalTime = 41400), // 11:30
                    BusSchedule(stopName = "Terminal Arjosari", arrivalTime = 25200), // 07:00
                    BusSchedule(stopName = "Terminal Arjosari", arrivalTime = 43200), // 12:00
                    BusSchedule(stopName = "Terminal Leuwi Panjang", arrivalTime = 30600), // 08:30
                    BusSchedule(stopName = "Terminal Leuwi Panjang", arrivalTime = 45000), // 12:30
                    BusSchedule(stopName = "Terminal Giwangan", arrivalTime = 27000), // 07:30
                    BusSchedule(stopName = "Terminal Giwangan", arrivalTime = 46800)  // 13:00
                )
                schedules.forEach { busScheduleDao.insert(it) }
            }
        }
    }
}
