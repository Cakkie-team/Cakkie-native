package com.cakkie.networkModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Balance(
    val balance: Double = 0.0,
    val createdAt: String = "",
    val credit: Double = 0.0,
    val debit: Double = 0.0,
    val exchangeRate: Double = 0.0,
    val id: String = "",
    val isActive: Boolean = false,
    val name: String = "",
    val icon: String = "",
    val symbol: String = "",
    val type: String = "",
    val updatedAt: String = "",
    val withdrawalFee: Double = 0.0,
) : Parcelable