package com.dam.liststarwars.ui.screen.film

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.data.network.BaseResult
import com.dam.liststarwars.data.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val filmRepository: FilmRepository
) : ViewModel() {
    var state by mutableStateOf(FilmState())
        private set
    var filmOriginal: Film? = null
    var releaseDateCoverted: LocalDate? = null

    /**
     * Función que guarda el formulario teniendo en cuenta la validación y si está o no en
     * modo de edición, mostrando los correspondientes mensajes de error al usuario ya sea
     * mediante supportingText en los campos o mediante un alertDialog en caso de duplicidad
     * de nombres.
     *
     * Novedades: Actualizada a los nuevos tipos y se añade la muestra de error por alertDialog
     *
     * @param context
     * @param onSuccess - función que se ejecuta cuando se guarda correctamente la film
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 2.0
     */
    fun onSave(context: Context? = null, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val formularioValido = validateForm()

            val tituloActual = state.title.trim()
            val tituloOriginal = filmOriginal?.title?.trim()
            if (!state.isEditMode && filmRepository.checkName(tituloActual)) {
                state = state.copy(isNameDuplicated = true)
            } else if (state.isEditMode && tituloActual != tituloOriginal && filmRepository.checkName(
                    tituloActual
                )
            ) {
                state = state.copy(isNameDuplicated = true)
            } else if (formularioValido && releaseDateCoverted != null) {

                val fechaCreacion =
                    if (state.isEditMode) state.created else LocalDateTime.now().toString()
                val fechaEdicion = LocalDateTime.now().toString()

                val film =
                    createFilmFromState(releaseDateCoverted!!, fechaCreacion, fechaEdicion)

                if (state.isEditMode) {
                    val conseguido = filmRepository.updateFilm(film)
                    if (conseguido is BaseResult.Success<Film>) {
                        withContext(Dispatchers.Main) { onSuccess() }
                    }
                } else {
                    val conseguido = filmRepository.saveFilm(film)
                    if (conseguido is BaseResult.Success) {
                        withContext(Dispatchers.Main) { onSuccess() }
                    } else {
                        if (context != null)
                            withContext(Dispatchers.Main) { onEditError(context) }
                    }
                }
            }
        }
    }

    fun onEditError(context: Context) {
        Toast.makeText(context, "Ya existe esta película", Toast.LENGTH_SHORT).show()
    }

    fun onTitleChange(newVal: String) {
        state = state.copy(title = newVal)
    }

    fun onEpisodeIdChange(newVal: Int) {
        state = state.copy(episodeId = newVal)
    }

    fun onOpeningCrawlChange(newVal: String) {
        state = state.copy(openingCrawl = newVal)
    }

    fun onDirectorChange(newVal: String) {
        state = state.copy(director = newVal)
    }

    fun onProducerChange(newVal: String) {
        state = state.copy(producer = newVal)
    }

    fun onReleaseDateChange(newVal: String) {
        state = state.copy(releaseDate = newVal)
    }

    fun onStarshipsChange(newVal: String) {
        state = state.copy(starships = newVal)
    }

    fun onVehiclesChange(newVal: String) {
        state = state.copy(vehicles = newVal)
    }

    fun onCreatedChange(newVal: String) {
        state = state.copy(created = newVal)
    }

    fun onEditedChange(newVal: String) {
        state = state.copy(edited = newVal)
    }

    fun onUrlChange(newVal: String) {
        state = state.copy(url = newVal)
    }

    fun onAlertDialogDimiss() {
        state = state.copy(isNameDuplicated = false)
    }

    fun onHasVaderChange(newVal: Boolean) {
        state = state.copy(hasVader = newVal)
    }

    val formatos: List<DateTimeFormatter> =
        listOf(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
        )

    /**
     * Función que intenta convertir la fecha del usuario usando los formatos establecidos
     *
     * @return - Localdate si se puede convertir o null si la fecha no es válida
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun validarYConvertir(): LocalDate? {
        for (e in formatos) {
            try {
                return LocalDate.parse(state.releaseDate, e)
            } catch (e: Exception) {
            }
        }
        return null
    }

    /**
     * Función que se encrga de validar los dato introducidos en los campos del formulario
     *
     * @return - True si todos los campos obligatorios tienen nombre, las fechas se pueden convertir a LocalDate
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun validateForm(): Boolean {
        releaseDateCoverted = validarYConvertir()

        val titleErr = state.title.isBlank()
        val episodeErr = state.episodeId == 0
        val directorErr = state.director.isBlank()
        val producerErr = state.producer.isBlank()
        val urlErr = state.url.isBlank()

        val releaseDateErrText = if (state.releaseDate.isBlank()) {
            "Este campo es obligatorio"
        } else if (releaseDateCoverted == null) {
            "Fecha no válida"
        } else {
            ""
        }

        val releaseDateErr = releaseDateErrText.isNotBlank()

        state = state.copy(
            titleError = if (titleErr) "Este campo es obligatorio" else "",
            titleIsError = titleErr,

            episodeIdError = if (episodeErr) "Este campo es obligatorio" else "",
            episodeIdIsError = episodeErr,

            directorError = if (directorErr) "Este campo es obligatorio" else "",
            directorIsError = directorErr,

            producerError = if (producerErr) "Este campo es obligatorio" else "",
            producerIsError = producerErr,

            releaseDateError = releaseDateErrText,
            releaseDateIsError = releaseDateErr,

            urlError = if (urlErr) "Este campo es obligatorio" else "",
            urlIsError = urlErr,
        )

        return !titleErr && !episodeErr && !directorErr && !producerErr &&
                !releaseDateErr && !urlErr
    }

    /**
     * Función que se encrga de crear un objeto film desde los datos almacenados en el estado
     * y las fechas pasadas por parámetro.
     *
     * (Lo he implementado así porque si leia las fechas del estado directamente, al convertirlas
     * me daba condiciones de carrera en las corrutinas, sobre todo con que introduce el usuario
     * ya que al convertirla podede ser null y aun que lo controlase antes de llamar a save,
     * al llamarlo desde una corrutina guardaba null en la bd y se cerraba)
     *
     * @return - Film
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.0
     */
    fun createFilmFromState(
        fechaRelease: LocalDate,
        fechaCreated: String,
        fechaEdited: String
    ): Film {
        return Film(
            title = state.title,
            episode_id = state.episodeId,
            director = state.director,
            producer = state.producer,
            release_date = fechaRelease,
            url = state.url,
            opening_crawl = state.openingCrawl,
            starships = state.starships,
            vehicles = state.vehicles,
            created = LocalDateTime.parse(fechaCreated),
            edited = LocalDateTime.parse(fechaEdited),
            has_vader = state.hasVader
        )
    }

    /**
     * Función que se encarga de cargar los datos en el estado en función de si hay
     * o no película (edición o add)
     *
     * --Modificada para incluir nuevos tipos de datos
     *
     * @param film - Película a cargar o null para datos vacíos
     *
     * @author: Daniel Rodíguez Pérez
     * @version: 1.1
     */
    fun loadData(film: Film?)  //Función que se llama al entrar en el la pantalla de edit/add con LaunchedEffect
    {
        if (film != null) {
            filmOriginal = film//Comprobamos si nos pasan Datos y actualizamos el estado con ellos
            state = state.copy(
                url = film.url,
                urlError = "",
                urlIsError = false,
                title = film.title,
                titleError = "",
                titleIsError = false,
                episodeId = film.episode_id,
                episodeIdError = "",
                episodeIdIsError = false,
                openingCrawl = film.opening_crawl,
                director = film.director,
                directorError = "",
                directorIsError = false,
                producer = film.producer,
                producerError = "",
                producerIsError = false,
                releaseDate = film.release_date.toString(),
                releaseDateError = "",
                releaseDateIsError = false,
                starships = film.starships,
                vehicles = film.vehicles,
                hasVader = film.has_vader,
                created = film.created.toString(),
                edited = film.edited.toString(),
                isEditMode = true
            )
        } else {
            state = state.copy(
                url = "",
                urlError = "",
                urlIsError = false,
                title = "",
                titleError = "",
                titleIsError = false,
                episodeId = 0,
                episodeIdError = "",
                episodeIdIsError = false,
                openingCrawl = "",
                director = "",
                directorError = "",
                directorIsError = false,
                producer = "",
                producerError = "",
                producerIsError = false,
                releaseDate = "",
                releaseDateError = "",
                releaseDateIsError = false,
                starships = "",
                vehicles = "",
                hasVader = false,
                created = LocalDateTime.now().toString(),
                edited = LocalDateTime.now().toString(),
                isEditMode = false
            )
        }
    }
}