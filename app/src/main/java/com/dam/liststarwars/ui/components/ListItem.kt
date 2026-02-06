package com.dam.liststarwars.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.dam.liststarwars.R
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.ui.common.LocalDimensions
import java.time.format.DateTimeFormatter


/**
 * Función composable que muestra un resmumen de un objeto película en una card
 * para el lisatdo
 *
 * Novedades:
 * Menos información, ajustes de los tamaños y espaciados con compisition local
 * nuevo botón de eliminar para poder tambien eliminar además de con pulsación larga
 *
 * @param modifier
 * @param element - pelicula a mostrar
 * @param onDeleteItem - Evento que se ejecuta al eliminar
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 2.0
 */
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    element: Film,
    onDeleteItem: () -> Unit
) {
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
                .padding(dimensiones.large),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(Modifier.width(dimensiones.large))

            Icon(
                painter = painterResource(id = R.drawable.film),
                contentDescription = stringResource(id = R.string.film_desc),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensiones.huge)
            )

            Spacer(Modifier.width(dimensiones.large * 2))

            Column(Modifier.weight(1f)) {
                Text(
                    text = element.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(R.string.title_field_id) + ": ${element.episode_id}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.title_field_director) + ": ${element.director}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = element.release_date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.width(dimensiones.large * 2))

            IconButton(onClick = onDeleteItem) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.del_icon),
                    tint = MaterialTheme.colorScheme.error // Rojo para resaltar
                )

            }
        }
    }
}