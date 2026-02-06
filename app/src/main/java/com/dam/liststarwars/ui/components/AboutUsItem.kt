package com.dam.liststarwars.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.dam.liststarwars.R
import com.dam.liststarwars.ui.common.LocalDimensions

/**
 * Función composable que muestra datos en el aboutUs
 *
 * @param titulo
 * @param subtitulo
 * @param painter - Icono a mostrar
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 1.0
 */
@Composable
fun AboutUsItem(modifier: Modifier, titulo: String, subtitulo: String, painter: Painter){
    val dimensiones = LocalDimensions.current

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensiones.medium),
        border = BorderStroke(
            width = dimensiones.tiny * 2,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensiones.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painter,
                contentDescription = stringResource(id = R.string.abus_item_icon),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensiones.extraBig)
            )

            Spacer(Modifier.width(dimensiones.large))

            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = subtitulo,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}