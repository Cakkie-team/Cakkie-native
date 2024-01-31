package com.cakkie.ui.screens.shop

import android.content.Context
import android.location.Address
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.loader.content.CursorLoader
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.LoginResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.io.File

class ShopViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _shop = MutableLiveData<ShopModel>()

    val user = _user
    val shop = _shop

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }


    //create shop
    fun createShop(
        name: String,
        description: String,
        address: String,
        imageUrl: String,
        location: Address,
    ) =
        NetworkCalls.post<LoginResponse>(
            endpoint = Endpoints.CREATE_SHOP, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("address", address),
                Pair("city", location.subAdminArea),
                Pair("state", location.adminArea),
                Pair("latitude", location.latitude),
                Pair("longitude", location.longitude),
                Pair("country", location.countryName),
                Pair("image", imageUrl),
            )
        )

    //create listings
    fun createListing(
        name: String,
        description: String,
        prices: List<Int>,
        sizes: List<String>,
        media: List<String>,
        availability: String,
        shopId: String,
        meta: List<Pair<String, Any?>>,
        available: Boolean = true,
    ) =
        NetworkCalls.post<LoginResponse>(
            endpoint = Endpoints.CREATE_LISTING, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("price", prices),
                Pair("media", media),
                Pair("sizes", sizes),
                Pair("availablity", availability),
                Pair("available", available),
                Pair("shopId", shopId),
                Pair("meta", meta),
            )
        )

    fun uploadImage(image: File, path: String, fileName: String) =
        NetworkCalls.uploadFile(
            endpoint = Endpoints.UPLOAD_IMAGE(path, fileName), media = image
        )

    fun getMedias(context: Context): List<MediaModel> {
        val files = mutableListOf<MediaModel>()
        val projections = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
        )

// This will Return only video and image metadata.
        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)

        val queryUri = MediaStore.Files.getContentUri("external")
        val cursorLoader = CursorLoader(
            context,
            queryUri,
            projections,
            selection,
            null,  // Selection args (none).
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        ).loadInBackground()
//        Timber.d("cursor: ${cursorLoader?.count}")
        viewModelScope.launch {
            if (cursorLoader != null) {
                //clear all previous data
//                files.clearAll()

                val media = cursorLoader.getColumnIndex(MediaStore.Files.FileColumns._ID)
                val mediaDate = cursorLoader.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED)
                val mediaType = cursorLoader.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)
                val mediaMime = cursorLoader.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
                val mediaName = cursorLoader.getColumnIndex(MediaStore.Files.FileColumns.TITLE)
                while (cursorLoader.moveToNext()) {
                    if (cursorLoader.getInt(mediaType) == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                        files.add(
                            MediaModel(
                                uri = Uri.withAppendedPath(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    cursorLoader.getString(media)
                                ).toString(), dateAdded = cursorLoader.getLong(mediaDate),
                                isVideo = false,
                                mediaMimeType = cursorLoader.getString(mediaMime),
                                name = cursorLoader.getString(mediaName)
                            )

                        )
                    } else if (cursorLoader.getInt(mediaType) == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                        files.add(
                            MediaModel(
                                uri = Uri.withAppendedPath(
                                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                    cursorLoader.getString(media)
                                ).toString(), dateAdded = cursorLoader.getLong(mediaDate),
                                isVideo = true,
                                mediaMimeType = cursorLoader.getString(mediaMime),
                                name = cursorLoader.getString(mediaName)
                            )
                        )
                    }
                }
//                repo.insertMediaList(gelImedia)
//                Timber.d("gelImedia: $files")
            } else {
                Timber.d("cursorLoader is null: $cursorLoader")
            }

        }
        return files
    }

    private fun getMyShop() = NetworkCalls.get<ShopModel>(
        endpoint = Endpoints.CREATE_SHOP,
        body = listOf()
    ).addOnSuccessListener {
        _shop.value = it
    }

    init {
        getUser()
        getMyShop()
    }
}

data class MediaModel(
    val uri: String,
    val dateAdded: Long,
    val isVideo: Boolean,
    val mediaMimeType: String,
    val name: String
)