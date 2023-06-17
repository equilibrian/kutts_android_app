/*
 * Copyright (c) 2023 Denis Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class Util {

    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            val networkCallback = ConnectivityManager.NetworkCallback()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val isInternetAvailable =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            connectivityManager.unregisterNetworkCallback(networkCallback)

            return isInternetAvailable
        }
    }
}