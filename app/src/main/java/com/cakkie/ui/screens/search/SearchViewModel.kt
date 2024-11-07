package com.cakkie.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.repositories.ListingRepository
import com.cakkie.networkModels.JobModel
import com.cakkie.ui.screens.jobs.JobsViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel(), KoinComponent {
    private val listingRepository: ListingRepository by inject()
    private val jobViewModel: JobsViewModel by inject()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredListings = MutableStateFlow<List<Listing>>(emptyList())
    private val _filteredJobs = MutableStateFlow<List<JobModel>>(emptyList())
    private val _filteredShops = MutableStateFlow<List<ShopModel>>(emptyList())

    val filteredListings: StateFlow<List<Listing>> =
        searchQuery.applyFilter(_filteredListings) { item, query ->
            item.name.contains(query, ignoreCase = true)
        }

    val filteredJobs: StateFlow<List<JobModel>> =
        searchQuery.applyFilter(_filteredJobs) { item, query ->
            item.title.contains(query, ignoreCase = true)
        }

    val filteredShops: StateFlow<List<ShopModel>> =
        searchQuery.applyFilter(_filteredShops) { item, query ->
            item.name.contains(query, ignoreCase = true)
        }

    val filteredAll: StateFlow<List<Any>> = searchQuery.debounce(300)
        .onStart { _isSearching.value = true }
        .combine(filteredListings) { _, listings -> listings }
        .combine(filteredJobs) { listings, jobs -> listings + jobs }
        .combine(filteredShops) { combinedList, shops ->
            combinedList + shops
        }
        .map { combinedList ->
            combinedList.filter { it.toString().contains(searchQuery.value, ignoreCase = true) }
        }
        .onCompletion { _isSearching.value = false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadListings()
        loadJobs()
        // loadShops()
    }

    // For single data flow
    private fun <T> StateFlow<String>.applyFilter(
        dataFlow: MutableStateFlow<List<T>>,
        filter: (T, String) -> Boolean
    ): StateFlow<List<T>> = debounce(300)
        .onStart { _isSearching.value = true }
        .combine(dataFlow) { query, items ->
            if (query.isBlank()) items else items.filter { filter(it, query) }
        }
        .onCompletion { _isSearching.value = false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // _isSearching.value = query.isNotBlank()
    }

    fun loadListings() {
        viewModelScope.launch {
            listingRepository.getListings()
                .onStart { _isSearching.value = true }
                .onEach { listings -> _filteredListings.value = listings.shuffled() }
                .onCompletion { _isSearching.value = false }
                .collectLatest {}
        }
    }


    private fun loadJobs() {
        viewModelScope.launch {
            jobViewModel.getJobs()
            jobViewModel.jobRes.observeForever { jobResponse ->
                _filteredJobs.value = jobResponse.data
                _isSearching.value = false
            }
        }
    }
}

