package com.cakkie.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CakkieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin dependency injection
        startKoin {
            modules(appModule)
            androidContext(this@CakkieApp)
        }

        // Create notification channel
        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Cakkie notification",
                NotificationManager.IMPORTANCE_HIGH,
            )
            channel.description = "Cakkie notification"
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        // ID of the notification channel used by upload service. This is needed by Android API 26+
        // but you have to always specify it even if targeting lower versions, because it's handled
        // by AndroidX AppCompat library automatically
        const val NOTIFICATION_CHANNEL_ID = "Cakkie"
    }
}