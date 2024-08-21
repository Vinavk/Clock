package com.example.alarm.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alarm.Screenss
import com.example.alarm.ViewModel.AlarmViewModel
@Composable
fun SleepScreens(viewModel: AlarmViewModel, navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        OutlinedCard {
            TimePicker(viewModel, navController)
        }
    }
}

@Composable
fun TimePicker(
    viewModel: AlarmViewModel,
    navController: NavHostController,

) {

    val hours = (1..12).toList()
    val minutes = (0..59).toList()
    val amPmOptions = listOf("AM", "PM")

    val selectedHour = remember { mutableStateOf(6) }
    val selectedMinute = remember { mutableStateOf(30) }
    val selectedAmPm = remember { mutableStateOf("AM") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            viewModel.incrementcounter()

            val hour = selectedHour.value
            val minute = selectedMinute.value
            val timeValue = "$hour:${minute.toString().padStart(2, '0')} ${selectedAmPm.value}"

            viewModel.saveAllarmdata(viewModel.counter.toString(), timeValue)

            val finalHour = when {
                selectedAmPm.value == "AM" && hour == 12 -> 0 // 12 AM should be 00:00
                selectedAmPm.value == "PM" && hour != 12 -> hour + 12 // Convert PM hours to 24-hour format
                else -> hour
            }

            viewModel.scheduleAlarm(finalHour, minute)
            navController.navigate(Screenss.TimerScreen.value)

        }) {
            Text(text = "Save Alarm")
        }

        PickerColumn(
            items = hours,
            selectedItem = selectedHour.value,
            onItemSelected = { selectedHour.value = it }
        )

        PickerColumn(
            items = minutes,
            selectedItem = selectedMinute.value,
            onItemSelected = { selectedMinute.value = it }
        )

        PickerColumn(
            items = amPmOptions,
            selectedItem = selectedAmPm.value,
            onItemSelected = { selectedAmPm.value = it }
        )
    }
}

@Composable
fun <T> PickerColumn(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                val item = items[index]
                val isSelected = item == selectedItem
                Text(
                    text = item.toString(),
                    style = if (isSelected) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { onItemSelected(item) }
                )
            }
        }
    }
}