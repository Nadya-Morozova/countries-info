package com.ndmrzzzv.countriesinfo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class InternetCheckerTest {

    @Test
    fun isExecuteSystemService() {
        // Setup
        val connectivityManagerMock = mock<ConnectivityManager>()

        val contextMock = mock<Context>()
        `when`(
            contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)
        ).thenReturn(connectivityManagerMock)

        // Call
        val subject = InternetChecker(contextMock)
        val result = subject.checkConnection()

        // Assert
        Assert.assertFalse(result)
    }

    @Test
    fun isExecuteGetNetworkCapabilities() {
        // Setup
        val connectivityManagerMock = mock<ConnectivityManager>()
        val contextMock = mock<Context>()
        `when`(
            contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)
        ).thenReturn(connectivityManagerMock)

        `when`(
            connectivityManagerMock.getNetworkCapabilities(connectivityManagerMock.activeNetwork)
        ).thenReturn(null)

        // Call
        val subject = InternetChecker(contextMock)
        val result = subject.checkConnection()

        // Assert
        Assert.assertFalse(result)

    }

}