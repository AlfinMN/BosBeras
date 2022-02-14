package com.project.bosberas.order.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.project.bosberas.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class OrderRepo @Inject constructor(val orderAPI: OrderAPI) {
    var resOrder = MutableLiveData<ResponseAPI>()

    fun postOrder(token : String , insertOrderModel: InsertOrderModel,context: Context){
        orderAPI.postOrder(token, insertOrderModel).enqueue(object  : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                  resOrder.value = res
                }else {
                    Toast.makeText(
                        context,
                        "Failed request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {

                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })
    }
}