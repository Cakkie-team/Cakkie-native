package com.cakkie.data.db.models

import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cakkie.networkModels.Comment
import com.cakkie.networkModels.CurrencyRate
import com.cakkie.networkModels.Meta
import com.cakkie.networkModels.Pagination
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "listing")
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
    val type: String = "",
    @PrimaryKey
    val id: String = "",
    val isLiked: Boolean = false,
    val isStarred: Boolean = false,
    val media: List<String> = listOf(""),
    @Embedded(prefix = "meta_")
    val meta: Meta = Meta(),
    val name: String = "",
    val currencySymbol: String = "",
    val price: List<Int> = listOf(0),
    @Embedded(prefix = "currency_")
    val currency: CurrencyRate = CurrencyRate(),
    @Embedded(prefix = "shop_")
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
