package com.cakkie.ui.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.Dispatchers
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

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.clear()
            settings.clearAllPreference()
        }
    }

    private fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.updateUser(it)
        }
    }

    //delete account
    fun deleteAccount(reason: String) = NetworkCalls.delete<User>(
        endpoint = Endpoints.DELETE_ACCOUNT(reason),
        body = listOf()
    )

    //delete account
    fun reactivateAccount(onSuccess: (Boolean) -> Unit) {
        NetworkCalls.put<User>(
            endpoint = Endpoints.REACTIVATE_ACCOUNT,
            body = listOf()
        ).addOnSuccessListener {
            onSuccess(true)
            viewModelScope.launch {
                userRepository.updateUser(it)
            }
        }.addOnFailureListener {
            onSuccess(false)
        }
    }

    init {
        getProfile()
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