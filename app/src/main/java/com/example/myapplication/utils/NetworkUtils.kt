package com.example.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {
    
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }
    
    fun getErrorMessage(e: Throwable): String {
        return when {
            e.message?.contains("Job was cancelled") == true -> 
                "Operação cancelada"
            e.message?.contains("cancelled") == true -> 
                "Operação cancelada"
            e.message?.contains("timeout") == true -> 
                "Timeout de conexão. Verifique sua internet."
            e.message?.contains("No address associated with hostname") == true -> 
                "Servidor não encontrado. Verifique se o servidor Node.js está rodando."
            e.message?.contains("Connection refused") == true -> 
                "Conexão recusada. Verifique se o servidor está rodando na porta 3000."
            else -> "Erro de conexão: ${e.message}"
        }
    }
}

