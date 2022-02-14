package com.project.bosberas.store.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class ProductRepo @Inject constructor(val productAPI: ProductAPI) {
    var resProduct = MutableLiveData<List<ProductModel>>()

    fun product(context: Context){
        productAPI.product().enqueue(object : Callback<ResponseProduct>{
            override fun onResponse(
                call: Call<ResponseProduct>,
                response: Response<ResponseProduct>
            ) {
                val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode == "00"){
                        val resData = res.data
                        val gson = Gson()
                        val listObj : Type = object : TypeToken<List<ProductModel>>() {}.type
                        val output : List<ProductModel> = gson.fromJson(gson.toJson(resData),listObj)
                        resProduct.value = output
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<ResponseProduct>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }
        })
    }
}