package com.santosh.republicservices.ui

import androidx.lifecycle.ViewModel
import com.santosh.republicservices.model.DriversAndRoutesRepository
import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.model.data.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DriversAndRoutesViewModel @Inject constructor(
    private val driversAndRoutesRepository: DriversAndRoutesRepository
) : ViewModel() {


    private val list = ArrayList<Driver>()

    fun fetchDrivers() = flow {
        driversAndRoutesRepository.getDrivers().collect {
            if(it is NetworkResult.Success) {
                list.clear()
                list.addAll(it.data)
            }
            emit(it)
        }
    }

    fun getSortedList(): List<Driver> {
        list.sortBy { it.name.split(" ")[1] }
        return list
    }

    fun getPossibleRouteForADriver(id: String) = flow {
        driversAndRoutesRepository.getRoutes().collect { it ->
            val driverId = id.toInt()
            val first = it.firstOrNull { driverId == it.id }
            if(first != null) {
                emit(first)
                return@collect
            }
            if(driverId % 2 == 0) {
                val firstR = it.firstOrNull { "R" == it.type }
                if(firstR != null) {
                    emit(firstR)
                    return@collect
                }
            }
            if(driverId % 5 == 0) {
                val secondC = it.filter { "C" == it.type }.getOrNull(1)
                if(secondC != null) {
                    emit(secondC)
                    return@collect
                }
            }
            val lastI = it.lastOrNull { "I" == it.type }
            if(lastI != null) {
                emit(lastI)
                return@collect
            }
        }
    }
}