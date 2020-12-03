package com.example.interviewtest.network

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData
import com.example.interviewtest.utils.extensions.IllegalStateExceptions

@Suppress("DEPRECATION")
class NetworkConnectionStatus(private val context: Context) : LiveData<Boolean>() {
    private val connectivityManager: ConnectivityManager =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    private lateinit var networkCallbacks: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        checkConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(
                    networkConnectivityManagerCallback()
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                networkRequestForLollipopAndAbove()
            }

            else -> {
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }

    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallbacks)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun networkRequestForLollipopAndAbove() {
        val networkRequestBuilder = NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        }
        connectivityManager.registerNetworkCallback(
            networkRequestBuilder.build(),
            networkConnectivityManagerCallback()
        )
    }

    private fun networkConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallbacks = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallbacks
        } else {
            throw IllegalStateExceptions("error fount when checking status of connectivity")
        }
    }

    private fun checkConnection() {
        val activeNetWork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetWork?.isConnected == true)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            checkConnection()
        }

    }
}