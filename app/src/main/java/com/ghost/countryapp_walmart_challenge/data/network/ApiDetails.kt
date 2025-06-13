package com.ghost.countryapp_walmart_challenge.data.network

import com.ghost.countryapp_walmart_challenge.domain.model.CountryListItemModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiDetails {
    //Fetch all countries
    @GET(ApiReference.COUNTRY_END_POINT)
    suspend fun getCountries(): Response<ArrayList<CountryListItemModel>>
}