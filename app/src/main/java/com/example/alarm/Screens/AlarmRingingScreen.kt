package com.example.alarm.Screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.alarm.AlarmReceiver
import com.example.alarm.Screenss

@Composable
fun AlarmRingingScreen(navController: NavHostController, context: Context) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter ){
        Button(onClick = {
            val testIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("STOP_ALARM","STOP_ALARM")
            }
            context.sendBroadcast(testIntent)
            navController.navigate(Screenss.ClockScreen.value)
        }) {
            Text(text = "Stop")

        }
    }
}