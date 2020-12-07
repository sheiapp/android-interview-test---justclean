package com.example.interviewtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.interviewtest.db.PostEntity
import com.example.interviewtest.model.CommentsResponseItem
import com.example.interviewtest.utils.extensions.Resource

class FakeMainRepoTest : MainRepository {

    private val favorites = mutableListOf<PostEntity>()
    private val networkConnectionStatus = MutableLiveData(true)
    private val posts = mutableListOf<PostEntity>()
    private val comments = mutableListOf<CommentsResponseItem>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    init {
        posts.add(PostEntity(1, 1, 1, "body1", "title1"))
        posts.add(PostEntity(2, 2, 2, "body2", "title2"))
        posts.add(PostEntity(3, 3, 3, "body3", "title3"))
        posts.add(PostEntity(4, 4, 4, "body4", "title4"))

        comments.add(CommentsResponseItem("body1", "email1", 1, "name1", 1))
        comments.add(CommentsResponseItem("body2", "email2", 2, "name2", 2))
        comments.add(CommentsResponseItem("body3", "email3", 3, "name3", 3))
        comments.add(CommentsResponseItem("body4", "email4", 4, "name4", 4))

    }

    override suspend fun getPosts(): Resource<List<PostEntity>> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(posts)
        }
    }

    override suspend fun getComments(post_id: Int): Resource<List<CommentsResponseItem>> {
        val comment = mutableListOf<CommentsResponseItem>()
        comment.add(comments.find { it.postId == post_id }!!)
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(comment)
        }
    }


    override fun getNetworkConnectionStatus(): LiveData<Boolean> {
        return networkConnectionStatus
    }

    override suspend fun addALLPostDataToPostEntity(postEntity: List<PostEntity>) {
        posts.addAll(postEntity)
    }

    override suspend fun getAllPostDataFromPostEntity(): List<PostEntity> = posts


    override suspend fun getPostDataFromPostEntity(id: Int): PostEntity =
        posts.find { it.id == id }!!

    override suspend fun deleteAllPostWhichIsNotFavorite() {
        posts.remove(posts.find { !it.isFavorite })
    }


    override suspend fun checkTheFavoritePostAlreadyExist(id: Int): PostEntity =
        favorites.find { it.id == id }!!

    override suspend fun getAllFavoriteDataFromFavoriteEntity(): List<PostEntity> = favorites
}