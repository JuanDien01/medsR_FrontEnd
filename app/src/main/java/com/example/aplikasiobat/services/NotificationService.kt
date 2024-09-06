package com.example.aplikasiobat.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.aplikasiobat.R

class NotificationService : Service() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val interval: Long = 100000 // 10 seconds

    override fun onBind(intent: Intent?): IBinder? {
        // Not bound
        return null
    }

    override fun onCreate() {
        super.onCreate()

        // Create a notification channel (for Android 8.0 and above)
        createNotificationChannel()

        // Start the service in the foreground
        val notification = createNotification("Service Started")
        startForeground(1, notification)

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                // Show the notification every 10 seconds
                showNotification("This is your periodic notification.")
                handler.postDelayed(this, interval)
            }
        }
        handler.post(runnable) // Start the handler
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Stop the handler when the service is destroyed
    }

    // Create and show a notification
    private fun showNotification(message: String) {
        val notification = createNotification(message)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    // Create the notification
    private fun createNotification(message: String): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.logo) // Your notification icon
            .setContentTitle("Periodic Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        return notificationBuilder.build()
    }

    // Create notification channel for Android 8.0+
    private fun createNotificationChannel() {
        val name = "Notification Service Channel"
        val descriptionText = "Channel for background notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
