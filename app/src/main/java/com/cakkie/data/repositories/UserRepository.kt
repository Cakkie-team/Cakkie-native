package com.cakkie.data.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.cakkie.data.db.daos.UserDao
import com.cakkie.data.db.models.User

class UserRepository(private val userDao: UserDao) {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User) = userDao.createUser(user)
    fun getUser() = userDao.getUser()

    //    fun getChain(chainId: Int) = chainDao.getChain(chainId)
    suspend fun updateUser(user: User) = userDao.updateUser(user)

    //delete a user
    suspend fun deleteUser(user: User) = userDao.upsertUser(user)

    suspend fun clear() = userDao.deleteAll()
}