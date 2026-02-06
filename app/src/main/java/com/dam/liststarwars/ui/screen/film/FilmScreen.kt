package com.dam.liststarwars.ui.screen.film

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dam.liststarwars.R
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.permissions.AppPermissions
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState
import com.dam.liststarwars.ui.common.LocalDimensions
import com.dam.liststarwars.ui.components.AlertDialogOkCancel
import com.dam.liststarwars.ui.components.DataField
import com.dam.liststarwars.ui.components.LabeledCheckBox
import com.dam.liststarwars.ui.helper.NotificationHandler
import com.dam.liststarwars.ui.helper.rememberPermissionsLauncher
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FilmEvents(
    val onTitleChanged: (String) -> Unit,
    val onEpisodeIdChanged: (Int) -> Unit,
    val onDirectorChanged: (String) -> Unit,
    val onProducerChanged: (String) -> Unit,
    val onReleaseDateChanged: (String) -> Unit,
    val onUrlChanged: (String) -> Unit,
    val onOpeningCrawlChanged: (String) -> Unit,
    val onStarshipsChanged: (String) -> Unit,
    val onVehiclesChanged: (String) -> Unit,
    val onHasVaderChange: (Boolean) -> Unit,
    val onCreatedChanged: (String) -> Unit,
    val onEditedChanged: (String) -> Unit,
    val onSavePress: () -> Unit,
    val onGoToBack: () -> Unit,
    val onAlertDialogDimiss: () -> Unit,
)

/**
 * Función composable que muestra el contenido de la screen en formato de adición y
 * muestra una notificación por cada película añadida correctamente
 *
 * @param goToBack - Evento que se ejecutará al pulsar el icono de volver
 * @param onConfigureTopBar - Permite configurar la topbar
 * @param onEnableDrawerGestures - Permite activar y desactivar la apertura del drawer con gestos
 * @param onConfigureFab - Permite configurar el fab
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 2.0
 */
@Composable
fun FilmAddScreen(
    goToBack: () -> Unit,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
) {
    val viewModel: FilmViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        onEnableDrawerGestures(false)
        viewModel.loadData(null)
        onConfigureTopBar(
            BaseTopAppBarState(
                title = "Añadir una nueva película",
                iconUpAction = Icons.Default.ArrowBack,
                upAction = goToBack
            )
        )
    }

    val state = viewModel.state
    val context = LocalContext.current
    val notificationHandler = remember { NotificationHandler(context) }

    // Launcher genérico para permisos (aquí solo pedimos Notificaciones)
    val requestNotificationPermissionThenNotify = rememberPermissionsLauncher(
        permissions = listOf(AppPermissions.Notifications),
        onAllGranted = {
            notificationHandler.showSimpleNotification(
                contentTitle = "Película creada",
                contentText = "Se ha dado de alta ${state.title}"
            )
            goToBack()
        },
        onDenied = {
            // Opcional: si no concede, puedes avisar con snackbar
            Toast.makeText(context, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show()
            //onShowSnackbar("Permiso de notificaciones denegado")
            goToBack()
        }
    )

    val events = FilmEvents(
        onTitleChanged = viewModel::onTitleChange,
        onUrlChanged = viewModel::onUrlChange,
        onEpisodeIdChanged = viewModel::onEpisodeIdChange,
        onDirectorChanged = viewModel::onDirectorChange,
        onProducerChanged = viewModel::onProducerChange,
        onReleaseDateChanged = viewModel::onReleaseDateChange,
        onOpeningCrawlChanged = viewModel::onOpeningCrawlChange,
        onEditedChanged = viewModel::onEditedChange,
        onCreatedChanged = viewModel::onCreatedChange,
        onVehiclesChanged = viewModel::onVehiclesChange,
        onStarshipsChanged = viewModel::onStarshipsChange,
        onHasVaderChange = viewModel::onHasVaderChange,
        onAlertDialogDimiss = viewModel::onAlertDialogDimiss,
        onSavePress = {
            viewModel.onSave(context, onSuccess = {
                requestNotificationPermissionThenNotify()
                goToBack
            })
        },
        onGoToBack = goToBack,
    )

    FilmScreenContent(
        state,
        events,
        onConfigureFab,
    )
}

/**
 * Función composable que muestra el contenido de la screen en formato de edición,
 * cargando en el estado y por lo tanto en los campos del listado los datos del item seleccionado
 * Esta NO muestra notificaciones tras la edición
 *
 * @param film - Película a editar y que cargará sus datos en el estado
 * @param goToBack - Evento que se ejecutará al pulsar el icono de volver
 * @param onConfigureTopBar - Permite configurar la topbar
 * @param onEnableDrawerGestures - Permite activar y desactivar la apertura del drawer con gestos
 * @param onConfigureFab - Permite configurar el fab
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 2.0
 */
@Composable
fun FilmEditScreen(
    film: Film?,
    goToBack: () -> Unit,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
) {

    val viewModel: FilmViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        onEnableDrawerGestures(false)
        viewModel.loadData(film)
        onConfigureTopBar(
            BaseTopAppBarState(
                "Editar ${film?.title}",
                Icons.Default.ArrowBack,
                upAction = goToBack
            )
        )
    }

    val state = viewModel.state

    val events = FilmEvents(
        onTitleChanged = viewModel::onTitleChange,
        onUrlChanged = viewModel::onUrlChange,
        onEpisodeIdChanged = viewModel::onEpisodeIdChange,
        onDirectorChanged = viewModel::onDirectorChange,
        onProducerChanged = viewModel::onProducerChange,
        onReleaseDateChanged = viewModel::onReleaseDateChange,
        onOpeningCrawlChanged = viewModel::onOpeningCrawlChange,
        onEditedChanged = viewModel::onEditedChange,
        onCreatedChanged = viewModel::onCreatedChange,
        onVehiclesChanged = viewModel::onVehiclesChange,
        onStarshipsChanged = viewModel::onStarshipsChange,
        onHasVaderChange = viewModel::onHasVaderChange,
        onAlertDialogDimiss = viewModel::onAlertDialogDimiss,
        onSavePress = { viewModel.onSave(onSuccess = goToBack) },
        onGoToBack = goToBack
    )

    FilmScreenContent(
        state,
        events,
        onConfigureFab,
    )
}

/**
 * Función composable que crea una página con los siguientes componentes:
 * -Cabecera con foto y titulo dinámico
 * -LazyColumn con los campos de texto y checkboxes necesarios para el modelo de datos de films
 *
 * Permite añadir o modificar una película según el estado de la screen
 *
 * Contiene validación con comprobación de nombres duplicados, fechas incorrectas y campos obligatorios
 *
 * @param state
 * @param events
 * @param onConfigureFab - permite configurar el fab
 *
 * @author: Daniel Rodíguez Pérez
 * @version: 2.0
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScreenContent(
    state: FilmState,
    events: FilmEvents,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
) {
    val listState = rememberLazyListState()
    val dimensiones = LocalDimensions.current

    LaunchedEffect(Unit) {
        onConfigureFab(
            FloatingActionButtonState(
                visible = true,
                icon = Icons.Default.Check,
                onClick = events.onSavePress
            )
        )
    }

    if (state.isNameDuplicated) {
        AlertDialogOkCancel(
            title = "Nombre duplicado",
            text = "Ya existe una película con el título ${state.title}",
            onConfirm = events.onAlertDialogDimiss,
            onDismiss = events.onAlertDialogDimiss
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensiones.standard),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.title.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensiones.header),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = if (!isSystemInDarkTheme()) painterResource(id = R.drawable.light_background) else painterResource(
                        id = R.drawable.dark_background
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.starwars)),
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = androidx.compose.ui.graphics.Color.Black,
                            offset = androidx.compose.ui.geometry.Offset(4f, 4f),
                            blurRadius = 8f
                        )
                    ),
                    color = if (!isSystemInDarkTheme()) MaterialTheme.colorScheme.background else androidx.compose.ui.graphics.Color.Yellow,
                    modifier = Modifier.padding(dimensiones.large),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = dimensiones.extraLarge)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensiones.standard),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensiones.medium)
            ) {
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.url,
                        onValueChange = events.onUrlChanged,
                        label = stringResource(R.string.title_field_url),
                        icon = painterResource(R.drawable.url),
                        enabled = !state.isEditMode,
                        isError = state.urlIsError,
                        errorText = state.urlError
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.title,
                        onValueChange = events.onTitleChanged,
                        label = stringResource(R.string.title_field_title),
                        icon = painterResource(R.drawable.title),
                        isError = state.titleIsError,
                        errorText = state.titleError
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.episodeId.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isEmpty()) {
                                events.onEpisodeIdChanged(0)
                            } else if (newValue.all { it.isDigit() }) {
                                events.onEpisodeIdChanged(newValue.toInt())
                            }
                        },
                        label = stringResource(R.string.title_field_id),
                        icon = painterResource(R.drawable.id),
                        enabled = !state.isEditMode,
                        isError = state.episodeIdIsError,
                        errorText = state.episodeIdError
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.openingCrawl,
                        onValueChange = events.onOpeningCrawlChanged,
                        label = stringResource(R.string.title_field_openingcrawl),
                        icon = painterResource(R.drawable.crawl),
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.director,
                        onValueChange = events.onDirectorChanged,
                        label = stringResource(R.string.title_field_director),
                        icon = painterResource(R.drawable.director),
                        isError = state.directorIsError,
                        errorText = state.directorError
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.producer,
                        onValueChange = events.onProducerChanged,
                        label = stringResource(R.string.title_field_producer),
                        icon = painterResource(R.drawable.producer),
                        isError = state.producerIsError,
                        errorText = state.producerError
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.releaseDate,
                        onValueChange = events.onReleaseDateChanged,
                        label = stringResource(R.string.title_field_releasedate),
                        icon = painterResource(R.drawable.release),
                        isError = state.releaseDateIsError,
                        errorText = state.releaseDateError,
                        numeric = true
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.starships,
                        onValueChange = events.onStarshipsChanged,
                        label = stringResource(R.string.title_field_starships),
                        icon = painterResource(R.drawable.starships),
                    )
                }
                item {
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.vehicles,
                        onValueChange = events.onVehiclesChanged,
                        label = stringResource(R.string.title_field_vehicles),
                        icon = painterResource(R.drawable.vehicles),
                    )
                }
                item {
                    LabeledCheckBox(label = stringResource(R.string.has_vader), checked = state.hasVader, onCheckedChange = events.onHasVaderChange)
                }
                item {
                    val fechaCreacion = if (state.created.isNotBlank()) {
                        LocalDateTime.parse(state.created)
                    } else {
                        LocalDateTime.now()
                    }

                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                        onValueChange = events.onCreatedChanged,
                        label = stringResource(R.string.title_field_created),
                        icon = painterResource(R.drawable.created),
                        enabled = false
                    )
                }
                item {
                    val fechaEdiciom = if (state.created.isNotBlank()) {
                        LocalDateTime.parse(state.edited)
                    } else {
                        LocalDateTime.now()
                    }
                    DataField(
                        modifier = Modifier.fillMaxWidth(),
                        value = fechaEdiciom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                        onValueChange = events.onEditedChanged,
                        label = stringResource(R.string.title_field_edited),
                        icon = painterResource(R.drawable.edited),
                        enabled = false
                    )
                }
            }
        }
    }
}


@Composable
@Preview(
    name = "PreviewAniadirPersonalizadoManualmente",
    showBackground = true,
    fontScale = 2f,
    showSystemUi = false,
    widthDp = 1200,
    heightDp = 2000,
    uiMode = Configuration.KEYBOARDHIDDEN_NO
)
fun PreviewFilmAddScreen() {
    val state = FilmState(
        title = "A New Hope",
        episodeId = 4,
        director = "",
        producer = "",
        releaseDate = "",
        url = "https://swapi.dev/api/films/1/",
        isEditMode = false
    )

    val events = FilmEvents(
        onTitleChanged = {},
        onEpisodeIdChanged = {},
        onDirectorChanged = {},
        onProducerChanged = {},
        onReleaseDateChanged = {},
        onUrlChanged = {},
        onOpeningCrawlChanged = {},
        onStarshipsChanged = {},
        onVehiclesChanged = {},
        onCreatedChanged = {},
        onEditedChanged = {},
        onSavePress = {},
        onGoToBack = {},
        onAlertDialogDimiss = {},
        onHasVaderChange = {}
    )

    FilmScreenContent(
        state = state,
        events = events,
        onConfigureFab = {}
    )
}

@Composable
@Preview(
    name = "PreviewEditarPersonalizadoManualmente",
    showBackground = true,
    fontScale = 0.8f,
    showSystemUi = true,
    device = Devices.NEXUS_5
)
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
fun PreviewFilmEditScreen() {
    val state = FilmState(
        title = "A New Hope",
        episodeId = 4,
        director = "George Lucas",
        producer = "Gary Kurtz, Rick McCallum",
        releaseDate = "1977-05-25",
        url = "https://swapi.dev/api/films/1/",
        isEditMode = true
    )

    val events = FilmEvents(
        onTitleChanged = {},
        onEpisodeIdChanged = {},
        onDirectorChanged = {},
        onProducerChanged = {},
        onReleaseDateChanged = {},
        onUrlChanged = {},
        onOpeningCrawlChanged = {},
        onStarshipsChanged = {},
        onVehiclesChanged = {},
        onCreatedChanged = {},
        onEditedChanged = {},
        onSavePress = {},
        onGoToBack = {},
        onAlertDialogDimiss = {},
         onHasVaderChange = {}
    )

    FilmScreenContent(
        state = state,
        events = events,
        onConfigureFab = {}
    )
}