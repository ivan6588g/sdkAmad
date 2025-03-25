package com.s10plusArtifacts.app.domain.model
import android.os.Build
import com.google.gson.annotations.SerializedName


data class TokenRequestModel(
    @SerializedName("android_version")
    var androidVersion: String = "${Build.VERSION.SDK_INT}",
    @SerializedName("id_application")
    var idApplication: String = "1",
    @SerializedName("model")
    var model: String = Build.MODEL,
    @SerializedName("other_information")
    var otherInformation: OtherInformation = OtherInformation(),
    @SerializedName("phone_id")
    var phoneId: String = "",
    @SerializedName("phone_number")
    var phoneNumber: String = "",
)
data class OtherInformation(
    @SerializedName("lat")
    var lat: Double = 0.0,
    @SerializedName("long")
    var long: Double = 0.0,
    @SerializedName("origin")
    var origin: String = "ANDROID-APP",
    @SerializedName("state")
    var state: String = "",
    @SerializedName("tel_marcado")
    var telMarcado: String = "",

    )

