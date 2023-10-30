package com.cakkie.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun EmailScreen() {
    Column(Modifier.fillMaxSize()) {
        Text("Email Screen")
    }
}