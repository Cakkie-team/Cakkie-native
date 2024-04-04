package com.cakkie.ui.screens.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cakkie.networkModels.Balance
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import org.koin.core.component.KoinComponent

class WalletViewModel : ViewModel(), KoinComponent {
    private val _balance = MutableLiveData<List<Balance>>()

    val balance = _balance

    fun getBalance() {
        NetworkCalls.getObjectList<Balance>(
            endpoint = Endpoints.GET_BALANCE
        ).addOnSuccessListener {
            _balance.value = it
        }
    }


}