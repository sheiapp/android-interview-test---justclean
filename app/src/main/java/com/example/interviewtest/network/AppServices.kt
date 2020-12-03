package com.example.interviewtest.network

import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppServices {
    @GET("/posts")
    suspend fun getPost(): Response<List<PostEntity>>

    @GET(" /posts/{post_id}/comments")
    suspend fun getComments(@Path("post_id") postId: Int): Response<List<CommentsResponseItem>>
}