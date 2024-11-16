package com.cakkie.networkModels

import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import kotlinx.serialization.Serializable

@Serializable
data class SearchModel(
    val shops: List<ShopModel> = listOf(),
    val jobs: List<JobModel> = listOf(),
    val listings: List<Listing> = listOf(),
    val users: List<User> = listOf(),
    val transactions: List<Transaction> = listOf(),
    val meta: Pagination = Pagination(),
)