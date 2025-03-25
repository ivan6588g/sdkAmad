package com.s10plusArtifacts.network.interceptors.logging


import com.s10plusArtifacts.AppBuildConfig.isReleaseDebug
import com.s10plusArtifacts.utils.Constants.MAXIMUM_VALUE
import com.s10plusArtifacts.utils.Constants.MAX_LOG_SIZE
import com.s10plusArtifacts.utils.Constants.NUMBER_SIXTEEN
import com.s10plusArtifacts.utils.Constants.ZERO
import okhttp3.Headers
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

@Suppress("unused", "TooManyFunctions")
object Printer {

    private const val JSON_INDENT = 3
    private val LINE_SEPARATOR = System.getProperty("line.separator") ?: ""
    private val DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR
    private const val N = "\n"
    private const val T = "\t"
    private const val REQUEST_UP_LINE =
        "┌────── Request ────────────────────────────────────────────────────────────────────────"
    private const val END_LINE =
        "└───────────────────────────────────────────────────────────────────────────────────────"
    private const val RESPONSE_UP_LINE =
        "┌────── Response ───────────────────────────────────────────────────────────────────────"
    private const val REQUEST_UP_LINE_REMOTE =
        "┌ Request ─"
    private const val END_LINE_REMOTE =
        "───"
    private const val RESPONSE_UP_LINE_REMOTE =
        "ㄴ Response ─"
    private const val BODY_TAG = "Body:"
    private const val URL_TAG = "URL: "
    private const val METHOD_TAG = "Method: @"
    private const val HEADERS_TAG = "Headers:"
    private const val STATUS_CODE_TAG = "Status Code: "
    private const val RECEIVED_TAG = "Received in: "
    private const val DEFAULT_LINE = "│ "
    private val OOM_OMITTED = LINE_SEPARATOR + "Output omitted because of Object size."
    private fun isEmpty(line: String): Boolean {
        return line.isEmpty() || N == line || T == line || line.trim { it <= ' ' }.isEmpty()
    }

    fun printJsonRequest(
        builder: LoggingInterceptor.Builder,
        body: RequestBody?,
        url: String,
        header: Headers,
        method: String
    ) {
        val requestBody =
            body?.let { LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyToString(body, header) }
                ?: ""
        val tag = builder.getTag(true)
        if (builder.logger == null) I.log(
            builder.type,
            tag,
            REQUEST_UP_LINE,
            builder.isLogHackEnable
        )
        logLines(
            builder.type,
            tag,
            arrayOf(URL_TAG + url),
            builder.logger,
            false,
            builder.isLogHackEnable
        )
        val requestLines = getRequest(builder.level, header, method)
        logLines(
            builder.type,
            tag,
            requestLines,
            builder.logger,
            true,
            builder.isLogHackEnable
        )
        val requestBodyLines = requestBody.split(LINE_SEPARATOR).toTypedArray()
        if (builder.level == Level.BASIC || builder.level == Level.BODY) {
            logLines(
                builder.type,
                tag,
                requestBodyLines,
                builder.logger,
                true,
                builder.isLogHackEnable
            )
        }
        if (builder.logger == null) I.log(builder.type, tag, END_LINE, builder.isLogHackEnable)

        if (isReleaseDebug()) {
            var requestLog = "$REQUEST_UP_LINE_REMOTE $N $URL_TAG $N $url"
            requestLines.forEach {
                requestLog = "$requestLog $N $it"
            }
            requestBodyLines.forEach {
                requestLog = "$requestLog $N $it"
            }
            requestLog = "$requestLog $N $END_LINE_REMOTE"
            Timber.log(builder.type, requestLog)
        }
    }

    @Suppress("LongParameterList", "TooManyFunctions")
    fun printJsonResponse(
        builder: LoggingInterceptor.Builder,
        chainMs: Long,
        isSuccessful: Boolean,
        code: Int,
        headers: Headers,
        response: Response,
        segments: List<String>,
        message: String,
        responseUrl: String
    ) {
        val responseBody = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + getResponseBody(response)
        val tag = builder.getTag(false)
        val urlLine = arrayOf(URL_TAG + responseUrl, N)
        val responseString =
            getResponse(headers, chainMs, code, isSuccessful, builder.level, segments, message)
        if (builder.logger == null) {
            I.log(builder.type, tag, RESPONSE_UP_LINE, builder.isLogHackEnable)
        }
       // logLines(builder.type, tag, urlLine, builder.logger, true, builder.isLogHackEnable)
        //ogLines(builder.type, tag, responseString, builder.logger, true, builder.isLogHackEnable)
        if (builder.level == Level.BASIC || builder.level == Level.BODY) {
            logLines(
                builder.type,
                tag,
                responseBody.split(LINE_SEPARATOR).toTypedArray(),
                builder.logger,
                true,
                builder.isLogHackEnable
            )
        }
        if (builder.logger == null) {
            I.log(builder.type, tag, END_LINE, builder.isLogHackEnable)
        }

        if (isReleaseDebug()) {
            var requestLog = "$RESPONSE_UP_LINE_REMOTE $N $URL_TAG $N $responseUrl"
            responseString.forEach {
                requestLog = "$requestLog $N $it"
            }
            requestLog = "$requestLog $N $responseBody"
            requestLog = "$requestLog $N $END_LINE_REMOTE"
            Timber.log(builder.type, requestLog)
        }
    }

    @SuppressWarnings("ReturnCount")
    fun getResponseBody(response: Response): String {
        val responseBody = response.body!!
        val headers = response.headers
        val contentLength = responseBody.contentLength()
        if (!response.promisesBody()) {
            return "End request - Promises Body"
        } else if (bodyHasUnknownEncoding(response.headers)) {
            return "encoded body omitted"
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer

            var gzippedLength: Long? = null
            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                gzippedLength = buffer.size
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val contentType = responseBody.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            if (!buffer.isProbablyUtf8()) {
                return "End request - binary ${buffer.size}:byte body omitted"
            }

            if (contentLength != 0L) {
                return getJsonString(buffer.clone().readString(charset))
            }

            return if (gzippedLength != null) {
                "End request - ${buffer.size}:byte, $gzippedLength-gzipped-byte body"
            } else {
                "End request - ${buffer.size}:byte body"
            }
        }
    }

    private fun getRequest(level: Level, headers: Headers, method: String): Array<String> {
        val log: String
        val loggableHeader = level == Level.HEADERS || level == Level.BASIC
        log =
            METHOD_TAG +
                    method +
                    DOUBLE_SEPARATOR +
                    if (isEmpty("$headers")) {
                        ""
                    } else if (loggableHeader) HEADERS_TAG + LINE_SEPARATOR + dotHeaders(headers) else ""
        return log.split(LINE_SEPARATOR).toTypedArray()
    }

    @Suppress("LongParameterList", "TooManyFunctions")
    private fun getResponse(
        headers: Headers,
        tookMs: Long,
        code: Int,
        isSuccessful: Boolean,
        level: Level,
        segments: List<String>,
        message: String
    ): Array<String> {
        val log: String
        val loggableHeader = level == Level.HEADERS || level == Level.BASIC
        val segmentString = slashSegments(segments)
        log =
            (
                    (if (segmentString.isNotEmpty()) "$segmentString - " else "") +
                            "[is success : " +
                            isSuccessful +
                            "] - " +
                            RECEIVED_TAG +
                            tookMs +
                            "ms" +
                            DOUBLE_SEPARATOR +
                            STATUS_CODE_TAG +
                            code +
                            " / " +
                            message +
                            DOUBLE_SEPARATOR +
                            when {
                                isEmpty("$headers") -> ""
                                loggableHeader -> HEADERS_TAG + LINE_SEPARATOR + dotHeaders(headers)
                                else -> ""
                            }
                    )
        return log.split(LINE_SEPARATOR).toTypedArray()
    }

    private fun slashSegments(segments: List<String>): String {
        val segmentString = StringBuilder()
        for (segment in segments) {
            segmentString.append("/").append(segment)
        }
        return segmentString.toString()
    }

    private fun dotHeaders(headers: Headers): String {
        val builder = StringBuilder()

        headers.forEach { pair -> builder.append("${pair.first}: ${pair.second}").append(N) }
        return builder.dropLast(1).toString()
    }

    @Suppress("LongParameterList", "TooManyFunctions")
    private fun logLines(
        type: Int,
        tag: String,
        lines: Array<String>,
        logger: Logger?,
        withLineSize: Boolean,
        useLogHack: Boolean
    ) {
        for (line in lines) {
            val lineLength = line.length
            val maxLogSize = if (withLineSize) MAX_LOG_SIZE else lineLength
            for (i in 0..lineLength / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end = if (end > line.length) line.length else end
                if (logger == null) {
                    I.log(type, tag, DEFAULT_LINE + line.substring(start, end), useLogHack)
                } else {
                    logger.log(type, tag, line.substring(start, end))
                }
            }
        }
    }

    @SuppressWarnings("ReturnCount", "NestedBlockDepth")
    fun bodyToString(requestBody: RequestBody?, headers: Headers): String {
        return requestBody?.let {
            return try {
                when {
                    bodyHasUnknownEncoding(headers) -> {
                        return "encoded body omitted)"
                    }

                    requestBody.isDuplex() -> {
                        return "duplex request body omitted"
                    }

                    requestBody.isOneShot() -> {
                        return "one-shot body omitted"
                    }

                    else -> {
                        val buffer = Buffer()
                        requestBody.writeTo(buffer)

                        val contentType = requestBody.contentType()
                        val charset: Charset =
                            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                        return if (buffer.isProbablyUtf8()) {
                            getJsonString(buffer.readString(charset)) +
                                    LINE_SEPARATOR +
                                    "${requestBody.contentLength()}-byte body"
                        } else {
                            "binary ${requestBody.contentLength()}-byte body omitted"
                        }
                    }
                }
            } catch (e: IOException) {
                "{\"err\": \"" + e.message + "\"}"
            }
        }
            ?: ""
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    @SuppressWarnings("SwallowedException", "NestedBlockDepth")
    private fun getJsonString(msg: String): String {
        val message: String = try {
            when {
                msg.startsWith("{") -> {
                    val jsonObject = JSONObject(msg)
                    jsonObject.toString(JSON_INDENT)
                }

                msg.startsWith("[") -> {
                    val jsonArray = JSONArray(msg)
                    jsonArray.toString(JSON_INDENT)
                }

                else -> {
                    msg
                }
            }
        } catch (e: JSONException) {
            msg
        } catch (e1: OutOfMemoryError) {
            OOM_OMITTED
        }
        return message
    }

    fun printFailed(tag: String, builder: LoggingInterceptor.Builder, reason: Exception) {
        I.log(builder.type, tag, RESPONSE_UP_LINE, builder.isLogHackEnable)
        I.log(
            builder.type,
            tag,
            DEFAULT_LINE +
                    "Response failed for the reason: ${reason.message} with url ${builder.httpUrl}",
            builder.isLogHackEnable
        )
        I.log(builder.type, tag, END_LINE, builder.isLogHackEnable)
    }
}

@Suppress("ReturnCount", "TooManyFunctions")
internal fun Buffer.isProbablyUtf8(): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = size.coerceAtMost(MAXIMUM_VALUE)
        copyTo(prefix, 0, byteCount)
        for (i in ZERO until NUMBER_SIXTEEN) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (_: EOFException) {
        return false // Truncated UTF-8 sequence.
    }
}
