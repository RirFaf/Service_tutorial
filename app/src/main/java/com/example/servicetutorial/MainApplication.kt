package com.example.servicetutorial

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() {
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build()
        }

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

            val workerChannel = NotificationChannel(
                WORKER_CHANNEL_ID,
                "Worker Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannel(foregroundChannel)
            notificationManager.createNotificationChannel(backgroundChannel)
            notificationManager.createNotificationChannel(workerChannel)
        }
    }
}