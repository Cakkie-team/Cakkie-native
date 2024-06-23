package com.cakkie.networkModels

import java.io.File

data class FileModel(
    val file: File,
    val url: String,
    val mediaMimeType: String = file.extension
)