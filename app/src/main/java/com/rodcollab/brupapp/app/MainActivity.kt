package com.rodcollab.brupapp.app

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rodcollab.brupapp.app.theme.BrupAppTheme
import com.rodcollab.brupapp.di.ConnectionObserver
import com.rodcollab.brupapp.hangman.ui.ScreenHost
import com.rodcollab.brupapp.util.sharePerformance

class MainActivity : ComponentActivity() {

    val networkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }
    var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                ConnectionObserver.updateConnection(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                ConnectionObserver.updateConnection(false)
            }
        }

        val connectivityManager =
            baseContext.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(
            networkRequest,
            networkCallback as ConnectivityManager.NetworkCallback
        )

        setContent {

            BrupAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenHost(sharePerformance = { screenshot ->
                        sharePerformance(screenshot)
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkCallback = null
    }

}