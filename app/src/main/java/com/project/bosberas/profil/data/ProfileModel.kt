package com.project.bosberas.profil.data

class ProfileModel (
    val id : Int,
    val name : String ,
    val email : String,
    val email_verified : String ,
    val phone : String,
    val profile : String,
    val gender : String,
    val birth : String,
    val type : String,
    val role_id : String,
    val baranch : String,
    val address : ArrayList<AdressModel>,
    val saldo : ArrayList<SaldoModel>,
    val referal_link : ArrayList<ReferalModel>
        ){}

class ReferalModel(
    val id : Int,
    val user_id : String,
    val code : String,
    val active : String,
) {}

class SaldoModel(
    val id : Int,
    val user_id : String,
    val saldo_topup : Int,
    val saldo_referal : Int,
    val total_saldo : Int,
) {}

class AdressModel(
    val id : Int,
    val user_id : String,
    val title : String,
    val name : String,
    val phone : String,
    val province : String,
    val city : String,
    val district : String,
    val postal_code : String,
    val address : String,
    val is_default : String,
    val active : String,
    val latitude : String,
    val longitude : String,

) {}

class ResponseProfile(
    var msgCode : String ="",
    var msgStatus : String ="",
    var msg : String ="",
    var data : ProfileModel
) {}
