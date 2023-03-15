package com.santosh.republicservices.model

import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.model.data.NetworkResult
import com.santosh.republicservices.model.data.Route
import kotlinx.coroutines.flow.Flow

interface DriversAndRoutesRepository {
    suspend fun getDrivers(): Flow<NetworkResult<List<Driver>>>
    
    suspend fun getRoutes(): Flow<List<Route>>
}