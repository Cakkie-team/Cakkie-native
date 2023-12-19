package com.cakkie.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsConstants {
    val TOKEN = stringPreferencesKey("TOKEN")
    val NOTIFICATION_TIP = booleanPreferencesKey("NOTIFICATION_TIP")
}