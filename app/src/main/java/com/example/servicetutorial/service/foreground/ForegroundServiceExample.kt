package com.example.servicetutorial.service.foreground

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.servicetutorial.FOREGROUND_CHANNEL_ID
import com.example.servicetutorial.FOREGROUND_NOTIFICATION_CHANNEL
import com.example.servicetutorial.R
import com.example.servicetutorial.data.DownloadMock
import javax.inject.Inject

class ForegroundServiceExample @Inject constructor() : Service() {
    private val manager = DownloadMock()
    private val notificationBuilder = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
        .setOnlyAlertOnce(true)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Sample title")
        .setContentText("0%")
        .setProgress(100, 0, false)

    override fun onBind(intent: Intent?): IBinder? {
        return null // need to be not null when multiple apps bind to service or when work manager is used
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        manager.addListeners(
            onProgress = { progress ->
                notificationBuilder
                    .setContentText("background service progress = $progress%")
                    .setProgress(100, progress, false)

                notificationManager.notify(
                    FOREGROUND_NOTIFICATION_CHANNEL,
                    notificationBuilder.build()
                )
            },
            onComplete = {
                stopSelf()
            }
        )

        startForeground(FOREGROUND_NOTIFICATION_CHANNEL, notificationBuilder.build())

        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> {
                manager.stopFakeDownload()
                stopSelf()
            }

            Actions.PAUSE.toString() -> manager.stopFakeDownload()
        }

        return START_STICKY
    }

    private fun start() {
        manager.startFakeDownload()
    }

    enum class Actions {
        START, PAUSE, STOP
    }
}