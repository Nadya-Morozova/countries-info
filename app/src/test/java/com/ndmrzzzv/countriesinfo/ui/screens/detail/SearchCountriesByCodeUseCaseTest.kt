package com.ndmrzzzv.countriesinfo.ui.screens.detail

import com.ndmrzzzv.countriesinfo.data.CountriesForTesting
import com.ndmrzzzv.domain.repository.CountriesRepository
import com.ndmrzzzv.domain.usecase.SearchCountriesByCodeUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

class SearchCountriesByCodeUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var mockCountriesRepository: CountriesRepository
    private lateinit var mockSearchCountriesByCodeUseCase: SearchCountriesByCodeUseCase

    @Before
    fun setup() {
        mockCountriesRepository = mock()
        mockSearchCountriesByCodeUseCase = SearchCountriesByCodeUseCase(mockCountriesRepository)
    }

    @Test
    fun getResultByCode_UseCase() = scope.runTest {
        val code = "test"
        val expectedResult = CountriesForTesting.countriesFilterByCode(code)
        `when`(mockCountriesRepository.searchCountryByCode(code)).thenReturn(expectedResult)

        val country = mockSearchCountriesByCodeUseCase.invoke(code)

        Assert.assertEquals(expectedResult, country)
    }

    @Test
    fun getResultByEmptyCode_UseCase() = scope.runTest {
        val code = ""
        val expectedResult = CountriesForTesting.countriesFilterByCode(code)
        `when`(mockCountriesRepository.searchCountryByCode(code)).thenReturn(expectedResult)

        val country = mockSearchCountriesByCodeUseCase.invoke(code)

        Assert.assertEquals(expectedResult, country)
    }

}