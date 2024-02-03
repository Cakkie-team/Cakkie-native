package com.cakkie.ui.screens.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Listing
import com.cakkie.networkModels.ListingResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExploreViewModal : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _listings = MutableLiveData<ListingResponse>()

    val listings = _listings
    val user = _user


    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    fun getListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_LISTINGS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _listings.value = it
    }

    fun getListing(id: String) = NetworkCalls.get<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf()
    )


    init {
        getUser()
        getListings()
    }
}