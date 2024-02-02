package com.cakkie.utill

fun String.isVideoUrl(): Boolean {
    val videoExtensions = listOf("mp4", "avi", "mkv", "mov", "wmv", "flv", "webm")

    // Extract the file extension from the URL
    val fileExtension = this.substringAfterLast('.', "").lowercase()

    // Check if the file extension is in the list of video extensions
    return videoExtensions.contains(fileExtension)
}