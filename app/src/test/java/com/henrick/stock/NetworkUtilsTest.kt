package com.henrick.stock

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.henrick.stock.network.NetworkCheckerImpl
import com.henrick.stock.utils.NetworkUtils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class NetworkUtilsTest {

    private val context: Context = mock()
    private val connectivityManager: ConnectivityManager = mock()
    private val network: Network = mock()
    private val networkCapabilities: NetworkCapabilities = mock()
    private lateinit var networkChecker: NetworkCheckerImpl

    @Before
    fun setUp() {
        networkChecker = NetworkCheckerImpl(context)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager
        )
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
    }

    @Test
    fun `isNetworkAvailable return true when WIFI is connected`() {
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return true when CELLULAR is connected`() {
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(true)
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return true when ETHERNET is connected`() {
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(true)
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return true when VPN is connected`() {
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)).thenReturn(true)
        assertTrue(networkChecker.isNetworkAvailable())
        assertTrue(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return false when no network is active`() {
        `when`(connectivityManager.activeNetwork).thenReturn(null)
        assertFalse(networkChecker.isNetworkAvailable())
        assertFalse(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return false when active network has no capabilities`() {
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(null)
        assertFalse(networkChecker.isNetworkAvailable())
        assertFalse(NetworkUtils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable return false when no WIFI, CELLULAR, ETHERNET, VPN is connected`() {
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(false)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(false)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(false)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)).thenReturn(false)
        assertFalse(networkChecker.isNetworkAvailable())
        assertFalse(NetworkUtils.isNetworkAvailable(context))
    }

}