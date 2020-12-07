package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.utils.extensions.Resource

interface MainRepository {
    suspend fun getPosts(): Resource<List<PostEntity>>
    suspend fun getComments(post_id: Int): Resource<List<CommentsResponseItem>>
    fun getNetworkConnectionStatus(): LiveData<Boolean>
    //DB
    suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>)
    suspend fun getAllPostDataFromPostEntity(): List<PostEntity>
    suspend fun getPostDataFromPostEntity(id: Int): PostEntity
    suspend fun deleteAllPostWhichIsNotFavorite()
    suspend fun checkTheFavoritePostAlreadyExist(id: Int): PostEntity
    suspend fun getAllFavoriteDataFromFavoriteEntity(): List<PostEntity>
}