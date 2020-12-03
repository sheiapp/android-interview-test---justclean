package com.example.interviewtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>)

    @Query("SELECT * FROM post_table")
    suspend fun getAllPostDataFromPostEntity(): List<PostEntity>

    @Query("SELECT * FROM post_table WHERE id = :id")
    suspend fun getPostDataFromPostEntity(id: Int): PostEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostDataToFavoriteEntity(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    suspend fun checkThePostAlreadyExist(id: Int): FavoriteEntity

    @Query("SELECT * FROM favorite_table")
    suspend fun getAllFavoriteDataFromFavoriteEntity(): List<FavoriteEntity>
}