package com.project.bosberas.cart.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bosberas.utils.ResponseAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class CartRepo @Inject constructor(val cartAPI: CartAPI){
    var resCart = MutableLiveData<List<CartModel>>()
    var resInsert = MutableLiveData<List<CartInsert>>()


    fun getCart(token : String,context: Context){
        cartAPI.getCart(token).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode == "00"){
                     val resData = res.data
                     val gson = Gson()
                        val listObj : Type = object : TypeToken<List<CartModel>>() {}.type
                        val output : List<CartModel> = gson.fromJson(gson.toJson(resData),listObj)
                        resCart.value = output
                    } else {
                        Toast.makeText(
                            context,
                            "Something Wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }   else {
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

    fun postCart(token: String,cartInsert: CartInsert,context: Context){
        val id = convert(cartInsert.product_id.toString())
        val total = convert(cartInsert.total.toString())
        cartAPI.postCart(token,id,total).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode == "00"){
                        val resData = res.data
                        val gson = Gson()
                        val listObj : Type = object : TypeToken<List<CartInsert>>() {}.type
                        val output : List<CartInsert> = gson.fromJson(gson.toJson(resData),listObj)
                        resInsert.value = output
                    }
                }   else {
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

    private fun convert (string:String) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(),string)
    }
}