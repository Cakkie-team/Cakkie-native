package com.cakkie.utill.locationModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val location: Location? = null,
    @SerialName("location_type")
    val locationType: String?,
    val viewport: Viewport?
)