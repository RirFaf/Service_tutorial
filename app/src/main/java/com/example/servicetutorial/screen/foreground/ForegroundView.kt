package com.example.servicetutorial.screen.foreground

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.servicetutorial.foreground.ForegroundServiceExample
import com.example.servicetutorial.screen.MainScreenViewModel

@Composable
fun ForegroundServiceView(
    context: Context,
    viewModel: MainScreenViewModel
) {
    val applicationContext = context.applicationContext
    val foregroundIntent = Intent(applicationContext, ForegroundServiceExample::class.java)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.checkPermissions(
                    context = context,
                    onPermissionsGranted = {
                        foregroundIntent.also {
                            it.action = ForegroundServiceExample.Actions.START.toString()
                            context.startService(it)
                        }
                    },
                    onPermissionsDenied = {
                        Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && context is Activity) {
                            ActivityCompat.requestPermissions(
                                context,
                                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                                0
                            )
                        }
                    }
                )
            }
        ) {
            Text(text = "Start Foreground Service")
        }
        Button(
            onClick = {
                foregroundIntent.also {
                    it.action = ForegroundServiceExample.Actions.PAUSE.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Pause Foreground Service")
        }
        Button(
            onClick = {
                foregroundIntent.also {
                    it.action = ForegroundServiceExample.Actions.STOP.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Stop Foreground Service")
        }
    }
}