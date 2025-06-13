package com.ghost.countryapp_walmart_challenge.utils

import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel

sealed class CountryListItem {
    data class Header(val title: String) : CountryListItem()
    data class Country(val country: CountryListItemModel) : CountryListItem()
}