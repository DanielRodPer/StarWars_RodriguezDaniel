package com.dam.liststarwars.ui.common

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * clase sellada que presenta los actions de la topappbar
 * @property name nombre de la action
 * @property contentDescription descripcion de la action
 * @property onClick evento de la action
 * @property isVisible si la action es visible o no
 */
sealed class Action(
    open val name:String,
    open val contentDescription:String,
    open val onClick: ()->Unit,
    open val isVisible:Boolean=true){

    /**
     * data class que construye el action con un painter
     * @property icono en formato painter
     */
    data class ActionPainter(
        override val name:String,
        val icon: Painter?,
        override val contentDescription:String,
        override val onClick: ()->Unit,
        override val isVisible:Boolean=true
    ): Action(name, contentDescription, onClick ,isVisible)
    /**
     * data class que construye el action con un imageVector
     * @property icon icono en formato imageVector
     */
    data class ActionImageVector(
        override val name:String,
        val icon: ImageVector?,
        override val contentDescription:String,
        override val onClick: ()->Unit,
        override val isVisible:Boolean=true
    ): Action(name, contentDescription, onClick ,isVisible)

    data class DropdownItem(
        val name: String,
        val onClick: () -> Unit
    )

    data class ActionMenu(
        override val name: String,
        val icon: ImageVector,
        val items: List<DropdownItem>, // Lista de opciones
        override val contentDescription: String,
        override val isVisible: Boolean = true
    ) : Action(name, contentDescription, {}, isVisible)
}