package com.cakkie.ui.screens.auth

import android.os.Build
import androidx.lifecycle.ViewModel
import com.cakkie.datastore.Settings
import com.cakkie.models.User
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import org.koin.core.component.KoinComponent

class AuthViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val deviceName = Build.MODEL
    private val deviceID = Build.ID
    private val os = "Android"


    fun checkEmail(email: String) =
        NetworkCalls.get<User>(endpoint = Endpoints.CHECK_EMAIL(email), body = listOf())

    //login
    fun login(email: String, password: String) =
        NetworkCalls.post<User>(
            endpoint = Endpoints.LOGIN, body = listOf(
                "email" to email,
                "password" to password,
                "deviceName" to deviceName,
                "deviceToken" to deviceID,
                "os" to os
            )
        )
}