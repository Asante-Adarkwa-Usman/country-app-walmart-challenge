package com.ghost.countryapp_walmart_challenge.data.network

import com.ghost.countryapp_walmart_challenge.data.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiReference {
    const val COUNTRY_END_POINT="db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

    //OkHttp client
    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .callTimeout(
            30,
            TimeUnit.SECONDS
        )
        .build()

    //Retrofit builder
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Api service class
    val apiReference: ApiDetails = retrofit.create(ApiDetails::class.java)
}