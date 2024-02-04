package com.cakkie.networkModels

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.cakkie.data.db.models.ShopModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Stable
data class Listing(
    @SerialName("Comment")
    val comments: List<Comment> = listOf(),
    val available: Boolean = false,
    val availablity: String = "",
    val commentCount: Int = 0,
    val createdAt: String = "",
    val description: String = "",
    val id: String = "",
    val isLiked: Boolean = false,
    val isStarred: Boolean = false,
    val media: List<String> = listOf(),
    val meta: Meta = Meta(),
    val name: String = "",
    val price: List<Int> = listOf(),
    val shop: ShopModel = ShopModel(),
    val shopId: String = "",
    val sizes: List<String> = listOf(),
    val totalLikes: Int = 0,
    val updatedAt: String = "",
) : Parcelable


@Serializable
data class ListingResponse(
    val data: List<Listing> = listOf(),
    val meta: Pagination = Pagination(),
)

@Serializable
data class Pagination(
    val currentPage: Int = 0,
    val nextPage: Int = 0,
    val previousPage: Int = 0,
    val pageSize: Int = 0,
)
