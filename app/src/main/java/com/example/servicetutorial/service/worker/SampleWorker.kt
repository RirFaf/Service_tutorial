package com.example.servicetutorial.service.worker

import android.app.NotificationManager
import android.app.Service.NOTIFICATION_SERVICE
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.servicetutorial.R
import com.example.servicetutorial.WORKER_CHANNEL_ID
import com.example.servicetutorial.WORKER_NOTIFICATION_CHANNEL
import com.example.servicetutorial.data.DownloadMock
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@HiltWorker
class SampleWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
    private val manager = DownloadMock()
    private val notificationBuilder = NotificationCompat.Builder(appContext, WORKER_CHANNEL_ID)
        .setOnlyAlertOnce(true)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Sample title")
        .setContentText("0%")
        .setProgress(100, 0, false)

    override suspend fun doWork(): Result {
        val notificationManager =
            appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val result = suspendCancellableCoroutine { cont ->
            cont.invokeOnCancellation {
                manager.stopFakeDownload()
                notificationManager.cancel(WORKER_NOTIFICATION_CHANNEL)
            }

            manager.addListeners(
                onProgress = {
                    notificationBuilder
                        .setContentText("worker progress = $it%")
                        .setProgress(100, it, false)

                    notificationManager.notify(
                        WORKER_NOTIFICATION_CHANNEL,
                        notificationBuilder.build()
                    )
                },
                onComplete = {
                    if (cont.isActive) cont.resume(Result.success())
                }
            )
            manager.startFakeDownload()
        }

        return result
    }
}