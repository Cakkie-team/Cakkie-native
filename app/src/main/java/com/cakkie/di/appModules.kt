package com.cakkie.di

import androidx.room.Room
import com.cakkie.data.db.DB
import com.cakkie.data.repositories.ListingRepository
import com.cakkie.data.repositories.UserRepository
import com.cakkie.datastore.Settings
import com.cakkie.socket.SocketClient
import com.cakkie.ui.screens.auth.AuthViewModel
import com.cakkie.ui.screens.chat.ChatViewModel
import com.cakkie.ui.screens.explore.ExploreViewModal
import com.cakkie.ui.screens.jobs.JobsViewModel
import com.cakkie.ui.screens.notification.NotificationViewModel
import com.cakkie.ui.screens.orders.OrderViewModel
import com.cakkie.ui.screens.profile.ProfileViewModel
import com.cakkie.ui.screens.settings.SettingsViewModel
import com.cakkie.ui.screens.shop.ShopViewModel
import com.cakkie.ui.screens.splash.SplashViewModel
import com.cakkie.ui.screens.wallet.WalletViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Settings(androidApplication())
    }

    single {
        SocketClient()
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

    //inject shopViewModel
    viewModel {
        ShopViewModel()
    }

    //inject NotificationViewModel
    viewModel {
        NotificationViewModel(get())
    }

    //inject SettingsViewModel
    viewModel {
        SettingsViewModel(get())
    }

    //inject ProfileViewModel
    viewModel {
        ProfileViewModel()
    }

    //inject ChatViewModel
    viewModel {
        ChatViewModel()
    }

    //inject OrderViewModel
    viewModel {
        OrderViewModel()
    }

    //inject WalletViewModel
    viewModel {
        WalletViewModel()
    }

    //inject JobViewModel
    viewModel {
        JobsViewModel()
    }
    // Database
    single {

        Room.databaseBuilder(
            androidApplication(),
            DB::class.java,
            "cakkie.db",
        )
            .fallbackToDestructiveMigration()
//            .openHelperFactory(get()) //TODO: enable this when we're going live
            .build()
    }

    // DAOs
    single { get<DB>().userDao() }
    single { get<DB>().listingDao() }

    // Repositories
    single { UserRepository(get()) }
    single { ListingRepository(get()) }


}