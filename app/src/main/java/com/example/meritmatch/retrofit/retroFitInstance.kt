package com.example.meritmatch.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retroFitInstance {
    val api:meritMatchAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(meritMatchAPI::class.java)
    }
}