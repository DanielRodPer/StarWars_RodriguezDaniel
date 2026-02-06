package com.dam.liststarwars.ui.screen.list

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dam.liststarwars.R
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.ui.common.Action
import com.dam.liststarwars.ui.common.BaseTopAppBarState
import com.dam.liststarwars.ui.common.FloatingActionButtonState
import com.dam.liststarwars.ui.common.LocalDimensions
import com.dam.liststarwars.ui.components.AlertDialogOkCancel
import com.dam.liststarwars.ui.components.DataField
import com.dam.liststarwars.ui.components.ListItem
import com.dam.liststarwars.ui.screen.LoadingScreen
import com.dam.liststarwars.ui.screen.NoDataScreen
import java.time.LocalDate
import java.time.LocalDateTime

data class ListEvents(
    val onAdd: () -> Unit,
    val onEdit: (Film) -> Unit,
    val onAboutUs: () -> Unit,
    val onDelete: (Film) -> Unit,
    val onListClick: () -> Unit,
    val onBuesquedaChanged: (String) -> Unit
)

/**
 * Función encargada de mandar la orden al viewmodel de cargar los datos
 * y en función del estado que este modifica según el resultado de la carga de datos
 * dirige a una screen u otra o muestra el contenido:
 *
 * - Cargando -> LoadingScreen
 * - Repositorio sin datos -> NoDataScreen
 * - Datos cargados correctamente -> Muestra el contenido llamando a ListContent
 *
 * @param modifier
 * @param onAdd - Evento que se lanzará al pulsar el fab o el acceso de la topbar
 * @param onEdit - Evento que se lanzará al pulsar sobre un item en el listado
 * @param onAboutUs - Evento que se lanzará al pulsar sobre AboutUs
 * @param onEdit - Evento que se lanzará al pulsar sobre un item en el listado
 * @param onConfigureTopBar - Permite personalizar la appbar según se necesite
 * @param onOpenDrawer - Evento que ocurre al seleccionar un elemento dedicado a abrir el drawer
 * @param onConfigureFab - Permite personalizar el fab según se necesite
 * @param onEnableDrawerGestures - Permite activar o desactivar los gestos para abrir y cerrar el drawer
 * @param onShowSnackbar - Permite mostrar una sncakbar con un texto
 *
 * @author Daniel Rodríguez Pérez
 * @version 2.0
 */
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
    onEdit: (Any) -> Unit,
    onAboutUs: () -> Unit,
    onListClick: () -> Unit,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit,
    onConfigureFab: (FloatingActionButtonState) -> Unit,
    onEnableDrawerGestures: (Boolean) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val viewModel: ListViewModel = hiltViewModel()
    val state = viewModel.state
    val dimensiones = LocalDimensions.current

    val currentState = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    when (currentState) {
        ListState.Loading -> {
            LoadingScreen(onConfigureTopBar, onEnableDrawerGestures, onConfigureFab)
            LaunchedEffect(Unit) {
                BaseTopAppBarState(
                    "Cargando...",
                    upAction = onOpenDrawer,
                )
            }
        }

        ListState.NoData -> {
            NoDataScreen(onConfigureTopBar, onEnableDrawerGestures, onConfigureFab, onOpenDrawer, onAdd)
        }

        is ListState.Success ->{
            ListContent(
                modifier.padding(dimensiones.large),
                list = currentState.dataSet,
                events = ListEvents(
                    onAdd = onAdd,
                    onEdit = onEdit,
                    onDelete = viewModel::delete,
                    onAboutUs = onAboutUs,
                    onListClick = onListClick,
                    onBuesquedaChanged = viewModel::onBuesquedaChanged
                ),
                onConfigureTopBar,
                onOpenDrawer,
                state as ListState.Success,
                onShowSnackbar = onShowSnackbar
            )

            LaunchedEffect(Unit) {
                onConfigureFab(FloatingActionButtonState(visible = true, onClick = onAdd))
                onEnableDrawerGestures(true)
            }
        }


    }
}

/**
 * Funcion encargada de mostrar las películas leídas del repositorio,
 * permite ver, y acceder a la edición de cada película al hacer click sobre ellas
 * también permite eliminarlas con una pulsación larga sobre cada una mostrando
 * un dialogo de alerta para la confirmación.
 *
 * Ahora tembién permite eliminar desde un icono en la card
 *
 * Desde la topbar se puede desplegar el drawer, añadir una película
 * o desplegar un ovreflowMenu con accesos a otras Screens
 *
 * @param modifier
 * @param list - Lista de películas a mostrar
 * @param events - Lista de eventos usados en la screen
 * @param onConfigureTopBar - Permite configurar el contenido de la appbar según sea necesario
 * @param onOpenDrawer - Abre el drawer
 * @param state - Estado que maneja el listado
 * @param onShowSnackbar - Permite invocar un snakbar con un mensaje
 *
 * @author Daniel Rodríguez Pérez
 * @version 2.0
 */
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    modifier: Modifier,
    list: List<Film>,
    events: ListEvents,
    onConfigureTopBar: (BaseTopAppBarState) -> Unit,
    onOpenDrawer: () -> Unit,
    state: ListState.Success,
    onShowSnackbar: (String) -> Unit
) {

    var deleteElement by remember { mutableStateOf<Film?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val addFilmText = stringResource(R.string.addFilm)
    val moreOptText = stringResource(R.string.moreOpt)
    val titleText = stringResource(R.string.film)
    val dimensiones = LocalDimensions.current

    LaunchedEffect(Unit) {
        onConfigureTopBar(
            BaseTopAppBarState(
                title = titleText,
                upAction = onOpenDrawer,
                actions = listOf(
                    Action.ActionImageVector(
                        name = addFilmText,
                        Icons.Default.Add,
                        addFilmText,
                        events.onAdd
                    ),
                    Action.ActionMenu(
                        name = moreOptText,
                        icon = Icons.Default.MoreVert,
                        items = listOf(Action.DropdownItem("About Us", onClick = events.onAboutUs)),
                        moreOptText
                    ),
                )
            )
        )
    }

    Column {
        DataField(
            value = state.busqueda,
            onValueChange = events.onBuesquedaChanged,
            label = stringResource(R.string.search_field),
            icon = painterResource(R.drawable.search),
            modifier = Modifier
                .padding(horizontal = dimensiones.large)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensiones.header),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (!isSystemInDarkTheme()) painterResource(id = R.drawable.light_list) else painterResource(
                    id = R.drawable.dark_list
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(R.string.list_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(R.font.starwars)),
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f
                    )
                ),
                color = if (!isSystemInDarkTheme()) MaterialTheme.colorScheme.background else Color.Yellow,
                modifier = Modifier.padding(dimensiones.large),
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(dimensiones.standard))
        if (state.dataSet.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensiones.large),
                modifier = Modifier.padding(horizontal =  dimensiones.large)
            ) {
                items(list) { element ->
                    ListItem(
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                events.onEdit(element)
                            },
                            onLongClick = {
                                deleteElement = element
                            }
                        ),
                        element = element,
                        onDeleteItem = { deleteElement = element }
                    )
                }
            }

            if (deleteElement != null) {
                AlertDialogOkCancel(
                    title = "Eliminar",
                    text = "¿Quieres eliminar ${deleteElement?.title}?",
                    cancelText = "Cancelar",
                    onConfirm = {
                        events.onDelete(deleteElement!!)
                        onShowSnackbar("Se ha eliminado la película ${deleteElement?.title}")
                        deleteElement = null
                    },
                    onDismiss = { deleteElement = null }
                )
            }
        } else {
            NoResults(state)
        }
    }
}

@Composable
fun NoResults(state: ListState.Success) {
    val dimensiones = LocalDimensions.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search_icon),
            Modifier.size(dimensiones.header),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(dimensiones.extraLarge))
        Text(
            text = "No hay resultados para la búsqueda:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = state.busqueda,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
@Preview(
    name = "PreviewPersonalizadoManualmente",
    showBackground = true,
    fontScale = 1.5f,
    showSystemUi = true,
    uiMode = Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK
)
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
fun PreviewListScreen() {
    val events = ListEvents(
        onAdd = {},
        onEdit = {},
        onAboutUs = {},
        onDelete = {},
        onListClick = {},
        onBuesquedaChanged = {}
    )

    val peliculas =
        listOf(
            Film(
                title = "The Phantom Menace",
                episode_id = 1,
                director = "George Lucas",
                producer = "Rick McCallum",
                release_date = LocalDate.of(1999, 5, 19),
                url = "https://swapi.dev/api/films/4/",
                opening_crawl = "Turmoil has engulfed the Galactic Republic...",
                starships = "Naboo fighter, Scimitar",
                vehicles = "Vulture Droid, MTT",
                created = LocalDateTime.parse("2014-12-10T14:23:31"),
                edited = LocalDateTime.parse("2014-12-20T19:49:45"),
                has_vader = false // Solo aparece como el niño Anakin
            ),
            Film(
                title = "Attack of the Clones",
                episode_id = 2,
                director = "George Lucas",
                producer = "Rick McCallum",
                release_date = LocalDate.of(2002, 5, 16),
                url = "https://swapi.dev/api/films/5/",
                opening_crawl = "There is unrest in the Galactic Senate...",
                starships = "Jedi fighter, Slave I",
                vehicles = "Speeder, AT-TE",
                created = LocalDateTime.parse("2014-12-20T10:57:57"),
                edited = LocalDateTime.parse("2014-12-20T20:17:30"),
                has_vader = false // Sigue siendo Anakin joven
            ),
            Film(
                title = "Revenge of the Sith",
                episode_id = 3,
                director = "George Lucas",
                producer = "Rick McCallum",
                release_date = LocalDate.of(2005, 5, 19),
                url = "https://swapi.dev/api/films/6/",
                opening_crawl = "War! The Republic is crumbling...",
                starships = "Jedi Interceptor, ARC-170",
                vehicles = "Wheel bike, V-wing",
                created = LocalDateTime.parse("2014-12-20T18:49:38"),
                edited = LocalDateTime.parse("2014-12-20T20:47:52"),
                has_vader = true // Aquí nace Darth Vader al final
            ),
            Film(
                title = "A New Hope",
                episode_id = 4,
                director = "George Lucas",
                producer = "Gary Kurtz",
                release_date = LocalDate.of(1977, 5, 25),
                url = "https://swapi.dev/api/films/1/",
                opening_crawl = "It is a period of civil war...",
                starships = "X-wing, Millennium Falcon",
                vehicles = "Sandcrawler, Landspeeder",
                created = LocalDateTime.parse("2014-12-10T14:23:31"),
                edited = LocalDateTime.parse("2014-12-20T19:49:45"),
                has_vader = true
            )
        )



    MaterialTheme {
        ListContent(
            modifier = Modifier,
            list = peliculas,
            events = events,
            {},
            {},
            state = ListState.Success(peliculas, ""),
            {}
        )
    }
}