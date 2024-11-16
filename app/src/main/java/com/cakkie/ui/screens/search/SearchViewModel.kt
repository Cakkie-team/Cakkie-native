package com.cakkie.ui.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.repositories.ListingRepository
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.SearchModel
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel(), KoinComponent {
    private val listingRepository: ListingRepository by inject()
    // private val shopViewModel: ShopViewModel by inject()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    //for searched listing
    private val _searchedListing = MutableStateFlow<List<Listing>>(emptyList())

    val searchedListing: StateFlow<List<Listing>> = _searchedListing

    private val _filteredListings = MutableStateFlow<List<Listing>>(emptyList())
    private val _filteredJobs = MutableStateFlow<List<JobModel>>(emptyList())
    private val _filteredShops = MutableStateFlow<List<ShopModel>>(emptyList())

    val filteredListings: StateFlow<List<Listing>> = _filteredListings

    val filteredJobs: StateFlow<List<JobModel>> = _filteredJobs

    val filteredShops: StateFlow<List<ShopModel>> = _filteredShops

    val filteredAll: StateFlow<List<Any>> = combine(
        searchedListing, filteredJobs, filteredShops
    ) { listings, jobs, shops ->
        listings + jobs + shops
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadListings()
    }

    // For single data flow
    private fun <T> StateFlow<String>.applyFilter(
        dataFlow: MutableStateFlow<List<T>>,
        filter: (T, String) -> Boolean
    ): StateFlow<List<T>> = debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(dataFlow) { query, items ->
            if (query.isBlank()) items else items.filter { filter(it, query) }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String, tab: String) {
        _searchQuery.update { query }
        search(query, SearchType.valueOf(tab))
        _isSearching.update { query.isNotBlank() }
    }

    private fun loadListings() {
        viewModelScope.launch {
            listingRepository.getListings()
//                .onStart { _isLoading.postValue(true)  }
                .onEach { listings ->
                    _filteredListings.value = listings
                }
//                .onCompletion { _isLoading.postValue(false) }
                .collect {}
        }
    }

//    fun loadJobs() {
//        viewModelScope.launch {
//            _isLoading.update { true }
//            jobViewModel.getJobs()
//            jobViewModel.jobRes.observeForever { jobResponse ->
//                _filteredJobs.value = jobResponse.data
//                _isLoading.update { false }
//            }
//        }
//    }

//    private fun loadShop() {
//        _isSearching.value = true
//        viewModelScope.launch {
//            shopViewModel.getShops()
//            shopViewModel.shopRes.observeForever { shopResponse ->
//                _filteredShops.value = shopResponse.data
//                _isSearching.value = false
//            }
//        }
//    }

//    fun loadShops() {
//        viewModelScope.launch {
//            _isLoading.update { true }
//            val dummyShops = getDummyShops()
//            _filteredShops.value = dummyShops
//            _isLoading.update { false }
//        }
//    }

    fun search(query: String, type: SearchType, page: Int = 0, size: Int = 50) =
        NetworkCalls.get<SearchModel>(
            endpoint = Endpoints.SEARCH(query, page, size, type.name),
            body = listOf()
        ).addOnSuccessListener { res ->
            _isSearching.update { false }
            _searchedListing.update { res.listings }
            _filteredShops.update { res.shops }
            _filteredJobs.update { res.jobs }
        }
}

enum class SearchType {
    listing, job, shop, all, transaction, user
}


