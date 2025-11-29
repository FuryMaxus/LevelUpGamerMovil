package com.example.levelupmovil.data.remote

import com.example.levelupmovil.BuildConfig

object ApiConfig {

    private const val IP = BuildConfig.API_IP

    const val PRODUCT_BASE_URL = "http://$IP:8080/"
    const val AUTH_BASE_URL = "http://$IP:8081/"
}