package com.project.bosberas.config

import com.project.bosberas.auth.data.AuthAPI
import com.project.bosberas.banner.BannerAPI
import com.project.bosberas.cart.data.CartAPI
import com.project.bosberas.order.data.OrderAPI
import com.project.bosberas.profil.data.ProfilAPI
import com.project.bosberas.store.data.ProductAPI
import dagger.Module
import dagger.Provides


@Module
class NetworkModul {
    @Provides
    fun provideLoginAPI(): AuthAPI {
        return Connection.urlCon().create(AuthAPI::class.java)
    }
    @Provides
    fun provideBannerAPI(): BannerAPI {
        return Connection.urlCon().create(BannerAPI::class.java)
    }
    @Provides
    fun provideProfilAPI(): ProfilAPI {
        return Connection.urlCon().create(ProfilAPI::class.java)
    }
    @Provides
    fun provideProductAPI(): ProductAPI {
        return Connection.urlCon().create(ProductAPI::class.java)
    }
    @Provides
    fun provideCartAPI(): CartAPI {
        return Connection.urlCon().create(CartAPI::class.java)
    }
    @Provides
    fun provideOrderAPI(): OrderAPI {
        return Connection.urlCon().create(OrderAPI::class.java)
    }
}