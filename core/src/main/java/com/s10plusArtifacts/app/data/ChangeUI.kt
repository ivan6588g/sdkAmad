package com.s10plusArtifacts.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ChangeUI(
    val id: String
): Parcelable
