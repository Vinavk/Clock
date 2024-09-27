package com.example.alarm.ViewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.AlarmReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar

class AlarmViewModel(
    var datastore: DataStore<Preferences>,
    var alarmdataStore: DataStore<Preferences>,
    var context: Context,
   ) : ViewModel() {

    var counter = 0
    fun savedata(key: String, value: String) {
        viewModelScope.launch {
            var keyvalue = stringPreferencesKey(key)
            datastore.edit {
                it[keyvalue] = value
            }

        }
    }

    fun getAllData(): Flow<Preferences> {
        return datastore.data
    }


    fun clearAllData() {
        viewModelScope.launch {
            datastore.edit {
                it.clear()
            }
        }

    }


    fun saveAllarmdata(key: String, value: String) {
        viewModelScope.launch {
            var keyvalue = stringPreferencesKey(key)
            alarmdataStore.edit {
                it[keyvalue] = value
            }

        }
    }


    fun DeleteAlarmData(key: String) {
        viewModelScope.launch {
            var keyvalue = stringPreferencesKey(key)
            alarmdataStore.edit {
                it.remove(keyvalue)
            }

        }
    }


    fun getAllAllarmData(): Flow<Preferences> {
        return alarmdataStore.data
    }

    fun incrementcounter() {
        counter++
    }

    fun scheduleAlarm(hour: Int, minute: Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        Log.d("AlarmViewModel", "Scheduling alarm at ${calendar.time}")

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )


    }

    fun stopAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Use the same Intent and PendingIntent as in scheduleAlarm
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("stop", "stop") // Ensure the intent has the "stop" extra
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        Log.d("AlarmViewModel", "Alarm canceled")
        alarmManager.cancel(pendingIntent)
    }
}