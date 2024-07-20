package com.cakkie.ui.screens.jobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.JobResponse
import com.cakkie.networkModels.OrderResponse
import com.cakkie.networkModels.PreferenceModel
import com.cakkie.networkModels.Proposal
import com.cakkie.networkModels.ProposalResponse
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
    private val _myJobRes = MutableLiveData<JobResponse>()
    private val _orders = MutableLiveData<OrderResponse>()
    private val _contracts = MutableLiveData<OrderResponse>()
    private val _proposals = MutableLiveData<ProposalResponse>()
    private val _preference = MutableLiveData<PreferenceModel>()

    val user = _user
    val shop = _shop
    val jobRes = _jobRes
    val myJobRes = _myJobRes
    val orders = _orders
    val proposals = _proposals
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
        salary: Double,
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
        endpoint = Endpoints.CREATE_JOB,
        body = listOf(
            Pair("salary", salary),
            Pair("productType", productType),
            Pair("deadline", deadline),
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


    fun submitProposal(
        deadline: String,
        proposalAmount: Double,
        message: String,
        jobId: String
    ) = NetworkCalls.post<Proposal>(
        endpoint = Endpoints.SUBMIT_PROPOSAL(jobId),
        body = listOf(
            Pair("proposedPrice", proposalAmount),
            Pair("message", message),
            Pair("proposedDeadline", deadline),
        )
    )

    fun awardContract(
        proposalId: String,
        pin: String,
        jobId: String
    ) = NetworkCalls.post<Proposal>(
        endpoint = Endpoints.AWARD_CONTRACT(jobId),
        body = listOf(
            Pair("pin", pin),
            Pair("proposalId", proposalId)
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

    fun getProposals(id: String, page: Int = 0, size: Int = 20) =
        NetworkCalls.get<ProposalResponse>(
            endpoint = Endpoints.GET_PROPOSALS(id, page, size),
            body = listOf()
        ).addOnSuccessListener {
            _proposals.value = it
        }

    fun getProposal(id: String) = NetworkCalls.get<Proposal>(
        endpoint = Endpoints.GET_PROPOSAL(id),
        body = listOf()
    )
    fun myJobs(page: Int = 0, size: Int = 20) = NetworkCalls.get<JobResponse>(
        endpoint = Endpoints.GET_MY_JOBS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _myJobRes.value = it
    }


    fun getJob(id: String) = NetworkCalls.get<JobModel>(
        endpoint = Endpoints.GET_JOB(id),
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
        getMyShop()
        getPreference()
//        getMyListings()
    }
}