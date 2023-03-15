package com.santosh.republicservices.model.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.santosh.republicservices.model.data.Route

@Dao
interface RoutesDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: Route)

    @Query("select * from routes")
    suspend fun getRoutes(): List<Route>
}