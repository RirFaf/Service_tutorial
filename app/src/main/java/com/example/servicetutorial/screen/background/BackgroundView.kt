package com.example.servicetutorial.screen.background

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.servicetutorial.background.BackgroundServiceExample
import com.example.servicetutorial.screen.MainScreenViewModel

@Composable
fun BackgroundServiceView(
    context: Context,
    viewModel: MainScreenViewModel
) {
    val backgroundServiceIntent = Intent(context, BackgroundServiceExample::class.java)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                backgroundServiceIntent.also {
                    it.action = BackgroundServiceExample.Action.START_MAIN.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Start Background Service at Main Thread")
        }
        Button(
            onClick = {
                backgroundServiceIntent.also {
                    it.action = BackgroundServiceExample.Action.STOP_MAIN.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Stop Background Service at Main Thread")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                backgroundServiceIntent.also {
                    it.action = BackgroundServiceExample.Action.START_BACKGROUND.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Start Background Service at Background Thread")
        }
        Button(
            onClick = {
                backgroundServiceIntent.also {
                    it.action = BackgroundServiceExample.Action.STOP_BACKGROUND.toString()
                    context.startService(it)
                }
            }
        ) {
            Text(text = "Stop Background Service at Background Thread")
        }
    }
}