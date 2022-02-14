package com.project.bosberas.profil.data

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bosberas.R
import com.project.bosberas.auth.ui.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class ProfilRepo @Inject constructor(val profilAPI: ProfilAPI){
    var resProfil = MutableLiveData<ProfileModel>()

    fun profil(token: String,context: Context){
        profilAPI.profil(token).enqueue(object :Callback<ResponseProfile>{
            override fun onResponse(
                call: Call<ResponseProfile>,
                response: Response<ResponseProfile>
            ) {
                val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode == "00"){
                        val resData = res.data
                        val gson = Gson()
                        val listObj : Type = object : TypeToken<ProfileModel>() {}.type
                        val resValue : ProfileModel = gson.fromJson(gson.toJson(resData),listObj)
                        resProfil.value = resValue
                    } else if(res?.msgCode == "88") {
                        val settings =
                            context.getSharedPreferences(context.getString(R.string.sp), Context.MODE_PRIVATE)
                        settings.edit().clear().apply()
//                    codeError.value = response.code()
                        Toast.makeText(
                            context,
                            "Sesi anda telah habis , mohon login ulang",
                            Toast.LENGTH_SHORT
                        ).show()
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    } else {
                        Toast.makeText(
                            context,
                            "data profil not found",
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

            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })
    }
}