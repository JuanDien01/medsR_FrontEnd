package com.example.aplikasiobat.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.aplikasiobat.R
import com.example.aplikasiobat.api.repository.MainRepository
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NotificationService : Service() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val interval: Long = 10000 // 100 sec, adjust as needed
    private lateinit var mainRepository: MainRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val userId = intent?.getIntExtra("userId", 0) ?: 0

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                fetchObatPasien(userId)
                handler.postDelayed(this, interval)
            }
        }
        handler.post(runnable)

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification = createNotification("Jadwal Minum Obat Kamu Sedang DiCek Ya \uD83D\uDE42")
        startForeground(1, notification)
        val apiHelper = ApiHelper(ApiClient.instance)
        mainRepository = MainRepository(apiHelper)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    private fun parseTime(timeString: String): Calendar {
        val calendar = Calendar.getInstance()
        val timeParts = timeString.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())
        return calendar
    }

    private fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!canScheduleExactAlarms()) {
                val intent = Intent(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    Uri.parse("package:${packageName}")
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        }
    }

    private fun scheduleNotification(notificationId: Int, message: String, startTime: String, endTime: String,
                                     userId: Int, idObat: Int, idObatPasien: Int, aturan: String,
                                     dosis: String, tanggalPemberian: String, namaObat: String, catatan: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Intent to show the notification at start time
        val notificationIntent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("message", "Hai! Jangan Lupa Minum Obat sesuai jadwal hari ini. Tetap sehat dan semangat ya!")
            putExtra("idObatPasien", idObatPasien)
            putExtra("userId", userId)
            putExtra("idObat", idObat)
            putExtra("waktuMulaiMinumObat", startTime)
            putExtra("waktuSelesaiMinumObat", endTime)
            putExtra("dosisObat", dosis)
            putExtra("aturan", aturan)
            putExtra("tanggalPemberian", tanggalPemberian)
            putExtra("namaObat", namaObat)
            putExtra("catatan", catatan)
        }

        val showPendingIntent = PendingIntent.getBroadcast(
            this,
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val startCalendar = parseTime(startTime)

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "MedsR::NotificationWakeLock"
        )
        wakeLock.acquire(5000) // Acquire the wake lock for 5 seconds

        // Schedule the notification to appear at start time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startCalendar.timeInMillis, showPendingIntent)
    }





    private fun fetchObatPasien(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mainRepository.getObatPasien(userId)
                val obatData = response.data

                obatData.forEach { data ->
                    // Only schedule a notification if sudahMinumObat is null (i.e., not "false" or "true")
                    if (data.sudahMinumObat.isNullOrEmpty()) {
                        // Log the scheduling for debugging purposes
                        Log.d("NotificationService", "Scheduling notification for ${data.namaObat}")

                        scheduleNotification(
                            notificationId = data.idObatPasien, // Ensure a unique ID if needed
                            message = data.namaObat,
                            startTime = data.waktuMulaiMinumObat,
                            endTime = data.waktuSelesaiMinumObat,
                            userId = data.idUser,
                            idObat = data.idObat,
                            idObatPasien = data.idObatPasien,
                            aturan = data.aturanPenggunaanObat,
                            dosis = data.dosisObat,
                            tanggalPemberian = data.tanggalDiberikan,
                            namaObat = data.namaObat,
                            catatan = data.catatan
                        )
                    } else {
                        Log.d("NotificationService", "No notification for ${data.namaObat}, sudahMinumObat is ${data.sudahMinumObat}")
                    }
                }

            } catch (e: Exception) {
                Log.e("Service", "Exception during fetch: ${e.message}", e)
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(message: String): Notification {
        return NotificationCompat.Builder(this, "CHANNEL_ID_FOR")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("MedsR Sedang Melakukan Magicnya \uD83E\uDE84")
            .setContentText(message)
            .setSound(null)
            .setAutoCancel(true)
            .build()
    }

    private fun createNotificationChannel() {
        val name = "Notification Service Channel"
        val descriptionText = "Channel for background notifications"
        val importance = NotificationManager.IMPORTANCE_LOW // Set to high importance for alarm-like behavior
        val channel = NotificationChannel("CHANNEL_ID_FOR", name, importance).apply {
            description = descriptionText
            setSound(null, null) // Disable sound
            enableVibration(false)
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}



