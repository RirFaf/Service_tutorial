package com.example.servicetutorial.service.worker

import android.content.Context
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class WorkerManagerWrapper @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val workManager = WorkManager.getInstance(appContext)

    private fun getWorkInfoByIdFlow(id: UUID): Flow<WorkInfo?> {
        return workManager.getWorkInfoByIdFlow(id)
    }

    suspend fun observeWorkInfoFlow(
        id: UUID,
        onEnqueued: () -> Unit = {},
        onRunning: () -> Unit = {},
        onSucceeded: () -> Unit = {},
        onFailed: () -> Unit = {},
        onBlocked: () -> Unit = {},
        onCancelled: () -> Unit = {},
        onNull: () -> Unit = {},
    ) {
        val workInfoFlow = getWorkInfoByIdFlow(id)

        workInfoFlow.collect { workInfo ->
            when (workInfo?.state) {
                WorkInfo.State.ENQUEUED -> onEnqueued()
                WorkInfo.State.RUNNING -> onRunning()
                WorkInfo.State.SUCCEEDED -> onSucceeded()
                WorkInfo.State.FAILED -> onFailed()
                WorkInfo.State.BLOCKED -> onBlocked()
                WorkInfo.State.CANCELLED -> onCancelled()
                null -> onNull()
            }
        }
    }

    fun cancelWorkById(id: UUID) {
        workManager.cancelWorkById(id)
    }

    fun enqueue(request: WorkRequest) {
        workManager.enqueue(request)
    }
}