package com.dam.liststarwars.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Clase que representa el estado del fab.
 * @property visible         muestra u oculta el fab.
 * @property onClick        acción que ocurrirá al pulsarlo
 * @property icon        Icono del botón
 */
data class FloatingActionButtonState(
    val icon: ImageVector = Icons.Default.Add,
    val visible: Boolean = false,
    val onClick: () -> Unit = {}
)