package com.example.aplikasiobat.api.service


import com.example.aplikasiobat.api.request.DetailObatPasienRequest
import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.response.dashboard.ObatPasien.GetObatPasienResponse
import com.example.aplikasiobat.api.response.dashboard.detailObatPasien.DetailObatPasienResponse
import com.example.aplikasiobat.api.response.dashboard.sudahMinum.UpdateSudahMinumResponse
import com.example.aplikasiobat.api.response.login.LoginResponse
import com.example.aplikasiobat.api.response.register.user.RegisterResponse
import com.example.aplikasiobat.api.response.register.userbiodata.RegisterUserBiodataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/user/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("api/userBiodata/addUserBiodata")
    suspend fun registerUserBiodata(@Body registerUserBiodataRequest: RegisterUserBiodataRequest): RegisterUserBiodataResponse

    @GET("api/obatPasien/getObatPasienById/{idUser}")
    suspend fun getObatPasienById(@Path("idUser") idUser:Int): GetObatPasienResponse

    @GET("api/obatPasien/getObatPasienByIdSudahDiminum/{idUser}")
    suspend fun getObatPasienByIdSudahDiminum(@Path("idUser") idUser:Int): GetObatPasienResponse

    @GET("api/obatPasien/getObatPasienByIdBelumDiminum/{idUser}")
    suspend fun getObatPasienByIdBelumDiminum(@Path("idUser") idUser:Int): GetObatPasienResponse

    @POST("api/obatPasien/getObatPasienByUserIdAndObatId")
    suspend fun getObatPasienByUserIdAndObatId(@Body request: DetailObatPasienRequest): DetailObatPasienResponse

    @PUT("api/obatPasien/updateSudahMinum/{idObatPasien}")
    suspend fun updateSudahMinum(@Path("idObatPasien") idObatPasien: Int): UpdateSudahMinumResponse
}