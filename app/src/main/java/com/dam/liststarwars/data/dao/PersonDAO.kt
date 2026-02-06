package com.dam.liststarwars.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dam.liststarwars.data.model.Person
import com.dam.liststarwars.data.model.PersonWithPlanet
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert
    fun insert(person: Person)

    @Delete
    fun delete(person: Person)

    @Update
    fun update(person: Person)

    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT EXISTS (SELECT * FROM person WHERE person.name = :name)")
    fun exists(name: Int): Boolean

    //Para contar cuantos personajes hay
    @Query("SELECT COUNT(*) FROM person")
    fun count(): Int

    @Transaction
    @Query("SELECT * FROM Person WHERE id = :id")
    fun getPersonWithPlanet(id:Int): PersonWithPlanet
}