package com.cakkie.ui.screens.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cakkie.data.db.models.User
import com.cakkie.data.repositories.UserRepository
import com.cakkie.networkModels.Order
import com.cakkie.networkModels.OrderResponse
import com.cakkie.utill.Endpoints
import com.cakkie.utill.NetworkCalls
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrderViewModel : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()
    private val _user = MutableLiveData<User>()
    private val _orders = MutableLiveData<OrderResponse>()

    val orders = _orders
    val user = _user

    fun getMyOrders(page: Int = 0, size: Int = 20, status: String?) =
        NetworkCalls.get<OrderResponse>(
            endpoint = Endpoints.GET_ORDERS(status, page, size),
            body = listOf()
        ).addOnSuccessListener {
            _orders.value = it
        }

    fun getOrder(id: String) = NetworkCalls.get<Order>(
        endpoint = Endpoints.GET_ORDER(id),
        body = listOf()
    )

    fun cancelOrder(id: String, reason: String) = NetworkCalls.put<Order>(
        endpoint = Endpoints.CANCEL_ORDER(id),
        body = listOf(
            Pair("reason", reason)
        )
    )

    fun declineOrder(id: String, reason: String) = NetworkCalls.put<Order>(
        endpoint = Endpoints.DECLINE_ORDER(id),
        body = listOf(
            Pair("reason", reason)
        )
    )

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().asLiveData().observeForever {
                _user.value = it
            }
        }
    }

    init {
        getUser()
    }
}
