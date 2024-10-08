package com.example.aplikasiobat.api.repository

import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.request.ForgotPasswordRequest
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.request.UpdateBiodataRequest
import com.example.aplikasiobat.api.request.UpdatePasswordRequest
import com.example.aplikasiobat.api.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun registerUser(registerRequest: RegisterRequest) = apiHelper.registerUser(registerRequest)
    suspend fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest) = apiHelper.registerUserBiodata(registerUserBiodataRequest)
    suspend fun getUserBiodataById(idPengguna:Int) = apiHelper.getUserBiodataById(idPengguna)
    suspend fun updateUserBiodata(userBiodataRequest: UpdateBiodataRequest) = apiHelper.updateUserBiodata(userBiodataRequest)
    suspend fun login(request: LoginRequest) = apiHelper.login(request)
    suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) = apiHelper.updatePassword(updatePasswordRequest)
    suspend fun findUsername(username: String) = apiHelper.findUsername(username)
    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) = apiHelper.forgotPassword(forgotPasswordRequest)
    suspend fun getObatPasien(idUser:Int) = apiHelper.getObatPasien(idUser)
    suspend fun getObatPasienSudahDiminum(idUser:Int) = apiHelper.getObatPasinSudahDiminum(idUser)
    suspend fun getObatPasienBelumDiminum(idUser:Int) = apiHelper.getObatPasienBelumDiminum(idUser)
    suspend fun getObatPasienByUserIdAndObatId(request:DetailObatPasienRequest) = apiHelper.getDetailObatPasien(request)
    suspend fun updateSudahMinum(idObatPasien:Int,status:String) = apiHelper.updateSudahMinum(idObatPasien,status)
}