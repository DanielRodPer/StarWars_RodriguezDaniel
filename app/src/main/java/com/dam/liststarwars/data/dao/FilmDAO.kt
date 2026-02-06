package com.dam.liststarwars.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.data.model.FilmPlanetEntity
import com.dam.liststarwars.data.model.FilmWithPlanet
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDAO {
    @Insert
    fun insert(film: Film)

    @Delete
    fun delete(film: Film) //Solo tiene en cuenta el id, si no lo encuentra no hace nada, no devuelve error

    @Update
    fun update(film: Film)

    @Query (value = "SELECT * FROM film")
    fun getAll(): Flow<List<Film>> //Se devuelve un flujo porque es reactivo

    @Query (value = "SELECT EXISTS (SELECT * FROM film WHERE film.episode_id = :id)")
    //Id tiene que ser un parametro simple,  no podemos poner en un select un parámetro complejo,
    // como la propiedad de un objeto
    fun exists(id: Int): Boolean

    @Query (value = "SELECT EXISTS (SELECT * FROM film WHERE film.title = :name)")
    fun duplicatedName(name: String): Boolean

    //He encontrado que LIKE sirve para introducir patrones de búsqueda con caracteres especiales
    //Los porcentajes hacesn que la consulta contenga lo introducido, para que no tenga que poner el titulo perfecto
    //Si solo los ponemos al principio, el titulo debe empezar por lo que pasemos por parámetro
    //Si lo ponemos al final debe de terminar por eso
    //los || concatenan, es como poner los + en la formación de Strings en Java
    //Entonces la sentencia encuentra todas las peliculas que contengan lo que llega por el parámetro title en su títutlo
    @Query (value = "SELECT * FROM film WHERE film.title LIKE '%' || :title || '%'")
    fun getByTitle(title: String): Flow<List<Film>> //Se devuelve un flujo porque es reactivo

    //1. Busca la palícula
    //2. Busca los planetas que aparecen en la película
    //3. Devuelve un objeto de la clase FilmWithPlanet
    @Transaction
    @Query( "SELECT * FROM film WHERE film.episode_id = :id")
    fun getFilmWithPlanet(id: Int): FilmWithPlanet

    @Insert
    fun insertJoinFilmPlanet(join: FilmPlanetEntity)
}