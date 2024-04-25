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
import com.cakkie.utill.locationModels.LocationResult
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

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

    fun uploadImage(image: File, path: String, fileName: String) =
        NetworkCalls.uploadFile(
            endpoint = Endpoints.UPLOAD_IMAGE(path, fileName), media = image
        )

    fun updateAddress(
        address: String,
        location: LocationResult,
    ) = NetworkCalls.put<User>(
        endpoint = Endpoints.UPDATE_PROFILE,
        body = listOf(
            Pair("address", address),
            Pair(
                "city",
                location.addressComponents.firstOrNull { it.types.contains("locality") }?.longName
                    ?: ""
            ),
            Pair(
                "state",
                location.addressComponents.firstOrNull { it.types.contains("administrative_area_level_1") }?.longName
                    ?: ""
            ),
            Pair("latitude", location.geometry?.location?.lat ?: 0.0),
            Pair("longitude", location.geometry?.location?.lng ?: 0.0),
            Pair(
                "country",
                location.addressComponents.firstOrNull { it.types.contains("country") }?.longName
                    ?: ""
            ),
        )
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    fun updateProfile(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        imageUrl: String,
        location: LocationResult,
    ) = NetworkCalls.put<User>(
        endpoint = Endpoints.UPDATE_PROFILE,
        body = listOf(
            Pair("firstName", firstName),
            Pair("lastName", lastName),
            Pair("address", address),
            Pair("phoneNumber", phone),
            Pair("profileImage", imageUrl),
            Pair(
                "city",
                location.addressComponents.firstOrNull { it.types.contains("locality") }?.longName
                    ?: ""
            ),
            Pair(
                "state",
                location.addressComponents.firstOrNull { it.types.contains("administrative_area_level_1") }?.longName
                    ?: ""
            ),
            Pair("latitude", location.geometry?.location?.lat ?: 0.0),
            Pair("longitude", location.geometry?.location?.lng ?: 0.0),
            Pair(
                "country",
                location.addressComponents.firstOrNull { it.types.contains("country") }?.longName
                    ?: ""
            ),
        )
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    fun getMyListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_MY_LISTINGS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _listings.value = it
    }

    fun getShopListings(id: String, page: Int = 0, size: Int = 20) =
        NetworkCalls.get<ListingResponse>(
            endpoint = Endpoints.GET_SHOP_LISTINGS(id, page, size),
            body = listOf()
        )

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

    fun getShop(id: String) = NetworkCalls.get<ShopModel>(
        endpoint = Endpoints.GET_SHOP(id),
        body = listOf()
    )

    fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    init {
        getProfile()
        getUser()
        getMyListings()
        getMyShop()
    }
}