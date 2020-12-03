package com.example.interviewtest.repository

import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.db.PostEntity

class FakeDBRepo : DBRepository {

    private val posts = mutableListOf<PostEntity>()
    private val favorites = mutableListOf<FavoriteEntity>()


    override suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>) {
        posts.addAll(postEntity)
    }

    override suspend fun getAllPostDataFromPostEntity(): List<PostEntity> = posts


    override suspend fun getPostDataFromPostEntity(id: Int): PostEntity =
        posts.find { it.id == id }!!


    override suspend fun addPostDataToFavoriteEntity(favoriteEntity: FavoriteEntity) {
        favorites.add(favoriteEntity)
    }

    override suspend fun checkTheFavoritePostAlreadyExist(id: Int): FavoriteEntity =
        favorites.find { it.id == id }!!

    override suspend fun getAllFavoriteDataFromFavoriteEntity(): List<FavoriteEntity> = favorites
}