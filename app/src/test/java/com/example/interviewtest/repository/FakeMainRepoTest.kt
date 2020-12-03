package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem

class FakeMainRepoTest : MainRepository {
    private val postsMutableData = MutableLiveData<List<PostEntity>>()
    private val commentsMutableData = MutableLiveData<List<CommentsResponseItem>>()
    private val networkConnectionStatus = MutableLiveData<Boolean>(true)
    private val posts = mutableListOf<PostEntity>()
    private val comments = mutableListOf<CommentsResponseItem>()

    fun setNetworkConnectionStatus(value: Boolean) {
        networkConnectionStatus.value = value
    }

    private fun refreshLiveData() {
        postsMutableData.postValue(posts)
        commentsMutableData.postValue(comments)
    }


    init {
            posts.add(PostEntity(1, 1, "body1", "title1"))
            posts.add(PostEntity(2, 2, "body2", "title2"))
            posts.add(PostEntity(3, 3, "body3", "title3"))
            posts.add(PostEntity(4, 4, "body4", "title4"))

            comments.add(CommentsResponseItem("body1", "email1", 1, "name1", 1))
            comments.add(CommentsResponseItem("body2", "email2", 2, "name2", 2))
            comments.add(CommentsResponseItem("body3", "email3", 3, "name3", 3))
            comments.add(CommentsResponseItem("body4", "email4", 4, "name4", 4))
            refreshLiveData()

    }

    override suspend fun getPosts(): List<PostEntity> {
        return posts
    }

    override suspend fun getComments(post_id: Int): List<CommentsResponseItem> {
        val comment=mutableListOf<CommentsResponseItem>()
        comment.add(comments.find { it.postId == post_id }!!)
        return comment
    }


    override fun getNetworkConnectionStatus(): LiveData<Boolean> {
        return networkConnectionStatus
    }
}