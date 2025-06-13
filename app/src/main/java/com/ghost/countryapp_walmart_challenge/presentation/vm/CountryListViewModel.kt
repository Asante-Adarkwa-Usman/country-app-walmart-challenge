package com.ghost.countryapp_walmart_challenge.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghost.countryapp_walmart_challenge.domain.CountryListUseCase
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem
import com.ghost.countryapp_walmart_challenge.utils.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countryUseCase: CountryListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _countries = MutableLiveData<UiState<List<CountryListItem>>>(UiState.Loading)
    val countries: LiveData<UiState<List<CountryListItem>>> = _countries

    fun getAllCountries() {
        viewModelScope.launch(dispatcher) {
            countryUseCase.getAllCountries().collect { status ->
                when (status) {
                    is UiState.Success -> {
                        val grouped = countryUseCase.countryByFirstLetter(status.data)
                        _countries.postValue(UiState.Success(grouped))
                    }
                    is UiState.Error -> _countries.postValue(UiState.Error(status.message))
                    is UiState.Loading -> _countries.postValue(UiState.Loading)
                }
            }
        }
    }
}