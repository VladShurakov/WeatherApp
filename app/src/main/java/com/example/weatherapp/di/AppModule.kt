package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.data.network.GeoApi
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.repository.NetworkRepositoryImpl
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.usecase.GetCurrentWeather
import com.example.weatherapp.domain.usecase.GetDailyWeather
import com.example.weatherapp.domain.usecase.GetGeoByCity
import com.example.weatherapp.domain.usecase.GetHourlyWeather
import com.example.weatherapp.domain.usecase.UseCases
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_PRAGMA = "Pragma"

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        /*
         *   Get data from cache when offline
         */
        fun offlineInterceptor(): Interceptor {
            return Interceptor { chain ->
                var request: Request = chain.request()
                if (WeatherApplication.hasNetwork() == false) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(3, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                chain.proceed(request)
            }
        }

        /*
         *   Get data from network when online
         */
        fun networkInterceptor(): Interceptor {
            return Interceptor { chain ->
                val response: Response = chain.proceed(chain.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(10, TimeUnit.MINUTES)
                    .build()
                response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }

        /*
         *   Get data when offline OR online (For logging)
         */
        val httpLoginInterceptor = HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }

        // Creating cache
        val cacheMaxSize: Long = 5 * 1024 * 1024  // 5 MiB
        val cache = Cache(File(context.cacheDir, "weather-cache"), cacheMaxSize)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoginInterceptor)
            .addInterceptor(networkInterceptor())
            .addInterceptor(offlineInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(okHttpClient: OkHttpClient): WeatherApi {
        val baseurl = "https://api.open-meteo.com/v1/"
        val retrofit= createRetrofit(okHttpClient, baseurl)
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideGeoApi(okHttpClient: OkHttpClient): GeoApi {
        val baseurl = "https://geocoding-api.open-meteo.com/v1/"
        val retrofit= createRetrofit(okHttpClient, baseurl)
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(weatherApi: WeatherApi, geoApi: GeoApi): NetworkRepository{
        return NetworkRepositoryImpl(weatherApi = weatherApi, geoApi = geoApi)
    }

    @Provides
    @Singleton
    fun provideUseCases(networkRepository: NetworkRepository): UseCases{
        return UseCases(
            getCurrentWeather = GetCurrentWeather(networkRepository),
            getHourlyWeather = GetHourlyWeather(networkRepository),
            getDailyWeather = GetDailyWeather(networkRepository),
            getGeoByCity = GetGeoByCity(networkRepository)
        )
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType: MediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}