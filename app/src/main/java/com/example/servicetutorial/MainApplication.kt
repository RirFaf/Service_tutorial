package com.example.servicetutorial

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val foregroundChannel = NotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            val backgroundChannel = NotificationChannel(
                BACKGROUND_CHANNEL_ID,
                "Background Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannel(foregroundChannel)
            notificationManager.createNotificationChannel(backgroundChannel)
        }
    }
}