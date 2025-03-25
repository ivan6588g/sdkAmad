package com.s10plusArtifacts.utils

data class PermissionState(
    val permissions: List<String> = emptyList(),
    val askPermission: Boolean = false,
    val showRational: Boolean = false,
    val rationals: List<String> = emptyList(),
    val navigateToSetting: Boolean = false
)
