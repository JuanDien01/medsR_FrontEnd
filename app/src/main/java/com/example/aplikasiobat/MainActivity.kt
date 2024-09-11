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

        // Navigate based on user login status
        if (dashboardViewModel.isUserLoggedIn()) {
            // Navigate to DashboardFragment if user is already logged in
            navController.navigate(R.id.dashboardFragment)
//            dashboardViewModel.clearUserData()
        } else {
            // Show WelcomeFragment if user is not logged in
            navController.navigate(R.id.welcomeFragment)
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