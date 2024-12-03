package com.example.alertifyapp.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Get the instance of the database, with an option for destructive migration or version handling
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                    // Option 1: Allow destructive migration if the version changes or migration path is missing
                    .fallbackToDestructiveMigration()  // This will reset the database if schema version changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
