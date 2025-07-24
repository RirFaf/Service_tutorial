package com.example.servicetutorial.foreground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForegroundServiceManager @Inject constructor() {
    private var progress = 0
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var currentJob: Job? = null

    var progressListener: (Int) -> Unit = {}
    var stopListener: () -> Unit = {}

    fun startFakeDownload() {
        currentJob?.cancel()
        currentJob = scope.launch {
            while (progress < 100) {
                delay(250)
                progress++
                progressListener(progress)
            }
            stopListener()
        }
    }

    fun stopFakeDownload() {
        currentJob?.cancel()
    }

    fun addListeners(
        onProgress: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        progressListener = onProgress
        stopListener = onComplete
    }
}