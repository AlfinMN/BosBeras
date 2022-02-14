package com.project.bosberas.store.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.project.bosberas.R
import com.project.bosberas.cart.data.CartInsert
import com.project.bosberas.cart.data.CartRepo
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.ActivityHomeBinding
import com.project.bosberas.databinding.ActivityProductDetailBinding
import com.project.bosberas.home.ui.HomeActivity
import com.project.bosberas.utils.Token
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ProductDetail : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    var num : Int = 1
    var productId : Int = 0
    var productName : String? = null
    private lateinit var tokenOuth: Token
    @Inject
    lateinit var cartRepo: CartRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (this.applicationContext as BosBeras).applicationComponent.inject(this)

        tokenOuth = Token(this)
        productName = intent.getStringExtra(EXTRA_PRODUCTNAME)
        val productImage = intent.getStringExtra(EXTRA_PRODUCTIMAGE)
         productId = intent.getIntExtra(EXTRA_PRODUCTID,1)
        val productRate = intent.getIntExtra(EXTRA_RATE,1)
        val productSale = intent.getIntExtra(EXTRA_SALE,1)
        val productGrandprice = intent.getIntExtra(EXTRA_GRANDPRICE,1)
        val productPrice = intent.getIntExtra(EXTRA_PRICE,1)
        val productDesc = intent.getStringExtra(EXTRA_DESCRIPTION)
        binding.apply {
            btnCart.setOnClickListener(this@ProductDetail)
            btnInc.setOnClickListener(this@ProductDetail)
            btnDec.setOnClickListener(this@ProductDetail)
        nameProduct.text = productName
            Glide
                .with(this@ProductDetail)
                .load(productImage)
                .fitCenter()
                .into(imgProduct);
            produkTerjual.text = "terjual $productSale"
            priceProduct.setText(formatRupiah.format(productGrandprice))
            priceDiscount.setText(formatRupiah.format(productPrice))
//            expandTextView.setText(productDesc)
            description.setText(productDesc)
        }
    }

    companion object {
        const val EXTRA_PRODUCTID = "productid"
        const val EXTRA_PRODUCTNAME = "productname"
        const val EXTRA_PRODUCTIMAGE = "productimage"
        const val EXTRA_RATE = "productrate"
        const val EXTRA_SALE = "productsale"
        const val EXTRA_GRANDPRICE = "productgrandprice"
        const val EXTRA_PRICE = "productprice"
        const val EXTRA_DESCRIPTION = "productdesc"
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnInc ->{
                num++
                binding.total.text = num.toString()
            }
            binding.btnDec ->{
                if (num <= 1 ){
                    Toast.makeText(
                        this,
                        "Minimum order 1 ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    num--
                    binding.total.text = num.toString()
                }
            }
            binding.btnCart -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Masukkan ke keranjang ? ")
                builder.setMessage("Produk : ${productName}\n"+
                        "Total : ${binding.total.text}\n")

                builder.setPositiveButton("Ya") { dialog, which ->

                    val cartInsert = CartInsert(
                        product_id = productId,
                        total = "${binding.total.text}".toInt()
                    )
             cartRepo.resInsert.observe(this, androidx.lifecycle.Observer {

                 if (it!=null){
                     Toast.makeText(
                         this,
                         "Dimasukan ke keranjang",
                         Toast.LENGTH_SHORT
                     ).show()
                     startActivity(Intent(this,HomeActivity::class.java))
                 }
             })
                    cartRepo.postCart("Bearer ${tokenOuth.fetchAuthToken()}",cartInsert,this)
                }

                builder.setNegativeButton("Tidak"){dialog,which ->

                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }
}