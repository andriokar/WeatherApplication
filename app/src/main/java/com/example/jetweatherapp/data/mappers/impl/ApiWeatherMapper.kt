package com.example.jetweatherapp.data.mappers.impl

import com.example.jetweatherapp.data.mappers.ApiMapper
import com.example.jetweatherapp.data.remote.models.ApiCurrentWeather
import com.example.jetweatherapp.data.remote.models.ApiDailyWeather
import com.example.jetweatherapp.data.remote.models.ApiHourlyWeather
import com.example.jetweatherapp.data.remote.models.ApiWeather
import com.example.jetweatherapp.di.ApiCurrentWeatherMapperAnnotation
import com.example.jetweatherapp.di.ApiDailyWeatherMapperAnnotation
import com.example.jetweatherapp.di.ApiHourlyWeatherMapperAnnotation
import com.example.jetweatherapp.domain.models.CurrentWeather
import com.example.jetweatherapp.domain.models.Daily
import com.example.jetweatherapp.domain.models.Hourly
import com.example.jetweatherapp.domain.models.Weather
import javax.inject.Inject

class ApiWeatherMapper @Inject constructor(
    @ApiDailyWeatherMapperAnnotation private val apiDailyWeatherMapper: ApiMapper<Daily, ApiDailyWeather>,
    @ApiHourlyWeatherMapperAnnotation private val apiHourlyWeatherMapper: ApiMapper<Hourly, ApiHourlyWeather>,
    @ApiCurrentWeatherMapperAnnotation private val apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>
) : ApiMapper<Weather, ApiWeather> {

    override fun mapToDomain(apiEntity: ApiWeather): Weather {
        return Weather(
            currentWeather = apiCurrentWeatherMapper.mapToDomain(apiEntity.current),
            daily = apiDailyWeatherMapper.mapToDomain(apiEntity.daily),
            hourly = apiHourlyWeatherMapper.mapToDomain(apiEntity.hourly)
        )
    }
}
