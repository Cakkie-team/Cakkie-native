package com.cakkie.ui.screens.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()

    val user = _user


    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    init {
        getUser()
    }
}