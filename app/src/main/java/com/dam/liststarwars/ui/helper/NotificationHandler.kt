package com.dam.liststarwars.ui.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.dam.liststarwars.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {

    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    private val notificationChannelID = "planet_channel_id"

    init {
        createNotificationChannel()
    }

    //Inicializa el canal
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelID,
            "Películas",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de creación de películas"
        }

        notificationManager.createNotificationChannel(channel)
    }

    //Esto lo tenemos que implementar nosotros

    //Función que te ayuda a acceder al manager y al canal para mostrar la notificación pasándole el título y el contenido
    fun showSimpleNotification(contentTitle: String, contentText: String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID) //creo el elemeto notificacion y le pasamos el builder
            .setContentTitle(contentTitle) //Estos campos son obligatorios
            .setContentText(contentText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}