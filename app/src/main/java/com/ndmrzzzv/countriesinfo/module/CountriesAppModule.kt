package com.ndmrzzzv.countriesinfo.module

import com.ndmrzzzv.countriesinfo.feature.InternetChecker
import com.ndmrzzzv.countriesinfo.screens.detail.DetailViewModel
import com.ndmrzzzv.countriesinfo.screens.main.MainListViewModel
import com.ndmrzzzv.domain.usecase.GetAllCountriesUseCase
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { GetAllCountriesUseCase(get()) }

    single { SearchCountriesByCodeUseCase(get()) }

    single { InternetChecker(androidContext()) }

    viewModel { MainListViewModel(get(), get()) }

    viewModel { DetailViewModel(get(), get(), get()) }

}