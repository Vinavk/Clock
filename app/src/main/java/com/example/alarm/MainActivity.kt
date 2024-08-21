package com.example.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alarm.Screens.AlarmScreen
import com.example.alarm.Screens.ClockScreens
import com.example.alarm.Screens.SleepScreens
import com.example.alarm.Screens.StopWatchScreens
import com.example.alarm.ViewModel.AlarmViewModel
import com.example.alarm.ViewModel.DataFactory
import com.example.alarm.ui.theme.AlarmTheme
import com.example.alarm.ui.theme.greencolor

class MainActivity : ComponentActivity() {
    val Context.datastoresss by preferencesDataStore("DataStore")
    val Context.alarmdatastoress by preferencesDataStore("AlarmData")

    val viewModel by viewModels<AlarmViewModel> {
        DataFactory(datastoresss, alarmdatastoress, applicationContext)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarm Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alarm notifications"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            Log.d("MainActivity", "Notification channel created!")
        }

        setContent {
            val navController = rememberNavController()

            AlarmTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screenss.ClockScreen.value,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screenss.ClockScreen.value) {
                            ClockScreens()
                        }
                        composable(Screenss.SleepScreen.value) {
                            SleepScreens(viewModel, navController)
                        }
                        composable(Screenss.StopWatchScreen.value) {
                            StopWatchScreens(viewModel)
                        }
                        composable(Screenss.TimerScreen.value) {
                            AlarmScreen(viewModel, navController)
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun BottomBar(navController: androidx.navigation.NavController) {
        BottomAppBar(
            containerColor = greencolor,
            tonalElevation = 10.dp,
            modifier = Modifier
                .shadow(10.dp, spotColor = Color.White, ambientColor = Color.Magenta)
                .clip(shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.navigate(Screenss.ClockScreen.value)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm_pics),
                        contentDescription = "Alarm Pics"
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Screenss.StopWatchScreen.value)


                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm_duration),
                        contentDescription = "Alarm Duration"
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Screenss.TimerScreen.value)

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm_logo),
                        contentDescription = "Alarm Logo"
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Screenss.SleepScreen.value)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm_sleep),
                        contentDescription = "Alarm Sleep"
                    )
                }
            }
        }
    }
}


    @Composable
    fun AppTitle() {

        Box(modifier = Modifier.padding(top = 80.dp), contentAlignment = Alignment.Center) {
            Text(
                text = "Clock",
                style = MaterialTheme.typography.headlineLarge,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )

        }
    }

