package com.example.aplikasiobat.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.aplikasiobat.api.repository.MainRepository
import com.example.aplikasiobat.api.service.ApiClient
import com.example.aplikasiobat.api.service.ApiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CancelNotificationReceiver : BroadcastReceiver() {
    private lateinit var mainRepository: MainRepository

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val idObatPasien = intent.getIntExtra("idObatPasien", 0)
        val waktuSelesaiMinumObat = intent.getStringExtra("waktuSelesaiMinumObat") ?: ""

        val apiHelper = ApiHelper(ApiClient.instance)
        mainRepository = MainRepository(apiHelper)

        // Check if the time limit is exceeded
        if (isTimeLimitExceeded(waktuSelesaiMinumObat)) {
            // Cancel the notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)

            Log.d("CancelNotificationReceiver", "Cancelling notification with ID: $notificationId")
            updateStatus(idObatPasien)
        }
    }

    private fun isTimeLimitExceeded(waktuSelesai: String): Boolean {
        val currentTime = Calendar.getInstance().timeInMillis
        val endTime = parseTime(waktuSelesai).timeInMillis
        return currentTime > endTime
    }

    private fun parseTime(timeString: String): Calendar {
        val calendar = Calendar.getInstance()
        val timeParts = timeString.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())
        return calendar
    }

    private fun updateStatus(idObatPasien: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mainRepository.updateSudahMinum(idObatPasien, "false")
                if (response.status == "T") {
                    Log.d("CancelNotificationReceiver", "API call successful: ${response.data}")
                } else {
                    Log.e("CancelNotificationReceiver", "API call failed: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("CancelNotificationReceiver", "Exception during API call: ${e.message}")
            }
        }
    }
}

