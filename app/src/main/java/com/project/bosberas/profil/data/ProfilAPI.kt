package com.project.bosberas.profil.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfilAPI {

    @GET("User/profile")
    fun profil(@Header("Authorization")token : String) : Call<ResponseProfile>
}