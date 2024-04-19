package com.cakkie.networkModels

import android.os.Parcelable
import com.cakkie.data.db.models.ShopModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ShopResponse(
    val message: String = "",
    val shop: ShopModel = ShopModel(),
    val exists: Boolean = false
) : Parcelable