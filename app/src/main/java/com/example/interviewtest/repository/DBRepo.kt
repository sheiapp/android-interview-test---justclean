package com.example.interviewtest.repository

import com.example.interviewtest.db.FavoriteEntity
import com.example.interviewtest.db.PostDataBase
import com.example.interviewtest.db.PostEntity
import javax.inject.Inject

class DBRepo @Inject constructor(private val postDataBase: PostDataBase) : DBRepository {

    override suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>) =
        postDataBase.getPostDao().addALLPostDataToPostEntity(postEntity)

    override suspend fun getAllPostDataFromPostEntity() =
        postDataBase.getPostDao().getAllPostDataFromPostEntity()

    override suspend fun getPostDataFromPostEntity(id: Int) =
        postDataBase.getPostDao().getPostDataFromPostEntity(id)

    override suspend fun addPostDataToFavoriteEntity(favoriteEntity: FavoriteEntity) =
        postDataBase.getPostDao().addPostDataToFavoriteEntity(favoriteEntity)

    override suspend fun checkTheFavoritePostAlreadyExist(id: Int) =
        postDataBase.getPostDao().checkThePostAlreadyExist(id)

    override suspend fun getAllFavoriteDataFromFavoriteEntity() =
        postDataBase.getPostDao().getAllFavoriteDataFromFavoriteEntity()
}