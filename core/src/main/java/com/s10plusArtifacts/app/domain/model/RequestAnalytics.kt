package com.s10plusArtifacts.app.domain.model
import com.google.gson.annotations.SerializedName


data class RequestAnalytics(
    @SerializedName("id_action")
    var idAction: String = "",
    @SerializedName("other_information")
    var otherInformation: String = ""
)