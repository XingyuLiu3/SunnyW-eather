package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN="KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
    }


    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}