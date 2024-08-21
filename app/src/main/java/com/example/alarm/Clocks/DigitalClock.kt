package com.example.alarm.Clocks


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun DigitalClocks(hour: String, min: String, AmorPm: String) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "$hour : $min : $AmorPm", style = MaterialTheme.typography.displayMedium)
            Text(
                text = "India : Chennai",
                style = MaterialTheme.typography.titleMedium.merge(
                    color = MaterialTheme.colorScheme.background.copy(0.6f)
                )
                ,color = Color.White
            )
        }
    }
}
