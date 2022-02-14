package com.project.bosberas.cart.data

import com.project.bosberas.utils.ResponseAPI
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CartAPI {
    @GET("User/Cart")
    fun getCart(@Header("Authorization")token : String) : Call<ResponseAPI>


    @Multipart
    @POST("User/Cart")
    fun postCart(@Header("Authorization")token : String,
                 @Part("product_id")product_id : RequestBody,
                 @Part("total")total : RequestBody
    ) : Call<ResponseAPI>


}