package com.cakkie.utill

import android.content.Context
import android.content.Intent
import android.widget.Toast
import timber.log.Timber

enum class ContentType {
    Cakespiration, Listing, Shop, Job, Profile
}

private val baseUri = "https://app.cakkie.com"

fun Context.share(
    contentType: ContentType,
    contentId: String,
    username: String? = null,
    shareMessage: String? = null
) {
    val deepLink = generateContentLink(contentId, username ?: "", contentType)
    val message = shareMessage ?: "Check out this ${contentType.name.lowercase()}:\n$deepLink"

    Timber.tag("Share").d("Generated Deep Link: %s", deepLink)

    // Create a share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check this out")
        putExtra(Intent.EXTRA_TEXT, message)
    }

    // start share intent
    val chooser = Intent.createChooser(shareIntent, "Share via")
    if (shareIntent.resolveActivity(this.packageManager) != null) {
        this.startActivity(chooser)
    } else {
        // Handle case when no activity is available to handle the intent
        Toast.makeText(this, "No app available to handle share action", Toast.LENGTH_SHORT)
            .show()
    }
}

private fun generateContentLink(id: String, username: String, type: ContentType): String {
    val usernameWithoutSpace = username.replace(" ", "")
    return if (type == ContentType.Profile) {
        "$baseUri/${usernameWithoutSpace}/$id"
    } else {
        "$baseUri/${type.name.lowercase()}/$id"
    }
}