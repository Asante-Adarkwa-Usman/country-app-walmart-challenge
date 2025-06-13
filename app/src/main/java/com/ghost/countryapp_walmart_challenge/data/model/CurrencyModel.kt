package com.ghost.countryapp_walmart_challenge.data.model


import com.google.gson.annotations.SerializedName

data class CurrencyModel(
    val code: String = "",
    val name: String = "",
    val symbol: String? = null
)