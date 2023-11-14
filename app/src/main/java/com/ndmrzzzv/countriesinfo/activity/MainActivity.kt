package com.ndmrzzzv.countriesinfo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ndmrzzzv.countriesinfo.screens.detail.CountryDetailScreen
import com.ndmrzzzv.countriesinfo.screens.main.CountriesScreen
import com.ndmrzzzv.countriesinfo.screens.main.state.CountriesState
import com.ndmrzzzv.countriesinfo.ui.theme.CountriesInfoAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesInfoAppTheme {
                CountriesApp()
            }
        }

    }

    @Composable
    private fun CountriesApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "countries") {
            composable(route = "countries") {
//                val viewModel: MainListViewModel = viewModel()
//                CountriesScreen(
//                    CountriesState(),
//                    onItemClick = { code -> navController.navigate("countries/$code") }
//                )
            }
            composable(
                route = "countries/country_code",
                arguments = listOf(navArgument("country_code") {
                    type = NavType.StringType
                })
            ) {
                CountryDetailScreen()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesInfoAppTheme {
        CountriesScreen(CountriesState(listOf(), true)) {}
    }
}