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
//         * Migration from version 1 to version 2
//         * Added new Table [Transaction]
//         */
//        AutoMigration (from = 1, to = 2),
//    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun listingDao(): ListingDao
}