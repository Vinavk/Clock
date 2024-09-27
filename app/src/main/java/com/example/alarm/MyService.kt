package com.example.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            IntentAction.START.toString() -> {
                startMyService()
            }
            IntentAction.STOP.toString() -> {
                stopMyService()
            }
        }
        return START_STICKY
    }

    private fun startMyService() {
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI).apply {
                isLooping = true
                start()
            }


            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("screen","AlarmScreen")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            val notification = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.img)
                .setContentTitle("Alarm Service")
                .setContentText("Alarm is ringing")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .build()



            startForeground(1001, notification)
        } else {
            mediaPlayer?.start()
        }
    }

    private fun stopMyService() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopForeground(true)
        stopSelf()
    }

    enum class IntentAction {
        START, STOP
    }
}

