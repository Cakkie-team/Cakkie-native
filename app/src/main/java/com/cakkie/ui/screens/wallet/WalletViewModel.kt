package com.cakkie.ui.screens.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Balance
import com.cakkie.networkModels.DepositResponse
import com.cakkie.networkModels.Transaction
import com.cakkie.networkModels.TransactionResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class WalletViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _balance = MutableLiveData<List<Balance>>()
    private val _transaction = MutableLiveData<TransactionResponse>()
    private val _user = MutableLiveData<User>()

    val user = _user
    val balance = _balance
    val transaction = _transaction

    fun getBalance() {
        NetworkCalls.getObjectList<Balance>(
            endpoint = Endpoints.GET_BALANCE
        ).addOnSuccessListener {
            _balance.value = it
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    fun deposit(amount: Double) = NetworkCalls.post<DepositResponse>(
        endpoint = Endpoints.DEPOSIT,
        body = listOf(
            Pair("amount", amount)
        )
    )

    fun getTransactions(currencyId: String? = null, page: Int = 0, pageSize: Int = 10) {
        Timber.d("getTransactions: currencyId: $currencyId, page: $page, pageSize: $pageSize")
        NetworkCalls.get<TransactionResponse>(
            endpoint = Endpoints.GET_TRANSACTION(currencyId, page, pageSize),
            listOf()
        ).addOnSuccessListener {
            _transaction.value = it
        }
    }

    fun getProfile() = NetworkCalls.get<User>(
        endpoint = Endpoints.ACCOUNT,
        body = listOf()
    ).addOnSuccessListener {
        viewModelScope.launch {
            userRepository.updateUser(it)
        }
    }

    fun mine() = NetworkCalls.get<Transaction>(
        endpoint = Endpoints.MINE,
        body = listOf()
    )

    init {
        getProfile()
        getUser()
    }

}