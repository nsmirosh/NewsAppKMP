package nick.mirosh.newsappkmp.ui.country

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import nick.mirosh.newsappkmp.domain.feed.model.Country

@Composable
fun CountryDialog(
    countries: List<Country>,
    onCountryClicked: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color = Color.White)
                .shadow(elevation = 2.dp)
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
}


@Composable
fun CountryItem(country: Country, onClicked: (String) -> Unit) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Text(
            text = country.name,
            color = Color.DarkGray,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis, // Add ellipsis if the text is too long
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onClicked(country.code)
                })

        if (country.selected) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Select Country",
                tint = Color.Black
            )
        }
    }
}
