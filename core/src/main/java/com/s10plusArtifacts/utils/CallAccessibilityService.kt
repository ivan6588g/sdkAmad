package com.s10plusArtifacts.utils

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class CallAccessibilityService: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            val text = event.text?.toString() ?: return
            if (text.contains("5555555555")) {
                Log.i("","")
                // Realiza la acción deseada, como abrir tu aplicación
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onInterrupt() {
        Log.i("","")
    }
}