package com.project.bosberas.store.data

class ProductModel(
     val id : String ,
     val branch : String ,
     val images : ArrayList<String> ,
     val title : String ,
     val product_category : ProductCategory ,
     val price : Int ,
     val rate : Int ,
     val sales : Int ,
     val stock : String ,
     val description : String ,
     val active : String ,
     val discount_percent : Int ,
     val grand_price : Int ,
     val ngebul_detail : NgebulDetail ,
) {}

class NgebulDetail(
    val id : Int ,
    val category_id : CategoryId,
    val discount_percent : String  ,
    val discount_price : Int ,
    val active : String ,
    val limit : String ,
) {}

class CategoryId(
    val id : String ,
    val started_at : String ,
    val ended_at : String ,
    val category : String ,
    val active : String ,

) {}

class ProductCategory(
    val id : String ,
    val category : String,
    val parent : String = "" ,
    val sub_parent : String = "",
    val active : String

) {}

class ResponseProduct(
    val msgCode : String,
    val msgStatus : String,
    val msg : String,
    val data : ArrayList<ProductModel>
)
{}

