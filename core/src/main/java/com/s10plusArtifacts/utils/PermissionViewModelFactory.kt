package com.s10plusArtifacts.utils

import androidx.lifecycle.ViewModel

class PermissionViewModelFactory(
    private val permissions: List<PermissionModel>,
    private val askPermission: Boolean
) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PermissionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PermissionViewModel(permissions, askPermission) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}