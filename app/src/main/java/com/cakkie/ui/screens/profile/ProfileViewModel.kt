package com.cakkie.ui.screens.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.ListingResponse
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfileViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _listings = MutableLiveData<ListingResponse>()
    private val _shop = MutableLiveData<ShopModel>()

    val shop = _shop
    val listings = _listings
    val user = _user

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    fun getMyListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_MY_LISTINGS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _listings.value = it
    }

    private fun getMyShop() = NetworkCalls.get<ShopModel>(
        endpoint = Endpoints.CREATE_SHOP,
        body = listOf()
    ).addOnSuccessListener {
        _shop.value = it
    }

    fun getListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_LISTINGS(page, size),
        body = listOf()
    )

    init {
        getUser()
        getMyListings()
        getMyShop()
    }
}