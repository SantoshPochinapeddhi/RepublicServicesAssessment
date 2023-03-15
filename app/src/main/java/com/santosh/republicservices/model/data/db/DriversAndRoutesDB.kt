package com.santosh.republicservices.model.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santosh.republicservices.model.data.Driver
import com.santosh.republicservices.model.data.Route
import com.santosh.republicservices.model.data.db.dao.DriversDao
import com.santosh.republicservices.model.data.db.dao.RoutesDao

@Database(version = 1, entities = [Driver::class, Route::class])
abstract class DriversAndRoutesDB: RoomDatabase() {

    abstract fun driversDao(): DriversDao

    abstract fun routesDao(): RoutesDao
}