package com.cakkie.ui.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _notificationState = MutableStateFlow(NotificationState())

    val notificationState = _notificationState

    val user = _user

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever { _user.value = it }
        }
    }

    fun setPauseNotification(state: String) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.PAUSE_NOTIFICATIONS, state)
        }
    }

    fun setPostsAndComments(state: Boolean) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.POSTS_COMMENTS, state)
        }
    }

    fun setFollowingsAndFollowers(state: Boolean) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.FOLLOWING_FOLLOWERS, state)
        }
    }

    fun setEmailNotifications(state: Boolean) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.EMAIL_NOTIFICATIONS, state)
        }
    }

    fun setMessages(state: Boolean) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.MESSAGE, state)
        }
    }

    fun setProposal(state: Boolean) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.PROPOSAL, state)
        }
    }


    init {
        getUser()
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.PAUSE_NOTIFICATIONS, "")
                .asLiveData()
                .observeForever {
                    //update notification state
                    _notificationState.value = _notificationState.value.copy(pauseNotification = it)
                }

            settings.getPreference(SettingsConstants.POSTS_COMMENTS, false)
                .asLiveData()
                .observeForever {
                    _notificationState.value = _notificationState.value.copy(postAndComment = it)
                }

            settings.getPreference(SettingsConstants.EMAIL_NOTIFICATIONS, false)
                .asLiveData()
                .observeForever {
                    _notificationState.value = _notificationState.value.copy(emailNotification = it)
                }

            settings.getPreference(SettingsConstants.MESSAGE, false)
                .asLiveData()
                .observeForever {
                    _notificationState.value = _notificationState.value.copy(message = it)
                }

            settings.getPreference(SettingsConstants.PROPOSAL, false)
                .asLiveData()
                .observeForever {
                    _notificationState.value = _notificationState.value.copy(proposal = it)
                }

            settings.getPreference(SettingsConstants.FOLLOWING_FOLLOWERS, false)
                .asLiveData()
                .observeForever {
                    _notificationState.value =
                        _notificationState.value.copy(followingAndFollowers = it)
                }
        }
    }

}


data class NotificationState(
    var pauseNotification: String = "",
    var postAndComment: Boolean = false,
    var followingAndFollowers: Boolean = false,
    var emailNotification: Boolean = false,
    var message: Boolean = false,
    var proposal: Boolean = false,
)