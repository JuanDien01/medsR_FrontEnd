package com.example.aplikasiobat

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.response.dashboard.ObatPasien.Data
import com.example.aplikasiobat.viewmodel.DashboardViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var requestLauncher: ActivityResultLauncher<String>

    private val dashboardViewModel by lazy {
        ViewModelProvider(this)[DashboardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController


        requestLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                showToast("Notification Permission Granted")
            } else {
                showToast("Notification Permission Denied")
            }
        }

        requestNotificationPermission()

        // Check if the activity was launched from a notification click
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
        if (idObat != 0 && userId != 0) {
            val dataDetail = Data(
                idObatPasien = idObatPasien, // Use appropriate value if available
                idObat = idObat,
                idUser = userId,
                dosisObat = dosisObat, // Default or fetched value
                namaObat = namaObat, // Default or fetched value
                aturanPenggunaanObat = aturan, // Default or fetched value
                waktuMulaiMinumObat = startTime, // Default or fetched value
                durasi = "", // Default or fetched value
                frekuensi = "", // Default or fetched value
                waktuSelesaiMinumObat = endTime, // Default or fetched value
                tanggalDiberikan = tanggalPemberian, // Default or fetched value
                catatan = catatanObat, // Default or fetched value
                sudahMinumObat = null // Default or fetched value
            )
            val action = MainNavDirections.actionGlobalDetailReminderFragment(dataDetail)
            navController.navigate(action)
        } else {
            // Navigate based on user login status
            if (dashboardViewModel.isUserLoggedIn()) {
                // Navigate to DashboardFragment if the user is already logged in
                navController.navigate(R.id.dashboardFragment)
            } else {
                // Show WelcomeFragment if the user is not logged in
                navController.navigate(R.id.welcomeFragment)
            }
        }
    }



    private fun requestNotificationPermission() {
        // Android 13 and above
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}