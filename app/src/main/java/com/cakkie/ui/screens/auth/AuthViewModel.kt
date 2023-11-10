package com.cakkie.ui.screens.auth

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cakkie.datastore.Settings
import com.cakkie.models.LoginResponse
import com.cakkie.models.User
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AuthViewModel(private val settings: Settings) : ViewModel(), KoinComponent {
    private val deviceName = Build.MODEL
    private val deviceID = Build.ID
    private val os = "Android"


    fun checkEmail(email: String) =
        NetworkCalls.get<User>(endpoint = Endpoints.CHECK_EMAIL(email), body = listOf())

    //login
    fun login(email: String, password: String) =
        NetworkCalls.post<LoginResponse>(
            endpoint = Endpoints.LOGIN, body = listOf(
                Pair("email", email),
                Pair("password", password),
                Pair("deviceName", deviceName),
                Pair("deviceToken", deviceID),
                Pair("os", os)
            )
        ).addOnSuccessListener {
            if (it.token.isNotEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
//                    settings.putPreference(SettingsConstants.TOKEN, it.token)
                }
            }
        }
}