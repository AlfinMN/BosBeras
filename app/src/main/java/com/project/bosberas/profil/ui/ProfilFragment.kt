package com.project.bosberas.profil.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.project.bosberas.R
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.FragmentProfilBinding
import com.project.bosberas.profil.data.ProfilRepo
import com.project.bosberas.utils.Token
import javax.inject.Inject


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    var dataLogin : SharedPreferences? = null
    private lateinit var tokenOuth: Token
    @Inject
    lateinit var profilRepo: ProfilRepo
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
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoad(true)
        tokenOuth = Token(requireContext())
        val name = dataLogin?.getString(
            getString(R.string.name),
            getString(R.string.default_value)
        )
        binding.apply {
            username.text = name
            profilRepo.resProfil.observe(viewLifecycleOwner, Observer {
                if (it!=null){
                    saldoUser.text = "Rp.  ${it.saldo[0].total_saldo}"
                    addressUser.text = "${it.address[0].city}, ${it.address[0].province}"
                    Glide
                        .with(this@ProfilFragment)
                        .load(it.profile)
                        .fitCenter()
                        .into(profilImage);
                    showLoad(false)
                } else {
                    println("notfound")
                }
            })
            profilRepo.profil("Bearer ${tokenOuth.fetchAuthToken()}",requireContext())
        }
    }
    private fun showLoad(state : Boolean){
        if (state){
            binding.laoding.visibility = View.VISIBLE
        }else{
            binding.laoding.visibility = View.GONE
        }
    }

}