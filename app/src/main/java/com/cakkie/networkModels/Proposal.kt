package com.cakkie.networkModels

import com.cakkie.data.db.models.ShopModel

data class Proposal(
    val id: String = "",
    val shop: ShopModel = ShopModel(),
    val shopId: String = "",
    val jobId: String = "",
    val message: String = "",
    val media: List<String> = listOf(),
    val status: String = "",
    val proposedPrice: Double = 0.0,
    val proposedDeadline: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
)
