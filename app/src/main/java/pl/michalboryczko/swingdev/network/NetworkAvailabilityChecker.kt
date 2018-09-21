package pl.michalboryczko.swingdev.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class NetworkAvailabilityChecker @Inject constructor(val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}