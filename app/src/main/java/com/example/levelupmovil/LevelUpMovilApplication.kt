package com.example.levelupmovil

import android.app.Application
import com.example.levelupmovil.data.AppContainer

class LevelUpMovilApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}