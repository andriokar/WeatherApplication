package com.example.jetweatherapp.data.mappers.impl

import com.example.jetweatherapp.data.mappers.ApiMapper
import com.example.jetweatherapp.data.remote.models.ApiDailyWeather
import com.example.jetweatherapp.domain.models.Daily
import com.example.jetweatherapp.utils.Util
import com.example.jetweatherapp.utils.WeatherInfoItem

class ApiDailyWeatherMapper : ApiMapper<Daily, ApiDailyWeather> {

    override fun mapToDomain(apiEntity: ApiDailyWeather): Daily {
        return Daily(
            temperatureMax = apiEntity.temperature2mMax,
            temperatureMin = apiEntity.temperature2mMin,
            time = parseTime(apiEntity.time),
            weatherStatus = parseWeatherStatus(apiEntity.weatherCode),
            windDirection = parseWindDirection(apiEntity.windDirection10mDominant),
            windSpeed = apiEntity.windSpeed10mMax,
            sunrise = apiEntity.sunrise.map { Util.formatUnixDate("HH:mm", it.toLong()) },
            sunset = apiEntity.sunset.map { Util.formatUnixDate("HH:mm", it.toLong()) },
            uvIndex = apiEntity.uvIndexMax,
        )
    }

    private fun parseTime(time: List<Long>): List<String> {
        return time.map { Util.formatUnixDate("E", it) }
    }

    private fun parseWeatherStatus(code: List<Int>): List<WeatherInfoItem> {
        return code.map {
            Util.getWeatherInfo(it)
        }
    }

    private fun parseWindDirection(windDirection: List<Double>): List<String> {
        return windDirection.map {
            Util.getWindDirection(it)
        }
    }
}
