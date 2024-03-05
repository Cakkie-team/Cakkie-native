package com.cakkie.utill.locationModels

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val lat: Double?,
    val lng: Double?
)