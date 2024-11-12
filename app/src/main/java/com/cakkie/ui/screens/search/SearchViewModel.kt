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
    // private val shopViewModel: ShopViewModel by inject()

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

    val filteredAll: StateFlow<List<Any>> =
        searchQuery.debounce(500)
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
        loadShops()
    }

    // For single data flow
    private fun <T> StateFlow<String>.applyFilter(
        dataFlow: MutableStateFlow<List<T>>,
        filter: (T, String) -> Boolean
    ): StateFlow<List<T>> = debounce(500)
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
        _isSearching.value = true
        viewModelScope.launch {
            listingRepository.getListings()
                .onStart { _isSearching.value = true }
                .onEach { listings -> _filteredListings.value = listings }
                .onCompletion { _isSearching.value = false }
                .collectLatest {}
        }
    }

    fun loadJobs() {
        _isSearching.value = true
        viewModelScope.launch {
            jobViewModel.getJobs()
            jobViewModel.jobRes.observeForever { jobResponse ->
                _filteredJobs.value = jobResponse.data
                _isSearching.value = false
            }
        }
    }

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

    fun loadShops() {
        _isSearching.value = true
        viewModelScope.launch {
            val dummyShops = getDummyShops()
            _filteredShops.value = dummyShops
            _isSearching.value = false
        }
    }
}

fun getDummyShops(): List<ShopModel> {
    return listOf(
        ShopModel(
            id = 1.toString(),
            name = "Cakkie Foods",
            image = "https://koala.sh/api/image/v2-8dk70-v8lus.jpg?width=1344&height=768&dream",
            city = "Akwa Ibom"
        ),
        ShopModel(
            id = 2.toString(),
            name = "Bredan Bakery",
            image = "https://thumbs.dreamstime.com/b/cake-house-logo-design-template-shop-brand-icon-cupcake-sweet-bakery-vector-316934595.jpg",
            city = "Ondo State"
        ),
        ShopModel(
            id = 3.toString(),
            name = "LeftSide Cake Company",
            image = "https://miro.medium.com/v2/resize:fit:2000/1*yEmY69FOKn4ebaxWYfdtwA.jpeg",
            city = "Tech Plaza, Near University"
        ),
        ShopModel(
            id = 4.toString(),
            name = "The Cheese Cake Shop",
            image = "https://www.thecheesecakeshop.co.nz/pub/media/revslider/rebrand/the_cheesecake_shop_brand_refresh_mobile.jpg",
            city = "Fashion Street, Uptown"
        ),
        ShopModel(
            id = 5.toString(),
            name = "The Online Cake Shop",
            image = "https://work.vikijohnson.com/wp-content/uploads/2023/07/TOCS-Logo-mckp-1024x682.png",
            city = "Green Market, City Square"
        )
    )
}

