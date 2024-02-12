package com.cakkie.ui.screens.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import com.cakkie.networkModels.NotificationResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NotificationViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val _isNotificationTipShown = MutableStateFlow(false)
    private val _notifications = MutableLiveData<NotificationResponse>()

    val notifications = _notifications

    val isNotificationTipShown = _isNotificationTipShown

    fun setNotificationTipShown() {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.NOTIFICATION_TIP, true)
        }
    }

    fun getNotifications(page: Int = 0, size: Int = 20) =
        NetworkCalls.get<NotificationResponse>(
            endpoint = Endpoints.GET_NOTIFICATIONS(page, size),
            body = listOf()
        ).addOnSuccessListener {
            _notifications.value = it
        }

    init {
        getNotifications()
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.NOTIFICATION_TIP, false).asLiveData()
                .observeForever {
                    _isNotificationTipShown.value = it
                }
        }
    }

}


