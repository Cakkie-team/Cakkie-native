package com.cakkie.networkModels

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val flavour: String = "",
    val quantity: String = "",
    val shape: String = "",
)