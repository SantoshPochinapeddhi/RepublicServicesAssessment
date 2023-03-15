package com.santosh.republicservices.model.repository

import com.santosh.republicservices.model.DriversAndRoutesRepository
import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.model.data.NetworkResult
import com.santosh.republicservices.model.data.Route
import com.santosh.republicservices.model.datasource.LocalDataSource
import com.santosh.republicservices.model.datasource.RemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriversAndRoutesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): DriversAndRoutesRepository {

    override suspend fun getDrivers() = flow {
        emit(NetworkResult.Loading(true))
        val response = remoteDataSource.getDriversAndRoutes()
        response.drivers.forEach {
            insertDriver(it)
        }
        response.routes.forEach {
            insertRoute(it)
        }
        emit(NetworkResult.Success(response.drivers))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }

    override suspend fun getRoutes() = flow {
        emit(localDataSource.getRoutes())
    }

    private suspend fun insertDriver(driver: Driver) {
        localDataSource.insertDriver(driver)
    }

    private suspend fun insertRoute(route: Route) {
        localDataSource.insertRoute(route)
    }
}