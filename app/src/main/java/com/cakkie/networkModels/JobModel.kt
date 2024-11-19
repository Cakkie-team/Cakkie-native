package com.cakkie.networkModels

import android.os.Parcelable
import com.cakkie.data.db.models.User
import com.cakkie.ui.screens.shop.MediaModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class JobModel(
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val createdAt: String = "",
    val currencySymbol: String = "",
    val deadline: String = "",
    val description: String = "",
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val media: List<String> = listOf(),
    val meta: Meta = Meta(),
    val productType: String = "",
    val proposalFee: Double = 0.0,
    val salary: Double = 0.0,
    val state: String = "",
    val status: String = "",
    val title: String = "",
    val updatedAt: String = "",
    val userId: String = "",
    val user: User = User(),
    val hasApplied: Boolean = false,
    val totalProposal: Int = 0,
    val hasEnoughBalance: Boolean = false,
) : Parcelable

@Serializable
data class JobResponse(
    val data: List<JobModel> = listOf(),
    val meta: Pagination = Pagination(),
)

@Parcelize
data class JobEdit(
    val job: JobModel = JobModel(),
    val media: List<MediaModel> = listOf(),
) : Parcelable