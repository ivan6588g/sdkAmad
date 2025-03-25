package com.s10plusArtifacts.utils

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.s10plusArtifacts.extensions.openAppSetting

@Composable
fun PermissionHandler(
    permissions: List<PermissionModel>,
    askPermission: Boolean,
    result: (Map<String, Boolean>) -> Unit = {}
) {
    val activity = LocalContext.current as Activity
    val viewModel: PermissionViewModel = viewModel(
        factory = PermissionViewModelFactory(
            permissions = permissions,
            askPermission = askPermission
        )
    )
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var hasLaunchedPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            val deniedPermissions = result.filterValues { !it }.keys

            if (deniedPermissions.isNotEmpty()) {
                // Si hay permisos denegados, vuelve a lanzarlos
                viewModel.onPermissionDenied(deniedPermissions.toList())
            } else {
                // Todos los permisos concedidos
                viewModel.onPermissionGranted()
                result(result) // Callback con el resultado
            }
        }
    )

    // Solicitar permisos una sola vez
    LaunchedEffect(key1 = state.askPermission) {
        if (state.askPermission && !hasLaunchedPermission) {
            hasLaunchedPermission = true
            permissionLauncher.launch(state.permissions.toTypedArray())
        }
    }

    // Navegar a configuración si es necesario
    LaunchedEffect(key1 = state.navigateToSetting) {
        if (state.navigateToSetting) {
            activity.openAppSetting()
            viewModel.onPermissionRequested()
        }
    }

    AnimatedVisibility(
        visible = state.showRational,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Access denied",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            state.rationals.forEachIndexed { index, item ->
                Text(
                    text = "${index + 1}) $item",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Button(
                onClick = {
                    hasLaunchedPermission = false // Reinicia el flag para relanzar los permisos
                    permissionLauncher.launch(state.permissions.toTypedArray())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Text(text = "Grant Permission", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}


