package com.project.bosberas.store.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.bosberas.R
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.FragmentStoreBinding
import com.project.bosberas.store.data.ProductAdapter
import com.project.bosberas.store.data.ProductRepo
import javax.inject.Inject


class StoreFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    var dataLogin : SharedPreferences? = null
    @Inject
    lateinit var productRepo: ProductRepo
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as BosBeras).applicationComponent.inject(this)
        dataLogin = activity?.getSharedPreferences(
            getString(R.string.sp),
            Context.MODE_PRIVATE
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoad(true)
        binding.apply {

            rvProduct.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            productRepo.resProduct.observe(viewLifecycleOwner, Observer {
                if (it!=null){
                    productAdapter = ProductAdapter(it,requireActivity())
                    rvProduct.adapter = productAdapter
                    showLoad(false)
                } else {
                    Toast.makeText(
                        context,
                        "Produk tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
            productRepo.product(requireContext())

            btnBerasPremium.setOnClickListener(this@StoreFragment)
            btnBerasPandanwangi.setOnClickListener(this@StoreFragment)
            btnBerasRojolele.setOnClickListener(this@StoreFragment)
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
        when (v) {
            binding.btnBerasPremium ->{
                binding.btnBerasPremium.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn2))
                binding.btnBerasPremium.setTextColor(Color.WHITE)
                binding.btnBerasPandanwangi.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasPandanwangi.setTextColor(resources.getColor(R.color.colortext6))
                binding.btnBerasRojolele.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasRojolele.setTextColor(resources.getColor(R.color.colortext6))
            }
            binding.btnBerasPandanwangi->{
                binding.btnBerasPandanwangi.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn2))
                binding.btnBerasPandanwangi.setTextColor(Color.WHITE)
                binding.btnBerasPremium.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasPremium.setTextColor(resources.getColor(R.color.colortext6))
                binding.btnBerasRojolele.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasRojolele.setTextColor(resources.getColor(R.color.colortext6))
            }
            binding.btnBerasRojolele -> {
                binding.btnBerasRojolele.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn2))
                binding.btnBerasRojolele.setTextColor(Color.WHITE)
                binding.btnBerasPandanwangi.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasPandanwangi.setTextColor(resources.getColor(R.color.colortext6))
                binding.btnBerasPremium.setBackgroundDrawable( ContextCompat.getDrawable(requireContext(), R.drawable.bg_btn3))
                binding.btnBerasPremium.setTextColor(resources.getColor(R.color.colortext6))
            }
        }
    }
}