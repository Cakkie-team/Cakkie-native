package com.cakkie.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ListingResponse
import com.cakkie.data.db.models.ShopModel
import com.cakkie.networkModels.JobModel
import com.cakkie.networkModels.SearchModel
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

enum class SearchType {
    Listing, Job, Shop, All, Transaction, User
}

class SearchViewModel : ViewModel(), KoinComponent {
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _initialListings = MutableStateFlow<List<Listing>>(emptyList())
    val initialListings: StateFlow<List<Listing>> = _initialListings

    private val _searchedListings = MutableStateFlow<List<Listing>>(emptyList())
    val searchedListings: StateFlow<List<Listing>> = _searchedListings

    private val _searchedJobs = MutableStateFlow<List<JobModel>>(emptyList())
    val searchedJobs: StateFlow<List<JobModel>> = _searchedJobs

    private val _searchedShops = MutableStateFlow<List<ShopModel>>(emptyList())
    val searchedShops: StateFlow<List<ShopModel>> = _searchedShops

    val searchedAllResults: StateFlow<List<Any>> = combine(
        searchedListings, searchedJobs, searchedShops
    ) { listings, jobs, shops ->
        listings + jobs + shops
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadListings()
    }

    fun onSearchQueryChanged(query: String, tab: String) {
        _searchQuery.update { query }
        if (query.isBlank()) {
            viewModelScope.launch {
                _isSearching.update { false }
                _searchedListings.update { _initialListings.value }
                _searchedShops.update { emptyList() }
                _searchedJobs.update { emptyList() }
            }
        } else {
            performSearch(query, SearchType.valueOf(tab))
            _isSearching.update { query.isNotBlank() }
        }
    }

    private fun performSearch(query: String, type: SearchType, page: Int = 0, size: Int = 50) =
        NetworkCalls.get<SearchModel>(
            endpoint = Endpoints.SEARCH(query, page, size, type.name),
            body = listOf()
        ).addOnSuccessListener { res ->
            viewModelScope.launch {
                _searchedListings.update { res.listings }
                _searchedShops.update { res.shops }
                _searchedJobs.update { res.jobs }
                _isSearching.update { false }
            }
        }.addOnFailureListener { exception ->
            viewModelScope.launch {
                _isSearching.update { false }
                Timber.e(exception, "Failed to fetch search results")
            }
        }

    fun loadListings(page: Int = 0, size: Int = 50) =
        NetworkCalls.get<ListingResponse>(
            endpoint = Endpoints.GET_LISTINGS(page, size),
            body = listOf()
        ).addOnSuccessListener { listings ->
            viewModelScope.launch {
                _initialListings.update { listings.data }
                _searchedListings.update { listings.data }
                Timber.tag("SearchViewModel").d("Listings fetched: %s", listings.data.size)
            }
        }.addOnFailureListener { exception ->
            viewModelScope.launch {
                Timber.e(exception, "Failed to fetch listings")
            }
        }
}



