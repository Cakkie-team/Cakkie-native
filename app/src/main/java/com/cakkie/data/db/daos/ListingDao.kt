package com.cakkie.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cakkie.data.db.models.Listing
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {
    //   get listings
    @Query("SELECT * FROM listing")
    fun getListings(): Flow<List<Listing>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListings(chains: List<Listing>)

    //delete old listings and add new ones
    @Transaction
    suspend fun replaceListings(chains: List<Listing>) {
        deleteAll()
        addListings(chains)
    }

    // update a chain
    @Update
    suspend fun updateListing(listing: Listing)

    @Delete
    suspend fun upsertListing(listing: Listing)

    //delete all
    @Query("DELETE FROM listing")
    suspend fun deleteAll()

}