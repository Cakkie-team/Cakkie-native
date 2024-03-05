package com.cakkie.utill.locationModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prediction(
    val description: String,
    @SerialName("place_id")
    val placeId: String,
    val reference: String,
    val types: List<String>
)