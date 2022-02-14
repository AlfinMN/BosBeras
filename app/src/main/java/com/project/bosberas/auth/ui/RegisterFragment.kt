package com.project.bosberas.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.project.bosberas.R
import com.project.bosberas.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            toLogin.setOnClickListener(this@RegisterFragment)
        }
    }

    override fun onClick(v: View?) {
      when (v){
          binding?.toLogin ->{
              view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
              navController = view?.let { Navigation.findNavController(it) }!!
          }
      }
    }

}