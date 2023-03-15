package com.santosh.republicservices.model.datasource

import com.santosh.republicservices.model.data.Response
import com.santosh.republicservices.model.data.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDriversAndRoutes(): Response {
        return apiService.getDriversAndRoutes()
    }
}