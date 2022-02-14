package com.project.bosberas.order.data

import com.project.bosberas.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderAPI {
    @POST("User/Order")
    fun postOrder(@Header("Authorization")token : String,
                  @Body insertOrderModel : InsertOrderModel):Call<ResponseAPI>
}