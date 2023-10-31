package com.cakkie.utill

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.serialization.responseObject
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import timber.log.Timber

object NetworkCalls {
    inline fun <reified T : Any> post(
        endpoint: String,
        body: List<Pair<String, Any?>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpPost().body(JsonBody.generate(body))
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        Timber.d("body: ${JsonBody.generate(body)}")
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }

    inline fun <reified T : Any> get(
        endpoint: String,
        body: List<Pair<String, Any?>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpGet().body(JsonBody.generate(body))
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        Timber.d("body: ${JsonBody.generate(body)}")
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }

    inline fun <reified T : Any> put(
        endpoint: String,
        body: List<Pair<String, Any>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpPut().body(JsonBody.generate(body))
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }


    fun getStringList(endpoint: String): NetworkResult<List<String>, FuelError> {
        val networkResult = NetworkResult<List<String>, FuelError>()
        endpoint.httpGet()
            .responseObject(loader = ListSerializer(String.serializer()), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->

                result.fold(
                    {
                        Timber.d("interests: $it")
                        networkResult.onSuccess(it)
                    },
                    {
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }

    @OptIn(InternalSerializationApi::class)
    inline fun <reified T : Any> getObjectList(endpoint: String): NetworkResult<List<T>, FuelError> {
        val networkResult = NetworkResult<List<T>, FuelError>()
        endpoint.httpGet()
            .responseObject(loader = ListSerializer(T::class.serializer()), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->

                result.fold({
                    Timber.d("success: $it")
                    networkResult.onSuccess(it)
                },
                    {
                        Timber.d("error: $it from request: $request")
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }

    @OptIn(InternalSerializationApi::class)
    inline fun <reified T : Any> getObject(endpoint: String): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpGet()
            .responseObject(T::class.serializer(), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold({
                    Timber.d("success: $it")
                    networkResult.onSuccess(it)
                },
                    {
                        Timber.d("error: $it from request: $request")
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }


//    fun upload(
//        context: Context,
//        token: String,
//        media: Uri?,
//        endpoint: String,
//        parameterName: String
//    ) {
//        MultipartUploadRequest(
//            context,
//            serverUrl = endpoint
//        ).apply {
//            setMethod("POST")
//            addHeader("Authorization", "Bearer $token")
//            if (media != null) {
//                addFileToUpload(
//                    filePath = media.toString(),
//                    parameterName = parameterName
//                )
//            }
//        }
//            .setNotificationConfig { _, uploadId ->
//                UploadNotificationConfig(
//                    notificationChannelId = "Cakkie",
//                    isRingToneEnabled = false,
//                    success = UploadNotificationStatusConfig(
//                        title = "Upload Image",
//                        message = "Image upload successful",
//                        iconResourceID = R.drawable.ic_azabox_logo,
//                    ),
//                    error = UploadNotificationStatusConfig(
//                        title = "Upload Image",
//                        message = "Fail to upload image ",
//                        iconResourceID = R.drawable.ic_azabox_logo,
//                    ),
//                    cancelled = UploadNotificationStatusConfig(
//                        title = "Upload Image",
//                        message = "Image upload cancelled",
//                        iconResourceID = R.drawable.ic_azabox_logo,
//                    ),
//                    progress = UploadNotificationStatusConfig(
//                        title = "Upload Image",
//                        message = "Image upload in progress",
//                        iconResourceID = R.drawable.ic_azabox_logo,
//                    )
//                )
//            }
//
//            .startUpload()
//    }
}