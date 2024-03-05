package com.cakkie.utill.locationModels

data class Place(
    val results: List<LocationResult> = emptyList(),
    val status: String
)