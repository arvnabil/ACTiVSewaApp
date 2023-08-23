package com.activ.activsewa

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.activ.activsewa.network.HttpClient

class ActivSewa : MultiDexApplication() {
    companion object {
        lateinit var instance : ActivSewa
        fun getApp() : ActivSewa {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    //    Penyimpanan
    fun getPreferences() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    //    Set Token to buildRetrofitClient
    fun setToken(token:String) {
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
        HttpClient.getInstance().buildRetrofitClient(token)
    }

    //    get token
    fun getToken(): String? {
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    fun setUser(user:String) {
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
        HttpClient.getInstance().buildRetrofitClient(user)
    }

    fun getUser(): String? {
        return getPreferences().getString("PREFERENCES_USER", null)
    }
}