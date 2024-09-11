package com.example.aplikasiobat.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> get() = _fullName

    private val _idPengguna = MutableLiveData<Int>()
    val idPengguna: LiveData<Int> get() = _idPengguna

    init {
        // Load saved data from SharedPreferences when ViewModel is initialized
        _userId.value = sharedPreferences.getInt("userId", 0) // Default to 0 if not found
        _fullName.value = sharedPreferences.getString("fullName", "Tamu")
        _idPengguna.value = sharedPreferences.getInt("idPengguna", 0)// Default to "Tamu" if not found
    }

    fun setUserData(id: Int, name: String, idPengguna : Int) {
        // Save the data in ViewModel LiveData
        _userId.value = id
        _fullName.value = name
        _idPengguna.value = idPengguna

        // Store the data in SharedPreferences
        sharedPreferences.edit().apply {
            putInt("userId", id)
            putString("fullName", name)
            putInt("idPengguna", idPengguna)
            apply() // Apply changes asynchronously
        }
    }

    fun isUserLoggedIn(): Boolean {
        // Check if user data exists in SharedPreferences
        return _userId.value != 0
    }

    fun clearUserData() {
        // Clear user data from SharedPreferences
        sharedPreferences.edit().apply {
            remove("userId")
            remove("fullName")
            remove("idPengguna")
            apply() // Apply changes asynchronously
        }
        // Clear LiveData
        _userId.value = 0
        _fullName.value = "Tamu"
        _idPengguna.value = 0
    }

}
