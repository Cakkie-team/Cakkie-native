package com.cakkie.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.cakkie.data.db.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //   get user
    @Query("SELECT * FROM user")
    fun getUser(): Flow<User>

    // create a User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(chain: User)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun createUser(chains: List<User>)


    // update a chain
    @Update
    suspend fun updateUser()

    @Upsert
    suspend fun upsertUser()
}