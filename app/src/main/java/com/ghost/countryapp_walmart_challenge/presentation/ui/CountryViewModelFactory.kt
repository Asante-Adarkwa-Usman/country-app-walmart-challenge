package com.ghost.countryapp_walmart_challenge.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghost.countryapp_walmart_challenge.domain.CountryListUseCase
import com.ghost.countryapp_walmart_challenge.presentation.vm.CountryListViewModel

class CountryViewModelFactory(
    private val countryUseCase: CountryListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountryListViewModel(countryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}