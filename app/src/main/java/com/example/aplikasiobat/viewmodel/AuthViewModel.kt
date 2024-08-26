package com.example.aplikasiobat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasiobat.api.data.Biodata

class AuthViewModel(): ViewModel() {
    private val _biodata = MutableLiveData<Biodata?>()
    val biodata: LiveData<Biodata?> get() = _biodata

    fun setData(biodata: Biodata) {
        _biodata.value = biodata
    }
}