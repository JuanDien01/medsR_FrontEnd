package com.example.aplikasiobat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.aplikasiobat.api.data.MainRepository
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.service.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher


class MainViewModel(private val mainRepository: MainRepository):ViewModel() {

    fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.registerUserBiodata(registerUserBiodataRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun registerUser(registerRequest: RegisterRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.registerUser(registerRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun login(loginRequest: LoginRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.login(loginRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun getObatPasien(idUser:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getObatPasien(idUser)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun getObatPasienSudahDiminum(idUser:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getObatPasienSudahDiminum(idUser)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun getObatPasienBelumDiminum(idUser:Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getObatPasienBelumDiminum(idUser)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
}