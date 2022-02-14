package com.project.bosberas.auth.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.bosberas.utils.ResponseAPI
import com.project.bosberas.utils.Token
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class AuthRepo @Inject constructor(val authAPI: AuthAPI){
    private lateinit var tokenOuth: Token
    var authResponse = MutableLiveData<AuthModel>()
    var resAPI = MutableLiveData<ResponseAPI>()


    fun login(loginModel: LoginModel,context: Context){
        tokenOuth = Token(context)

        val email = convert(loginModel.email)
        val password = convert(loginModel.password)

        authAPI.login(email, password).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
                    if (res?.msgCode   == "00"){
                        val resData = res.data
                        val gson = Gson()
                        val listObj : Type = object : TypeToken<AuthModel>() {}.type
                        val resValue : AuthModel = gson.fromJson(gson.toJson(resData),listObj)
                        tokenOuth.saveAuthToken(resValue.token)
                        authResponse.value = resValue
                        resAPI.value = res
                    } else {
                        resAPI.value = res
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed request",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun convert (string:String) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(),string)
    }
}