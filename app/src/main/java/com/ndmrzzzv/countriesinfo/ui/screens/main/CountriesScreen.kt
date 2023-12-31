package com.ndmrzzzv.countriesinfo.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.ndmrzzzv.countriesinfo.R
import com.ndmrzzzv.countriesinfo.ui.screens.main.state.CountriesState
import com.ndmrzzzv.domain.model.Country
import com.ndmrzzzv.domain.model.SortType
import com.ndmrzzzv.domain.model.SortTypes

data class CountriesScreenAction(
    val onItemClick: (code: String) -> Unit = {},
    val searchEvent: (searchString: String) -> Unit = {},
    val sortEvent: (type: SortType?) -> Unit = {},
    val getAllCountriesEvent: () -> Unit = {},
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesScreen(
    state: CountriesState,
    actions: CountriesScreenAction,
) {
    var defaultStringForSearchInput by remember { mutableStateOf(state.searchRequest) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .width(55.dp)
                    .height(55.dp)
                    .clickable {
                        expanded = !expanded
                    },
                bitmap = ImageBitmap.imageResource(id = R.drawable.sort),
                contentDescription = stringResource(id = R.string.sort_by_title),
            )

            Box {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    SortTypes.getAll().forEach { pair ->
                        SortDropdownItem(pairOfSort = pair) {
                            actions.sortEvent(it)
                            expanded = false
                        }
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = defaultStringForSearchInput,
                label = { Text(text = stringResource(id = R.string.type_here_to_find_country_title)) },
                onValueChange = {
                    defaultStringForSearchInput = it
                    actions.searchEvent(it)
                },
            )
        }

        when (state) {
            is CountriesState.LoadedData -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.listOfCountries) { country ->
                        CountryItem(country) {
                            actions.onItemClick(it)
                        }
                    }
                }
            }

            is CountriesState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CountriesState.LoadingFailed -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.message,
                        textAlign = TextAlign.Center,
                    )
                    Button(
                        onClick = { actions.getAllCountriesEvent() },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200)),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }
        }
    }

}

@Composable
fun SortDropdownItem(
    pairOfSort: Map.Entry<String, SortType>,
    sortEvent: (type: SortType) -> Unit = {},
) {
    DropdownMenuItem(
        text = { Text(text = pairOfSort.key) },
        onClick = { sortEvent(pairOfSort.value) })
}

@Composable
fun CountryItem(
    country: Country,
    onItemClick: (code: String) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable {
                onItemClick(country.code ?: "")
            }
    ) {
        val (imgCountry, tvCountryName, tvCapital, tvPopulation, tvSurface) = createRefs()
        AsyncImage(
            model = country.image,
            contentDescription = stringResource(id = R.string.img_country),
            Modifier
                .width(120.dp)
                .height(120.dp)
                .constrainAs(imgCountry) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(8.dp))

        Text(text = country.name ?: "", fontSize = 16.sp, modifier = Modifier
            .constrainAs(tvCountryName) {
                top.linkTo(imgCountry.top)
                start.linkTo(imgCountry.end)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp, top = 8.dp))

        val capital = if (country.capital.isNullOrEmpty()) " - " else country.capital.toString()
        Text(text = capital, fontSize = 14.sp, modifier = Modifier
            .constrainAs(tvCapital) {
                top.linkTo(tvCountryName.bottom)
                start.linkTo(tvCountryName.start)
                end.linkTo(tvCountryName.end)
                bottom.linkTo(tvPopulation.top)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp))

        Text(
            text = "Population: ${country.population}",
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(tvPopulation) {
                    start.linkTo(tvCapital.start)
                    end.linkTo(tvCapital.end)
                    top.linkTo(tvCapital.bottom)
                    bottom.linkTo(tvSurface.top)

                    width = Dimension.fillToConstraints
                }
                .padding(start = 8.dp, end = 8.dp)
        )

        Text(text = "Surface: ${country.surface}", fontSize = 14.sp, modifier = Modifier
            .constrainAs(tvSurface) {
                start.linkTo(tvPopulation.start)
                end.linkTo(tvPopulation.end)
                top.linkTo(tvPopulation.bottom)
                bottom.linkTo(parent.bottom)

                width = Dimension.fillToConstraints
            }
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp))

    }
}
