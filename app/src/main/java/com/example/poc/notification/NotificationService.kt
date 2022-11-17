package com.example.poc.notification


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.poc.R
import com.example.poc.ui.MainActivity


class CounterNotificationService(
    private val context: Context
){

    private val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(){
        val activityIntent=Intent(context, MainActivity::class.java)

        val activityPendingIntent=PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        ) //Flag is mandatory.


        val notification=NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("ALERT")
            .setContentText("MOTION DETECTED!!!")
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentIntent(activityPendingIntent)
            .build()



        notificationManager.notify(
            1,
            notification
        )

    }

    companion object{
        const val COUNTER_CHANNEL_ID="id"
    }
}