package com.dam.liststarwars.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Función composable para formularios que muestra una chackbox con un texto
 *
 * @param modifier
 * @param label - Película a editar y que cargará sus datos en el estado
 * @param checked - Indica si está o no marcada la casilla
 * @param onCheckedChange
 * @param enabled - Controla si está activada o no
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 1.0
 */
@Composable
fun LabeledCheckBox(
    modifier: Modifier = Modifier,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onCheckedChange(!checked) }, //Así no hay que clicar en la casilla, vale en toda la row
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}