package com.santosh.republicservices.model.datasource

import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.model.data.Route
import com.santosh.republicservices.model.data.db.DriversAndRoutesDB
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val db: DriversAndRoutesDB
) {

    suspend fun getRoutes(): List<Route> {
        return db.routesDao().getRoutes()
    }

    suspend fun insertDriver(driver: Driver) {
        db.driversDao().insertDriver(driver)
    }

    suspend fun insertRoute(route: Route) {
        db.routesDao().insertRoute(route)
    }
}