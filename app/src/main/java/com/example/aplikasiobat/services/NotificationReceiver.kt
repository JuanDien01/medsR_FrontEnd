package com.example.aplikasiobat.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.aplikasiobat.MainActivity
import com.example.aplikasiobat.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val message = intent.getStringExtra("message") ?: "No message"
        val userId = intent.getIntExtra("userId", 0)
        val idObat = intent.getIntExtra("idObat", 0)
        val idObatPasien = intent.getIntExtra("idObatPasien", 0)
        val startTime = intent.getStringExtra("waktuMulaiMinumObat") ?: ""
        val endTime = intent.getStringExtra("waktuSelesaiMinumObat") ?: ""
        val dosisObat = intent.getStringExtra("dosisObat") ?: ""
        val namaObat = intent.getStringExtra("namaObat") ?: ""
        val catatanObat = intent.getStringExtra("catatan") ?: ""
        val tanggalPemberian = intent.getStringExtra("tanggalPemberian") ?: ""
        val aturan = intent.getStringExtra("aturan") ?: ""

        // Intent to open the MainActivity and navigate to the specific fragment
        val clickIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("userId", userId)
            putExtra("idObat", idObat)
            putExtra("idObatPasien", idObatPasien)
            putExtra("dosisObat", dosisObat)
            putExtra("namaObat", namaObat)
            putExtra("waktuMulaiMinumObat", startTime)
            putExtra("waktuSelesaiMinumObat", endTime)
            putExtra("catatan", catatanObat)
            putExtra("tanggalPemberian", tanggalPemberian)
            putExtra("aturan", aturan)
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, clickIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        // Define a vibration pattern
        val vibrationPattern = longArrayOf(0, 1000, 500, 1000)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Waktunya Minum Obat! \uD83D\uDC8A")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSound)
            .setVibrate(vibrationPattern)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
        notificationManager.cancel(notificationId)
    }
}

