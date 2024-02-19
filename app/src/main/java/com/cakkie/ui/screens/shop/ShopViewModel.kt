package com.cakkie.ui.screens.shop

import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.LoginResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

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


    //create shop
    fun createShop(
        name: String,
        description: String,
        address: String,
        imageUrl: String,
        location: Address,
    ) =
        NetworkCalls.post<LoginResponse>(
            endpoint = Endpoints.CREATE_SHOP, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("address", address),
                Pair("city", location.subAdminArea),
                Pair("state", location.adminArea),
                Pair("latitude", location.latitude),
                Pair("longitude", location.longitude),
                Pair("country", location.countryName),
                Pair("image", imageUrl),
            )
        )


    fun uploadImage(image: File, path: String, fileName: String) =
        NetworkCalls.uploadFile(
            endpoint = Endpoints.UPLOAD_IMAGE(path, fileName), media = image
        )

    init {
        getUser()
    }
}