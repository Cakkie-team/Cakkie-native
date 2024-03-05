package com.cakkie.utill.locationModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResult(
    @SerialName("address_components")
    val addressComponents: List<AddressComponent> = emptyList(),
    @SerialName("formatted_address")
    val formattedAddress: String = "",
    val geometry: Geometry?,
    @SerialName("place_id")
    val placeId: String?,
    val types: List<String> = emptyList()
)
