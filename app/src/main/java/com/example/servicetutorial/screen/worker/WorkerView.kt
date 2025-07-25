package com.example.servicetutorial.screen.worker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun WorkerView() {
    val viewModel = hiltViewModel<WorkerViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.createNewRequest()
            },
            enabled = state.currentRequest == null || !state.enqueued,
        ) {
            Text(text = "Start Simple Worker")
        }
        Button(
            onClick = {
                viewModel.cancelCurrentRequest()
            },
            enabled = state.currentRequest != null && state.enqueued,
        ) {
            Text(text = "Stop Simple Worker")
        }
    }
}
