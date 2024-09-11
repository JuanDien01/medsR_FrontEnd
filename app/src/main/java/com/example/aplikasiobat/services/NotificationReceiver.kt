package com.example.aplikasiobat.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.aplikasiobat.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val message = intent.getStringExtra("message") ?: "No message"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Waktunya Minum Obat! \uD83D\uDC8A")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
