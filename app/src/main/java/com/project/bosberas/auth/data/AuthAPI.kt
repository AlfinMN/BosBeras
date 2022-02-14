package com.project.bosberas.auth.data

import com.project.bosberas.utils.ResponseAPI
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthAPI {
    @Multipart
    @POST("Auth/login")
    fun login(@Part("email")email : RequestBody,
              @Part("password")password : RequestBody
    ): Call<ResponseAPI>
}