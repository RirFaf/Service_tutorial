package com.example.servicetutorial.foreground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForegroundServiceManager {
    private val _progress = MutableStateFlow(0)
    val downloadJob = Job() + Dispatchers.IO
    val scope = CoroutineScope(downloadJob)

    var progressListener: (Int) -> Unit = {}
    var stopListener: () -> Unit = {}

    fun startFakeDownload() {
        scope.launch {
            while (_progress.value < 100) {
                delay(250)
                _progress.update { it + 1 }
                progressListener(_progress.value)
            }
            stopListener()
        }
    }

    fun stopFakeDownload() {
        downloadJob.cancel()
    }

    fun resetFakeDownload() {
        _progress.update { 0 }
    }

    fun addListeners(
        onProgress: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        progressListener = onProgress
        stopListener = onComplete
    }
}