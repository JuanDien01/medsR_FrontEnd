package com.example.aplikasiobat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.aplikasiobat.api.data.MainRepository
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.service.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher


class MainViewModel(private val mainRepository: MainRepository):ViewModel() {
    fun registerUser(registerRequest: RegisterRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.registerUser(registerRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
}