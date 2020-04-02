package com.geekhub_android_2019.cherkasyguide.network

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

class NetHelperTest {

    private lateinit var application: Application
    private lateinit var netHelper: NetHelper
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var network: Network

    @Before
    fun setUp() {

        network = mock()

    }

        @Test
    fun isOnline() {

    }

    @Test
    fun isOverWifi() {
    }

    @Test
    fun isOverCellular() {
    }

    @Test
    fun isOverEthernet() {
    }
    

}
