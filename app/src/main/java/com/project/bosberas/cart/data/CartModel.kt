package com.project.bosberas.cart.data

import com.project.bosberas.store.data.ProductCategory

class CartModel(
    val id : Int ,
    val user_id : String ,
    val product_id : String ,
    val total : Int,
    val active : String,
    val amount : Int ,
    val detail_product : DetailProduct,

) {}

class CartInsert(
    val product_id: Int,
    val total : Int
){

}

class DetailProduct(
    val id : Int ,
    val branch : String ,
    val images : ArrayList<String>,
    val title : String ,
    val product_category : ProductCategory,
    val product_id: String,
    val discount_percent : Int,
    val price : Int,
    val grand_price : Int,
    val rate : Int ,
    val sales : String ,
    val stock : String ,
){}
