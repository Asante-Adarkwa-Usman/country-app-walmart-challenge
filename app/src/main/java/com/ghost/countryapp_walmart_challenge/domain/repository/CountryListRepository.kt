package com.ghost.countryapp_walmart_challenge.domain.repository

import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem
import com.ghost.countryapp_walmart_challenge.utils.UiState
import kotlinx.coroutines.flow.Flow


interface CountryListRepository {
    suspend fun getAllCountries(): Flow<UiState<ArrayList<CountryListItemModel>>>

    suspend fun countryByFirstLetter(countries: List<CountryListItemModel>): List<CountryListItem>
}