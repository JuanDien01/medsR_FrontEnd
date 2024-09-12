package com.example.aplikasiobat.api.service

import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.request.ForgotPasswordRequest
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.request.UpdateBiodataRequest
import com.example.aplikasiobat.api.request.UpdatePasswordRequest

class ApiHelper(private val apiService: ApiService) {
    suspend fun registerUser(registerRequest: RegisterRequest)= apiService.registerUser(registerRequest)
    suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest)= apiService.updatePassword(updatePasswordRequest)
    suspend fun findUsername(username:String)= apiService.findUsername(username)
    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) = apiService.forgotPassword(forgotPasswordRequest)
    suspend fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest)= apiService.registerUserBiodata(registerUserBiodataRequest)
    suspend fun getUserBiodataById(idPengguna:Int) = apiService.getUserBiodataById(idPengguna)
    suspend fun updateUserBiodata(userBiodataRequest: UpdateBiodataRequest) = apiService.updateUserBiodata(userBiodataRequest)
    suspend fun login(request: LoginRequest)= apiService.login(request)
    suspend fun getObatPasien(idUser:Int)= apiService.getObatPasienById(idUser)
    suspend fun getObatPasinSudahDiminum(idUser:Int)= apiService.getObatPasienByIdSudahDiminum(idUser)
    suspend fun getObatPasienBelumDiminum(idUser:Int)= apiService.getObatPasienByIdBelumDiminum(idUser)
    suspend fun getDetailObatPasien(request:DetailObatPasienRequest)= apiService.getObatPasienByUserIdAndObatId(request)
    suspend fun updateSudahMinum(idObatPasien:Int,status:String)= apiService.updateSudahMinum(idObatPasien,status)


}