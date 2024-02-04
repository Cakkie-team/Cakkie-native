package com.cakkie.socket

import com.cakkie.utill.Endpoints
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import timber.log.Timber

class SocketClient {
    private var _token = ""

    //        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiYTQzNWU5NDQtMjc5OS00MDAzLWFiYTctYTQxZjY2MjAzYTc2IiwibmFtZSI6IkNha2tpZSBTdXBwb3J0IiwiZmlyc3ROYW1lIjoiQ2Fra2llIiwibGFzdE5hbWUiOiJTdXBwb3J0IiwiZW1haWwiOiJzdXBwb3J0QGNha2tpZS5jb20iLCJwaW4iOm51bGwsInByb2ZpbGVJbWFnZSI6Imh0dHA6Ly9hcGkuY2Fra2llLmNvbS9tZWRpYS9hdmF0YXIvZGVmYXVsdC5wbmciLCJjb3ZlckltYWdlIjpbImh0dHA6Ly9hcGkuY2Fra2llLmNvbS9tZWRpYS9hdmF0YXIvY292ZXIucG5nIl0sInVzZXJuYW1lIjoic3VwcG9ydEBjYWtraWUiLCJnZW5kZXIiOiJNYWxlIiwicGhvbmVOdW1iZXIiOm51bGwsImJpbyI6bnVsbCwiZGF0ZU9mQmlydGgiOm51bGwsImFkZHJlc3MiOiJBa3BhbiBOc2VrIFN0LCBVeW8gNTIwMTAxLCBBa3dhIElib20sIE5pZ2VyaWEiLCJjaXR5IjoiVXlvIiwic3RhdGUiOiJBa3dhIElib20iLCJjb3VudHJ5IjoiTmlnZXJpYSIsImxhdGl0dWRlIjo1LjAyNTU1MjYsImxvbmdpdHVkZSI6Ny45NzcxOTgxOTk5OTk5OTksInVzZXJUeXBlIjoiVXNlciIsImlzRHJpdmVyIjpmYWxzZSwiaGFzU2hvcCI6ZmFsc2UsInJlZmVycmFsQ29kZSI6InpnbzUyNXR2IiwicmVmZXJyZXJJZCI6bnVsbCwib3RwQ291bnRlciI6NCwid2Vic29ja2V0SWQiOm51bGwsImVtYWlsVmVyaWZpZWRBdCI6IjIwMjQtMDEtMTFUMjE6MTU6MTkuNzY1WiIsInBob25lTnVtYmVyVmVyaWZpZWRBdCI6bnVsbCwiY3JlYXRlZEF0IjoiMjAyNC0wMS0xMVQyMDoyMDozNC43MzBaIiwidXBkYXRlZEF0IjoiMjAyNC0wMS0xMVQyMToxNToxOS43NjhaIn0sInVzZXJJZCI6ImE0MzVlOTQ0LTI3OTktNDAwMy1hYmE3LWE0MWY2NjIwM2E3NiIsImlhdCI6MTcwNTMyMTk5MX0._ZZjjC9XdzGHGPO6sjWTX31_XrIN1CwgfjN2yViXpDg"
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer $_token")
                .build()
            it.proceed(request)
        })
        .build()
    private val options = IO.Options().apply {
        //set auth token
        webSocketFactory = httpClient
        callFactory = httpClient
        transports = arrayOf(WebSocket.NAME)
    }
    val socket: Socket = IO.socket(Endpoints.SOCKET_URL, options)

    fun connect(token: String) {
        _token = token
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            Timber.d("Socket: Connected to server")
//            socket.emit("chat message", "$username joined the chat")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.d("Socket: Connection error ${it[0]}")
        }


        socket.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.d("Socket: Error ${it[0]}")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            Timber.d("Socket: Disconnected from server")
        }
    }

//    fun sendMessage(message: String) {
//        socket.emit("chat message", "$username: $message")
//    }

    fun disconnect() {
        socket.disconnect()
    }

    init {
//        connect()
    }
}