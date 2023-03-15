package com.santosh.republicservices.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

data class Response(
    val drivers: List<Driver>,
    val routes: List<Route>
)

@Entity(tableName = "drivers")
data class Driver(
    @PrimaryKey
    val id: String,
    val name: String
)

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey
    val id: Int,
    val type: String,
    val name: String
)