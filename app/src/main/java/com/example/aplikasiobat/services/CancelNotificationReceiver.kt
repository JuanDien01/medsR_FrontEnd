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

class CancelNotificationReceiver : BroadcastReceiver() {
    private lateinit var mainRepository: MainRepository
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val idObatPasien = intent.getIntExtra("idObatPasien", 0)
        val apiHelper = ApiHelper(ApiClient.instance)
        mainRepository = MainRepository(apiHelper)

        // Cancel the notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
        Log.d("CancelNotificationReceiver", "Cancelling notification with ID: $notificationId")
        updateStatus(idObatPasien)
    }

    private fun updateStatus(idUser:Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {
               val response = mainRepository.updateSudahMinum(idUser,"false")
                if (response.status == "T") {
                    Log.d("CancelNotificationReceiver", "API call successful: ${response.data}")
                } else {
                    Log.e("CancelNotificationReceiver", "API call failed: ${response.message}")
                }
            }catch (e: Exception){
                Log.e("CancelNotificationReceiver", "Exception during API call: ${e.message}")
            }
        }
    }
}
