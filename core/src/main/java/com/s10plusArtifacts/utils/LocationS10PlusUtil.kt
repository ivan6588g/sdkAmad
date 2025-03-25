package com.s10plusArtifacts.utils


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("MissingPermission")
fun getCurentLocation(
    onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
    onGetCurrentLocationFaild: (Exception) -> Unit,
    priority: Boolean = true,
    context: Context
) {
    val precision = if (priority) Priority.PRIORITY_HIGH_ACCURACY
    else Priority.PRIORITY_BALANCED_POWER_ACCURACY

    val fusedLocsationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    if (areLocationPermissionsGranted(context)) {
        fusedLocsationProviderClient.getCurrentLocation(precision, CancellationTokenSource().token)
            .addOnSuccessListener { location ->
                location?.let {
                    onGetCurrentLocationSuccess(Pair(location.latitude, it.longitude))
                }
            }.addOnFailureListener { exception ->
                onGetCurrentLocationFaild(exception)
            }
    }

}

fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)
}