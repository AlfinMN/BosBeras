package com.project.bosberas.auth.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.project.bosberas.R
import com.project.bosberas.auth.data.AuthRepo
import com.project.bosberas.auth.data.LoginModel
import com.project.bosberas.config.BosBeras
import com.project.bosberas.databinding.FragmentLoginBinding
import javax.inject.Inject


class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    lateinit var navController : NavController

    var dataLogin : SharedPreferences? = null

    @Inject
    lateinit var authRepo: AuthRepo

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            toDaftar.setOnClickListener(this@LoginFragment)
            btnLogin.setOnClickListener(this@LoginFragment)
            btnLogin.isEnabled = false

            authEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun afterTextChanged(p0: Editable?) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(authEmail.text.toString()).matches())
                        btnLogin.isEnabled = true
                    else{
                        btnLogin.isEnabled = false
                        authEmail.setError("Email/NoTlp tidak valid!")
                    }
                }
                })
        }

        if (dataLogin?.contains(getString(R.string.id))!! && dataLogin?.contains(getString(R.string.login_method_key))!! ){
            view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
        }
        navController = Navigation.findNavController(view)
        authRepo.resAPI.observe(viewLifecycleOwner, Observer {
            if (it.msgCode != "00"){
                Toast.makeText(
                    this.context,
                    "Email/NoTlp dan kata sandi tidak cocok ! ",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                    this.context,
                    "Login success",
                    Toast.LENGTH_SHORT
                ).show()
                authRepo.authResponse.observe(viewLifecycleOwner, Observer {
                   with(dataLogin?.edit()){
                       this?.putString(getString(R.string.id),it.id)
                       this?.putString(getString(R.string.name),it.name)
                       this?.putString(getString(R.string.email),it.email)
                       this?.putString(getString(R.string.email_verified_at),it.email_verified_at)
                       this?.putString(getString(R.string.phone),it.phone)
                       this?.putString(getString(R.string.profile),it.profile)
                       this?.putString(getString(R.string.gender),it.gender)
                       this?.putString(getString(R.string.birth),it.birth)
                       this?.putString(getString(R.string.type),it.type)
                       this?.putString(getString(R.string.role_id),it.role_id)
                       this?.putString(getString(R.string.baranch),it.baranch)
                       this?.putString(getString(R.string.login_method_key),"loginData")
                       this?.commit()
                   }
                    navController.navigate(R.id.action_loginFragment_to_homeActivity)
                })
            }
        })

    }

    override fun onClick(v: View?) {
       when (v){
           binding?.toDaftar ->{
               view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
               navController = view?.let { Navigation.findNavController(it) }!!
           }
           binding?.btnLogin -> {
               val loginModel = LoginModel(
                   email = binding?.authEmail?.text.toString(),
                   password = binding?.authPassword?.text.toString()
               )

               val emailErr : String = binding?.authEmail?.text.toString()

               if (emailErr.trim().isEmpty()){
                   binding?.authEmail?.error   = "Diperlukan"
                   Toast.makeText(this.requireContext(), "Mohon isi Email/Notlp", Toast.LENGTH_SHORT).show()
               } else if (binding?.authPassword?.text.toString().trim().isEmpty()){
//                   binding?.authPassword?.error  = "Diperlukan"
                   Toast.makeText(this.requireContext(), "Mohon isi Password", Toast.LENGTH_SHORT).show()

               }
               else if (binding?.authPassword?.length()!! < 6){
                   Toast.makeText(this.requireContext(), "Min 6 karakter", Toast.LENGTH_SHORT).show()
//                   binding!!.authPassword.error = "Min 6 karakter"
               }
               else {
//                   println("EMAIL ${loginModel.email}")
//                   println("Password ${loginModel.password}")
                  authRepo.login(loginModel,requireContext())
               }
//               view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
//               navController = view?.let { Navigation.findNavController(it) }!!

           }
       }
    }

}