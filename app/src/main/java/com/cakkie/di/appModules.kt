package com.cakkie.di

import androidx.room.Room
import com.cakkie.data.db.DB
import com.cakkie.data.repositories.UserRepository
import com.cakkie.datastore.Settings
import com.cakkie.ui.screens.auth.AuthViewModel
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.screens.notification.NotificationViewModel
import com.cakkie.ui.screens.orders.OrderViewModel
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
        SplashViewModel(get())
    }

    //inject AuthViewModel
    viewModel {
        AuthViewModel(get())
    }

    //inject ExploreViewModal
    viewModel {
        ExploreViewModal()
    }

    //inject NotificationViewModel
    viewModel {
        NotificationViewModel(get())
    }

//    //inject OrderViewModel
//    viewModel {
//        OrderViewModel(get())
//    }

    // Database
    single {

        Room.databaseBuilder(
            androidApplication(),
            DB::class.java,
            "cakkie.db",
        )
//            .openHelperFactory(get()) //TODO: enable this when we're going live
            .build()
    }

    // DAOs
    single { get<DB>().userDao() }

    // Repositories
    single { UserRepository(get()) }
}