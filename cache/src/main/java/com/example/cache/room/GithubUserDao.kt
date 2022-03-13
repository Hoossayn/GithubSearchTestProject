package com.example.cache.room

import androidx.room.*
import com.example.cache.models.GithubUsersCacheModel
import com.example.cache.models.GithubUsersCacheModel.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow


@Dao
interface GithubUserDao {

    @Query("select * from $TABLE_NAME")
    fun getUsersFromCache(): Flow<List<GithubUsersCacheModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteUser(user: GithubUsersCacheModel)

    @Delete
    fun removeUserFromFavorite(user: GithubUsersCacheModel)

    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE id = :id)")
    fun checkIfUserIsFavorite(id: Int): Boolean


}