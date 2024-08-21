package com.example.alarm.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.alarm.Clocks.AnalogClock
import com.example.alarm.AppTitle
import com.example.alarm.Clocks.DigitalClocks
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun ClockScreens(){

    var hour by remember {
        mutableStateOf("0")
    }

    var min by remember {
        mutableStateOf("0")
    }


    var sec by remember {
        mutableStateOf("0")
    }

    var AmorPms by remember {
        mutableStateOf("0")
    }

    LaunchedEffect(Unit){
        while (true){
            var cal  = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR).run {
                if(this.toString().length==1) "0$this" else this.toString()
            }

            min = cal.get(Calendar.MINUTE).run {
                if(this.toString().length==1) "0$this" else this.toString()
            }


            sec = cal.get(Calendar.SECOND).run {
                if(this.toString().length==1) "0$this" else this.toString()
            }

            AmorPms = cal.get(Calendar.AM_PM).run {
                if(this == Calendar.AM_PM) "AM" else "PM"
            }
            delay(1000)
        }
    }


    Box(
        modifier = androidx.compose.ui.Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            AppTitle()

            AnalogClock(Integer.parseInt(hour),Integer.parseInt(min),Integer.parseInt(sec))

            DigitalClocks(hour,min,AmorPms)

        }

    }

}