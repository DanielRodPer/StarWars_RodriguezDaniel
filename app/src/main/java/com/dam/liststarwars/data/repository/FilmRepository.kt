package com.dam.liststarwars.data.repository

import com.dam.liststarwars.data.dao.FilmDAO
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.data.network.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepository @Inject constructor(
    private val filmDAO: FilmDAO
){
    fun getData() : Flow<List<Film>> = filmDAO.getAll()

    /**
     * Función que pide al Dao la consulta de búsqueda por título
     *
     * @param title - texto a buscar
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun getDataByTitle(title: String) : Flow<List<Film>> = filmDAO.getByTitle(title)

    fun saveFilm(film : Film) : BaseResult<Film>{
        if(filmDAO.exists(film.episode_id)){
            return BaseResult.Error(Exception("La película ya existe"))
        }
        filmDAO.insert(film)
        return BaseResult.Success(film)
    }

    fun checkName(title: String) : Boolean {
        return filmDAO.duplicatedName(title)
    }

    fun updateFilm(filmNueva: Film) : BaseResult<Film>{
        filmDAO.update(filmNueva)
        return BaseResult.Success(filmNueva)
    }

    fun removeFilm(film: Film){
        filmDAO.delete(film)
    }
}