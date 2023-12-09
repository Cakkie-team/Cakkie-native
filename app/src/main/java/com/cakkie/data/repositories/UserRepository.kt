package com.cakkie.data.repositories

import com.cakkie.data.db.daos.UserDao
import com.cakkie.data.db.models.User

class UserRepository(private val userDao: UserDao) {
    suspend fun createUser(user: User) = userDao.createUser(user)
    fun getUser() = userDao.getUser()

    //    fun getChain(chainId: Int) = chainDao.getChain(chainId)
    suspend fun updateUser() = userDao.updateUser()

    //delete a user
    suspend fun deleteUser() = userDao.upsertUser()
}