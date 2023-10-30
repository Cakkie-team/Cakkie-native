package com.cakkie.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.cakkie.datastore.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

class SplashViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            settings.getPreference(SettingsConstants.TOKEN, "").asLiveData().observeForever {
//                _isLoggedIn.value = !it.isNullOrEmpty()
//            }
//        }
    }
}