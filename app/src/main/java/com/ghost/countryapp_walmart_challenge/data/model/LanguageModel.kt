package com.ghost.countryapp_walmart_challenge.data.model


import com.google.gson.annotations.SerializedName

data class LanguageModel(
    val code: String? = null,
    @SerializedName("iso639_2")
    val iso6392: String? = null,
    val name: String = "",
    val nativeName: String? = null
)