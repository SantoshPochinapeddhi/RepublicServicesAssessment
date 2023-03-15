package com.santosh.republicservices.model.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santosh.republicservices.model.data.Driver

@Dao
interface DriversDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriver(driver: Driver)

    @Query("select * from drivers")
    suspend fun getDrivers(): List<Driver>
}