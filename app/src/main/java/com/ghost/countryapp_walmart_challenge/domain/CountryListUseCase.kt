package com.ghost.countryapp_walmart_challenge.domain

import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel
import com.ghost.countryapp_walmart_challenge.domain.repository.CountryListRepository

class CountryListUseCase(private val repository: CountryListRepository) {
    suspend fun getAllCountries() = repository.getAllCountries()

    suspend fun countryByFirstLetter(countries: List<CountryListItemModel>) = repository.countryByFirstLetter(countries)
}