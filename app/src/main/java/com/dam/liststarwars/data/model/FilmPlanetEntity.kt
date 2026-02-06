package com.dam.liststarwars.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "film_planet",
    //1. Se establece la clave primaria formada por los id de las tablas vinculadas
    primaryKeys = ["episodeId", "planetId"],
    foreignKeys = [
        ForeignKey(
            entity = Film::class,
            parentColumns = ["episode_id"],
            childColumns = ["episodeId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Planet::class,
            parentColumns = ["id"],
            childColumns = ["planetId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class FilmPlanetEntity(
    @ColumnInfo (name = "episodeId")
    val episodeId: Int,
    @ColumnInfo (name = "planetId")
    val planetId: Int
)