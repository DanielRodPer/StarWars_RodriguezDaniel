package com.dam.liststarwars.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import com.dam.liststarwars.R
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.LocalDimensions
import com.dam.liststarwars.ui.components.AboutUsItem

/**
 * Función composable que muestra información sobre el desarrollador
 *
 * Novedades:
 * -logo de la app
 * -título de la app
 * -Card con información más detallada
 * -Botones con enlaces
 *
 * @param modifier
 * @param onConfigureTopBar - Permite configurar el contenido de la appbar según sea necesario
 * @param onEnableDrawerGestures - Permite activar o desactivar los gestos para abrir y cerrar el drawer
 * @param onGoTOBack - Evento que se ejecuta al pulsar en el icono de volver de la appbar
 *
 * @author Daniel Rodríguez Pérez
 * @version 2.0
 */
@Composable
fun AboutUsScreen(
    modifier: Modifier = Modifier,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onGoTOBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        onEnableDrawerGestures(false)
    }

    val uriHandler =
        LocalUriHandler.current //He encontrado esta función para abrir enlaces externos

    val dimensiones = LocalDimensions.current

    onConfigureTopBar(
        BaseTopAppBarState(
            "About us",
            actions = listOf(),
            iconUpAction = Icons.Default.ArrowBack,
            upAction = onGoTOBack
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensiones.large),
    ) {
        Spacer(Modifier.height(dimensiones.standard))

        Image(
            painter = painterResource(id = R.mipmap.ic_launcher),
            contentDescription = stringResource(id = R.string.abus_item_icon),
            modifier = Modifier.size(dimensiones.bigIcon)
        )

        Box(Modifier.height(dimensiones.standard))

        Text(
            text = stringResource(R.string.abus_app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.starwars)),
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = androidx.compose.ui.graphics.Color.Black,
                    offset = androidx.compose.ui.geometry.Offset(4f, 4f),
                    blurRadius = 8f
                )
            ),
            color = if (!isSystemInDarkTheme()) androidx.compose.ui.graphics.Color.Red else androidx.compose.ui.graphics.Color.Yellow,
        )

        Spacer(Modifier.height(dimensiones.extraLarge))


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensiones.large),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = dimensiones.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensiones.extraLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.abus_title),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(dimensiones.extraLarge))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensiones.large)
                ) {
                    item {
                        AboutUsItem(
                            modifier,
                            "Nombre",
                            "Daniel Rodríguez Pérez",
                            painter = painterResource(id = R.drawable.name)
                        )
                    }
                    item {
                        AboutUsItem(
                            modifier,
                            "Curso",
                            "2º DAM",
                            painter = painterResource(id = R.drawable.grade)
                        )
                    }
                    item {
                        AboutUsItem(
                            modifier,
                            "Centro",
                            "IES Portada Alta, Málaga",
                            painter = painterResource(id = R.drawable.school)
                        )
                        Spacer(Modifier.height(dimensiones.tiny))
                    }


                    item {
                        Text(
                            text = stringResource(R.string.abus_links_title),
                            style = TextStyle(
                                fontSize = 12.sp,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Button(
                            onClick = { uriHandler.openUri("https://github.com/DanielRodPer") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensiones.huge)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.github),
                                contentDescription = null,
                                modifier = Modifier.size(dimensiones.bigIcon)
                            )
                        }
                    }


                    item {
                        Button(
                            onClick = { uriHandler.openUri("https://www.linkedin.com/in/danielrodrp/") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensiones.huge)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.linkedin),
                                contentDescription = null,
                                modifier = Modifier.size(dimensiones.bigIcon)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
fun AboutUsScreenPreview() {
    AboutUsScreen(Modifier, {}, {}, {})
}