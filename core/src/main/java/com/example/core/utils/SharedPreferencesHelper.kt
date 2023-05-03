package com.example.core.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE)

    fun putInt(key: String?, value: Int) {
        val editor = sharedPrefs.edit()
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String?): Int {
        return sharedPrefs.getInt(key, 0)
    }

    fun putBoolean(key: String?, value: Boolean) {
        val editor = sharedPrefs.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPrefs.getBoolean(key, false)
    }

    fun putString(key: String?, value: String?) {
        val editor = sharedPrefs.edit()
        editor.putString(key, value).apply()
    }

    fun getString(key: String?): String? {
        return sharedPrefs.getString(key, "")
    }

    fun putTimeGetAccessToken(key: String?, time: Long) {
        val editor = sharedPrefs.edit()
        editor.putLong(key, time).apply()
    }

    fun putTimeGetRefreshToken(key: String?, time: Long) {
        val editor = sharedPrefs.edit()
        editor.putLong(key, time).apply()
    }

    fun getTimeToken(key: String?): Long {
        return sharedPrefs.getLong(key, 0)
    }

    fun getTimeRefreshToken(key: String?): Long {
        return sharedPrefs.getLong(key, 0)
    }
}
