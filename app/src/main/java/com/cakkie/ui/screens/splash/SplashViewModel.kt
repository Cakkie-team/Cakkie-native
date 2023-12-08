package com.cakkie.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SplashViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()


    init {
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.TOKEN, "").asLiveData()
                .observeForever {
                    _isLoggedIn.value = it.isNotEmpty()
                }
        }
    }

}