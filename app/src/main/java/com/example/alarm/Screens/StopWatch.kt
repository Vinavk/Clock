package com.example.alarm.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.example.alarm.R
import com.example.alarm.ViewModel.AlarmViewModel
import com.example.alarm.ui.theme.greencolor
import kotlinx.coroutines.delay


@Composable
fun StopWatchScreens(viewmodel: AlarmViewModel) {

    val keyvalue = remember { mutableStateOf(0) }
    val timer = remember { mutableStateOf(0L) }
    val counter = remember { mutableStateOf(0L) }
    val isRunning = remember { mutableStateOf(false) }


    LaunchedEffect(isRunning.value) {
        while (isRunning.value) {
            delay(10)
            timer.value += 1L
            if ((timer.value % 360000) == 0L && timer.value != 0L) {
                counter.value += 1L
            }
        }
    }

    val prefrenceFlow = viewmodel.getAllData().collectAsState(initial = emptyPreferences())
    val storedata = prefrenceFlow.value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = formatTime(timer.value),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(storedata.asMap().entries.toList()) { entry ->


                    var values = formatTime(entry.value.toString().toLong())
                    ItemList(entry.key,values)
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = {
                    isRunning.value = !isRunning.value
                }) {
                    Icon(painter = painterResource(id = R.drawable.play), contentDescription = "play")
                }

                IconButton(onClick = {
                    timer.value = 0L
                    counter.value = 0L
                    isRunning.value = false
                    keyvalue.value = 0
                    viewmodel.clearAllData()
                }) {
                    Icon(painter = painterResource(id = R.drawable.reset), contentDescription = "reset")
                }

                IconButton(onClick = {
                    if (isRunning.value) {
                        keyvalue.value += 1
                        viewmodel.savedata(keyvalue.value.toString(), timer.value.toString())
                    }
                }) {
                    Icon(painter = painterResource(id = R.drawable.flag), contentDescription = "flag")
                }
            }
        }
    }
}

@Composable
fun ItemList(key: Preferences.Key<*>, value: Any) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
    ) {
        OutlinedCard(border = BorderStroke(color = greencolor, width = 3.dp)) {
            Text(
                text = "  #${key.name} :: ${value}    ",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun formatTime(timeInMillis: Long): String {
    val hours = (timeInMillis / 360000) % 24
    val minutes = (timeInMillis / 6000) % 60
    val seconds = (timeInMillis / 100) % 60
    val milliseconds = timeInMillis % 100
    return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
}

