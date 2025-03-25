package com.s10plusArtifacts.network.interceptors.logging

import okhttp3.Request
import java.io.IOException
import kotlin.jvm.Throws

interface BufferListener {
    @Throws(IOException::class)
    fun getJsonResponse(request: Request?):String?

}