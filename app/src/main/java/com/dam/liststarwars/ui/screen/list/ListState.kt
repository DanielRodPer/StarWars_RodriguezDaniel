package com.dam.liststarwars.ui.screen.list

import com.dam.liststarwars.data.model.Film

sealed class ListState {
    data object NoData: ListState()

    data object Loading: ListState()
    data class Success(
        val dataSet: List<Film>,
        val busqueda: String
    ): ListState()
}