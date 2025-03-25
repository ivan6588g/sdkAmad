package com.s10plusArtifacts.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.s10plusArtifacts.sesion.Phone
import io.reactivex.Observable
import java.util.Date

class CallInterceptor : CallListener() {

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        super.onOutgoingCallStarted(ctx, number, start)

        if (number == null || ctx == null) {
            return
        }

        Observable.fromCallable {
            handleCall(ctx, number)
        }.subscribe()
    }

    override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        super.onOutgoingCallEnded(ctx, number, start, end)
        LogUtils.d("Call ended: $number")
    }

    /**
     * Handles the call by checking if the number matches stored numbers,
     * ending the call, and showing a notification.
     */
    private fun handleCall(context: Context, phoneNumber: String) {
        val storedNumbers = context.Phone().split(",")
        val isRelevantNumber = storedNumbers.contains(phoneNumber)

        if (isRelevantNumber) {
            // End the call if possible
            val callEnded = endCall(context)
            LogUtils.d("Call ended: $callEnded")

            // Show notification to open the app
            showNotification(context)
        } else {
            LogUtils.d("Number $phoneNumber is not relevant.")
        }
    }

    /**
     * Ends the current call if the permission is granted.
     */
    private fun endCall(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED) {
                telecomManager.endCall()
                return true
            }
        }
        return false
    }

    /**
     * Shows a notification that allows the user to open the app.
     */
    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "call_action_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Call Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.sym_call_outgoing) // Replace with your app icon
            .setContentTitle("Llamada detectada")
            .setContentText("Toca para abrir la aplicación")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
