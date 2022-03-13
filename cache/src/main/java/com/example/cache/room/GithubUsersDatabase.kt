package com.example.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cache.BuildConfig
import com.example.cache.models.GithubUsersCacheModel

@Database(
    entities = [
        GithubUsersCacheModel::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUsersDatabase : RoomDatabase() {

    abstract fun userDao() : GithubUserDao

    companion object {
        private const val DATABASE_NAME = "users_db"

        @Volatile
        private var INSTANCE: GithubUsersDatabase? = null

        fun getInstance(context: Context): GithubUsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                GithubUsersDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}