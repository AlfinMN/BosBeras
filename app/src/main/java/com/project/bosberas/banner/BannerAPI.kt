package com.project.bosberas.banner

import com.project.bosberas.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface BannerAPI {
    @GET("banner")
    fun banner(): Call<ResponseAPI>
}