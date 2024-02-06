package com.cakkie.networkModels

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val currentPage: Int = 0,
    val nextPage: Int = 0,
    val previousPage: Int = 0,
    val pageSize: Int = 0,
)