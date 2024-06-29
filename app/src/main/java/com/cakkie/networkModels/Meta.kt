package com.cakkie.networkModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Meta(
    val flavour: String = "",
    val quantity: String = "",
    val shape: String = "",
    val size: String = "",
) : Parcelable {
    fun toListOfPairs(): List<Pair<String, String>> {
        return listOf(
            "Flavour" to flavour,
            "Quantity" to quantity,
            "Shape" to shape,
            "Size" to size
        )
    }
}