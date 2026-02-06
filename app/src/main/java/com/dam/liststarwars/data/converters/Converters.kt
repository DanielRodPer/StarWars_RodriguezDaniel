package com.dam.liststarwars.data.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Converters {
    private val lDformatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val lDTformatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDate(fecha: LocalDate?): String? {
        return fecha?.format(lDformatter)
    }

    @TypeConverter
    fun toLocalDate(fechaString: String?): LocalDate? {
        return fechaString?.let { LocalDate.parse(fechaString, lDformatter) }
    }

    @TypeConverter
    fun fromLocalDateTime(fechaHora: LocalDateTime?): String? {
        return fechaHora?.format(lDTformatter)
    }

    @TypeConverter
    fun toLocalDateTime(fechaHoraString: String?): LocalDateTime? {
        return fechaHoraString?.let { LocalDateTime.parse(it, lDTformatter) }
    }
}