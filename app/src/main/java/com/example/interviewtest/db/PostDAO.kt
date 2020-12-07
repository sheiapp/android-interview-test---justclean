package com.example.interviewtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>)

    @Query("SELECT * FROM post_table WHERE isFavorite = :isFavorite")
    suspend fun getAllPostDataFromPostEntity(isFavorite: Boolean = false): List<PostEntity>

    @Query("SELECT * FROM post_table WHERE id = :id AND isFavorite = :isFavorite")
    suspend fun getPostDataFromPostEntity(id: Int, isFavorite: Boolean = false): PostEntity

    @Query("SELECT * FROM post_table WHERE id = :id AND isFavorite = :isFavorite")
    suspend fun checkThePostAlreadyExist(id: Int, isFavorite: Boolean = true): PostEntity

    @Query("SELECT * FROM post_table WHERE isFavorite = :isFavorite")
    suspend fun getAllFavoriteDataFromFavoriteEntity(isFavorite: Boolean = true): List<PostEntity>

    @Query("DELETE  FROM post_table WHERE isFavorite = :isFavorite")
    suspend fun deleteAllPostWhichIsNotFavorite(isFavorite: Boolean = false)
}