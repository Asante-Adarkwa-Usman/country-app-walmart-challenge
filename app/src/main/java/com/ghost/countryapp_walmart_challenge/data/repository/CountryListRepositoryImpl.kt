package com.ghost.countryapp_walmart_challenge.data.repository

import com.ghost.countryapp_walmart_challenge.data.network.ApiDetails
import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel
import com.ghost.countryapp_walmart_challenge.domain.repository.CountryListRepository
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem
import com.ghost.countryapp_walmart_challenge.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CountryListRepositoryImpl(
    private val apiDetails: ApiDetails
): CountryListRepository {
    //Get the list of countries
    override suspend fun getAllCountries(): Flow<UiState<ArrayList<CountryListItemModel>>> = flow {
        emit(UiState.Loading)
        try {
            val result = apiDetails.getCountries()
            if (result.isSuccessful) {
                val countryList = result.body()
                countryList?.let {
                    val sortedList = it.sortedBy { country ->  country.name }
                    emit(UiState.Success(ArrayList(sortedList)))
                } ?: emit(UiState.Error("Country is null or empty"))
            } else {
                emit(UiState.Error("Failed to fetch country data"))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.toString()))
        }
    }

    //List of countries grouped by first letter
    override suspend fun countryByFirstLetter(countries: List<CountryListItemModel>): List<CountryListItem> {
        return countries
            .sortedBy { it.name }
            .groupBy { it.name.first().uppercaseChar() }
            .flatMap { (letter, items) ->
                listOf(CountryListItem.Header(letter.toString())) +
                        items.map { CountryListItem.Country(it) }
            }
    }
}