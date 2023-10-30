package com.cakkie.di

import com.cakkie.datastore.Settings
import com.cakkie.ui.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Settings(androidApplication())
    }

    //inject SplashViewModel
    viewModel {
        SplashViewModel()
    }
}