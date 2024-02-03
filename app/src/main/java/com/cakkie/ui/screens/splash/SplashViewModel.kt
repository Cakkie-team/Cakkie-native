package com.cakkie.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.datastore.Settings
import com.cakkie.datastore.SettingsConstants
import com.cakkie.networkModels.ListingResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val _isLoggedIn = MutableStateFlow(false)
    private val _isReady = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()
    val isReady = _isReady.asStateFlow()
    private val userRepository: UserRepository by inject()

    private fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
            _isReady.value = true
        }
    }

    fun getListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_LISTINGS(page, size),
        body = listOf()
    )

    init {
        viewModelScope.launch {
            delay(500)
            settings.getPreference(SettingsConstants.TOKEN, "").asLiveData()
                .observeForever {
                    _isLoggedIn.value = it.isNotEmpty()
                    if (it.isNotEmpty()) {
                        getProfile()
                    } else {
                        _isReady.value = true
                    }
                }
        }
    }

}