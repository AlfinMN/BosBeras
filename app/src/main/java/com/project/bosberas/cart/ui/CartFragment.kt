package com.project.bosberas.cart.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.bosberas.R
import com.project.bosberas.cart.data.CartAdapter
import com.project.bosberas.cart.data.CartRepo
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.FragmentCartBinding
import com.project.bosberas.home.ui.HomeActivity
import com.project.bosberas.order.data.InsertOrderModel
import com.project.bosberas.order.data.OrderRepo
import com.project.bosberas.utils.Token
import javax.inject.Inject


class CartFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenOuth: Token
    private var cartId : ArrayList<Int> = ArrayList()

    @Inject
    lateinit var cartRepo: CartRepo
    lateinit var cartAdapter: CartAdapter

    @Inject
    lateinit var orderRepo: OrderRepo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as BosBeras).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoad(true)
        tokenOuth = Token(requireContext())
        binding.apply {
btnPay.setOnClickListener(this@CartFragment)
            rvCart.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            cartRepo.resCart.observe(viewLifecycleOwner, Observer {
                if (it!=null){
                    it.forEach {
                        cartId.add(it.id)
                    }
                    println("ID $cartId")
                    cartAdapter = CartAdapter(it)
                    rvCart.adapter = cartAdapter
                    showLoad(false)
                }
            })
            cartRepo.getCart("Bearer ${tokenOuth.fetchAuthToken()}",requireContext())


        }
    }
    private fun showLoad(state : Boolean){
        if (state){
            binding.laoding.visibility = View.VISIBLE
        }else{
            binding.laoding.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v){
            binding.btnPay -> {

                val insertOrderModel = InsertOrderModel(
                    cart_id = cartId
                )

                orderRepo.resOrder.observe(viewLifecycleOwner, Observer {
                    if (it.msgCode == "00"){
                        startActivity(Intent(this@CartFragment.context,HomeActivity::class.java))
                        Toast.makeText(
                            context,
                            it.msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                orderRepo.postOrder("Bearer ${tokenOuth.fetchAuthToken()}",insertOrderModel,requireContext())
            }
        }
    }

}