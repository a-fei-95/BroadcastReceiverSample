package com.example.broadcastreceiversample.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AirplaneModeChangeBroadcastReceiver : BroadcastReceiver() {

    private val tag = this::class.java.name

    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply {
            append("Action: ${intent?.action}\n")
            append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d(tag, log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
    }
}