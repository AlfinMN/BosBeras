package com.project.bosberas.cart.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project.bosberas.databinding.ItemCartBinding

class CartAdapter(val list : List<CartModel>) : RecyclerView.Adapter<CartVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartVH((view))
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.data(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }
}

class CartVH (val binding : ItemCartBinding): RecyclerView.ViewHolder(binding.root) {
    fun data(cartModel: CartModel){
        binding.apply {
            Glide.with(itemView)
                .load(cartModel.detail_product.images[0])
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(imgCart)

            nameProductCart.text = cartModel.detail_product.title
            total.text = "${cartModel.total}X"
            price.text = cartModel.detail_product.grand_price.toString()
        }
    }

}
