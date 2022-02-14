package com.project.bosberas.home.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.project.bosberas.R
import com.project.bosberas.banner.BannerRepo
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.FragmentHomeBinding
import com.project.bosberas.profil.data.ProfilRepo
import com.project.bosberas.utils.Token
import javax.inject.Inject


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var dataLogin : SharedPreferences? = null
    private lateinit var tokenOuth: Token
    @Inject
    lateinit var bannerRepo: BannerRepo

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val banner_menu = binding.bannerSlide
        val imageList = ArrayList<SlideModel>()
        tokenOuth = Token(requireContext())
        bannerRepo.resBanner.observe(viewLifecycleOwner, Observer {
            it?.forEach {
                imageList.add(SlideModel(it.img,ScaleTypes.FIT))
            }
            banner_menu.setImageList(imageList)
        })
        bannerRepo.banner(requireContext())

        val name = dataLogin?.getString(
            getString(R.string.name),
            getString(R.string.default_value)
        )

        profilRepo.resProfil.observe(viewLifecycleOwner, Observer {
            binding.tvSaldo.text = "Saldo kamu : ${it.saldo[0].total_saldo}"
        })

        profilRepo.profil("Bearer ${tokenOuth.fetchAuthToken()}",requireContext())

        binding.apply {
            nameUser.text = "Hi,$name!"
            viewTimer.isCountDown = true
            viewTimer.base = SystemClock.elapsedRealtime() + 80000
            viewTimer.start()

        }
    }

}