package com.cakkie.ui.screens.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cakkie.networkModels.Balance
import com.cakkie.networkModels.DepositResponse
import com.cakkie.networkModels.TransactionResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import org.koin.core.component.KoinComponent

class WalletViewModel : ViewModel(), KoinComponent {
    private val _balance = MutableLiveData<List<Balance>>()
    private val _transaction = MutableLiveData<TransactionResponse>()

    val balance = _balance
    val transaction = _transaction

    fun getBalance() {
        NetworkCalls.getObjectList<Balance>(
            endpoint = Endpoints.GET_BALANCE
        ).addOnSuccessListener {
            _balance.value = it
        }
    }

    fun deposit(amount: Double) = NetworkCalls.post<DepositResponse>(
        endpoint = Endpoints.DEPOSIT,
        body = listOf(
            Pair("amount", amount)
        )
    )

    fun getTransactions(currencyId: String? = null, page: Int = 1, pageSize: Int = 20) {
        NetworkCalls.get<TransactionResponse>(
            endpoint = Endpoints.GET_TRANSACTION(currencyId, page, pageSize),
            listOf()
        ).addOnSuccessListener {
            _transaction.value = it
        }
    }

}