package com.project.bosberas.config

import com.project.bosberas.auth.ui.LoginFragment
import com.project.bosberas.cart.ui.CartFragment
import com.project.bosberas.home.ui.HomeFragment
import com.project.bosberas.profil.ui.ProfilFragment
import com.project.bosberas.store.ui.ProductDetail
import com.project.bosberas.store.ui.StoreFragment
import dagger.Component

@Component(modules = [NetworkModul::class])
interface ApplicationComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(storeFragment: StoreFragment)
    fun inject(profilFragment: ProfilFragment)
    fun inject(cartFragment: CartFragment)
    fun inject(productDetail: ProductDetail)

}