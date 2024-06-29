package com.cakkie.ui.screens.jobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.JobResponse
import com.cakkie.networkModels.Order
import com.cakkie.networkModels.OrderResponse
import com.cakkie.networkModels.PreferenceModel
import com.cakkie.utill.Endpoints
import com.cakkie.utill.JsonBody
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.util.Locale

class JobsViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _shop = MutableLiveData<ShopModel>()
    private val _jobRes = MutableLiveData<JobResponse>()
    private val _orders = MutableLiveData<OrderResponse>()
    private val _contracts = MutableLiveData<OrderResponse>()
    private val _preference = MutableLiveData<PreferenceModel>()

    val user = _user
    val shop = _shop
    val jobRes = _jobRes
    val orders = _orders
    val contracts = _contracts
    val preference = _preference

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    private fun getPreference() = NetworkCalls.get<PreferenceModel>(
        endpoint = Endpoints.GET_PREFERENCE,
        body = listOf()
    ).addOnSuccessListener {
        _preference.value = it
    }


    fun createJob(
        salary: String,
        productType: String,
        deadline: String,
        proposalFee: Double,
        address: String,
        city: String,
        state: String,
        country: String,
        latitude: Double,
        longitude: Double,
        currencySymbol: String,
        title: String,
        media: List<String>,
        description: String,
        pin: String,
        meta: List<Pair<String, Any?>>
    ) = NetworkCalls.post<JobModel>(
        endpoint = Endpoints.CREATE_ORDER,
        body = listOf(
            Pair("salary", salary),
            Pair("productType", productType),
            Pair("quantity", deadline),
            Pair("city", city),
            Pair("state", state),
            Pair("country", country),
            Pair("address", address),
            Pair("proposalFee", proposalFee),
            Pair("latitude", latitude),
            Pair("longitude", longitude),
            Pair("currencySymbol", currencySymbol.uppercase(Locale.ROOT)),
            Pair("pin", pin),
            Pair("title", title),
            Pair("media", media),
            Pair("description", description),
            Pair("meta", JsonBody.generate(meta))
        )
    )


    fun uploadImage(image: File, path: String, fileName: String) =
        NetworkCalls.uploadFile(
            endpoint = Endpoints.UPLOAD_IMAGE(path, fileName), media = image
        )

    fun getMyShop() = NetworkCalls.get<ShopModel>(
        endpoint = Endpoints.CREATE_SHOP,
        body = listOf()
    ).addOnSuccessListener {
        _shop.value = it
    }

    fun getJobs(page: Int = 0, size: Int = 20) = NetworkCalls.get<JobResponse>(
        endpoint = Endpoints.GET_JOBS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _jobRes.value = it
    }


    fun getJob(id: String) = NetworkCalls.get<JobModel>(
        endpoint = Endpoints.GET_JOB(id),
        body = listOf()
    )

    fun getOrder(id: String) = NetworkCalls.get<Order>(
        endpoint = Endpoints.GET_ORDER(id),
        body = listOf()
    )

    fun setAvailability(id: String, available: Boolean) = NetworkCalls.put<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf(Pair("available", available))
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
        getMyShop()
        getPreference()
//        getMyListings()
    }
}