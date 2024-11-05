package com.cakkie.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.Listing
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel(), KoinComponent {
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _listings = MutableStateFlow<List<Listing>>(emptyList())
    val listings = _listings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _filteredListings = MutableStateFlow<List<Listing>>(emptyList())
    val filteredListings: StateFlow<List<Listing>> = _filteredListings

    init {
        viewModelScope.launch {
            _searchQuery.debounce(300).collectLatest { query ->
                performSearch(query)
            }
        }
        // load data
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            val filteredResults = if (query.isBlank()) {
                _listings.value
            } else {
                _listings.value.filter { listing ->
                    listing.name.contains(query, ignoreCase = true)
                }
            }
            _filteredListings.value = filteredResults
            _isSearching.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _isSearching.value = query.isNotBlank()
    }
}