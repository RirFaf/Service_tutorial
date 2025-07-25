package com.example.servicetutorial.screen.worker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import com.example.servicetutorial.service.worker.SampleWorker
import com.example.servicetutorial.service.worker.WorkerManagerWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkerUiState(
    val currentRequest: WorkRequest? = null,
    val enqueued: Boolean = false,
)

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workerWrapper: WorkerManagerWrapper
) : ViewModel() {
    private val _uiState = MutableStateFlow(WorkerUiState())
    val uiState = _uiState.asStateFlow()

    fun createNewRequest() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val sampleRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SampleWorker>()
                .setConstraints(constraints)
                .build()

        workerWrapper.enqueue(sampleRequest)

        CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO).launch {
            workerWrapper.observeWorkInfoFlow(
                id = sampleRequest.id,
                onEnqueued = {
                    _uiState.update {
                        it.copy(
                            enqueued = true
                        )
                    }
                },
                onRunning = {
                    _uiState.update {
                        it.copy(
                            enqueued = true
                        )
                    }
                },
                onSucceeded = {
                    _uiState.update {
                        it.copy(
                            enqueued = false,
                            currentRequest = null,
                        )
                    }
                },
                onFailed = {
                    _uiState.update {
                        it.copy(
                            enqueued = false,
                            currentRequest = null,
                        )
                    }
                },
                onCancelled = {
                    _uiState.update {
                        it.copy(
                            enqueued = false,
                            currentRequest = null,
                        )
                    }
                },
            )
        }

        _uiState.update {
            it.copy(
                currentRequest = sampleRequest
            )
        }
    }

    fun cancelCurrentRequest() {
        _uiState.value.currentRequest?.let {
            workerWrapper.cancelWorkById(it.id)
        }
    }
}