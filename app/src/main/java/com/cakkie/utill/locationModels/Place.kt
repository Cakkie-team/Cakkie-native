package com.cakkie.utill.locationModels

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val results: List<LocationResult> = emptyList(),
    val status: String
)