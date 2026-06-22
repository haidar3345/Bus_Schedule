package com.example.apparthithecturebusschedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apparthithecturebusschedule.BusScheduleApplication
import com.example.apparthithecturebusschedule.data.BusSchedule
import com.example.apparthithecturebusschedule.data.BusScheduleRepository
import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(private val repository: BusScheduleRepository) : ViewModel() {

    fun getFullSchedule(): Flow<List<BusSchedule>> = repository.getAllSchedules()

    fun getScheduleFor(stopName: String): Flow<List<BusSchedule>> = repository.getScheduleByStopName(stopName)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BusScheduleApplication)
                BusScheduleViewModel(application.container.busScheduleRepository)
            }
        }
    }
}
