package com.cakkie.utill

import android.content.Context
import android.net.Uri
import com.cakkie.R
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.serialization.responseObject
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import net.gotev.uploadservice.data.UploadNotificationConfig
import net.gotev.uploadservice.data.UploadNotificationStatusConfig
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import timber.log.Timber

object NetworkCalls {
    inline fun <reified T : Any> post(
        endpoint: String,
        body: List<Pair<String, Any?>>
    ): NetworkResult<T, String> {
        val networkResult = NetworkResult<T, String>()
        val jsonBody = JsonBody.generate(body)
        endpoint.httpPost().body(jsonBody)
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                response.let {
                    Timber.d("request: $request")
                    Timber.d("response: $response")
                }
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        Timber.d("body: $jsonBody")
                        response.let {
                            //get data from response
                            val data = response.data.toString(Charsets.UTF_8)
                            Timber.d("data: $data")
                            //convert data to json object
                            val json = Json.parseToJsonElement(data)
                            //get message from json object
                            val message = json.jsonObject["message"].toString()
                            networkResult.onFailure(message.replace("\"", ""))
                        }
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


    fun upload(
        context: Context,
        token: String,
        media: Uri?,
        endpoint: String,
        parameterName: String
    ) {
        MultipartUploadRequest(
            context,
            serverUrl = endpoint
        ).apply {
            setMethod("POST")
            addHeader("Authorization", "Bearer $token")
            if (media != null) {
                addFileToUpload(
                    filePath = media.toString(),
                    parameterName = parameterName
                )
            }
        }
            .setNotificationConfig { _, uploadId ->
                UploadNotificationConfig(
                    notificationChannelId = "Cakkie",
                    isRingToneEnabled = false,
                    success = UploadNotificationStatusConfig(
                        title = "Upload Image",
                        message = "Image upload successful",
                        iconResourceID = R.drawable.logo,
                    ),
                    error = UploadNotificationStatusConfig(
                        title = "Upload Image",
                        message = "Fail to upload image ",
                        iconResourceID = R.drawable.logo,
                    ),
                    cancelled = UploadNotificationStatusConfig(
                        title = "Upload Image",
                        message = "Image upload cancelled",
                        iconResourceID = R.drawable.logo,
                    ),
                    progress = UploadNotificationStatusConfig(
                        title = "Upload Image",
                        message = "Image upload in progress",
                        iconResourceID = R.drawable.logo,
                    )
                )
            }

            .startUpload()
    }
}