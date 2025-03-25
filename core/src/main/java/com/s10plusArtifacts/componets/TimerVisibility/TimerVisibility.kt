package com.s10plusArtifacts.componets.TimerVisibility

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.s10plusArtifacts.utils.Schedule
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@Composable
fun TimerVisibility(
    targetTime: Long,
    date: Schedule? = null,
    content: @Composable () -> Unit
) {
    var remainingTime by remember(targetTime) {
        mutableStateOf(targetTime - System.currentTimeMillis())
    }

    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    // Parse hora de inicio y fin
    val timeToInit = date?.hourStart?.let { parseTimer(it, formatter) }
    val timeToFinish = date?.hourEnd?.let { parseTimer(it, formatter) }
    val timeNow = LocalTime.now().withSecond(0).withNano(0)

    // Validar si debe mostrarse u ocultarse el contenido
    val shouldDisplay = remember {
        if (date == null) {
            true // Si no hay programación, mostrar siempre
        } else if (date.dayStart.toInt() == LocalDateTime.now().dayOfWeek.value &&
            date.dayEnd.toInt() == LocalDateTime.now().dayOfWeek.value &&
            timeToInit != null && timeToFinish != null
        ) {
            // Dentro del rango de tiempo
            if (timeNow.isAfter(timeToInit) && timeNow.isBefore(timeToFinish)) {
                date.show // Mostrar según la bandera `show`
            } else {
                !date.show // Ocultar según la bandera `show`
            }
        } else {
            !date.show // Fuera del rango o día incorrecto, aplicar el inverso de `show`
        }
    }

    if (shouldDisplay) {
        content.invoke()
    }

    LaunchedEffect(remainingTime) {
        delay(1_000L)
        remainingTime = targetTime - System.currentTimeMillis()
    }
}

// Función auxiliar para analizar la hora
fun parseTimer(time: String, formatter: DateTimeFormatter): LocalTime? {
    return try {
        time.trim()
            .uppercase(Locale.ENGLISH) // Normaliza a mayúsculas
            .replace("\\s+".toRegex(), " ") // Reemplaza múltiples espacios
            .let { LocalTime.parse(it, formatter) }
    } catch (e: DateTimeParseException) {
        println("Error al analizar la hora '$time': ${e.localizedMessage}")
        null
    }
}
