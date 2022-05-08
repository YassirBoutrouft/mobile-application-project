package com.example.mobile_application_project.api

import com.example.mobile_application_project.ui.BaseUrl.Companion.url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}
