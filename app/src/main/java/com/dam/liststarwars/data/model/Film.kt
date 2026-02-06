package com.dam.liststarwars.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Parcelize
data class Film(
    @PrimaryKey (autoGenerate = true)
    val episode_id: Int = 0,
    @NonNull
    val title: String = "",
    val director: String,
    val producer: String,
    val release_date: LocalDate,
    val url: String,
    val opening_crawl: String,
    val starships: String,
    val vehicles: String,
    val created: LocalDateTime,
    val edited: LocalDateTime,
    val has_vader: Boolean
) : Parcelable
