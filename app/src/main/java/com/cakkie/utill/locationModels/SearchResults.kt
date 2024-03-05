package com.cakkie.utill.locationModels

import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val predictions: List<Prediction>,
    val status: String
)