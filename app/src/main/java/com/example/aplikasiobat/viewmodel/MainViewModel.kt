package com.example.aplikasiobat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.aplikasiobat.api.repository.MainRepository
import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.request.ForgotPasswordRequest
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.request.UpdateBiodataRequest
import com.example.aplikasiobat.api.request.UpdatePasswordRequest
import com.example.aplikasiobat.api.service.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val mainRepository: MainRepository):ViewModel() {

    fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.registerUserBiodata(registerUserBiodataRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun updateUserBiodata(updateBiodataRequest: UpdateBiodataRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.updateUserBiodata(updateBiodataRequest)))
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
    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.updatePassword(updatePasswordRequest)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun findUsername(username: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.findUsername(username)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.forgotPassword(forgotPasswordRequest)))
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
    fun getObatPasienByUserIdAndObatId(request:DetailObatPasienRequest) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getObatPasienByUserIdAndObatId(request)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
    fun getUserBiodataById(idPengguna: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getUserBiodataById(idPengguna)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }

    fun updateSudahMinum(idObatPasien:Int,status:String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.updateSudahMinum(idObatPasien,status)))
        }catch(exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
        }
    }
}