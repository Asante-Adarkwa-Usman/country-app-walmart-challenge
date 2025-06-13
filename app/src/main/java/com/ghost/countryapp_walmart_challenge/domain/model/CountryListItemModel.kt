package com.ghost.countryapp_walmart_challenge.domain.model

import com.ghost.countryapp_walmart_challenge.data.model.CurrencyModel
import com.ghost.countryapp_walmart_challenge.data.model.LanguageModel


data class CountryListItemModel(
    val capital: String = "",
    val code: String = "",
    val currency: CurrencyModel = CurrencyModel(),
    val demonym: String? = null,
    val flag: String = "",
    val language: LanguageModel = LanguageModel(),
    val name: String = "",
    val region: String = ""
)