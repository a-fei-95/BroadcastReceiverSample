package com.example.broadcastreceiversample

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.broadcastreceiversample.broadcasts.AirplaneModeChangeBroadcastReceiver
import com.example.broadcastreceiversample.broadcasts.CustomBroadcastReceiver
import com.example.broadcastreceiversample.ui.theme.BroadcastReceiverSampleTheme

class MainActivity : ComponentActivity() {

    private val tag = this::class.java.name

    private val airplaneModeBR by lazy {
        AirplaneModeChangeBroadcastReceiver()
    }

    private val customBR by lazy {
        CustomBroadcastReceiver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filterAirplane = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeBR, filterAirplane)

        val filterCustom = IntentFilter(CUSTOM_INTENT)
        ContextCompat.registerReceiver(
            applicationContext,
            customBR,
            filterCustom,
            ContextCompat.RECEIVER_EXPORTED
        )

        setContent {
            BroadcastReceiverSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        Greeting("Android")
                        Text(text = "Sample app for BroadcastReceiver")
                        Button(onClick = { onSendBroadcastEvent()} ) {
                            Text(text = "Send Broadcast Event")
                        }
                        Button(onClick = { onStartActivitySample() }) {
                            Text(text = "Start Activity Sample Project")
                        }
                    }
                }
            }
        }
    }

    private fun onStartActivitySample() {
        val textMessage = "Hello Activity Sample!"
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, textMessage)
        }
        startActivity(sendIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeBR)
        unregisterReceiver(customBR)
    }

    private fun onSendBroadcastEvent() {
        Intent().also { intent ->
            intent.action = CUSTOM_INTENT
            Log.d(tag, "onSendBroadcastEvent")
            sendBroadcast(intent)
        }
    }

    companion object {
        private const val CUSTOM_INTENT = "com.example.broadcastreceiversample.CUSTOM_INTENT"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadcastReceiverSampleTheme {
        Greeting("Android")
    }
}