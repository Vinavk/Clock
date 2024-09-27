package com.example.alarm

sealed class Screenss(var value : String) {
   data  object ClockScreen : Screenss("Clock")
    data object TimerScreen : Screenss("Timer")
    object SleepScreen : Screenss("Sleep")
    object StopWatchScreen : Screenss("StopWatch")
    object AlarmRingingScreen : Screenss("AlarmScreen")
}