package com.example.interviewtest.repository

import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.db.PostEntity

interface DBRepository {
    suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>)
    suspend fun getAllPostDataFromPostEntity(): List<PostEntity>
    suspend fun getPostDataFromPostEntity(id: Int): PostEntity
    suspend fun addPostDataToFavoriteEntity(favoriteEntity: FavoriteEntity)
    suspend fun checkTheFavoritePostAlreadyExist(id: Int): FavoriteEntity
    suspend fun getAllFavoriteDataFromFavoriteEntity(): List<FavoriteEntity>
}