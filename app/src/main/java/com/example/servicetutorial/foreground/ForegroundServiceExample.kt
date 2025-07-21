package com.example.servicetutorial.foreground

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.servicetutorial.R

class ForegroundServiceExample : Service() {
    private val manager = ForegroundServiceManager()

    override fun onBind(intent: Intent?): IBinder? {
        return null // need to be not null when multiple apps bind to service or when work manager is used
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        manager.addListeners(
            onProgress = {
                val notification = NotificationCompat.Builder(this, "channelId")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Sample title")
                    .setContentText(it.toString())
                    .build()
                startForeground(1, notification)
            },
            onComplete = {
                stopSelf()
            }
        )
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        manager.startFakeDownload()
    }

    enum class Actions {
        START, STOP
    }
}