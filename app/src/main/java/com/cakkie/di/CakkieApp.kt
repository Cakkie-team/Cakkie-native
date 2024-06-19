package com.cakkie.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.OptIn
import androidx.lifecycle.asLiveData
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.ExoDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import com.cakkie.socket.SocketClient
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.google.android.gms.ads.MobileAds
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.logger.UploadServiceLogger
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.io.File
import java.net.URISyntaxException

class CakkieApp : Application() {
    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        // Start Timber logging
        Timber.plant(Timber.DebugTree())
        // Start Koin dependency injection
        startKoin {
            modules(appModule)
            androidContext(this@CakkieApp)
        }
        AndroidThreeTen.init(this)
        //initialize admob
        MobileAds.initialize(this) {}

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
                    try {
                        if (it.isNullOrBlank().not()) {
                            val socketClient: SocketClient by inject()
                            socketClient.connect(it)
                        }
                    } catch (e: URISyntaxException) {
                        Timber.e(e)
                    }
                }
        }
        // Change the cache directory for ExoPlayer
        val cacheDirectory = File(getExternalFilesDir(null), "exo_cache_cakkie")
        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = ExoDatabaseProvider(this)
        simpleCache =
            SimpleCache(cacheDirectory, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)

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

        lateinit var simpleCache: SimpleCache
        const val exoPlayerCacheSize: Long = 90 * 1024 * 1024
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var exoDatabaseProvider: ExoDatabaseProvider
    }
}