package com.project.bosberas.store.data

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.project.bosberas.R
import com.project.bosberas.store.ui.ProductDetail

class ProductAdapter (val listProduct : List<ProductModel>,var activity : Activity) : RecyclerView.Adapter<ProductVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
       val list = listProduct[position]
        holder.productName.text = list.title
        holder.productCategory.text = list.product_category.category
        holder.priceDiscount.text = "Rp. ${list.grand_price}"
        holder.productPrice.text = "Rp. ${list.price}"
        Glide
            .with(this.activity)
            .load(list.images[0])
            .fitCenter()
            .into(holder.productImage);
        holder.card.setOnClickListener {
            val toDetail = Intent(it.context,ProductDetail::class.java)
            toDetail.putExtra(ProductDetail.EXTRA_PRODUCTNAME,list.title)
            toDetail.putExtra(ProductDetail.EXTRA_PRODUCTID,list.id)
            toDetail.putExtra(ProductDetail.EXTRA_PRODUCTIMAGE,list.images[0])
            toDetail.putExtra(ProductDetail.EXTRA_RATE,list.rate)
            toDetail.putExtra(ProductDetail.EXTRA_SALE,list.sales)
            toDetail.putExtra(ProductDetail.EXTRA_GRANDPRICE,list.grand_price)
            toDetail.putExtra(ProductDetail.EXTRA_PRICE,list.price)
            toDetail.putExtra(ProductDetail.EXTRA_DESCRIPTION,list.description)

            it.context.startActivity(toDetail)
        }

    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}

class ProductVH (v: View) : RecyclerView.ViewHolder(v) {
    val productName = v.findViewById<TextView>(R.id.nama_product)
    val productImage = v.findViewById<ImageView>(R.id.img_product)
    var productCategory = v.findViewById<TextView>(R.id.category_product)
    var productPrice = v.findViewById<TextView>(R.id.price_product)
    var priceDiscount = v.findViewById<TextView>(R.id.price_discount)
    var card = v.findViewById<CardView>(R.id.cardv)

}
