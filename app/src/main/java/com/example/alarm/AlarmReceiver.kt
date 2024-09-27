package com.example.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm triggered!")

        context?.let {
            val serviceIntent = Intent(context, MyService::class.java)


            if (intent?.getStringExtra("STOP_ALARM") == "STOP_ALARM") {
                serviceIntent.action = MyService.IntentAction.STOP.toString()
            } else {
                serviceIntent.action = MyService.IntentAction.START.toString()
            }

            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}


