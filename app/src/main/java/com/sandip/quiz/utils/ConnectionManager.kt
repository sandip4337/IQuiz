package com.sandip.quiz.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager {

    fun cheakConnectivity(context: Context):Boolean{

        val ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = ConnectivityManager.activeNetworkInfo

        return if (activeNetwork?.isConnected != null)
        {
            activeNetwork.isConnected
        }
        else
        {
            false
        }
    }

}