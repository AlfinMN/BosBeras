package com.project.bosberas.banner

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bosberas.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class BannerRepo @Inject constructor(val bannerAPI: BannerAPI) {
    var resBanner = MutableLiveData<List<BannerModel>>()

    fun banner(context:Context){
        bannerAPI.banner().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode == "00"){
                        val resData = res.data
                        val gson = Gson()
                        val listObj : Type = object : TypeToken<List<BannerModel>>() {}.type
                        val resValue : List<BannerModel> = gson.fromJson(gson.toJson(resData),listObj)
                        resBanner.value = resValue

                    } else {
                        Toast.makeText(
                            context,
                            "Banner not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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