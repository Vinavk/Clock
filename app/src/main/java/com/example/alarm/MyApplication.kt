package com.example.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.preferences.preferencesDataStore

class MyApplication  : Application(){

    companion object{
        val Context.datastoresss by preferencesDataStore("DataStore")
        val Context.alarmdatastoress by preferencesDataStore("AlarmData")
    }



    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notification_channel =
                NotificationChannel(
                    "channel_id",
                    "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )


            var notificationmanager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationmanager.createNotificationChannel(notification_channel)
        }

    }
}