package com.s10plusArtifacts.network

import com.google.gson.annotations.SerializedName
import com.s10plusArtifacts.utils.Constants.ALERT_TEXT_GENERIC
import com.s10plusArtifacts.utils.Constants.ERROR_TEXT_GENERIC

data class BaseResponse<T>(
    @SerializedName("response") var response: Response<T>? = null
)


data class Response<T>(
    @SerializedName("isSuccess") var isSuccess: Boolean? = null,
    @SerializedName("success") var success: Success? = null,
    @SerializedName("errors") var errors: ArrayList<Errors> = arrayListOf(),
    @SerializedName("data") var data: T? = null
)

data class Success(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("title")
    val title: String = ""
)

data class Errors(
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String = ERROR_TEXT_GENERIC,
    @SerializedName("title") var title: String = ALERT_TEXT_GENERIC,
    @SerializedName("status") var status: String? = null,
    @SerializedName("internal") var internal: Boolean? = null,
    @SerializedName("detail") var detail: Any? = null
)