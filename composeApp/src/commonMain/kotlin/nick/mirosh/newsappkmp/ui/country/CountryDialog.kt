package nick.mirosh.newsappkmp.ui.country

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nick.mirosh.newsappkmp.domain.feed.model.Country

@Composable
fun CountryDialog(
    countries: List<Country>,
    onCountryClicked: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.wrapContentSize()
                .background(Color.DarkGray)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Articles from:",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
            CountryGrid(
                countries = countries,
                onCountryClicked = onCountryClicked,
            )
        }
    }
}

@Composable
fun CountryGrid(
    countries: List<Country>,
    onCountryClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        items(countries) { country ->
            CountryItem(
                country = country,
            ) { chosenCountry ->
                onCountryClicked(chosenCountry)
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, onClicked: (String) -> Unit) {
    Text(
        text = country.name,
        color = if (country.selected) Color.Magenta else Color.White, // White text color
        style = MaterialTheme.typography.body1,
        maxLines = 1, // Limit to one line
        overflow = TextOverflow.Ellipsis, // Add ellipsis if the text is too long
        modifier = Modifier
            .padding(16.dp).clickable {
                onClicked(country.code)
            }
    )
}
