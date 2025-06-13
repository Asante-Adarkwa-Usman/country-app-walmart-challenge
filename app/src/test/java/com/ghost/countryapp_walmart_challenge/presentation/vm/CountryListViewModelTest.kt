package com.ghost.countryapp_walmart_challenge.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ghost.countryapp_walmart_challenge.data.model.CurrencyModel
import com.ghost.countryapp_walmart_challenge.data.model.LanguageModel
import com.ghost.countryapp_walmart_challenge.domain.CountryListUseCase
import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem
import com.ghost.countryapp_walmart_challenge.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CountryListViewModelTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = UnconfinedTestDispatcher()
  private lateinit var countryListUseCase: CountryListUseCase
  private lateinit var viewModel: CountryListViewModel

  @Before
  fun setUp() {
   Dispatchers.setMain(dispatcher)
   countryListUseCase = mock(CountryListUseCase ::class.java)
   viewModel = CountryListViewModel(countryListUseCase, dispatcher)
  }

  @After
  fun tearDown() {
   Dispatchers.resetMain()
  }

  @Test
  fun `getAllCountries emits Success with grouped countries list`() = runTest {
   //Arrange
   val mockCountries = listOf(
    CountryListItemModel(
     capital = "Accra",
     code = "GH",
     currency = CurrencyModel(name = "Ghanaian cedi", code = "GHS", symbol = "₵"),
     demonym = "Ghanaian",
     flag = "🇬🇭",
     language = LanguageModel(name = "English", code = "en"),
     name = "Ghana",
     region = "Africa"
    ),
    CountryListItemModel(
     capital = "Berlin",
     code = "DE",
     currency = CurrencyModel(name = "Euro", code = "EUR", symbol = "€"),
     demonym = "German",
     flag = "🇩🇪",
     language = LanguageModel(name = "German", code = "de"),
     name = "Germany",
     region = "Europe"
    )
   )

   val groupedCountries = listOf(
    CountryListItem.Header("A"),
    CountryListItem.Country(mockCountries[0]),
    CountryListItem.Header("B"),
    CountryListItem.Country(mockCountries[1])
   )


   // Act
   val mockCountriesArrayList = ArrayList(mockCountries)
   `when`(countryListUseCase.getAllCountries()).thenReturn(flowOf(UiState.Success(mockCountriesArrayList)))
   `when`(countryListUseCase.countryByFirstLetter(mockCountriesArrayList)).thenReturn(groupedCountries)
   viewModel.getAllCountries()

   //Assert
   viewModel.countries.observeForever { status ->
    if (status is UiState.Success) {
     assertEquals(groupedCountries, status.data)
    }
   }
  }


  @Test
  fun `getAllCountries emits Loading when fetching countries`() = runTest {
   //Arrange
   `when`(countryListUseCase.getAllCountries()).thenReturn(flowOf(UiState.Loading))

   //Act
   viewModel.getAllCountries()

   //Assert
   viewModel.countries.observeForever { status ->
    assertTrue(status is UiState.Loading)
   }
  }
 @Test
 fun `getAllCountries emits Error when use case fails`() = runTest {
  //Arrange
  val errorMessage = "Failed to fetch countries"
  `when`(countryListUseCase.getAllCountries()).thenReturn(flowOf(UiState.Error(errorMessage)))

  //Act
  viewModel.getAllCountries()

  //Assert
  viewModel.countries.observeForever { status ->
   if (status is UiState.Error) {
    assertEquals(errorMessage, status.message)
   }
  }

 }

  @Test
  fun `getAllCountries emits Error when countries list is null`() = runTest {
   //Arrange
   val mockCountriesArrayList: ArrayList<CountryListItemModel>? = null
   `when`(countryListUseCase.getAllCountries()).thenReturn(
    flowOf(
     UiState.Success(
      mockCountriesArrayList
     )
    ) as Flow<UiState<ArrayList<CountryListItemModel>>>
   )

   //Act
   viewModel.getAllCountries()

   //Assert
   viewModel.countries.observeForever { status ->
    if (status is UiState.Error) {
     assertEquals("Country is null or empty", status.message)
    }
   }
  }
 }