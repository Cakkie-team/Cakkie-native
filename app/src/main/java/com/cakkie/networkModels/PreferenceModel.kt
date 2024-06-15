package com.cakkie.networkModels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PreferenceModel(
    val deliveryFeePerKm: Double = 0.0,
    val earningRate: Double = 0.0,
    val premiumMonthlyFee: Double = 0.0,
    val premiumYearlyFee: Double = 0.0,
    val refferalReward: Double = 0.0,
    val salesCommission: Double = 0.0,
) : Parcelable