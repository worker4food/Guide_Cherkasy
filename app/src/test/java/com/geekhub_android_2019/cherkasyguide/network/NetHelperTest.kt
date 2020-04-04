@file:Suppress("DEPRECATION")

package com.geekhub_android_2019.cherkasyguide.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@Suppress("DEPRECATION")
@RunWith(MockitoJUnitRunner.Silent::class)
class NetHelperTest {

    private lateinit var application: Application
    private lateinit var netHelper: NetHelper
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var network: Network

    @Before
    fun setUp() {

        network = mock()

        val info:NetworkInfo = mock{
            on{isConnected} doReturn true
            on{type} doReturn (ConnectivityManager.TYPE_ETHERNET)
            }

        val connectivityManager:ConnectivityManager = mock{
            on{activeNetworkInfo} doReturn info
            on{}
        }


        }



}

/*@Test
fun testNetworkStateMeteredNotRoaming() {
    val networkInfo: NetworkInfo = mock(NetworkInfo::class.java)
    `when`(networkInfo.isConnected).thenReturn(true)
    `when`(networkInfo.isConnectedOrConnecting).thenReturn(true)
    `when`(networkInfo.type).thenReturn(ConnectivityManager.TYPE_MOBILE)
    `when`(networkInfo.isRoaming).thenReturn(false)
    val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)
    `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
    val context: Context = mock(MockContext::class.java)
    `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE))
        .thenReturn(connectivityManager) }*/


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
