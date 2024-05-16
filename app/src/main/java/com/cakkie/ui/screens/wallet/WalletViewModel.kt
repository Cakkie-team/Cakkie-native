package com.cakkie.ui.screens.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Balance
import com.cakkie.networkModels.CurrencyRate
import com.cakkie.networkModels.DepositResponse
import com.cakkie.networkModels.LoginResponse
import com.cakkie.networkModels.Transaction
import com.cakkie.networkModels.TransactionResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WalletViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _balance = MutableLiveData<List<Balance>>()
    private val _transaction = MutableLiveData<TransactionResponse>()
    private val _user = MutableLiveData<User>()
    private val _referrals = MutableLiveData<List<User>>()

    val user = _user
    val balance = _balance
    val transaction = _transaction
    val referrals = _referrals

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

    fun resetPin(pin: String, confirmPin: String, otp: String) = NetworkCalls.post<LoginResponse>(
        endpoint = Endpoints.RESET_PIN,
        body = listOf(
            Pair("pin", pin),
            Pair("pinConfirmation", confirmPin),
            Pair("otp", otp)
        )
    )

    //resend otp
    fun resendOtp(email: String) =
        NetworkCalls.post<LoginResponse>(
            endpoint = Endpoints.RESEND_OTP(email), body = listOf()
        )

    fun getTransactions(currencyId: String? = null, page: Int = 0, pageSize: Int = 30) {
        NetworkCalls.get<TransactionResponse>(
            endpoint = Endpoints.GET_TRANSACTION(currencyId, page, pageSize),
            listOf()
        ).addOnSuccessListener {
            _transaction.value = it
        }
    }

    fun getAllTransactions(page: Int = 0, pageSize: Int = 30) {
        NetworkCalls.get<TransactionResponse>(
            endpoint = Endpoints.GET_ALL_TRANSACTION(page, pageSize),
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
            userRepository.createUser(it)
        }
    }

    fun getConversionRate(symbol: String, amount: Double) =
        NetworkCalls.getObjectList<CurrencyRate>(
            endpoint = Endpoints.GET_CONVERSION_RATE(symbol, amount)
        )

    fun verifyPin(pin: String) = NetworkCalls.post<LoginResponse>(
        endpoint = Endpoints.VERIFY_PIN(pin),
        body = listOf()
    )

    fun mine() = NetworkCalls.get<Transaction>(
        endpoint = Endpoints.MINE,
        body = listOf()
    )

    fun mine(title: String) = NetworkCalls.get<Transaction>(
        endpoint = Endpoints.GET_REWARD(title),
        body = listOf()
    )

    private fun getReferrals() {
        NetworkCalls.getObjectList<User>(
            endpoint = Endpoints.GET_REFERRALS
        ).addOnSuccessListener {
            _referrals.value = it
        }
    }

    init {
        getProfile()
        getUser()
        getReferrals()
    }

}