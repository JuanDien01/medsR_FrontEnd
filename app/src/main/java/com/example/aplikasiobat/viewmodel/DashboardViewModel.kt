package com.example.aplikasiobat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel:ViewModel() {

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> get() = _fullName

    fun setUserData(id: Int, name: String) {
        _userId.value = id
        _fullName.value = name
    }
}