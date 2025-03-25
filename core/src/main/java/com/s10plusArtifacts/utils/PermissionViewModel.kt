package com.s10plusArtifacts.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PermissionViewModel(
    private val permissions: List<PermissionModel>,
    private val askPermission: Boolean
) : ViewModel() {

    // Estado interno mutable
    private val _state = MutableStateFlow(PermissionState())
    val state: StateFlow<PermissionState> get() = _state

    init {
        if (askPermission) {
            triggerPermissionRequest()
        }
    }

    /**
     * Función para iniciar la solicitud de permisos.
     */
    private fun triggerPermissionRequest() {
        _state.value = _state.value.copy(
            askPermission = true,
            permissions = permissions.map { it.permissionName }
        )
    }

    /**
     * Manejar el resultado de la solicitud de permisos.
     */
    fun onResult(result: Map<String, Boolean>) {
        val deniedPermissions = result.filterValues { !it }.keys
        if (deniedPermissions.isNotEmpty()) {
            // Si hay permisos denegados, mostrar racional
            _state.value = _state.value.copy(
                showRational = true,
                rationals = deniedPermissions.map { permission ->
                    permissions.find { it.permissionName == permission }?.rationale ?: ""
                },
                askPermission = false
            )
        } else {
            // Si todos los permisos son concedidos
            _state.value = _state.value.copy(
                showRational = false,
                askPermission = false
            )
        }
    }

    /**
     * Acción cuando se da clic en el botón de conceder permisos.
     */
    fun onGrantPermissionClicked() {
        _state.value = _state.value.copy(
            askPermission = true
        )
    }

    /**
     * Acción para abrir la configuración de la app si el usuario lo requiere.
     */
    fun onPermissionDenied(permissions: List<String>) {
        _state.value = _state.value.copy(
            navigateToSetting = true,
            askPermission = false
        )
    }

    /**
     * Acción para resetear el estado después de abrir configuración.
     */
    fun onPermissionRequested() {
        _state.value = _state.value.copy(
            navigateToSetting = false
        )
    }

    /**
     * Acción cuando se conceden los permisos.
     */
    fun onPermissionGranted() {
        _state.value = _state.value.copy(
            showRational = false,
            navigateToSetting = false
        )
    }
}

