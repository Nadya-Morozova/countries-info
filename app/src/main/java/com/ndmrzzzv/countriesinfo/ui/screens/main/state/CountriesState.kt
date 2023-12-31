package com.ndmrzzzv.countriesinfo.ui.screens.main.state

import com.ndmrzzzv.domain.model.Country

sealed class CountriesState(val searchRequest: String) {

    class LoadingFailed(val message: String) : CountriesState("")

    class LoadedData(val listOfCountries: List<Country>, searchSavedQuery: String) :
        CountriesState(searchSavedQuery)

    object Loading : CountriesState("")
}