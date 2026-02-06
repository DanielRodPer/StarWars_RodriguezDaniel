package com.dam.liststarwars.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dam.liststarwars.data.converters.Converters
import com.dam.liststarwars.data.dao.FilmDAO
import com.dam.liststarwars.data.dao.PersonDao
import com.dam.liststarwars.data.dao.PlanetDao
import com.dam.liststarwars.data.model.Film
import com.dam.liststarwars.data.model.FilmPlanetEntity
import com.dam.liststarwars.data.model.Person
import com.dam.liststarwars.data.model.Planet
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.Executors
import kotlin.jvm.java


@Database(
    version = 5,
    entities = [Film::class, Planet::class, Person::class, FilmPlanetEntity::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun getFilmDao(): FilmDAO
    abstract fun getPlanetDAO(): PlanetDao
    abstract fun getPersonDAO(): PersonDao

    companion object {
        /**
         * La variable se guarda en memoria. Cualquier cambio realizado en la variable por un hilo
         * se refleja de inmediado y es visible al resto de hilos. No hay copias antiguas o nulas.
         */
        @Volatile
        private var INSTANCE: StarWarsDatabase? = null

        fun getDatabase(context: Context): StarWarsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarWarsDatabase::class.java,
                    "starwars_database.db"
                )
                    // 2. CAMBIO IMPORTANTE: Permitir migración destructiva
                    // Si la versión del dispositivo es menor que la versión del código (2),
                    // y no hay una migración manual definida, Room borrará la base de datos
                    // y la creará de nuevo.
                    .fallbackToDestructiveMigration()
                    // Callback para pre-poblar la base de datos
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Se utiliza un executor para realizar la inserción en un hilo de fondo
                            //Las tareas se ejecutan de forma secuencial en un hilo/s
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    prepopulateDatabase(database)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun prepopulateDatabase(database: StarWarsDatabase) {
            val filmDao = database.getFilmDao()
            //val planetDao = database.getPlanetDAO()
            //val personDao = database.getPersonDAO()
            runBlocking {
                val filmsToInsert = listOf(
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
                    ),
                    Film(
                        title = "The Empire Strikes Back",
                        episode_id = 5,
                        director = "Irvin Kershner",
                        producer = "Gary Kurtz",
                        release_date = LocalDate.of(1980, 5, 21),
                        url = "https://swapi.dev/api/films/2/",
                        opening_crawl = "It is a dark time for the Rebellion...",
                        starships = "Millennium Falcon, Slave I",
                        vehicles = "Snowspeeder, AT-AT",
                        created = LocalDateTime.parse("2014-12-12T11:26:24"),
                        edited = LocalDateTime.parse("2014-12-15T13:07:53"),
                        has_vader = true
                    ),
                    Film(
                        title = "Return of the Jedi",
                        episode_id = 6,
                        director = "Richard Marquand",
                        producer = "Howard Kazanjian",
                        release_date = LocalDate.of(1983, 5, 25),
                        url = "https://swapi.dev/api/films/3/",
                        opening_crawl = "Luke Skywalker has returned to Tatooine...",
                        starships = "Death Star, Home One",
                        vehicles = "Speeder bike, AT-ST",
                        created = LocalDateTime.parse("2014-12-18T10:39:33"),
                        edited = LocalDateTime.parse("2014-12-20T09:48:37"),
                        has_vader = true
                    ),
                    Film(
                        title = "The Force Awakens",
                        episode_id = 7,
                        director = "J.J. Abrams",
                        producer = "Kathleen Kennedy",
                        release_date = LocalDate.of(2015, 12, 18),
                        url = "https://swapi.dev/api/films/7/",
                        opening_crawl = "Luke Skywalker has vanished...",
                        starships = "Finalizer, T-70 X-wing",
                        vehicles = "Rey's Speeder",
                        created = LocalDateTime.parse("2015-12-18T14:23:31"),
                        edited = LocalDateTime.parse("2015-12-20T19:49:45"),
                        has_vader = false
                    ),
                    Film(
                        title = "The Last Jedi",
                        episode_id = 8,
                        director = "Rian Johnson",
                        producer = "Kathleen Kennedy",
                        release_date = LocalDate.of(2017, 12, 15),
                        url = "https://swapi.dev/api/films/8/",
                        opening_crawl = "The FIRST ORDER reigns...",
                        starships = "Raddus, Kylo Ren's TIE Silencer",
                        vehicles = "Ski speeder, AT-M6",
                        created = LocalDateTime.parse("2017-12-15T14:23:31"),
                        edited = LocalDateTime.parse("2017-12-20T19:49:45"),
                        has_vader = false
                    ),
                    Film(
                        title = "The Rise of Skywalker",
                        episode_id = 9,
                        director = "J.J. Abrams",
                        producer = "Kathleen Kennedy",
                        release_date = LocalDate.of(2019, 12, 20),
                        url = "https://swapi.dev/api/films/9/",
                        opening_crawl = "The dead speak! The galaxy has heard a mysterious broadcast...",
                        starships = "Tantive IV, Millennium Falcon",
                        vehicles = "Treadspeeder, Skimmer",
                        created = LocalDateTime.parse("2019-12-20T14:23:31"),
                        edited = LocalDateTime.parse("2019-12-22T19:49:45"),
                        has_vader = false
                    ),
                    Film(
                        title = "Rogue One",
                        episode_id = 10,
                        director = "Gareth Edwards",
                        producer = "Kathleen Kennedy",
                        release_date = LocalDate.of(2016, 12, 16),
                        url = "https://swapi.dev/api/films/10/",
                        opening_crawl = "A world of chaos. A mission of hope...",
                        starships = "U-wing, Profundity",
                        vehicles = "TX-225 GAVw, AT-ACT",
                        created = LocalDateTime.parse("2016-12-16T14:23:31"),
                        edited = LocalDateTime.parse("2014-12-20T19:49:45"),
                        has_vader = true
                    )
                )

                filmsToInsert.forEach { film -> filmDao.insert(film) }

                /*
                planetDao.insert(
                    Planet(1, "Tatooine", 23, 3)
                )
                planetDao.insert(
                    Planet(2, "Hoth", 24, 549)
                )
                personDao.insert(
                    Person(
                        id = 0,
                        name = "Darth Vader",
                        height = "202",
                        mass = "136",
                        hairColor = "none",
                        skinColor = "white",
                        eyeColor = "yellow",
                        birthYear = "41.9BBY",
                        gender = "male",
                        imgStarWars = 0,
                        planetId = 1
                    )
                )
                personDao.insert(
                    Person(
                        id = 0,
                        name = "Luke Skywalker",
                        height = "172",
                        mass = "77",
                        hairColor = "blond",
                        skinColor = "fair",
                        eyeColor = "blue",
                        birthYear = "19BBY",
                        gender = "male",
                        imgStarWars = 0,
                        planetId = 1
                    )
                )
                filmDao.insertJoinFilmPlanet(FilmPlanetEntity(1, 1))
                filmDao.insertJoinFilmPlanet(FilmPlanetEntity(1, 2))
                val resultFilm = filmDao.getFilmWithPlanet(1)
                println("Título Película: ${resultFilm.film.title}")
                println(" - Planetas: ${resultFilm.planets}")
                val resultPersonWithPlanet = personDao.getPersonWithPlanet(1)
                println("${resultPersonWithPlanet.person.name} nació en ${resultPersonWithPlanet.planet.name}")

                  */
            }
        }
    }
}
