package com.dam.liststarwars.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PersonWithPlanet(
    @Embedded val person: Person,
    //La anotación RELATION sistituye al INNER JOIN
    //que tendríamos que hacer en la sentencia SELECT
    //Room hace una primera consulta e inicializa el primer objeto
    //Después hace una segunda consulta e inicializa el objeto
    @Relation(
        parentColumn = "planetId",
        entityColumn =  "id"
    )
    val planet: Planet
)