package com.example.apparthithecturebusschedule.data

import android.content.Context

interface AppContainer {
    val busScheduleRepository: BusScheduleRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val busScheduleRepository: BusScheduleRepository by lazy {
        OfflineBusScheduleRepository(AppDatabase.getDatabase(context).busScheduleDao())
    }
}
