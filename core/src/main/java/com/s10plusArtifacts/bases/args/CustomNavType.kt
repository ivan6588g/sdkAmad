package com.s10plusArtifacts.bases.args

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class CustomNavType<T:Parcelable>(
    private val clazz:Class<T>,
    private val serializer:KSerializer<T>
) : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz)
                ?: throw IllegalArgumentException("Failed to get Parcelable: $key")
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable<T>(key)
                ?: throw IllegalArgumentException("Failed to get Parcelable: $key")
        }
    }


    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)


    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)
    override val name: String = clazz.name
}