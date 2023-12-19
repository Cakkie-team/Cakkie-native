package com.cakkie.ui.screens.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NotificationViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val _isNotificationTipShown = MutableStateFlow(false)

    val isNotificationTipShown = _isNotificationTipShown

    fun setNotificationTipShown() {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.NOTIFICATION_TIP, true)
        }
    }

    init {
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.NOTIFICATION_TIP, false).asLiveData()
                .observeForever {
                    _isNotificationTipShown.value = it
                }
        }
    }

}


