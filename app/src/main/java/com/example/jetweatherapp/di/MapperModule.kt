package com.example.jetweatherapp.di

import com.example.jetweatherapp.data.mappers.ApiMapper
import com.example.jetweatherapp.data.mappers.impl.ApiCurrentWeatherMapper
import com.example.jetweatherapp.data.mappers.impl.ApiDailyWeatherMapper
import com.example.jetweatherapp.data.mappers.impl.ApiHourlyWeatherMapper
import com.example.jetweatherapp.data.mappers.impl.ApiWeatherMapper
import com.example.jetweatherapp.data.remote.models.ApiCurrentWeather
import com.example.jetweatherapp.data.remote.models.ApiDailyWeather
import com.example.jetweatherapp.data.remote.models.ApiHourlyWeather
import com.example.jetweatherapp.data.remote.models.ApiWeather
import com.example.jetweatherapp.domain.models.CurrentWeather
import com.example.jetweatherapp.domain.models.Daily
import com.example.jetweatherapp.domain.models.Hourly
import com.example.jetweatherapp.domain.models.Weather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @ApiDailyWeatherMapperAnnotation
    @Provides
    fun provideApiDailyWeatherMapper(): ApiMapper<Daily, ApiDailyWeather> {
        return ApiDailyWeatherMapper()
    }

    @ApiHourlyWeatherMapperAnnotation
    @Provides
    fun provideApiHourlyWeatherMapper(): ApiMapper<Hourly, ApiHourlyWeather> {
        return ApiHourlyWeatherMapper()
    }

    @ApiCurrentWeatherMapperAnnotation
    @Provides
    fun provideApiCurrentWeatherMapper(): ApiMapper<CurrentWeather, ApiCurrentWeather> {
        return ApiCurrentWeatherMapper()
    }

    @ApiWeatherMapperAnnotation
    @Provides
    fun provideApiWeatherMapper(
        apiDailyWeatherMapper: ApiMapper<Daily, ApiDailyWeather>,
        apiHourlyWeatherMapper: ApiMapper<Hourly, ApiHourlyWeather>,
        apiCurrentWeatherMapper: ApiMapper<CurrentWeather, ApiCurrentWeather>
    ): ApiMapper<Weather, ApiWeather> {
        return ApiWeatherMapper(
            apiDailyWeatherMapper,
            apiHourlyWeatherMapper,
            apiCurrentWeatherMapper
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiDailyWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHourlyWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiCurrentWeatherMapperAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiWeatherMapperAnnotation