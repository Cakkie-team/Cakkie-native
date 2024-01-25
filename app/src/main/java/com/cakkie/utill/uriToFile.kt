package com.cakkie.utill

import android.content.Context
import android.net.Uri
import java.io.File

fun Context.createTmpFileFromUri(uri: Uri, fileName: String): File? {
    return try {
        val stream = this.contentResolver.openInputStream(uri)
        val file = File.createTempFile(fileName, "", this.cacheDir)
        org.apache.commons.io.FileUtils.copyInputStreamToFile(stream, file)
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}