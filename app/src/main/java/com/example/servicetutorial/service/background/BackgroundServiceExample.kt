package com.example.servicetutorial.service.background

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.servicetutorial.BACKGROUND_CHANNEL_ID
import com.example.servicetutorial.BACKGROUND_NOTIFICATION_CHANNEL
import com.example.servicetutorial.R

class BackgroundServiceExample : Service() {
    private val notificationBuilder = NotificationCompat.Builder(this, BACKGROUND_CHANNEL_ID)
        .setOnlyAlertOnce(true)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Sample title")
        .setContentText("0%")
        .setProgress(100, 0, false)

    private var isRunning = false
    private var workerThread: Thread? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (!isRunning) {
            isRunning = true

            when (intent?.action) {
                Action.START_MAIN.toString() -> {
                    var progress = 1
                    for (i in 1..10) {
                        progress = i * 10
                        notificationBuilder
                            .setContentText("background service progress = $progress%")
                            .setProgress(100, progress, false)

                        notificationManager.notify(
                            BACKGROUND_NOTIFICATION_CHANNEL,
                            notificationBuilder.build()
                        )
                        Thread.sleep(1000)
                    }

                    stopSelf()
                }

                Action.STOP_MAIN.toString() -> {
                    stopSelf()
                }

                Action.START_BACKGROUND.toString() -> {
                    if (workerThread == null || workerThread?.isAlive == false) {
                        var progress = 1
                        workerThread = Thread {
                            for (i in 1..10) {
                                progress = i * 10
                                notificationBuilder
                                    .setContentText("background service progress = $progress%")
                                    .setProgress(100, progress, false)

                                notificationManager.notify(
                                    BACKGROUND_NOTIFICATION_CHANNEL,
                                    notificationBuilder.build()
                                )
                                Thread.sleep(1000)
                            }
                            stopSelf()
                        }
                        workerThread?.start()
                    }
                }

                Action.STOP_BACKGROUND.toString() -> {
                    stopSelf()
                    isRunning = false
                    workerThread?.interrupt()
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        workerThread?.interrupt()
    }

    enum class Action {
        START_MAIN,
        STOP_MAIN,
        START_BACKGROUND,
        STOP_BACKGROUND,
    }
}
