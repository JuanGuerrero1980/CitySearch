package com.jg.citysearch.di

import com.jg.citysearch.data.remote.CityApiService
import com.jg.citysearch.data.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Named("base_city_url")
    fun provideBaseUrl(): String = "https://gist.githubusercontent.com/"

    @Provides
    @Named("base_weather_url")
    fun provideBaseWeatherUrl(): String = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    @Named("city_retrofit")
    fun provideRetrofit(@Named("base_city_url") baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            })
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build())
        .build()

    @Provides
    @Singleton
    @Named("weather_retrofit")
    fun provideWeatherRetrofit(@Named("base_weather_url") baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val request = chain.request()
                    val newRequest = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(newRequest)
                })
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                ).build())
        .build()

    @Provides
    @Singleton
    fun provideCityApiService(@Named("city_retrofit") retrofit: Retrofit): CityApiService = retrofit.create(
        CityApiService::class.java
    )

    @Provides
    @Singleton
    fun provideWeatherApiService(@Named("weather_retrofit") retrofit: Retrofit): WeatherApiService = retrofit.create(
        WeatherApiService::class.java
    )

}
