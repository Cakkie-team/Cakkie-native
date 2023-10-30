package com.cakkie.di

import com.cakkie.ui.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //inject SplashViewModel
    viewModel {
        SplashViewModel()
    }
}