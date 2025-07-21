package com.example.servicetutorial.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.servicetutorial.foreground.ForegroundServiceExample

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val viewModel = hiltViewModel<MainScreenViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    //todo: Add dialog to ask to enable permissions and disable STOP SERVICE btn until service is started

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            enabled = state.hasPermissions,
            onClick = {
                viewModel.checkPermissions(
                    context = context,
                    onPermissionsGranted = {
                        Intent(
                            applicationContext,
                            ForegroundServiceExample::class.java
                        ).also {
                            it.action = ForegroundServiceExample.Actions.START.toString()
                            context.startService(it)
                        }
                    },
                    onPermissionsDenied = {
                        Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) {
            Text(text = "Start Service")
        }
        Button(
            onClick = {
                Intent(
                    applicationContext,
                    ForegroundServiceExample::class.java
                ).also {
                    it.action = ForegroundServiceExample.Actions.STOP.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Stop Service")
        }
    }
}
