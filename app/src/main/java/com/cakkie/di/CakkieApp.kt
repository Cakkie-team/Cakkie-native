package com.cakkie.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.asLiveData
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.logger.UploadServiceLogger
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CakkieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Timber logging
        Timber.plant(Timber.DebugTree())
        // Start Koin dependency injection
        startKoin {
            modules(appModule)
            androidContext(this@CakkieApp)
        }

        // Create notification channel
        createNotificationChannel()
        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = NOTIFICATION_CHANNEL_ID,
            debug = true //BuildConfig.DEBUG
        )
        UploadServiceLogger.setDelegate(object : UploadServiceLogger.Delegate {
            override fun error(
                component: String,
                uploadId: String,
                message: String,
                exception: Throwable?
            ) {
                Timber.e(exception)
                Timber.e(message)
            }

            override fun debug(component: String, uploadId: String, message: String) {
                Timber.d("component: $component message: $message")
            }

            override fun info(component: String, uploadId: String, message: String) {
                Timber.i("component: $component message: $message")
            }
        })

        val settings: Settings by inject()

        CoroutineScope(Dispatchers.Main).launch {
            settings.getPreference(SettingsConstants.TOKEN, "").asLiveData()
                .observeForever {
                    Timber.d("Token in: $it")

                    FuelManager.instance.apply {
                        baseHeaders = if (it.isNullOrBlank()) {
                            mapOf(Headers.CONTENT_TYPE to "application/json")
                        } else {
                            mapOf(
                                Headers.CONTENT_TYPE to "application/json",
                                Headers.AUTHORIZATION to "Bearer $it"
                            )
                        }
                    }
                }
        }

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