package com.henrick.stock.network

import android.content.Context
import com.henrick.stock.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NetworkChecker {
    fun isNetworkAvailable(): Boolean
}

class NetworkCheckerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NetworkChecker {
    override fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkAvailable(context)
    }

}