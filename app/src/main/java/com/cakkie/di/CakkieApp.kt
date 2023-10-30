package com.cakkie.di

import android.app.Application
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
    }
}