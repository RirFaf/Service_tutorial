package com.example.servicetutorial.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MainScreenState(
    val permissionsGranted: Boolean = false,
)

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    fun checkPermissions(
        context: Context,
        onPermissionsGranted: () -> Unit,
        onPermissionsDenied: () -> Unit
    ) {
        val permissions = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.FOREGROUND_SERVICE
                )
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                arrayOf(
                    Manifest.permission.FOREGROUND_SERVICE
                )
            }

            else -> {
                emptyArray<String>()
            }
        }

        val permissionResults = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (permissionResults) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }

}