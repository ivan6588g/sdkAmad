package com.s10plusArtifacts.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import java.util.Date

abstract class CallListener : BroadcastReceiver()  {
    override fun onReceive(context: Context, intent: Intent) {

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
            savedNumber = intent.extras!!.getString("android.intent.extra.PHONE_NUMBER")
        } else {
            val stateStr = intent.extras!!.getString(TelephonyManager.EXTRA_STATE)
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var state = 0
            if(stateStr.isNullOrBlank() || number.isNullOrBlank()){
                return
            }
            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    state = TelephonyManager.CALL_STATE_IDLE
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    state = TelephonyManager.CALL_STATE_OFFHOOK
                }
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    state = TelephonyManager.CALL_STATE_RINGING
                }
            }
            onCallStateChanged(context, state, number)
        }
    }

    //Derived classes should override these to respond to specific events of interest
    open fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {}
    open fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {}
    open fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {}
    open fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {}
    open fun onMissedCall(ctx: Context?, number: String?, start: Date?) {}

    //Deals with actual events
    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    fun onCallStateChanged(context: Context?, state: Int, number: String?) {
        if (lastState == state && savedNumber == number) {
            //No change, debounce extras
            return
        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                onIncomingCallStarted(context, number, callStartTime)
                savedNumber = null

            }
            TelephonyManager.CALL_STATE_OFFHOOK ->                 //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    savedNumber =if(savedNumber== null) number else savedNumber
                    isIncoming = false
                    callStartTime = Date()
                    onOutgoingCallStarted(context, savedNumber, callStartTime)
                    savedNumber = null
                }
            TelephonyManager.CALL_STATE_IDLE -> {               //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                when {
                    lastState == TelephonyManager.CALL_STATE_RINGING -> {
                        //Ring but no pickup-  a miss
                        onMissedCall(context, savedNumber, callStartTime)
                    }
                    isIncoming -> {
                        onIncomingCallEnded(context, savedNumber, callStartTime, Date())
                    }
                    else -> {
                        onOutgoingCallEnded(context, savedNumber, callStartTime, Date())
                    }
                }
                savedNumber = null

            }


        }
        lastState = state
    }

    companion object {
        //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private var callStartTime: Date? = null
        private var isIncoming = false
        private var savedNumber //because the passed incoming is only valid in ringing
                : String? = null
    }
}