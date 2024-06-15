package com.cakkie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cakkie.data.db.daos.ListingDao
import com.cakkie.data.db.daos.UserDao
import com.cakkie.data.db.models.Listing
import com.cakkie.data.db.models.User
import com.cakkie.data.db.models.typeConverters.Converters


@Database(
    entities = [
        User::class,
        Listing::class
    ],
//    autoMigrations = [
//        /**
//         * Migration from version 8 to version 10
//         *
//         */
//        AutoMigration (from = 9, to = 10)
//    ],
    version = 10, // Updated version to 10
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun listingDao(): ListingDao
}