package com.cakkie.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cakkie.data.db.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //   get user
    @Query("SELECT * FROM user")
    fun getUser(): Flow<User>

    // create a User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun createUser(chains: List<User>)


    // update a chain
    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun upsertUser(user: User)
}