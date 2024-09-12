package com.example.aplikasiobat

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

        // 1. Navigate based on user login status first
        if (!dashboardViewModel.isUserLoggedIn()) {
            // Show WelcomeFragment if the user is not logged in
            navController.navigate(R.id.welcomeFragment)
            return // Exit early since user is not logged in
        }

        // 2. If user is logged in, check if the activity was launched from a notification click
        val userId = intent.getIntExtra("userId", 0)
        val idObat = intent.getIntExtra("idObat", 0)

        if (idObat != 0 && userId != 0) {

            val action = MainNavDirections.actionGlobalDashboardFragment()

            navController.navigate(action)
        } else {
            // If user is logged in but no notification data is available, navigate to DashboardFragment
            navController.navigate(R.id.dashboardFragment)
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