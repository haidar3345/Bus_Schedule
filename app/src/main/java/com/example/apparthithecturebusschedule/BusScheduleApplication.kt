package com.example.apparthithecturebusschedule

import android.app.Application
import com.example.apparthithecturebusschedule.data.AppContainer
import com.example.apparthithecturebusschedule.data.DefaultAppContainer

class BusScheduleApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
