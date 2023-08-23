package com.activ.activsewa.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferenceHelper {

    val USER_ID = "USER_ID"
    val USER_NAME = "NAME"
    val USER_EMAIL = "EMAIL"
    val USER_TOKEN = "TOKEN"
    val USER_ROLES = "ROLES"
    val USER = "USER"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    inline fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    var SharedPreferences.userId
        get() = getInt(USER_ID, 0)
        set(value) {
            editMe {
                it.putInt(USER_ID, value)
            }
        }

    var SharedPreferences.token
        get() = getString(USER_TOKEN, "")
        set(value) {
            editMe {
                it.putString(USER_TOKEN, value)
            }
        }

    var SharedPreferences.user
        get() = getString(USER, "")
        set(value) {
            editMe {
                it.putString(USER, value)
            }
        }

    var SharedPreferences.email
        get() = getString(USER_EMAIL, "")
        set(value) {
            editMe {
                it.putString(USER_EMAIL, value)
            }
        }

    var SharedPreferences.name
        get() = getString(USER_NAME, "")
        set(value) {
            editMe {
                it.putString(USER_NAME, value)
            }
        }

    var SharedPreferences.roles
        get() = getString(USER_ROLES, "")
        set(value) {
            editMe {
                it.putString(USER_ROLES, value)
            }
        }

    var SharedPreferences.clearValues
        get() = {}
        set(value) {
            editMe {
                it.remove(USER_ID)
                it.remove(USER_NAME)
                it.remove(USER_EMAIL)
                it.remove(USER_TOKEN)
                it.clear()
            }
        }
}
