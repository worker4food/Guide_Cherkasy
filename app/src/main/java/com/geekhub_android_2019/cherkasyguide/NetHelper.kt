package com.geekhub_android_2019.cherkasyguide

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.google.firebase.firestore.remote.ConnectivityMonitor
import com.squareup.okhttp.internal.Internal.logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


class NetHelper(context: Context){

/* *//*   val context:Context? = null*//*
    private val connectivityManager =
        application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

    var isOnline = false
        get() {
            updateFields()
            return field
        }

    var isOverWifi = false
        get() {
            updateFields()
            *//*Toast.makeText(
                context,
                "Нет подключения к Интернету. Повторите попытку позже", Toast.LENGTH_LONG
            ).show()*//*
            return field
        }

    var isOverCellular = false
        get() {
            updateFields()
            return field
        }

    var isOverEthernet = false
        get() {
            updateFields()
            return field
        }

    companion object {
        @Volatile
        private var INSTANCE: NetHelper? = null

        fun getInstance(application: Application): NetHelper {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = NetHelper(application)
                }
                return INSTANCE!!
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun updateFields() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val networkAvailability =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (networkAvailability != null &&
                networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            ) {
                //has network
                isOnline = true

                // wifi
                isOverWifi =
                    networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

                // cellular
                isOverCellular =
                    networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

                // ethernet
                isOverEthernet =
                    networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } else {
                isOnline = false
                isOverWifi = false
                isOverCellular = false
                isOverEthernet = false
            }
        } else {

            val info = connectivityManager.activeNetworkInfo
            if (info != null && info.isConnected) {
                isOnline = true

                val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                isOverWifi = wifi != null && wifi.isConnected

                val cellular = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                isOverCellular = cellular != null && cellular.isConnected

                val ethernet = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET)
                isOverEthernet = ethernet != null && ethernet.isConnected

            } else {
                isOnline = false
                isOverWifi = false
                isOverCellular = false
                isOverEthernet = false
            }
        }
    }

    fun watchNetwork(): Flow<Boolean> = watchWifi()
        .combine(watchCellular()) { wifi, cellular -> wifi || cellular }
        .combine(watchEthernet()) { wifiAndCellular, ethernet -> wifiAndCellular || ethernet }

    fun watchNetworkAsLiveData(): LiveData<Boolean> = watchNetwork().asLiveData()

    fun watchWifi(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_WIFI)

    fun watchWifiAsLiveData() = watchWifi().asLiveData()

    fun watchCellular(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_CELLULAR)

    fun watchCellularAsLiveData() = watchCellular().asLiveData()

    fun watchEthernet(): Flow<Boolean> = callbackFlowForType(NetworkCapabilities.TRANSPORT_ETHERNET)

    fun watchEthernetAsLiveData() = watchEthernet().asLiveData()

    private fun callbackFlowForType(type: Int) = callbackFlow {

        offer(false)

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(type)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network?) {
                offer(false)
            }

            override fun onUnavailable() {
                offer(false)
            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                // do nothing
            }

            override fun onAvailable(network: Network?) {
                offer(true)
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
}*/




    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            val toast = Toast.makeText(
                context,
                "Нет подключения к Интернету. Повторите попытку позже", Toast.LENGTH_LONG
            )
            toast.show()
        }

        override fun onUnavailable() {
            Log.d("call from onUnvailable", "")
        }

        override fun onLosing(network: Network?, maxMsToLive: Int) {
            Log.d("called from onLosing", "")
        }

        override fun onAvailable(network: Network?) {
            Log.d("called from onAvailable", "")
            //record wi-fi connect event
        }
    }

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()



    fun isNetworkAvailable(context: Context): Boolean {
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            } else {
                try {

                    connectivityManager.unregisterNetworkCallback(networkCallback)
                } catch (e: Exception) {
                    val toast = Toast.makeText(
                        context,
                        "Нет подключения к Интернету. Повторите попытку позже", Toast.LENGTH_LONG
                    )
                    toast.show()
                }
                connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            }
        }
        return false
    }
}




