package com.example.apparthithecturebusschedule.data

import kotlinx.coroutines.flow.Flow

interface BusScheduleRepository {
    fun getAllSchedules(): Flow<List<BusSchedule>>
    fun getScheduleByStopName(stopName: String): Flow<List<BusSchedule>>
}

class OfflineBusScheduleRepository(private val busScheduleDao: BusScheduleDao) : BusScheduleRepository {
    override fun getAllSchedules(): Flow<List<BusSchedule>> = busScheduleDao.getAll()
    override fun getScheduleByStopName(stopName: String): Flow<List<BusSchedule>> = busScheduleDao.getByStopName(stopName)
}
