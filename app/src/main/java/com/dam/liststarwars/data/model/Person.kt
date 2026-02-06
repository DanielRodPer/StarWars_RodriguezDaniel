package com.dam.liststarwars.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Planet::class,
            parentColumns = ["id"],
            childColumns = ["planetId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["planetId"])]
)

@Parcelize
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val imgStarWars: Int,
    val planetId: Int
): Parcelable