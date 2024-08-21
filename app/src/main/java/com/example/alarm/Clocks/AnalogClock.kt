package com.example.alarm.Clocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.alarm.ui.theme.bluecolor
import com.example.alarm.ui.theme.greencolor
import com.example.alarm.ui.theme.pinkcolor
import kotlin.math.min


@Composable
fun AnalogClock(hour: Int, mins: Int, sec: Int) {

    Box(modifier = Modifier
        .fillMaxSize(0.5f).padding(top = 200.dp)
        .aspectRatio(1f).clip(CircleShape).background(greencolor), contentAlignment = Alignment.Center){

        Box(modifier = Modifier.fillMaxSize(fraction = 0.76f).aspectRatio(1f).clip(CircleShape).background(
            Color.White), contentAlignment = Alignment.Center) {


            Canvas(modifier = Modifier.fillMaxSize()) {
                val diameter = min(size.width, size.width) * 0.9f
                var radius = diameter / 2

                repeat(12) {

                    rotate(it / 12f * 360) {
                        var start = center - Offset(0f, radius)
                        var end = start + Offset(0f, radius / 40f)
                        if(it%3==0){
                            drawLine(
                                color = bluecolor,
                                start = start,
                                end = end, strokeWidth = 8.dp.toPx(),
                                cap = StrokeCap.Square
                            )
                        }
                        else{
                            drawLine(
                                color = Color.Black,
                                start = start,
                                end = end, strokeWidth = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }

                    }
                }
                var secondsratio = sec / 60f
                var minutesratio = mins / 60f
                var hourratio = hour / 12f

                rotate(hourratio * 360, center) {
                    drawLine(
                        color = Color.Black,
                        start = center - Offset(0f, radius * 0.4f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }


                rotate(minutesratio * 360, center) {
                    drawLine(
                        color = pinkcolor,
                        start = center - Offset(0f, radius * 0.6f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                rotate(secondsratio * 360, center) {
                    drawLine(
                        color = Color.Black,
                        start = center - Offset(0f, radius * 0.8f),
                        end = center + Offset(0f, radius * 0.1f),
                        strokeWidth = 3.8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                drawCircle(
                    color = Color.Black,
                    radius = 5.dp.toPx(),
                    center = center
                )
            }
        }
    }

}