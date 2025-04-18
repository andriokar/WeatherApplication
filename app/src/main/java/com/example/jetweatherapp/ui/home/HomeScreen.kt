package com.example.jetweatherapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetweatherapp.R
import com.example.jetweatherapp.domain.models.CurrentWeather
import com.example.jetweatherapp.domain.models.Daily
import com.example.jetweatherapp.domain.models.Hourly
import com.example.jetweatherapp.utils.Util
import java.util.Date

const val degreeTxt = "\u2103"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val homeState = homeViewModel.homeState
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (homeState.isLoading) {
            true -> {
                CircularProgressIndicator()
            }

            else -> {
                homeState.weather?.let {
                    CurrentWeatherItem(
                        modifier = Modifier.align(Alignment.TopCenter),
                        currentWeather = it.currentWeather
                    )
                    HourlyWeatherItem(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        hourly = it.hourly
                    )
                }
                homeState.dailyWeatherInfo?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .align(Alignment.Center),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SunRiseWeatherItem(weatherInfo = it)
                        SunSetWeatherItem(weatherInfo = it)
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherItem(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(
                id = currentWeather.weatherStatus.icon
            ),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${currentWeather.temperature} $degreeTxt",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Weather Status: ${currentWeather.weatherStatus.info}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Wind Speed: ${currentWeather.windDirection} ${currentWeather.windSpeed} km/h",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    hourly: Hourly
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = Util.formatNormalDate("MMMM,dd", Date().time),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyRow(
            modifier = Modifier.padding(16.dp)
        ) {
            items(hourly.weatherInfo) { infoItem ->
                HourlyWeatherInfoItem(infoItem = infoItem)
            }
        }
    }
}

@Composable
fun HourlyWeatherInfoItem(
    modifier: Modifier = Modifier,
    infoItem: Hourly.HourlyInfoItem
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${infoItem.temperature} $degreeTxt",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = infoItem.weatherStatus.icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = infoItem.time,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SunRiseWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.sunrise_ic),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sunrise",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Text(
                text = weatherInfo.sunrise,
                style = MaterialTheme.typography.displayMedium,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun SunSetWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.sunset_ic),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sunset",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Text(
                text = weatherInfo.sunset,
                style = MaterialTheme.typography.displayMedium,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun UvIndexWeatherItem(
    modifier: Modifier = Modifier,
    weatherInfo: Daily.WeatherInfo
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.uv_index_ic),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "UV index",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Text(
                text = weatherInfo.uvIndex.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontStyle = FontStyle.Italic
            )
            Text(
                modifier = modifier.padding(0.dp, 8.dp),
                text = "Status ${weatherInfo.weatherStatus.info}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
