package com.geekhub_android_2019.cherkasyguide.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkInfo
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@Suppress("DEPRECATION")
@RunWith(MockitoJUnitRunner.Silent::class)
class NetHelperTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var netHelper: NetHelper

    @Before
    fun setUp() {
        val network: Network = mock()

        val networkInfo: NetworkInfo = mock()
        `when`(networkInfo.isConnected).thenReturn(true)

        val networkAvailability: NetworkCapabilities = mock()
        `when`(networkAvailability.hasTransport(TRANSPORT_CELLULAR)).thenReturn(false)
        `when`(networkAvailability.hasTransport(TRANSPORT_WIFI)).thenReturn(true)
        `when`(networkAvailability.hasTransport(TRANSPORT_ETHERNET)).thenReturn(true)

        val connectivityManager: ConnectivityManager = mock()
        `when`(connectivityManager.activeNetworkInfo)
            .thenReturn(networkInfo)
        `when`(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkAvailability)

        val context: Context = mock()
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)

        netHelper = NetHelper(context)
    }

    @Test
    fun isOnline() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            assertEquals(netHelper.isOnline, false)
        } else {
            assertEquals(netHelper.isOnline, true)
        }
    }

    @Test
    fun isOverWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            assertEquals(netHelper.isOverWifi, true)
        } else {
            assertEquals(netHelper.isOverWifi, false)
        }
    }

    @Test
    fun isOverCellular() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            assertEquals(netHelper.isOverCellular, true)
        } else {
            assertEquals(netHelper.isOverCellular, false)
        }
    }

    @Test
    fun isOverEthernet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            assertEquals(netHelper.isOverEthernet, true)
        } else {
            assertEquals(netHelper.isOverEthernet, false)
        }
    }
}
