package com.example.servicetutorial

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundServiceExample : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null // need to be not null when multiple apps bind to service or when work manager is used
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "channelId")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Sample title")
            .setContentText("Some content should be displayed here")
            .build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}