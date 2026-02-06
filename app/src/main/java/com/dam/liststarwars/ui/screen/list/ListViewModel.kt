package com.dam.liststarwars.ui.screen.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.dam.liststarwars.data.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dam.liststarwars.data.model.Film
import kotlinx.coroutines.Dispatchers


@HiltViewModel
class ListViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
) : ViewModel() {

    var state: ListState by mutableStateOf(ListState.Loading)
        private set

    fun delete(element: Film) {
        viewModelScope.launch(context = Dispatchers.IO) {
            filmRepository.removeFilm(element)
        }
    }

    fun getData() {
        viewModelScope.launch {
            val flow = filmRepository.getData()

            flow.collect { items ->
                state = if (items.isNotEmpty()) {
                    ListState.Success(items, "")
                } else {
                    ListState.NoData
                }
            }
        }
    }

    /**
     * Función que busca en el repositorio las films por el título
     *
     * @param busqueda - texto a buscar
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun getDataByName(busqueda: String) {
        viewModelScope.launch {
            val flow = filmRepository.getDataByTitle(busqueda)
            flow.collect { items ->
                state =  ListState.Success(items, busqueda)
            }
        }
    }

    /**
     * Función que busca en el repositorio las films por el título cada vez
     * que se llama y actualiza el estado con los nuevos datos
     *
     * @param busqueda - texto a buscar
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun onBuesquedaChanged(busqueda: String) {
        state = (state as ListState.Success).copy(busqueda = busqueda)
        getDataByName(busqueda)
    }
}