package com.project.bosberas.config

import android.app.Application

class BosBeras : Application() {
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}