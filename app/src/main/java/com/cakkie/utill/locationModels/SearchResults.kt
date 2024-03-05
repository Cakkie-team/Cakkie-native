package com.cakkie.utill.locationModels

data class SearchResults(
    val predictions: List<Prediction>,
    val status: String
)