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
) : Parcelable