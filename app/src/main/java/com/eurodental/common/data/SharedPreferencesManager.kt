package com.eurodental.common.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context : Context
) {

    companion object {
        private const val SHARED_PREFERENCES_KEY = "GENERAL_SHARED_PREFERENCES"
    }

    fun save(key : String, value : String) {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_KEY,Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(key,value)
            apply()
        }
    }

    fun retrieve(key : String) : String? {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_KEY,Context.MODE_PRIVATE)
        return sharedPref.getString(key,null)
    }
}