package com.santosh.republicservices.model.data.network

import com.santosh.republicservices.model.data.Response
import retrofit2.http.GET

interface ApiService {

    @GET("data")
    suspend fun getDriversAndRoutes(): Response
}