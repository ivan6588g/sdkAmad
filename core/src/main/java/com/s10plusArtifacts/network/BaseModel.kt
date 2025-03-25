package com.s10plusArtifacts.network
import com.google.gson.annotations.SerializedName


data class BaseModel<T>(
    @SerializedName("Code")
    var code: Int,
    @SerializedName("data")
    var data: T,
    @SerializedName("errorDetail")
    val error: String? = null, // Cambiado de "Error" a "errorDetail"
    @SerializedName("token")
    var token: String? = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MDQ4NjM5NzgsImV4cCI6MTYwNDg2NzU3OCwiYXVkIjoiNzU1YjU3MDhkN2IxMDhkODE2YzViOTZlOGJkMDExMGU1MzAxMWRhOSIsImRhdGEiOnsicm9sIjoiMSIsImlkX3VzdWFyaW8iOiJ1c2VyMSIsInVzZXJOYW1lIjoiYWxmb25zb2xvcGV6IiwiZnVsbFVzZXJOYW1lIjoiQWxmb25zbyBMXHUwMGYzcGV6IiwiaWRJbnN0aXR1Y2lvbiI6bnVsbH19.ioU-ZSzdaVDX8FcQXLNuKmFesFnmDsxQDS0iSecD6_4",
    @SerializedName("message")
    val message: String? = null, // Cambiado de "Message" a "message"
    @SerializedName("timestamp")
    val timestamp: String? = null ,// Agregado para manejar el campo "timestamp"
    @SerializedName("status")
    var status: Int = 0
)

