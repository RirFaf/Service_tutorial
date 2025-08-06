package com.example.servicetutorial.service.bounded

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.example.servicetutorial.ISampleBoundedService

class SampleAidlService : Service() {

    private val binder = object : ISampleBoundedService.Stub() {
        override fun sendMessage(message: String?) {

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, "Сообщение: $message", Toast.LENGTH_SHORT).show()
            }
        }

        override fun receiveMessage(): String {
            return "Message from server has been received"
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}