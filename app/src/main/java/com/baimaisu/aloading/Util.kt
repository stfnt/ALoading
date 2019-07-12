package com.baimaisu.aloading

import android.content.Context
import android.net.ConnectivityManager


import java.util.Locale


object Util {

    val errorImage: String
        get() = "http://www." + System.currentTimeMillis() + ".com/abc.png"

    val randomImage: String
        get() {
            val id = (Math.random() * 100000).toInt()
            return String.format(Locale.CHINA, "https://www.thiswaifudoesnotexist.net/example-%d.jpg", id)
        }

    /**
     * check if the network connected or not
     * @param context context
     * @return true: connected, false:not, null:unknown
     */
    fun isNetworkConnected(context: Context): Boolean? {
        var context = context
        try {
            context = context.applicationContext
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                val networkInfo = cm.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        } catch (ignored: Exception) {
        }

        return null
    }
}
