package com.cakkie.ui.screens.shop

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.loader.content.CursorLoader
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.ListingResponse
import com.cakkie.data.db.models.ShopModel
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Order
import com.cakkie.networkModels.OrderResponse
import com.cakkie.networkModels.ShopResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.JsonBody
import com.cakkie.utill.NetworkCalls
import com.cakkie.utill.locationModels.LocationResult
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.io.File

class ShopViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _shop = MutableLiveData<ShopModel>()
    private val _listings = MutableLiveData<ListingResponse>()
    private val _orders = MutableLiveData<OrderResponse>()

    val user = _user
    val shop = _shop
    val listings = _listings
    val orders = _orders

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
        location: LocationResult,
    ) =
        NetworkCalls.post<ShopModel>(
            endpoint = Endpoints.CREATE_SHOP, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("address", address),
                Pair(
                    "city",
                    location.addressComponents.firstOrNull { it.types.contains("locality") }?.longName
                        ?: ""
                ),
                Pair(
                    "state",
                    location.addressComponents.firstOrNull { it.types.contains("administrative_area_level_1") }?.longName
                        ?: ""
                ),
                Pair("latitude", location.geometry?.location?.lat ?: 0.0),
                Pair("longitude", location.geometry?.location?.lng ?: 0.0),
                Pair(
                    "country",
                    location.addressComponents.firstOrNull { it.types.contains("country") }?.longName
                        ?: ""
                ),
                Pair("image", imageUrl),
            )
        )


    //update shop
    fun updateShop(
        name: String,
        description: String,
        address: String,
        imageUrl: String,
        location: LocationResult,
    ) =
        NetworkCalls.put<ShopModel>(
            endpoint = Endpoints.UPDATE_SHOP, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("address", address),
                Pair(
                    "city",
                    location.addressComponents.firstOrNull { it.types.contains("locality") }?.longName
                        ?: ""
                ),
                Pair(
                    "state",
                    location.addressComponents.firstOrNull { it.types.contains("administrative_area_level_1") }?.longName
                        ?: ""
                ),
                Pair("latitude", location.geometry?.location?.lat ?: 0.0),
                Pair("longitude", location.geometry?.location?.lng ?: 0.0),
                Pair(
                    "country",
                    location.addressComponents.firstOrNull { it.types.contains("country") }?.longName
                        ?: ""
                ),
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
        isListing: Boolean = true
    ) =
        NetworkCalls.post<Listing>(
            endpoint = Endpoints.CREATE_LISTING, body = listOf(
                Pair("name", name),
                Pair("description", description),
                Pair("price", prices),
                Pair("media", media),
                Pair("sizes", sizes),
                Pair("availablity", availability),
                Pair("available", available),
                Pair("shopId", shopId),
                Pair("meta", JsonBody.generate(meta)),
                Pair("type", if (isListing) "LISTING" else "CAKESPIRATION")
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

    fun getMyShop() = NetworkCalls.get<ShopModel>(
        endpoint = Endpoints.CREATE_SHOP,
        body = listOf()
    ).addOnSuccessListener {
        _shop.value = it
    }

    fun verifyShopName(name: String) = NetworkCalls.get<ShopResponse>(
        endpoint = Endpoints.VERIFY_SHOP_NAME(name),
        body = listOf()
    )

    fun getMyListings(page: Int = 0, size: Int = 20) = NetworkCalls.get<ListingResponse>(
        endpoint = Endpoints.GET_MY_LISTINGS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _listings.value = it
    }

    fun getRequests(page: Int = 0, size: Int = 20) = NetworkCalls.get<OrderResponse>(
        endpoint = Endpoints.GET_REQUESTS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _orders.value = it
    }

    fun getContracts(page: Int = 0, size: Int = 20) = NetworkCalls.get<OrderResponse>(
        endpoint = Endpoints.GET_CONTRACTS(page, size),
        body = listOf()
    ).addOnSuccessListener {
        _orders.value = it
    }

    fun getListing(id: String) = NetworkCalls.get<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf()
    )

    fun getOrder(id: String) = NetworkCalls.get<Order>(
        endpoint = Endpoints.GET_ORDER(id),
        body = listOf()
    )

    fun setAvailability(id: String, available: Boolean) = NetworkCalls.put<Listing>(
        endpoint = Endpoints.GET_LISTING(id),
        body = listOf(Pair("available", available))
    )

    fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.createUser(it)
        }
    }

    init {
        getProfile()
        getUser()
        getMyShop()
//        getMyListings()
    }
}

data class MediaModel(
    val uri: String,
    val dateAdded: Long,
    val isVideo: Boolean,
    val mediaMimeType: String,
    val name: String
)