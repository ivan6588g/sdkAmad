package com.s10plusArtifacts.network.interceptors.logging

import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level
import java.util.logging.Logger

object I {
    private val prefix = arrayOf(". ", " .")
    private var index = 0
    fun log(type: Int, tag: String, msg: String?, isLogHackEnable: Boolean) {
        val finalTag = getFinalTag(tag, isLogHackEnable)
        val logger = Logger.getLogger(if (isLogHackEnable) finalTag else tag)
        when (type) {
            INFO -> logger.log(Level.INFO, msg)
            else -> logger.log(Level.WARNING, msg)
        }
    }

    private fun getFinalTag(tag: String, isLogHackEnable: Boolean): String {
        return if (isLogHackEnable) {
            index = index xor 1
            prefix[index] + tag
        } else {
            tag
        }
    }
}