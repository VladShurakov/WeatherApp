package com.example.weatherapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@Suppress("DEPRECATION")
@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Get instance of application
        if (instance == null) {
            instance = this
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?
        var result = false

        when {
            // Build.VERSION.SDK_INT >= 23
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                connectivityManager?.run {
                    getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                        if (
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                        )
                            result = true
                    }
                }
            }

            // Build.VERSION.SDK_INT < 23
            else -> {
                connectivityManager?.run {
                    activeNetworkInfo?.run {
                        if (
                            type == ConnectivityManager.TYPE_WIFI ||
                            type == ConnectivityManager.TYPE_MOBILE ||
                            type == ConnectivityManager.TYPE_VPN
                        ) {
                            result = true
                        }
                    }
                }
            }
        }
        return result
    }

    companion object {
        // Create instance of application
        private var instance: WeatherApplication? = null

        // Public function for Retrofit
        fun hasNetwork(): Boolean? {
            return instance?.isNetworkConnected()
        }
    }
}