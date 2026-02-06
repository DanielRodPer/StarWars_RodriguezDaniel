package com.dam.liststarwars.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dam.liststarwars.data.model.Planet
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {

    @Insert
    fun insert (planet: Planet)

    @Delete
    fun delete (planet: Planet)

    @Update
    fun update (planet: Planet)

    @Query("SELECT * FROM planet")
    fun getAll(): Flow<List<Planet>>

    @Query ("SELECT EXISTS (SELECT * FROM planet WHERE planet.id = :id)")
    fun exists(id: Int): Boolean
}