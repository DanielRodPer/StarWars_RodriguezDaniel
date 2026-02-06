package com.dam.liststarwars.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FilmWithPlanet(
    @Embedded val film: Film,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "id",
        associateBy = Junction(FilmPlanetEntity::class, parentColumn = "episodeId", entityColumn = "planetId")
    )
    val planets: List<Planet>
)