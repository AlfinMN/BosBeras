package com.project.bosberas.store.data

import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {
    @GET("Product")
    fun product(): Call<ResponseProduct>

}