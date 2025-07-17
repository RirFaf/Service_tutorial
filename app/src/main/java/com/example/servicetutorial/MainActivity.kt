package com.example.servicetutorial

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.servicetutorial.ui.theme.ServiceTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        enableEdgeToEdge()
        setContent {
            ServiceTutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ForegroundServiceControllerScreen(
                        onStartService = {
                            Intent(
                                applicationContext,
                                ForegroundServiceExample::class.java
                            ).also {
                                it.action = ForegroundServiceExample.Actions.START.toString()
                                startService(it)
                            }
                        },
                        onStopService = {
                            Intent(
                                applicationContext,
                                ForegroundServiceExample::class.java
                            ).also {
                                it.action = ForegroundServiceExample.Actions.STOP.toString()
                                startService(it)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun ForegroundServiceControllerScreen(onStartService: () -> Unit, onStopService: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { onStartService() }) {
            Text(text = "Start Service")
        }
        Button(onClick = { onStopService() }) {
            Text(text = "Stop Service")
        }
    }
}