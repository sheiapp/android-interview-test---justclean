package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.utils.Resource
import retrofit2.Response

interface MainRepository {
    suspend fun getPosts():  List<PostEntity>?

    suspend fun getComments(post_id: Int): List<CommentsResponseItem>?

    fun getNetworkConnectionStatus():LiveData<Boolean>
}