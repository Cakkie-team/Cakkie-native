package com.cakkie.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsConstants {
    val TOKEN = stringPreferencesKey("TOKEN")
    val NOTIFICATION_TIP = booleanPreferencesKey("NOTIFICATION_TIP")
    val PAUSE_NOTIFICATIONS = stringPreferencesKey("PAUSE_NOTIFICATIONS")
    val POSTS_COMMENTS = booleanPreferencesKey("POSTS_AND_COMMENTS")
    val EMAIL_NOTIFICATIONS = booleanPreferencesKey("EMAIL_NOTIFICATION")
    val MESSAGE = booleanPreferencesKey("MESSAGE")
    val PROPOSAL = booleanPreferencesKey("PROPOSAL")
    val FOLLOWING_FOLLOWERS = booleanPreferencesKey("FOLLOWING_AND_FOLLOWERS")

}